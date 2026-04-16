package com.example.cocktails.web.resource;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/ingredients")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IngredientsResource {

    @EJB
    private RecipeServiceRemote cocktailService;

    @GET
    public Collection<Ingredient> getAllIngredients() {
        return cocktailService.getAllIngredients();
    }

    @GET
    @Path("/{id}")
    public Ingredient getIngredient(@PathParam("id") Long id) {
        return cocktailService.getIngredient(id);
    }

    @GET
    @Path("/{id}/cocktails")
    public IngredientDetails getCocktailsWithIngredient(@PathParam("id") Long id) {
        return cocktailService.getAllCocktailsWithIngredient(id);
    }

}
