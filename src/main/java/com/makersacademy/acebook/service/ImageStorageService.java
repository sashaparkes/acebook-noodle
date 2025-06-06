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

    // Locations for storing profile / post images within the app setup - configured within application.properties
    @Value("${file.upload-dir.user-profile}")
    private String uploadDirProfile;

    @Value("${file.upload-dir.post-images}")
    private String uploadDirPost;

    // Segregated methods for storing the different types of images
    public String storeProfileImage(MultipartFile file, String id) throws IOException {
        return storeImage(file, uploadDirProfile, id);
    }

    public String storePostImage(MultipartFile file, String id) throws IOException {
        return storeImage(file, uploadDirPost, id);
    }

    // Generic storeImage method which is called in both methods above, but fed different parameters for storage location
    // Takes a file, a String representing target directory (for image storage) and a string id which helps configure filename
    private String storeImage(MultipartFile file, String targetDir, String id) throws IOException {
        // Checks whether file is empty
        if (file.isEmpty()){
            return null;
        }

        // Check file is of correct type
        String contentType = file.getContentType();
        if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
            throw new IllegalArgumentException("Only JPEG or PNG images are allowed");
        }

        // Extracts the file extension from the filename, and concatenates with
        String extension = getFileExtension(file.getOriginalFilename());
        String newFileName = id + extension;

        // These two blocks handle setting the upload path and getting the file stored in right location
        Path uploadPath = Paths.get(targetDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }

    public static String getFileExtension(String fileName) {
        return Optional.ofNullable(fileName)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".")))
                .orElse("");
    }


}