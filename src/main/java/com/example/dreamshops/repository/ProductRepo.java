package com.example.dreamshops.repository;

import com.example.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    List<Product> findByCategoryNameAndBrand(String category, String brand);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByName(String name);

    Long countByBrandAndName(String brand, String name);
}
