package com.hungry.hungryapi.service;

import com.hungry.hungryapi.model.Recipe;
import com.hungry.hungryapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(Long id) {
        Optional<Recipe> recipeData = recipeRepository.findById(id);

        return recipeData.orElse(null);
    }

    public Recipe createRecipe(Recipe recipe) {
        Recipe newRecipe = recipeRepository.save(new Recipe(recipe.getName(), recipe.getDescription(), recipe.getCookTimeMinutes()));

        newRecipe.setCategories(recipe.getCategories());

        return recipeRepository.save(newRecipe);
    }

    public Recipe updateRecipe(Long id, Recipe recipe) {
        Optional<Recipe> recipeData = recipeRepository.findById(id);

        if(recipeData.isEmpty()) {
            return null;
        }

        Recipe updatedRecipe = recipeData.get();

        updatedRecipe.setName(recipe.getName());
        updatedRecipe.setDescription(recipe.getDescription());
        updatedRecipe.setCookTimeMinutes(recipe.getCookTimeMinutes());
        updatedRecipe.setCategories(recipe.getCategories());

        return recipeRepository.save(updatedRecipe);
    }

    public boolean deleteRecipe(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);

        if(recipe.isEmpty()) {
            return false;
        }

        // Remove association to categories table
        recipe.get().getCategories().clear();
        recipeRepository.deleteById(id);

        return true;
    }

}
