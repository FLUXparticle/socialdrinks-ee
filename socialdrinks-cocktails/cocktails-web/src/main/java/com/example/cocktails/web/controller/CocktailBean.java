package com.example.cocktails.web.controller;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.util.*;

@Named
@RequestScoped
public class CocktailBean {

    @EJB
    private RecipeServiceRemote cocktailService;

    private Collection<Cocktail> cocktails;

    public Collection<Cocktail> getCocktails() {
        if (cocktails == null) {
            cocktails = cocktailService.getAllCocktails(false);
        }
        return cocktails;
    }

}
