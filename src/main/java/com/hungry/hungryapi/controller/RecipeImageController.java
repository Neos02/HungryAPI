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
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://192.168.4.103:3000")
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
        try {
            Optional<Recipe> recipe = recipeRepository.findById(recipeId);

            if(recipe.isPresent()) {
                Recipe updatedRecipe = recipe.get();
                RecipeImage recipeImage = new RecipeImage(updatedRecipe, file.getContentType(), ImageUtility.compressImage(file.getBytes()));

                updatedRecipe.setImage(recipeImage);

                recipeRepository.save(updatedRecipe);

                return new ResponseEntity<>(recipeImage, HttpStatus.OK);
            }

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
