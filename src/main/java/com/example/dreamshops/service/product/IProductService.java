package com.example.dreamshops.service.product;

import com.example.dreamshops.dto.ProductDto;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.request.ProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    ProductDto addProduct(ProductRequest request);
    ProductDto updateProduct(ProductRequest request, Long productId);
    void deleteProduct(Long id);
    Product getProduct(Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByPriceRange(BigDecimal min, BigDecimal max);
    List<ProductDto> getProductsByCategoryAndBrand(String brand, String category);
    List<ProductDto> getProductsByBrandAndName(String brand, String name);
    List<ProductDto> getProductsByName(String name);
    Long countProductByBrandAndName(String brand, String name);

}
