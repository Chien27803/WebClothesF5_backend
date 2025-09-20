package com.javanc.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;
import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.admin.ProductAdminRequest;
import com.javanc.model.request.common.DetailCommonResquest;
import com.javanc.model.response.admin.DetailResponse;
import com.javanc.model.response.admin.ProductAdminResponse;
import com.javanc.model.response.admin.ProductPaginationResponse;
import com.javanc.model.response.client.*;
import com.javanc.repository.*;
import com.javanc.repository.custom.FilterProductCustom;
import com.javanc.repository.custom.impl.FilterProductUserCustomImpl;
import com.javanc.repository.entity.*;
import com.javanc.service.ProductService;
import com.javanc.service.UploadImageFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import me.xuender.unidecode.Unidecode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    ObjectMapper objectMapper;
    ProductRepository productRepository;
    DetailRepository detailRepository;
    ColorRepository colorRepository;
    SizeRepository sizeRepository;
    CategoryRepository categoryRepository;
    ImageRepository imageRepository;
    UploadImageFileService uploadImageFileService;
    FilterProductCustom filterProductCustom;
    FilterProductUserCustomImpl filterProductUserCustom;
    final Slugify slug = Slugify.builder().build();

    @Override
    public void createProduct(ProductAdminRequest productAdminRequest) throws IOException {
        List<DetailCommonResquest> variants = objectMapper.readValue(
                productAdminRequest.getVariants(),
                new TypeReference<List<DetailCommonResquest>>() {
                });
        // tim tat ca cac color trong bien the
        List<ColorEntity> listColor = colorRepository.findAllByIdIn(variants.stream().map(DetailCommonResquest::getColorId).collect(Collectors.toList()));
         CategoryEntity category = categoryRepository.findById(productAdminRequest.getCategoryId()).orElseThrow(

                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        // create Product
        ProductEntity product = null;
        if(productAdminRequest.getId() == null) {
            product = ProductEntity.builder()
                    .name(productAdminRequest.getName())
                    .slug(productAdminRequest.getSlug())
                    .price(productAdminRequest.getPrice())
                    .quantity(productAdminRequest.getQuantity())
                    .description(productAdminRequest.getDescription())
                    .category(category)
                    .build();
        }else {
            product = productRepository.findById(productAdminRequest.getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
            product.getImages().clear();
            product.getDetails().clear();
            product.setName(productAdminRequest.getName());
            product.setSlug(productAdminRequest.getSlug());
            product.setPrice(productAdminRequest.getPrice());
            product.setQuantity(productAdminRequest.getQuantity());
            product.setDescription(productAdminRequest.getDescription());
            product.setCategory(category);
        }
        productRepository.save(product);

        // create detail
        for (DetailCommonResquest variant : variants) {
            ColorEntity color = colorRepository.findById(variant.getColorId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            SizeEntity size = sizeRepository.findById(variant.getSizeId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );

            DetailEntity detail = DetailEntity.builder()
                    .product(product)
                    .color(color)
                    .size(size)
                    .stock(variant.getStock())
                    .build();
            detailRepository.save(detail);
        }
        // create image
        for (MultipartFile avatar : productAdminRequest.getImages()) {
            String src = uploadImageFileService.uploadImage(avatar);
            for(ColorEntity color : listColor){
                String slugColor = slug.slugify(Unidecode.decode(color.getName()));
                if(src.contains(slugColor)){
                    ImageEntity image = ImageEntity.builder()
                            .src(src)
                            .product(product)
                            .color_id(color.getId())
                            .build();
                    imageRepository.save(image);
                }
            }
        }
    }

    @Override
    public ProductPaginationResponse getProducts(String searchKey, String categoryId, String status, String pageNumber) {
        List<ProductAdminResponse> result = new ArrayList<>();
        // tim dannh sach san pham
        Map<String, Object> listProducts = filterProductCustom.findProductByOption(searchKey, categoryId, status, pageNumber);

        @SuppressWarnings("unchecked")
        List<ProductEntity> productEntities = (List<ProductEntity>) listProducts.get("products");
        Long total = (Long) listProducts.get("totalProducts");
        Long limitProduct = (Long) listProducts.get("limitProduct");

        for (ProductEntity productEntity : productEntities) {
            List<ImageEntity> listImages = imageRepository.findByProduct_Id(productEntity.getId());
            List<String> listSrc = listImages.isEmpty() ? new ArrayList<>() : listImages.stream().map(ImageEntity::getSrc).collect(Collectors.toList());
            ProductAdminResponse productAdminResponse = ProductAdminResponse.builder()
                    .id(productEntity.getId())
                    .name(productEntity.getName())
                    .slug(productEntity.getSlug())
                    .deleted(productEntity.getDeleted())
                    .price(productEntity.getPrice())
                    .quantity(productEntity.getQuantity())
                    .category(productEntity.getCategory())
                    .src(listSrc)
                    .build();
            result.add(productAdminResponse);
        }
        return ProductPaginationResponse.builder()
                .products(result)
                .totalProducts(total)
                .limitProduct(limitProduct)
                .build();
    }

    @Override
    public void deleteProducts(List<Long> ids) {
        List<ProductEntity> listProducts = productRepository.findAllById(ids);
        for(ProductEntity productEntity : listProducts){
            productEntity.setDeleted(!productEntity.getDeleted()    );

        }
        productRepository.saveAll(listProducts);
    }

    @Override
    public ProductAdminResponse getProductById(Long id) {
        List<String> src = new ArrayList<>();
        List<DetailResponse> variants = new ArrayList<>();

        ProductEntity product = productRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        List<DetailEntity> details = detailRepository.findAllByProduct_id(id);
        List<ImageEntity> images = imageRepository.findByProduct_Id(id);
        if(!details.isEmpty()){
            for(DetailEntity detailEntity : details){
                DetailResponse detailResponse = DetailResponse.builder()
                        .colorId(detailEntity.getColor().getId())
                        .sizeId(detailEntity.getSize().getId())
                        .stock(detailEntity.getStock())
                        .build();
                variants.add(detailResponse);
            }
        }

        if(!images.isEmpty()){
            for(ImageEntity imageEntity : images){
                src.add(imageEntity.getSrc());
            }
        }
        return ProductAdminResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .price(product.getPrice())
                .description(product.getDescription())
                .quantity(product.getQuantity())
                .category(product.getCategory())
                .src(src)
                .variants(variants)
                .build();
    }

    @Override
    public List<ProductClientResponse> getProductsForClient(Long categoryId, String searchKey) {
        List<ProductEntity> listProducts = filterProductUserCustom.findProductByOptionForUser(categoryId, searchKey);
        return listProducts.stream().map(this::mapToProductClientResponse).collect(Collectors.toList());
    }

    @Override
    public ProductClientResponse getDetailProduct(String slug) {
        ProductEntity product = productRepository.findBySlug(slug).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        return mapToProductClientResponse(product);
    }

    public ProductClientResponse mapToProductClientResponse(ProductEntity product) {
        return ProductClientResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .slug(product.getSlug())
                .category(CategoryClientResponse.builder()
                        .id(product.getCategory().getId())
                        .name(product.getCategory().getName())
                        .build())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .description(product.getDescription())
                .images(product.getImages().stream().map(image ->
                        ImageClientResponse.builder()
                                .id(image.getId())
                                .src(image.getSrc())
                                .colorId(image.getColor_id())
                                .build()).collect(Collectors.toList()))
                .details(product.getDetails().stream().map(detail ->
                        DetailClientResponse.builder()
                                .size(SizeClientResponse.builder()
                                        .id(detail.getSize().getId())
                                        .name(detail.getSize().getName())
                                        .description(detail.getSize().getDescription())
                                        .build())
                                .color(ColorClientResponse.builder()
                                        .id(detail.getColor().getId())
                                        .name(detail.getColor().getName())
                                        .hexCode(detail.getColor().getHexCode())
                                        .build())
                                .stock(detail.getStock())
                                .soldCount(detail.getSold_count())
                                .build()).collect(Collectors.toList()))
                .feedbacks(product.getFeedbacks().stream().map(feedback ->

                        FeedbackResponse.builder()
                                .id(feedback.getId())
                                .userId(feedback.getUser().getId())
                                .name(feedback.getUser().getName())
                                .description(feedback.getDescription())
                                .rating(feedback.getRating())
                                .time(feedback.getTime())
                                .build()).collect(Collectors.toList()))
                .feedbacks(product.getFeedbacks().stream().map(feedback ->
                        FeedbackResponse.builder()
                                .id(feedback.getId())
                                .userId(feedback.getUser().getId())
                                .name(feedback.getUser().getName())
                                .description(feedback.getDescription())
                                .rating(feedback.getRating())
                                .time(feedback.getTime())
                                .build()).collect(Collectors.toList()))
                .discounts(product.getProductDiscounts().stream().map(discount ->
                        DiscountClientResponse.builder()
                                .id(discount.getId())
                                .percent(discount.getDiscount().getPercent())
                                .endDate(discount.getEndTime())
                                .startDate(discount.getStartTime())
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
