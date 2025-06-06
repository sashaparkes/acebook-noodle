package com.makersacademy.acebook.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${file.upload-dir.user-profile}")
    private String uploadDirProfile;

    @Value("${file.upload-dir.post-images}")
    private String uploadDirPost;

    public String storeProfileImage(MultipartFile file, String id) throws IOException {
        return storeImage(file, uploadDirProfile, id);
    }

    public String storePostImage(MultipartFile file, String id) throws IOException {
        return storeImage(file, uploadDirPost, id);
    }

    private String storeImage(MultipartFile file, String targetDir, String id) throws IOException {
        if (file.isEmpty()){
            return null;
        }

        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
        }

        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String cleanFileName = UUID.randomUUID() + "_" + originalFileName;

        String extension = getFileExtension(cleanFileName);
        String newFileName = id + extension;

        Path uploadPath = Paths.get(targetDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("Saved image to: " + filePath.toAbsolutePath().toString());
        return newFileName;
    }

    public static String getFileExtension(String fileName) {
        Path path = Paths.get(fileName);
        return Optional.ofNullable(path.getFileName())
                .map(Path::toString)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".")))
                .orElse("");
    }


}
