package com.example.dreamshops.controller;

import com.example.dreamshops.dto.ProductDto;
import com.example.dreamshops.request.ProductRequest;
import com.example.dreamshops.service.product.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/products")
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/category/{category}")
    public List<ProductDto> getProductsByCategory(@PathVariable String category){
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/brand/{brand}")
    public List<ProductDto> getProductsByBrand(@PathVariable String brand){
        return productService.getProductsByBrand(brand);
    }

    @GetMapping("/price")
    public List<ProductDto> getProductsByPriceRange(@RequestParam("min") BigDecimal min, @RequestParam("max") BigDecimal max){
        return productService.getProductsByPriceRange(min, max);
    }

    @GetMapping("/category/{category}/brand/{brand}")
    public List<ProductDto> getProductsByCategoryAndBrand(@PathVariable String category, @PathVariable String brand){
        return productService.getProductsByCategoryAndBrand(brand, category);
    }

    @GetMapping("/brand/{brand}/name/{name}")
    public List<ProductDto> getProductsByBrandAndName(@PathVariable String brand, @PathVariable String name){
        return productService.getProductsByBrandAndName(brand, name);
    }

    @GetMapping("/name/{name}")
    public List<ProductDto> getProductsByName(@PathVariable String name){
        return productService.getProductsByName(name);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto addProduct(@RequestBody ProductRequest product){
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@RequestBody ProductRequest product, @PathVariable Long id){
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
    }

}
