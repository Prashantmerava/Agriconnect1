package com.prashant.agriconnect.controller;

import com.prashant.agriconnect.dto.ProductRequest;
import com.prashant.agriconnect.dto.ProductResponse;
import com.prashant.agriconnect.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService service;
    public ProductController(ProductService service) { this.service = service; }

    @GetMapping("/products")
    ResponseEntity<Map<String,Object>> products(@RequestParam(required = false) String search) {
        List<ProductResponse> products = service.findAll(search);
        return ResponseEntity.ok(Map.of("products", products));
    }

    @GetMapping("/products/{id}")
    ResponseEntity<ProductResponse> product(@PathVariable String id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping("/marketplace")
    ResponseEntity<Map<String,Object>> create(@Valid @RequestBody ProductRequest request, Authentication auth) {
        ProductResponse product = service.create(auth.getName(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("msg", "New product added successfully", "data", product));
    }
}
