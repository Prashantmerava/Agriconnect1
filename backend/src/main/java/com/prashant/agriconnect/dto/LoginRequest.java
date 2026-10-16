package com.prashant.agriconnect.dto;

import jakarta.validation.constraints.*;

public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank @Pattern(regexp = "farmer|customer") String accountType,
        String pmkisanId
) {}
