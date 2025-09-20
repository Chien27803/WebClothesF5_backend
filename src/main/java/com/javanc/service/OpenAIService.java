package com.javanc.service;

import java.util.List;

public interface OpenAIService {
    List<Float> createEmbedding(String text);
    String chatCompletion(String prompt);
}
