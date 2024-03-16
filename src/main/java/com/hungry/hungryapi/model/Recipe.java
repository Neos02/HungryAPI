package com.hungry.hungryapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NonNull
    @Column(name = "cook_time_minutes")
    private Integer cookTimeMinutes;

    @NonNull
    @Column(name = "views")
    private Integer views;

    @OneToOne(mappedBy = "recipe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private RecipeImage image;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(
        name = "recipe_categories",
        joinColumns = @JoinColumn(name = "recipe_id"),
        inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnoreProperties(value = "recipes")
    private Set<Category> categories;

    public Recipe() {}

    public Recipe(@NonNull String name, String description, @NonNull Integer cookTimeMinutes) {
        this.name = name;
        this.description = description;
        this.cookTimeMinutes = cookTimeMinutes;
        this.views = 0;
        this.image = null;
        this.categories = new HashSet<>();
    }

    public Recipe(Long id, @NonNull String name, String description, @NonNull Integer cookTimeMinutes, @NonNull Integer views) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cookTimeMinutes = cookTimeMinutes;
        this.views = 0;
        this.image = null;
        this.categories = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    public Integer getCookTimeMinutes() {
        return cookTimeMinutes;
    }

    public void setCookTimeMinutes(@NonNull Integer cookTimeMinutes) {
        this.cookTimeMinutes = cookTimeMinutes;
    }

    @NonNull
    public Integer getViews() {
        return views;
    }

    public void setViews(@NonNull Integer views) {
        this.views = views;
    }

    public RecipeImage getImage() {
        return image;
    }

    public void setImage(RecipeImage image) {
        this.image = image;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cookTimeMinutes=" + cookTimeMinutes +
                ", views=" + views +
                '}';
    }
}
