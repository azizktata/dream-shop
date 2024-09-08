package com.example.dreamshops.service.product;

import com.example.dreamshops.model.Product;
import com.example.dreamshops.request.ProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    Product addProduct(ProductRequest request);
    Product updateProduct(ProductRequest request, Long productId);
    void deleteProduct(Long id);
    Product getProduct(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max);
    List<Product> getProductsByCategoryAndBrand(String brand, String category);
    List<Product> getProductsByBrandAndName(String brand, String name);
    List<Product> getProductsByName(String name);
    Long countProductByBrandAndName(String brand, String name);

}
