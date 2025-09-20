package com.javanc.validation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javanc.controlleradvice.customeException.AppException;
import com.javanc.enums.ErrorCode;
import com.javanc.ultis.NumberUltis;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;


@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class VariantValidator {

    ObjectMapper objectMapper;  // Inject ObjectMapper

    public void validateVariants(String variantsJson) throws Exception {

        // phân tích chuỗi Json -> JsonNode (có thể là số, mảng JSON, object JSON
        JsonNode variantsNode = objectMapper.readTree(variantsJson);

        if (!variantsNode.isArray()) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
        }

        for (JsonNode variant : variantsNode) {
            validateVariant(variant);
        }
    }

    public void validateVariant(JsonNode variant) {
        if (!variant.has("colorId") || !variant.has("sizeId") || !variant.has("stock")) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);        }

        long colorId = NumberUltis.parseLong(variant.get("colorId").asText());
        long sizeId = NumberUltis.parseLong(variant.get("sizeId").asText());
        long stock = NumberUltis.parseLong(variant.get("stock").asText());

        if (stock < 0) {
            throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);        }
    }
}
