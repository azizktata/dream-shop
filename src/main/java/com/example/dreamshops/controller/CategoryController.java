package com.example.dreamshops.controller;

import com.example.dreamshops.dto.CategoryDto;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.request.CategoryRequest;
import com.example.dreamshops.service.category.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryDto> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public CategoryDto getCategoryById(@PathVariable Long id){
        return CategoryDto.toDto(categoryService.getCategoryById(id));
    }

    @GetMapping("/{name}")
    public CategoryDto getCategoryByName(@PathVariable String name){
        return CategoryDto.toDto(categoryService.getCategoryByName(name));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategory(@RequestBody CategoryRequest category){
        return categoryService.addCategory(category);
    }

    @PutMapping("/{id}")
    public CategoryDto updateCategory(@RequestBody CategoryRequest category, @PathVariable Long id){
        return categoryService.updateCategory(category, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
    }
}
