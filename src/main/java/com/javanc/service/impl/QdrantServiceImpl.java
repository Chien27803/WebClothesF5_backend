package com.javanc.service.impl;

import com.javanc.config.ChatbotConfig;
import com.javanc.service.QdrantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QdrantServiceImpl implements QdrantService {

    ChatbotConfig chatbotConfig;
    RestTemplate restTemplate;

    @Override

    public void upsertPoint(String collectionName, Object pointId, List<Float> vector, Map<String, Object> payload) {

        String url = chatbotConfig.getQdrantUrl() + "/collections/" + collectionName + "/points?wait=true";

        Map<String, Object> body = Map.of(
                "points",
                List.of(Map.of(
                        "id", pointId,
                        "vector", vector,
                        "payload", payload)));

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body);
        restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
    }

    @Override
    public List<Map<String, Object>> search(String collectionName, List<Float> vector, int top) {
        String url = chatbotConfig.getQdrantUrl() + "/collections/" + collectionName + "/points/search";

        Map<String, Object> body = Map.of(
                "vector", vector,
                "top", top,
                "with_payload", true);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body);

        var response = restTemplate.postForEntity(url, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // Lấy kết quả points
        return (List<Map<String, Object>>) responseBody.get("result");
    }
}
