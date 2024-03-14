package com.hungry.hungryapi.repository;

import com.hungry.hungryapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Set<Recipe> findByNameContaining(String name);

}
