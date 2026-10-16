package com.prashant.agriconnect.dto;

import jakarta.validation.constraints.*;

public record SignupRequest(
        @NotBlank @Size(min = 2, max = 60) String name,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 8) String password,
        @NotBlank @Pattern(regexp = "farmer|customer") String accountType,
        @Pattern(regexp = "^$|\\d{12}", message = "PMKISAN ID must be 12 digits") String pmkisanId,
        @NotBlank @Size(min = 10, max = 20) String phoneNumber,
        @NotBlank @Size(min = 3, max = 255) String address
) {}
