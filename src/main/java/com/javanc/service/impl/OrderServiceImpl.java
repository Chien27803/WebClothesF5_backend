package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.client.*;
import com.javanc.model.response.client.AddressResponse;

import com.javanc.model.response.client.FeedbackResponse;

import com.javanc.model.response.client.OrderResponse;
import com.javanc.model.response.client.PaymentResponse;
import com.javanc.repository.*;
import com.javanc.repository.entity.*;
import com.javanc.service.OrderService;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    EntityManager entityManager;
    OrderRepository orderRepository;
    UserRepository userRepository;
    AddressRepository addressRepository;
    ProductRepository productRepository;
    ImageRepository imageRepository;
    ColorRepository colorRepository;
    SizeRepository sizeRepository;
    ProductOrderRepository productOrderRepository;
    ShoppingCartRepository shoppingCartRepository;
    PaymentRepository paymentRepository;
    ProductShoppingCartRepository productShoppingCartRepository;
    OrderProductRepository orderProductRepository;

    FeedbackRepository feedbackRepository;


    @Override
    public List<OrderResponse> getAll() {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTS));

        List<OrderEntity> orders = orderRepository.findByUser(user);

        List<OrderResponse> result = new ArrayList<>();

        for (OrderEntity order : orders) {
            List<ProductCartItemRequest> cartItem = new ArrayList<>();
            List<OrderProductEntity> orderProducts = orderProductRepository.findAllByOrder_id(order.getId());
            for( OrderProductEntity orderProduct : orderProducts ) {
                ProductCartItemRequest productCartItemRequest = ProductCartItemRequest.builder()
                        .id(orderProduct.getProduct().getId())
                        .name(orderProduct.getProduct().getName())
                        .image(ImageRequest.builder().id(orderProduct.getImage().getId()).src(orderProduct.getImage().getSrc()).build())
                        .size(SizeRequest.builder().id(orderProduct.getSize().getId()).name(orderProduct.getSize().getName()).build())
                        .color(ColorRequest.builder().id(orderProduct.getColor().getId()).name(orderProduct.getColor().getName()).hex(orderProduct.getColor().getHexCode()).build())
                        .price(orderProduct.getPrice())
                        .quantity(orderProduct.getQuantity())
                        .feedbacks(
                                feedbackRepository.findAllByProduct_id(orderProduct.getProduct().getId())
                                        .stream().map(feedback ->
                                                        FeedbackResponse.builder()
                                                                .id(feedback.getId())
                                                                .rating(feedback.getRating())
                                                                .description(feedback.getDescription())
                                                                .time(feedback.getTime())
                                                                .name(feedback.getUser().getName())
                                                                .build()
                                                ).collect(Collectors.toList())
                        )

                        .build();
                cartItem.add(productCartItemRequest);
            }
            OrderResponse orderResponse = OrderResponse.builder()
                    .id(order.getId())
                    .time(order.getTime())
                    .status(order.getStatus())
                    .total(order.getTotal())
                    .productCartItems(cartItem)
                    .payment(PaymentResponse.builder()
                            .code(order.getPayment().getCode())
                            .name(order.getPayment().getName())
                            .build())
                    .address(AddressResponse.builder()
                                    .cityId(order.getAddress().getCityId())
                                    .districtId(order.getAddress().getDistrictId())
                                    .wardId(order.getAddress().getWardId())
                                    .detail(order.getAddress().getDetail())
                                    .build())
                    .build();
            result.add(orderResponse);
        }
        return result;
    }

    @Override
    public OrderResponse getById(Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        OrderEntity order = orderRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );
        order.setStatus("Đã hủy");
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void create(OrderRequest orderRequest) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String email = securityContext.getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));
        Float total = (float) orderRequest.getCart().stream().mapToDouble(item -> Math.round(item.getPrice() * (1 - item.getPercent() / 100.0)) * item.getQuantity()).sum();

        AddressEntity addressEntity = addressRepository.findById(orderRequest.getAddress().getAddressId()).orElseThrow(() -> new RuntimeException("Address not found"));

        boolean isAddressUsed = orderRepository.existsByAddress(addressEntity);
        AddressEntity newAddress;
        if (isAddressUsed) {
            newAddress = AddressEntity.builder()
                    .cityId(addressEntity.getCityId())
                    .wardId(addressEntity.getWardId())
                    .districtId(addressEntity.getDistrictId())
                    .detail(addressEntity.getDetail())
                    .build();
            addressEntity = addressRepository.save(newAddress);
        } else {
            addressEntity = addressRepository.save(addressEntity); // Tái sử dụng nếu chưa dùng
        }

        PaymentEntity paymentEntity = paymentRepository.findByCodeAndUser(orderRequest.getPayment().getCode(), userEntity)
                .orElseGet(() -> {
                    PaymentEntity newPayment = PaymentEntity.builder()
                            .code(orderRequest.getPayment().getCode())
                            .name(orderRequest.getPayment().getName())
                            .user(userEntity)
                            .build();
                    return paymentRepository.save(newPayment);
                });

        OrderEntity orderEntity = OrderEntity.builder()
                .user(userEntity)
                .deleted(false)
                .time(new Date())
                .payment(paymentEntity)
                .address(addressEntity)
                .status("Chờ xác nhận")
                .total(total)
                .build();
        orderRepository.save(orderEntity);

        for (ProductCartItemRequest productCartItemRequest : orderRequest.getCart()) {
            ProductEntity productEntity = productRepository.findById(productCartItemRequest.getId()).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
            ColorEntity colorEntity = colorRepository.findById(productCartItemRequest.getColor().getId()).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
            SizeEntity sizeEntity = sizeRepository.findById(productCartItemRequest.getSize().getId()).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));
            ImageEntity imageEntity = imageRepository.findById(productCartItemRequest.getImage().getId()).orElseThrow(() -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

            OrderProductEntity orderItem = OrderProductEntity.builder()
                    .order(orderEntity)
                    .product(productEntity)
                    .color(colorEntity)
                    .size(sizeEntity)
                    .image(imageEntity)
                    .price((float) Math.round(productCartItemRequest.getPrice() * (1 - productCartItemRequest.getPercent() / 100.0)))
                    .quantity(productCartItemRequest.getQuantity())
                    .build();
            productOrderRepository.save(orderItem);
        }

        ShoppingCartEntity cart = shoppingCartRepository.findByUser(userEntity).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION));

        // Xóa hết ProductShoppingCartEntity
        List<ProductShoppingCartEntity> items = productShoppingCartRepository.findByShoppingCart(cart);
        productShoppingCartRepository.deleteAll(items);
        // Clear collection trong cart hiện tại thay vì tạo mới
        cart.getProductShoppingCarts().clear();

    }

    @Override
    public void update(Long id, OrderRequest data) {

    }

}
