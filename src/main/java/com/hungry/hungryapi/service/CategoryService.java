package com.hungry.hungryapi.service;

import com.hungry.hungryapi.model.Category;
import com.hungry.hungryapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);

        return category.orElse(null);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Long id, Category category) {
        Optional<Category> categoryData = categoryRepository.findById(id);

        if(categoryData.isEmpty()) {
            return null;
        }

        Category updatedCategory = categoryData.get();

        updatedCategory.setName(category.getName());

        return categoryRepository.save(updatedCategory);
    }

    public boolean deleteCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);

        if(category.isEmpty()) {
            return false;
        }

        categoryRepository.deleteById(id);

        return true;
    }
}
