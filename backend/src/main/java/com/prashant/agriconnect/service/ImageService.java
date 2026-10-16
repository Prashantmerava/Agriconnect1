package com.prashant.agriconnect.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.prashant.agriconnect.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImageService {
    private final Cloudinary cloudinary;
    public ImageService(Cloudinary cloudinary) { this.cloudinary = cloudinary; }

    public String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new BadRequestException("Image file is required");
        try {
            Map<?, ?> result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "agriconnect/marketplace"));
            Object url = result.get("secure_url");
            if (url == null) throw new BadRequestException("Cloudinary did not return an image URL");
            return url.toString();
        } catch (IOException | RuntimeException ex) {
            throw new BadRequestException("Image upload failed. Configure Cloudinary credentials or use an image URL.");
        }
    }
}
