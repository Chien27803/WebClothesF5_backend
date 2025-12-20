package com.javanc.config;

import com.javanc.repository.entity.OrderEntity;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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

    @Value("${qdrant.api-key}")
    private String qrantApiKey;

    @Value("${qdrant.collection-name}")
    private String qdrantCollectName;

    @Value("${openai.completion-url}")
    private String openaiCompletionUrl;

    @Value("${openai.embedding-url}")
    private String openaiEmbeddingUrl;
}
