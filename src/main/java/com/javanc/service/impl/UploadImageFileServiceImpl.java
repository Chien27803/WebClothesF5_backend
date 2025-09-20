package com.javanc.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.javanc.service.UploadImageFileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UploadImageFileServiceImpl implements UploadImageFileService {

    Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            throw new IllegalArgumentException("File name is invalid.");
        }

        String extension = getExtension(originalName);
        String publicValue = generatePublicValue(originalName);

        log.info("publicValue: {}", publicValue);
        log.info("extension: {}", extension);

        File fileUpload = convert(file, publicValue, extension);
        log.info("fileUpload path: {}", fileUpload.getAbsolutePath());

        cloudinary.uploader().upload(fileUpload, ObjectUtils.asMap("public_id", publicValue));
        cleanDisk(fileUpload);

        return cloudinary.url().generate(publicValue);
    }

    private File convert(MultipartFile file, String publicValue, String extension) throws IOException {
        String safeFilename = sanitizeFileName(publicValue + "." + extension);
        Path tempPath = Files.createTempFile(safeFilename, null);
        try (InputStream is = file.getInputStream()) {
            Files.copy(is, tempPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        }
        return tempPath.toFile();
    }

    private void cleanDisk(File file) {
        try {
            log.info("Deleting file: {}", file.getAbsolutePath());
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            log.error("Failed to delete temporary file: {}", file.getAbsolutePath(), e);
        }
    }

    private String generatePublicValue(String originalName) {
        String baseName = getBaseName(originalName);
        return UUID.randomUUID() + "_" + baseName;
    }

    private String getBaseName(String originalName) {
        String sanitized = sanitizeFileName(originalName);
        int dotIndex = sanitized.lastIndexOf('.');
        return (dotIndex == -1) ? sanitized : sanitized.substring(0, dotIndex);
    }

    private String getExtension(String originalName) {
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex == -1 || dotIndex == originalName.length() - 1) {
            throw new IllegalArgumentException("File does not have a valid extension.");
        }
        return originalName.substring(dotIndex + 1);
    }

    private String sanitizeFileName(String input) {
        // Thay thế tất cả ký tự không hợp lệ
        return input.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}
