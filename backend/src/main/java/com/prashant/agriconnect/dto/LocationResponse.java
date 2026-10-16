package com.prashant.agriconnect.dto;

public record LocationResponse(String id, Double latitude, Double longitude, String address, String productId, String productName, Double price) {}
