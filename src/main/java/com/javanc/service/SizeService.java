package com.javanc.service;


import com.javanc.model.response.common.SizeResponse;
import com.javanc.repository.entity.SizeEntity;

import java.util.List;

public interface SizeService {


    List<SizeResponse> getSizes();

}
