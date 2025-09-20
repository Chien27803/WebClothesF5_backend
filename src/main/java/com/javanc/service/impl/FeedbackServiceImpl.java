package com.javanc.service.impl;

import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.model.request.client.FeedbackRequest;
import com.javanc.repository.FeedbackRepository;
import com.javanc.repository.ProductRepository;
import com.javanc.repository.UserRepository;
import com.javanc.repository.entity.FeedbackEntity;
import com.javanc.repository.entity.ProductEntity;
import com.javanc.repository.entity.UserEntity;
import com.javanc.service.FeedbackService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepository feedbackRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    @Override
    public List<FeedbackRequest> getAll() {
        return List.of();
    }

    @Override
    public FeedbackRequest getById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void create(FeedbackRequest data) {
        SecurityContext context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new AppException(ErrorCode.USER_EXISTED)
        );

        ProductEntity product = productRepository.findById(data.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION)
        );

        FeedbackEntity feedback = FeedbackEntity.builder()
                .time(new Date())
                .description(data.getDescription())
                .rating(data.getRate())
                .user(user)
                .product(product)
                .deleted(false)
                .build();
        feedbackRepository.save(feedback);
    }

    @Override
    public void update(Long id, FeedbackRequest data) {

    }
}
