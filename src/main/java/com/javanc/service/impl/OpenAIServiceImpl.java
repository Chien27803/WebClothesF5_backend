package com.javanc.service.impl;

import java.util.List;
import java.util.Map;

import com.javanc.config.ChatbotConfig;
import com.javanc.service.OpenAIService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    ChatbotConfig chatbotConfig;
    RestTemplate restTemplate;

    @Override
    public List<Float> createEmbedding(String text) {

        String embeddingURL =  chatbotConfig.getOpenaiEmbeddingUrl();

        Map<String, Object> body = Map.of("input", text, "model", "text-embedding-ada-002");
        org.springframework.http.HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(chatbotConfig.getOpenaiKey());
        headers.set("Content-Type", "application/json");
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        var response = restTemplate.postForEntity(embeddingURL, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        var data = (List<Map<String, Object>>) responseBody.get("data");
        List<Float> embedding = (List<Float>) data.get(0).get("embedding");
        return embedding;
    }

    @Override
    public String chatCompletion(String prompt) {

        String completionURL = chatbotConfig.getOpenaiCompletionUrl();


        Map<String, Object> message = Map.of("role", "user", "content", prompt);

        Map<String, Object> body = Map.of("model", "gpt-4o-mini", "messages", List.of(message));

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(chatbotConfig.getOpenaiKey());
        headers.set("Content-Type", "application/json");

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        var response = restTemplate.postForEntity(completionURL, request, Map.class);


        Map<String, Object> responseBody = response.getBody();
        var choices = (List<Map<String, Object>>) responseBody.get("choices");
        Map<String, Object> firstChoice = choices.get(0);
        Map<String, Object> messageResponse = (Map<String, Object>) firstChoice.get("message");
        String answer = (String) messageResponse.get("content");

        return answer;
    }
}
