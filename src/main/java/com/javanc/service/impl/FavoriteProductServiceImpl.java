package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.client.*;
import com.javanc.model.response.client.FavoriteResponse;
import com.javanc.model.response.client.ProductFavoriteResponse;
import com.javanc.repository.*;
import com.javanc.repository.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteProductServiceImpl implements FavoriteProductService {
    final UserRepository userRepository;
    final  ProductFavoriteRepository productFavoriteRepository;
    final FavoriteRepository favoriteRepository;
    final ProductRepository productRepository;
    final SizeRepository sizeRepository;
    final ImageRepository imageRepository;
    final ColorRepository colorRepository;
    final ProductDiscountRepository productDiscountRepository;


    @Override
    public void createFavorite(List<ProductFavoriteRequest> productFavoriteRequests) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        // create shopping cart
        Optional<FavoriteEntity> favoriteEntity= favoriteRepository.findByUser(user);
        if (favoriteEntity.isPresent()) {
                return;
        }

        FavoriteEntity favorite = FavoriteEntity.builder()
                .user(user)
                .build();
        favorite = favoriteRepository.save(favorite);

        for (ProductFavoriteRequest productFavoriteRequestItemRequest : productFavoriteRequests) {
            ProductEntity productEntity = productRepository.findById(productFavoriteRequestItemRequest.getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ColorEntity colorEntity = colorRepository.findById(productFavoriteRequestItemRequest.getColor().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            SizeEntity sizeEntity = sizeRepository.findById(productFavoriteRequestItemRequest.getSize().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ImageEntity imageEntity = imageRepository.findById(productFavoriteRequestItemRequest.getImage().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );

            ProductFavoriteEntity productFavoriteEntity = ProductFavoriteEntity.builder()
                    .favorite(favorite)
                    .product(productEntity)
                    .color(colorEntity)
                    .size(sizeEntity)
                    .image(imageEntity)
                    .quantity(1L)
                    .stock(productFavoriteRequestItemRequest.getStock())
                    .build();
            productFavoriteRepository.save(productFavoriteEntity);

        }



    }

    @Override
    public void updateFavorite(List<ProductFavoriteRequest> productFavoriteRequests) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        // create shopping cart
        FavoriteEntity favoriteEntity = favoriteRepository.findByUser(user).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        productFavoriteRepository.deleteAllByFavorite(favoriteEntity);

        for (ProductFavoriteRequest productFavoriteRequest : productFavoriteRequests) {
            ProductEntity productEntity = productRepository.findById(productFavoriteRequest.getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ColorEntity colorEntity = colorRepository.findById(productFavoriteRequest.getColor().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            SizeEntity sizeEntity = sizeRepository.findById(productFavoriteRequest.getSize().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ImageEntity imageEntity = imageRepository.findById(productFavoriteRequest.getImage().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ProductFavoriteEntity productFavoriteEntity = ProductFavoriteEntity.builder()
                    .favorite(favoriteEntity)
                    .product(productEntity)
                    .color(colorEntity)
                    .size(sizeEntity)
                    .image(imageEntity)
                    .quantity(1L)
                    .stock(productFavoriteRequest.getStock())
                    .build();
            productFavoriteRepository.save(productFavoriteEntity);
        }

    }

    @Override
    public List<ProductFavoriteResponse> getFavoriteByUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        FavoriteEntity favoriteEntity = favoriteRepository.findByUser(user).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        List<ProductFavoriteResponse> favorite = new ArrayList<>();
        List<ProductFavoriteEntity> favoriteItem = productFavoriteRepository.findAllByFavorite_id(favoriteEntity.getId());
        for (ProductFavoriteEntity Item : favoriteItem) {
            List<ProductDiscountEntity> discountEntities = productDiscountRepository.findAllByProductId(Item.getProduct().getId());
            DiscountEntity discountEntity = null;
            if (discountEntities != null && !discountEntities.isEmpty()) {
                discountEntity = discountEntities.get(0).getDiscount();
            }
            ProductFavoriteResponse item = ProductFavoriteResponse.builder()
                    .id(Item.getProduct().getId())
                    .name(Item.getProduct().getName())
                    .price(Item.getProduct().getPrice())
                    .slug(Item.getProduct().getSlug())
                    .quantity(Item.getQuantity())
                    .stock(Item.getStock())
                    .percent(discountEntity != null ? discountEntity.getPercent() : 0)
                    .image(ImageRequest.builder().id(Item.getImage().getId()).src(Item.getImage().getSrc()).build())
                    .size(SizeRequest.builder().id(Item.getSize().getId()).name(Item.getSize().getName()).build())
                    .color(ColorRequest.builder().id(Item.getColor().getId()).name(Item.getColor().getName()).hex(Item.getColor().getHexCode()).build())
                    .build();
            favorite.add(item);
        }
        return favorite;
    }
}
