package com.example.cocktails.fridge.ejb.model;

import com.example.cocktails.model.entity.Ingredient;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a user's fridge with their ingredients.
 */
public class Fridge implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final String username;
    private final Set<Ingredient> ingredients = new HashSet<>();
    
    public Fridge(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public Set<Ingredient> getIngredients() {
        return ingredients;
    }
    
    public void addIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            ingredients.add(ingredient);
        }
    }
    
    public void removeIngredient(Ingredient ingredient) {
        if (ingredient != null) {
            ingredients.remove(ingredient);
        }
    }
}