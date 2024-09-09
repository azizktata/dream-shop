package com.example.dreamshops.service.category;

import com.example.dreamshops.dto.CategoryDto;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.request.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<CategoryDto> getAllCategories();
    CategoryDto addCategory(CategoryRequest request);
    CategoryDto updateCategory(CategoryRequest request, Long id);
    void deleteCategory(Long id);

}
