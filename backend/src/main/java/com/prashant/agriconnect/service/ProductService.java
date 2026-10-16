package com.prashant.agriconnect.service;

import com.prashant.agriconnect.dto.*;
import com.prashant.agriconnect.entity.Location;
import com.prashant.agriconnect.entity.Product;
import com.prashant.agriconnect.entity.User;
import com.prashant.agriconnect.entity.UserRole;
import com.prashant.agriconnect.exception.BadRequestException;
import com.prashant.agriconnect.exception.ResourceNotFoundException;
import com.prashant.agriconnect.repository.ProductRepository;
import com.prashant.agriconnect.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository products;
    private final UserRepository users;

    public ProductService(ProductRepository products, UserRepository users) { this.products = products; this.users = users; }

    @Transactional
    public ProductResponse create(String farmerId, ProductRequest request) {
        User farmer = users.findById(farmerId).orElseThrow(() -> new ResourceNotFoundException("Farmer not found"));
        if (farmer.getRole() != UserRole.FARMER) throw new BadRequestException("Only farmers can list products");

        Product product = new Product();
        product.setName(request.productName().trim()); product.setDescription(request.description().trim());
        product.setPrice(request.price()); product.setPredictedPrice(request.price()); product.setQuantity(request.quantity());
        product.setUnit(request.unit()); product.setCategory(request.category()); product.setImageUrl(request.imageUrl());
        product.setHarvestDate(request.harvestDate()); product.setFarmer(farmer);

        Location location = new Location();
        location.setAddress(request.locationAddress().trim());
        location.setLatitude(request.latitude());
        location.setLongitude(request.longitude());
        location.setProduct(product);
        product.setLocation(location);
        products.save(product);
        return toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(String search) {
        List<Product> list = search == null || search.isBlank() ? products.findAllByOrderByCreatedAtDesc()
                : products.findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrderByCreatedAtDesc(search, search);
        return list.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(String id) {
        return toResponse(products.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
    }

    private ProductResponse toResponse(Product p) {
        User f = p.getFarmer();
        FarmerResponse farmer = new FarmerResponse(f.getId(), f.getName(), f.getPhoneNumber(), f.getEmail(), f.getAddress());
        Location l = p.getLocation();
        LocationResponse location = l == null ? null : new LocationResponse(l.getId(), l.getLatitude(), l.getLongitude(), l.getAddress(), p.getId(), p.getName(), p.getPrice());
        return new ProductResponse(p.getId(), p.getName(), p.getDescription(), p.getPrice(), p.getPredictedPrice(), p.getQuantity(), p.getUnit(), p.getCategory(), p.getImageUrl(), p.getHarvestDate(), farmer, location);
    }
}
