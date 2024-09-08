package com.example.dreamshops.service.product;

import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.repository.CategoryRepo;
import com.example.dreamshops.repository.ProductRepo;
import com.example.dreamshops.request.ProductRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{
    private final ProductRepo productRepository;
    private final CategoryRepo categoryRepo;

    public ProductService(ProductRepo productRepository, CategoryRepo categoryRepo) {
        this.productRepository = productRepository;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product addProduct(ProductRequest request) {
        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategoryName()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategoryName());
                    return categoryRepo.save(newCategory);
                });
        Product product = create(request, category);

        return productRepository.save(product);
    }

    private Product create(ProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                category
        );
    }

    @Override
    public Product updateProduct(ProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> prepareUpdate(request, existingProduct))
                .map(productRepository::save)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

    }

    private Product prepareUpdate(ProductRequest request, Product product) {
        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategoryName())).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        product.setCategory(category);
        product.setName(request.getName());
        product.setBrand(request.getBrand());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setInventory(request.getInventory());

        return product;
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
        return productRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String brand, String category) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
