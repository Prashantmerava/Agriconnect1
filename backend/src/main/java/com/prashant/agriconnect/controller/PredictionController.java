package com.prashant.agriconnect.controller;

import com.prashant.agriconnect.dto.PredictionRequest;
import com.prashant.agriconnect.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/predict-price")
public class PredictionController {
    private final PredictionService service;
    public PredictionController(PredictionService service) { this.service = service; }
    @PostMapping
    ResponseEntity<Map<String,Object>> predict(@Valid @RequestBody PredictionRequest request) {
        return ResponseEntity.ok(Map.of("predictedPrice", service.predict(request)));
    }
}
