package com.javanc.controller.client;

import com.javanc.model.response.client.UploadImgResponse;
import com.javanc.service.UploadImageFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class UpLoadImgController {
    private final UploadImageFileService uploadImageFileService;


    @PostMapping("/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file){
        try {
            String imgUrl = uploadImageFileService.uploadImage(file);
            return ResponseEntity.ok().body(new UploadImgResponse(imgUrl));
        }
        catch (Exception e) {
            log.error("Upload failed", e);
            return ResponseEntity.status(500).body("Upload ảnh thất bại");

        }
    }


}
