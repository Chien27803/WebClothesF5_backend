package com.javanc.model.response.client;

import lombok.Data;

@Data
public class UploadImgResponse {
    private String urlResponse;
    public UploadImgResponse(String urlResponse) {
        this.urlResponse = urlResponse;
    }
}
