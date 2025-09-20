package com.javanc.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Top5OrderResponse {
    private Long product_id;
    private String src;
    private String name;
    private String description;
    private Long price;
    private Long sold_count;

}
