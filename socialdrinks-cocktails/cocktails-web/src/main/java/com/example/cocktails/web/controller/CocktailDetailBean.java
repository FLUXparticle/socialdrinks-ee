package com.example.cocktails.web.controller;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.io.*;

@Named
@RequestScoped
public class CocktailDetailBean implements Serializable {

    @EJB
    private RecipeServiceRemote cocktailService;

    private Long id;
    private Cocktail cocktail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cocktail getCocktail() {
        return cocktail;
    }

    public void loadCocktail() {
        if (id != null) {
            // TODO Aufgabe 1:
            // Lade hier den ausgewählten Cocktail über RecipeServiceRemote.
            // Nutze CocktailBean als Beispiel für die EJB-Verwendung.
            cocktail = null;
        }
    }

}
