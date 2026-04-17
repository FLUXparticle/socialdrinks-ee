package com.example.cocktails.web.controller;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import com.example.cocktails.web.remote.*;
import com.example.socialdrinks.common.*;
import jakarta.ejb.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.io.*;

@Named
@RequestScoped
public class CocktailDetailBean implements Serializable {

    @EJB
    private RecipeServiceRemote cocktailService;

    @Inject
    @RatingsClient
    private RatingInterface ratingInterface;

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
            cocktail = cocktailService.getCocktail(id);
        }
    }

    public Integer getAvgRating() {
        return ratingInterface.getRate(id);
    }

}
