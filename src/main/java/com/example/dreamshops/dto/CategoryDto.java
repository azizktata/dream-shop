package com.example.dreamshops.dto;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto toDto(Category entity){
        return new CategoryDto(entity.getId(), entity.getName());
    }
}
