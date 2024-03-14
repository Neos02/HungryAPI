package com.hungry.hungryapi.repository;

import com.hungry.hungryapi.model.RecipeImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeImageRepository extends JpaRepository<RecipeImage, Long> {

    Optional<RecipeImage> findById(long recipeId);

}
