package com.example.dreamshops.service.category;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.request.CategoryRequest;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(CategoryRequest request);
    Category updateCategory(CategoryRequest request, Long id);
    void deleteCategory(Long id);

}
