package com.javanc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanc.model.response.client.DetailClientResponse;
import com.javanc.model.response.client.DiscountClientResponse;
import com.javanc.service.ChatbotService;
import com.javanc.service.OpenAIService;
import com.javanc.service.QdrantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ChatbotServiceImpl implements ChatbotService {

    OpenAIService openAIService;
    QdrantService qdrantService;
    ObjectMapper objectMapper;

    @Override
    public String answer(Map<String, Object> request) {
        String question = (String) request.get("question");
        List<Float> embedding = openAIService.createEmbedding(question);
        var results = qdrantService.search("products_collection", embedding, 10);
        StringBuilder context = new StringBuilder();
        boolean foundFAQ = false;

        for (var point : results) {
            Map<String, Object> payload = (Map<String, Object>) point.get("payload");
            if (payload.containsKey("question") && payload.containsKey("answer")) {
                foundFAQ = true;
                context.append(String.format("Question: %s\nAnswer: %s\n\n", payload.get("question"), payload.get("answer")));
            }
            else if (payload.containsKey("name") && payload.containsKey("description")) {
                String name = (String) payload.get("name");
                String description = (String) payload.get("description");
                Double price = (Double) payload.get("price");
                Double percent = 0.0;

                String detail = "";
                if (payload.get("details") instanceof List) {
                    List<DetailClientResponse> details = ((List<?>) payload.get("details")).stream()
                            .map(item -> objectMapper.convertValue(item, DetailClientResponse.class))
                            .collect(Collectors.toList());
                    detail = String.join(", ", details.stream().map(item ->
                            String.format("Size: %s, Màu sắc: %s, Còn lại: %s sản phẩm",
                                    item.getSize().getName(), item.getColor().getName(), item.getStock())
                    ).collect(Collectors.toList()));
                }

                if (payload.get("discounts") instanceof List discountsList && !discountsList.isEmpty()) {
                    List<DiscountClientResponse> discounts = ((List<?>) discountsList).stream()
                            .map(item -> objectMapper.convertValue(item, DiscountClientResponse.class))
                            .collect(Collectors.toList());
                    percent = discounts.get(0).getPercent();
                }

                context.append(String.format("Sản phẩm: %s\n", name));
                context.append(String.format("- Mô tả: %s\n", description));
                context.append(String.format("- Giá: %s VNĐ\n", price != null ? price.longValue() : 0));
                context.append(String.format("- Giảm giá: %s%%\n", percent));
                context.append("- Chi tiết: \n");
                context.append(String.format("+ %s\n", detail));
                context.append("\n\n");
            }
        }

        String prompt;
        if (foundFAQ) {
            prompt = String.format(
                    "Bạn là trợ lý ảo của cửa hàng. Dưới đây là một số câu hỏi và câu trả lời tham khảo:\n%s"
                            + "Dựa vào đó, hãy trả lời câu hỏi sau một cách chính xác, dễ hiểu và thân thiện:\nCâu hỏi: %s",
                    context.toString(), question);
        } else {
            prompt = String.format(
                    "Bạn là trợ lý ảo của cửa hàng. Hãy trả lời câu hỏi sau dựa trên thông tin sản phẩm:\n"
                            + "Câu hỏi: %s\n\n"
                            + "Thông tin sản phẩm liên quan:\n%s\n"
                            + "Hãy trả lời một cách thân thiện, chính xác và hữu ích.",
                    question, context.toString());
        }

        return openAIService.chatCompletion(prompt);
    }
}
