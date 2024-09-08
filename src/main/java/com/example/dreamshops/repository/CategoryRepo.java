package com.example.dreamshops.repository;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Category findByName(String categoryName);
}
