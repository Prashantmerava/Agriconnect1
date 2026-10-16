package com.prashant.agriconnect.repository;

import com.prashant.agriconnect.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByOrderByCreatedAtDesc();
    List<Product> findByNameContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrderByCreatedAtDesc(String name, String category);
}
