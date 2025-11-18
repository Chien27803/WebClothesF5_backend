package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.admin.DiscountAdminRequest;
import com.javanc.model.response.admin.DiscountAdminResponse;
import com.javanc.repository.DiscountRepository;
import com.javanc.repository.ProductDiscountRepository;
import com.javanc.repository.ProductRepository;
import com.javanc.repository.entity.DiscountEntity;
import com.javanc.repository.entity.ProductDiscountEntity;
import com.javanc.repository.entity.ProductEntity;
import com.javanc.service.ProductDiscountService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class ProductDiscountServiceImpl implements ProductDiscountService {

    ProductDiscountRepository productDiscountRepository;
    DiscountRepository discountRepository;
    ProductRepository productRepository;

    @Override
    public List<DiscountAdminResponse> findAllByProductId(Long productId) {
        List<DiscountAdminResponse> result = new ArrayList<>();
        List<ProductDiscountEntity> productDiscountEntityList = productDiscountRepository.findAllByProductId(productId);
        for (ProductDiscountEntity productDiscountEntity : productDiscountEntityList) {
            DiscountAdminResponse response = DiscountAdminResponse.builder()
                    .id(productDiscountEntity.getId())
                    .percent(productDiscountEntity.getDiscount().getPercent())
                    .startTime(productDiscountEntity.getStartTime())
                    .endTime(productDiscountEntity.getEndTime())
                    .build();
            result.add(response);
        }
        return result;
    }

    @Override
    public void createProductDisconut(DiscountAdminRequest discountAdminRequest) {
        // create discount
        DiscountEntity discountEntity = DiscountEntity.builder()
                .percent(discountAdminRequest.getPercent())
                .deleted(false)
                .build();
        discountRepository.save(discountEntity);

        ProductEntity productEntity = productRepository.findById(discountAdminRequest.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        // create product_discount
        ProductDiscountEntity productDiscountEntity = ProductDiscountEntity.builder()
                .product(productEntity)
                .discount(discountEntity)
                .startTime(discountAdminRequest.getStart_date())
                .endTime(discountAdminRequest.getEnd_date())
                .build();
        productDiscountRepository.save(productDiscountEntity);
    }

    @Override
    public void deleteProductDiscountByDiscountId(Long id) {
        productDiscountRepository.deleteByDiscount_Id(id);
    }
}
