package com.example.dreamshops.service.product;

import com.example.dreamshops.model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Long id);
    Product getProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max);
    List<Product> getProductsByBrandAndCategory(String brand, String category);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByName(String name);
    Long countProductByBrandAndName(String brand, String name);

}
