package com.example.cocktails.web.controller;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.io.*;
import java.util.*;

@Named
@RequestScoped
public class IngredientDetailBean implements Serializable {

    @EJB
    private RecipeServiceRemote cocktailService;

    private Long id;
    private String ingredientName;
    private Collection<Cocktail> cocktails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Collection<Cocktail> getCocktails() {
        return cocktails;
    }

    public void loadIngredient() {
        if (id != null) {
            IngredientDetails ingredientDetails = cocktailService.getAllCocktailsWithIngredient(id);
            ingredientName = ingredientDetails.getName();
            // Finden Sie alle Cocktails, die diese Zutat beinhalten
            cocktails = ingredientDetails.getCocktails();
        }
    }

}
