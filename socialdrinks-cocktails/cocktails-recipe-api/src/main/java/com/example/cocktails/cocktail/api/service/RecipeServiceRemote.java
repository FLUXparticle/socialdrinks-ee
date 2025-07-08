package com.example.cocktails.cocktail.api.service;

import com.example.cocktails.model.entity.*;

import java.util.*;

public interface RecipeServiceRemote {

    IngredientDetails getAllCocktailsWithIngredient(Long ingredientId);

    Collection<Ingredient> getAllIngredients();

    Collection<Cocktail> getAllCocktails(boolean withInstructions);

    Cocktail getCocktail(Long id);

    Ingredient getIngredient(Long id);

    Collection<Cocktail> search(String query);

}
