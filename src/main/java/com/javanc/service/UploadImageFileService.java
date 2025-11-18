package com.javanc.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadImageFileService {
    String uploadImage(MultipartFile file) throws IOException;
}
