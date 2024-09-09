package com.example.dreamshops.dto;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Image;
import com.example.dreamshops.model.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private CategoryDto category;
    private List<ImageDto> images = new ArrayList<>();

    public ProductDto(Long id, String name, String brand, String description, BigDecimal price, int inventory, CategoryDto category) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.category = category;
    }

    public static ProductDto toDto(Product entity){
        ProductDto productDto = new ProductDto(entity.getId(), entity.getName(), entity.getBrand(), entity.getDescription(), entity.getPrice(), entity.getInventory(), CategoryDto.toDto(entity.getCategory()));
        if(entity.getImages() == null) return productDto;
        entity.getImages().forEach(image -> productDto.getImages().add(ImageDto.toDto(image)));
        return productDto;
    }
}
