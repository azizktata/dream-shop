package com.example.dreamshops.service.category;

import com.example.dreamshops.exceptions.EntityNotFoundException;
import com.example.dreamshops.model.Category;
import com.example.dreamshops.repository.CategoryRepo;
import com.example.dreamshops.request.CategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService{
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category with id: " + id + " not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return Optional.ofNullable(categoryRepo.findByName(name)).orElseThrow(() -> new EntityNotFoundException("Category with name: " + name + " not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(CategoryRequest request, Long id) {
        Category category = getCategoryById(id);
        category.setName(request.getName());
        return categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete, ()-> {
            throw new EntityNotFoundException("Category with id: " + id + " not found");});
    }
}
