package com.example.dreamshops.service.product;

import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService implements IProductService{
    private final ProductRepo productRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new EntityNotFoundException("Product not found");
        });
    }


    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return null;
    }

    @Override
    public List<Product> getProductsByBrandAndCategory(String brand, String category) {
        return null;
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return null;
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return null;
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return null;
    }
}
