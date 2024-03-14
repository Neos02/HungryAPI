package com.hungry.hungryapi.controller;

import com.hungry.hungryapi.model.Recipe;
import com.hungry.hungryapi.model.RecipeImage;
import com.hungry.hungryapi.repository.RecipeImageRepository;
import com.hungry.hungryapi.repository.RecipeRepository;
import com.hungry.hungryapi.utils.ImageUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/images")
public class RecipeImageController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeImageRepository recipeImageRepository;

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable("id") long id) {
        try {
            Optional<RecipeImage> recipeImage = recipeImageRepository.findById(id);

            if(recipeImage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(MediaType.valueOf(recipeImage.get().getType()));

            return new ResponseEntity<>(ImageUtility.decompressImage(recipeImage.get().getData()), headers, HttpStatus.OK);
        } catch (IOException | DataFormatException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<RecipeImage> uploadImage(@PathVariable("id") long recipeId, @RequestParam("image") MultipartFile file) {
        String outputType = "png";

        try {
            Optional<RecipeImage> recipeImage = recipeImageRepository.findById(recipeId);

            if(recipeImage.isPresent()) {
                RecipeImage updatedRecipeImage = recipeImage.get();

                updatedRecipeImage.setType("image/%s".formatted(outputType));
                updatedRecipeImage.setData(ImageUtility.compressImage(ImageUtility.convertImage(file.getBytes(), outputType)));

                recipeImageRepository.save(updatedRecipeImage);

                return new ResponseEntity<>(updatedRecipeImage, HttpStatus.OK);
            } else {
                Optional<Recipe> recipe = recipeRepository.findById(recipeId);

                if (recipe.isPresent()) {
                    Recipe updatedRecipe = recipe.get();
                    RecipeImage newRecipeImage = new RecipeImage(
                            updatedRecipe,
                            "image/%s".formatted(outputType),
                            ImageUtility.compressImage(ImageUtility.convertImage(file.getBytes(), outputType)));

                    updatedRecipe.setImage(newRecipeImage);

                    recipeRepository.save(updatedRecipe);

                    return new ResponseEntity<>(newRecipeImage, HttpStatus.OK);
                }
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
