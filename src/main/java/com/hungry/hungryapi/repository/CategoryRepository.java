package com.hungry.hungryapi.repository;

import com.hungry.hungryapi.model.Category;
import com.hungry.hungryapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
