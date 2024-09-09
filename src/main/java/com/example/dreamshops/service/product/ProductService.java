package com.example.dreamshops.service.product;

import com.example.dreamshops.dto.ProductDto;
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
    public ProductDto addProduct(ProductRequest request) {
        Category category = Optional.ofNullable(categoryRepo.findByName(request.getCategoryName()))
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(request.getCategoryName());
                    return categoryRepo.save(newCategory);
                });
        Product product = create(request, category);

        return ProductDto.toDto(productRepository.save(product));
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
    public ProductDto updateProduct(ProductRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> prepareUpdate(request, existingProduct))
                .map(productRepository::save)
                .map(ProductDto::toDto)
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
    public List<ProductDto> getAllProducts() {
        return Optional.ofNullable(productRepository.findAll().stream().map(ProductDto::toDto).toList()).orElseThrow(() -> new EntityNotFoundException("Products not found"));
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
        return Optional.ofNullable(productRepository.findByCategoryName(category).stream().map(ProductDto::toDto).toList()).orElseThrow(() -> new EntityNotFoundException("Products not found"));
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return Optional.ofNullable(productRepository.findByBrand(brand).stream().map(ProductDto::toDto).toList()).orElseThrow(()-> new EntityNotFoundException("Products not found"));
    }

    @Override
    public List<ProductDto> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return Optional.ofNullable(productRepository.findByPriceBetween(min, max).stream().map(ProductDto::toDto).toList()).orElseThrow(()->    new EntityNotFoundException("Products not found"));
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String brand, String category) {
        return Optional.ofNullable(productRepository.findByCategoryNameAndBrand(category, brand).stream().map(ProductDto::toDto).toList()).orElseThrow(()-> new EntityNotFoundException("Products not found"));
    }

    @Override
    public List<ProductDto> getProductsByBrandAndName(String brand, String name) {
        return Optional.ofNullable(productRepository.findByBrandAndName(brand, name).stream().map(ProductDto::toDto).toList()).orElseThrow(()-> new EntityNotFoundException("Products not found"));
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return Optional.ofNullable(productRepository.findByName(name).stream().map(ProductDto::toDto).toList()).orElseThrow(()-> new EntityNotFoundException("Products not found"));
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
