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
public class IngredientBean implements Serializable {

    @EJB
    private RecipeServiceRemote cocktailService;

    private Collection<Ingredient> ingredients;

    public Collection<Ingredient> getIngredients() {
        if (ingredients == null) {
            // TODO Aufgabe 3:
            // Lade hier die Zutatenliste über RecipeServiceRemote.
            ingredients = Collections.emptyList();
        }
        return ingredients;
    }

}
