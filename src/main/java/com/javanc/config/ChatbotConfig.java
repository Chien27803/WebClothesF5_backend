package com.javanc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@PropertySource("classpath:application-uat.yml")
public class ChatbotConfig {

    @Value("${openai.key}")
    private String openaiKey;

    @Value("${qdrant.url}")
    private String qdrantUrl;

    @Value("${openai.completion-url}")
    private String openaiCompletionUrl;

    @Value("${openai.embedding-url}")
    private String openaiEmbeddingUrl;
}
