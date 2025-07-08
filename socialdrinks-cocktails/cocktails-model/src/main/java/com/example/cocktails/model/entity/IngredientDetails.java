package com.example.cocktails.model.entity;

import java.io.*;
import java.util.*;

public class IngredientDetails implements Serializable {

    private String name;

    private Collection<Cocktail>  cocktails;

    public IngredientDetails() {
        // empty
    }

    public IngredientDetails(String name, Collection<Cocktail> cocktails) {
        this.name = name;
        this.cocktails = cocktails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Cocktail> getCocktails() {
        return cocktails;
    }

    public void setCocktails(Collection<Cocktail> cocktails) {
        this.cocktails = cocktails;
    }

}
