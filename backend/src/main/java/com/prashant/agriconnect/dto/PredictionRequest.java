package com.prashant.agriconnect.dto;

import jakarta.validation.constraints.NotBlank;

public record PredictionRequest(@NotBlank String product, @NotBlank String season, @NotBlank String marketplace) {}
