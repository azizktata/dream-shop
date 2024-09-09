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

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private CategoryDto category;
    private List<ImageDto> images;

    public static ProductDto toDto(Product entity){
        return new ProductDto(entity.getId(), entity.getName(), entity.getBrand(), entity.getDescription(), entity.getPrice(), entity.getInventory(), CategoryDto.toDto(entity.getCategory()), entity.getImages().stream().map(ImageDto::toDto).toList());
    }
}
