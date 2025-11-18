package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.client.ColorRequest;
import com.javanc.model.request.client.ImageRequest;
import com.javanc.model.request.client.ProductCartItemRequest;
import com.javanc.model.request.client.SizeRequest;
import com.javanc.repository.*;
import com.javanc.repository.entity.*;
import com.javanc.service.ShoppingCartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    UserRepository userRepository;
    ProductShoppingCartRepository productShoppingCartRepository;
    ShoppingCartRepository shoppingCartRepository;
    ProductRepository productRepository;
    SizeRepository sizeRepository;
    ImageRepository imageRepository;
    ColorRepository colorRepository;
    ProductDiscountRepository productDiscountRepository;

    @Override
    public void createCart(List<ProductCartItemRequest> productCartItemRequests) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        // create shopping cart
        Optional<ShoppingCartEntity> shoppingCartEntity = shoppingCartRepository.findByUser(user);
        if (shoppingCartEntity.isPresent()) {
            return;
        }

        ShoppingCartEntity shoppingCart = ShoppingCartEntity.builder()
                .user(user)
                .build();
        shoppingCart = shoppingCartRepository.save(shoppingCart);

        for (ProductCartItemRequest productCartItemRequest : productCartItemRequests) {
            ProductEntity productEntity = productRepository.findById(productCartItemRequest.getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ColorEntity colorEntity = colorRepository.findById(productCartItemRequest.getColor().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            SizeEntity sizeEntity = sizeRepository.findById(productCartItemRequest.getSize().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ImageEntity imageEntity = imageRepository.findById(productCartItemRequest.getImage().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );

            ProductShoppingCartEntity productShoppingCartEntity = ProductShoppingCartEntity.builder()
                    .shoppingCart(shoppingCart)
                    .product(productEntity)
                    .color(colorEntity)
                    .size(sizeEntity)
                    .image(imageEntity)
                    .quantity(productCartItemRequest.getQuantity())
                    .stock(productCartItemRequest.getStock())
                    .build();
            productShoppingCartRepository.save(productShoppingCartEntity);
        }
    }

    @Override
    public void updateCart(List<ProductCartItemRequest> productCartItemRequests) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

        // create shopping cart
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByUser(user).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        productShoppingCartRepository.deleteAllByShoppingCart(shoppingCartEntity);

        for (ProductCartItemRequest productCartItemRequest : productCartItemRequests) {
            ProductEntity productEntity = productRepository.findById(productCartItemRequest.getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ColorEntity colorEntity = colorRepository.findById(productCartItemRequest.getColor().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            SizeEntity sizeEntity = sizeRepository.findById(productCartItemRequest.getSize().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ImageEntity imageEntity = imageRepository.findById(productCartItemRequest.getImage().getId()).orElseThrow(
                    () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
            );
            ProductShoppingCartEntity productShoppingCartEntity = ProductShoppingCartEntity.builder()
                    .shoppingCart(shoppingCartEntity)
                    .product(productEntity)
                    .color(colorEntity)
                    .size(sizeEntity)
                    .image(imageEntity)
                    .quantity(productCartItemRequest.getQuantity())
                    .stock(productCartItemRequest.getStock())
                    .build();
            productShoppingCartRepository.save(productShoppingCartEntity);
        }
    }

    @Override
    public List<ProductCartItemRequest> getCartByUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        ShoppingCartEntity shoppingCartEntity = shoppingCartRepository.findByUser(user).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        List<ProductCartItemRequest> cart = new ArrayList<>();
        List<ProductShoppingCartEntity> cartItems = productShoppingCartRepository.findAllByShoppingCart_id(shoppingCartEntity.getId());
        for (ProductShoppingCartEntity cartItem : cartItems) {
            List<ProductDiscountEntity> discountEntities = productDiscountRepository.findAllByProductId(cartItem.getProduct().getId());
            DiscountEntity discountEntity = null;
            if (discountEntities != null && !discountEntities.isEmpty()) {
                discountEntity = discountEntities.get(0).getDiscount();
            }
            ProductCartItemRequest item = ProductCartItemRequest.builder()
                    .id(cartItem.getProduct().getId())
                    .name(cartItem.getProduct().getName())
                    .price(cartItem.getProduct().getPrice())
                    .quantity(cartItem.getQuantity())
                    .stock(cartItem.getStock())
                    .percent(discountEntity != null ? discountEntity.getPercent() : 0)
                    .image(ImageRequest.builder().id(cartItem.getImage().getId()).src(cartItem.getImage().getSrc()).build())
                    .size(SizeRequest.builder().id(cartItem.getSize().getId()).name(cartItem.getSize().getName()).build())
                    .color(ColorRequest.builder().id(cartItem.getColor().getId()).name(cartItem.getColor().getName()).hex(cartItem.getColor().getHexCode()).build())
                    .build();
            cart.add(item);
        }
        return cart;
    }
}
