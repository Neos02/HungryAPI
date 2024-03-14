package com.hungry.hungryapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Arrays;

@Entity
@Table(name = "recipe_images")
public class RecipeImage {

    @Id
    @Column(name = "recipe_id")
    private long id;

    @Column(name = "type", columnDefinition = "VARCHAR(50)")
    private String type;

    @Lob
    @Column(name = "data", length = 5000)
    private byte[] data;

    @OneToOne
    @MapsId
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    public RecipeImage() {}

    public RecipeImage(Recipe recipe, String type, byte[] data) {
        this.recipe = recipe;
        this.type = type;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeImage{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
