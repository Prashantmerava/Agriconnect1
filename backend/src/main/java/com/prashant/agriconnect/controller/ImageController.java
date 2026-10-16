package com.prashant.agriconnect.controller;

import com.prashant.agriconnect.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/api/images")
public class ImageController {
    private final ImageService service;
    public ImageController(ImageService service) { this.service = service; }
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    ResponseEntity<Map<String,String>> upload(@RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(Map.of("url", service.upload(file)));
    }
}
