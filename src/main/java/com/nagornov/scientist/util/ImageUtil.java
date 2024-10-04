package com.nagornov.scientist.util;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@UtilityClass
public class ImageUtil {

    public String saveImage(MultipartFile imageFile, String dirName) {
        try {
            if (imageFile.isEmpty()) {
                throw new IOException("File is empty");
            }

            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(
                    "src/main/resources/%s/".formatted(dirName), fileName
            );

            Files.copy(imageFile.getInputStream(), filePath);

            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    public Resource getImage(String imageName, String dirName) {
        try {
            Path imagePath = Paths.get(
                    "src/main/resources/%s/".formatted(dirName)
            ).resolve(imageName).normalize();

            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not load image: " + imageName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid image URL", e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteImage(String imageName, String dirName) {
        try {
            Path filePath = Paths.get(
                    "src/main/resources/%s/".formatted(dirName), imageName
            );
            if (Files.exists(filePath)) {
                Files.delete(filePath);
            } else {
                throw new FileNotFoundException("Image not found: " + imageName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete image file", e);
        }
    }
}