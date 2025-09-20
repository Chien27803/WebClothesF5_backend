package com.javanc.model.response.admin;

import lombok.Data;

@Data
public class Top5RecentResponse {
    private String src;
    private String name;
    private String email;
    private Long totalPrice;

    public Top5RecentResponse(String src, String name, String email, Long totalPrice) {
        this.src = src;
        this.name = name;
        this.email = email;
        this.totalPrice = totalPrice;
    }
}
