package com.example.dreamshops.dto;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private Integer numberOfProducts;

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static CategoryDto toDto(Category entity){
        CategoryDto categoryDto = new CategoryDto(entity.getId(), entity.getName());
        if (entity.getProducts() != null)
        {
            categoryDto.setNumberOfProducts(entity.getProducts().size());
        }
        return categoryDto;
    }
}
