package com.javanc.service;

import java.util.List;
import java.util.Map;

public interface QdrantService {

    void upsertPoint(String collectionName, Object pointId, List<Float> vector, Map<String, Object> payload);

    List<Map<String, Object>> search(String collectionName, List<Float> vector, int top);
}