package com.example.cocktails.fridge.api.service;

import com.example.cocktails.fridge.api.model.*;
import com.example.cocktails.model.entity.*;

import java.util.*;

public interface FridgeServiceRemote {

    Set<Ingredient> getFridgeIngredients();

    Ingredient addIngredient(Long ingredientId);

    Ingredient removeIngredient(Long ingredientId);

    List<Ingredient> getIngredientsNotInFridge();

    List<Cocktail> getPossibleCocktails();

    List<ShoppingModel> getCocktailsWithMissingIngredients();

}
