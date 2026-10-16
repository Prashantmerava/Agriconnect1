package com.prashant.agriconnect.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record ProductRequest(
        @NotBlank @Size(min = 2, max = 100) String productName,
        @NotBlank @Size(min = 10, max = 1000) String description,
        @NotBlank String category,
        @NotNull @Positive Double price,
        @NotNull @Positive Integer quantity,
        @NotBlank String unit,
        @NotNull LocalDate harvestDate,
        @Size(max = 1000) String imageUrl,
        @NotBlank @Size(min = 3, max = 255) String locationAddress,
        @NotNull @DecimalMin("-90") @DecimalMax("90") Double latitude,
        @NotNull @DecimalMin("-180") @DecimalMax("180") Double longitude
) {}
