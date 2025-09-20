package com.javanc.service.impl;


import com.javanc.model.response.client.ColorClientResponse;

import com.javanc.repository.ColorRepository;
import com.javanc.repository.entity.ColorEntity;
import com.javanc.service.ColorService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    ColorRepository colorRepository;

    @Override
    public List<ColorClientResponse> getColors() {
        List<ColorEntity> colors = colorRepository.findAll();
        return colors.stream().map(color -> ColorClientResponse.builder()
                .id(color.getId())
                .name(color.getName())
                .build())
                .collect(Collectors.toList());
    }
}
