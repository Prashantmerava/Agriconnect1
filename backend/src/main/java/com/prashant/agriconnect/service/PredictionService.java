package com.prashant.agriconnect.service;

import com.prashant.agriconnect.dto.PredictionRequest;
import com.prashant.agriconnect.entity.Product;
import com.prashant.agriconnect.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PredictionService {
    private final ProductRepository products;
    private static final Map<String, Double> SEASON = Map.of("Summer", 1.05, "Winter", 0.95, "Rainy", 1.10);
    private static final Map<String, Double> CITY = Map.ofEntries(
            Map.entry("Mumbai", 1.10), Map.entry("Pune", 1.05), Map.entry("Nagpur", 0.95),
            Map.entry("Nashik", 1.00), Map.entry("Aurangabad", 0.98), Map.entry("Solapur", 0.97),
            Map.entry("Thane", 1.08), Map.entry("Kalyan", 1.04), Map.entry("Kolhapur", 1.02), Map.entry("Chandrapur", 0.94));

    public PredictionService(ProductRepository products) { this.products = products; }

    public double predict(PredictionRequest request) {
        Product product = products.findAll().stream().filter(p -> p.getName().equalsIgnoreCase(request.product())).findFirst().orElse(null);
        if (product == null) return 0.0;
        double seasonFactor = SEASON.getOrDefault(request.season(), 1.0);
        String marketplace = request.marketplace();
        double cityFactor = CITY.entrySet().stream().filter(e -> marketplace.toLowerCase().contains(e.getKey().toLowerCase())).map(Map.Entry::getValue).findFirst().orElse(1.0);
        return product.getPrice() * seasonFactor * cityFactor;
    }
}
