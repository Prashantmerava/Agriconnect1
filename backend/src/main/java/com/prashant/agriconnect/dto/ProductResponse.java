package com.prashant.agriconnect.dto;

import java.time.LocalDate;

public record ProductResponse(
        String id, String name, String description, Double price, Double predictedPrice,
        Integer quantity, String unit, String category, String imageUrl, LocalDate harvestDate,
        FarmerResponse farmer, LocationResponse location
) {}
