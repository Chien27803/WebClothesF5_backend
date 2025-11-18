package com.javanc.ultis;

import java.text.Normalizer;
import java.util.Locale;

public class SlugUltis {
    public static String toSlug(String input) {
        // 1. Loại bỏ dấu tiếng Việt
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        // 2. Chuyển về chữ thường và thay khoảng trắng/thừa bằng "-"
        String slug = withoutDiacritics
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9\\s-]", "")  // loại ký tự đặc biệt
                .replaceAll("\\s+", "-")         // thay khoảng trắng bằng "-"
                .replaceAll("-{2,}", "-")        // bỏ trùng dấu "-"
                .replaceAll("^-|-$", "");        // bỏ dấu "-" ở đầu/cuối nếu có
        return slug;
    }
}
