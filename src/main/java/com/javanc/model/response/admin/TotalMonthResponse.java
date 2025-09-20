package com.javanc.model.response.admin;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TotalMonthResponse {
    private int month;
    private Long total_month;
}
