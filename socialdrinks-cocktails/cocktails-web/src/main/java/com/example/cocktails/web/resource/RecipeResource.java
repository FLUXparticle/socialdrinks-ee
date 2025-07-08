package com.example.cocktails.web.resource;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/cocktails")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RecipeResource {

    @EJB
    private RecipeServiceRemote cocktailService;

    @GET
    public Collection<Cocktail> getAllCocktails() {
        return cocktailService.getAllCocktails(false);
    }

    @GET
    @Path("/{id}")
    public Cocktail getCocktail(@PathParam("id") Long id) {
        return cocktailService.getCocktail(id);
    }

    @GET
    @Path("/search")
    public Collection<Cocktail> searchCocktails(@QueryParam("query") String query) {
        return cocktailService.search(query);
    }

}
