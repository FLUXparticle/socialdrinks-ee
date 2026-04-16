package com.example.cocktails.web.controller;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;
import jakarta.validation.constraints.*;

import java.io.*;
import java.util.*;

@Named
@RequestScoped
public class CocktailSearchBean implements Serializable {

    @EJB
    private RecipeServiceRemote cocktailService;

    @NotBlank(message = "Bitte einen Suchbegriff eingeben.")
    @Size(min = 3, message = "Bitte mindestens 3 Zeichen eingeben.")
    private String query;
    private Collection<Cocktail> results;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Collection<Cocktail> getResults() {
        return results;
    }

    public void search() {
        results = cocktailService.search(query.trim());
    }

}
