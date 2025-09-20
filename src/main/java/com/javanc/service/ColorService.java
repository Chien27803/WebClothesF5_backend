package com.javanc.service;



import com.javanc.model.response.client.ColorClientResponse;
import com.javanc.repository.entity.ColorEntity;

import java.util.List;

public interface ColorService {
    List<ColorClientResponse> getColors();

}
