package com.example.cocktails.web.resource;

import com.example.cocktails.fridge.api.model.*;
import com.example.cocktails.fridge.api.service.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.*;

@Path("/fridge")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FridgeResource {

    @EJB
    private FridgeServiceRemote fridgeService;

    @GET
    @Path("/whoami")
    public Response whoAmI(@Context SecurityContext ctx) {
        if (ctx.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Nicht eingeloggt").build();
        }
        return Response.ok("Hallo " + ctx.getUserPrincipal().getName()).build();
    }

    @GET
    @Path("/ingredients")
    public Response getIngredients() {
        // Get all ingredients in fridge and not in fridge
        Set<Ingredient> inFridge = fridgeService.getFridgeIngredients();
        List<Ingredient> notInFridge = fridgeService.getIngredientsNotInFridge();
        
        // Create a list of all ingredients with their fridge status
        List<Map<String, Object>> result = new ArrayList<>();
        
        // Add ingredients in fridge
        for (Ingredient ingredient : inFridge) {
            result.add(toFridgeObject(ingredient, true));
        }
        
        // Add ingredients not in fridge
        for (Ingredient ingredient : notInFridge) {
            result.add(toFridgeObject(ingredient, false));
        }

        return Response.ok(result).build();
    }

    private static Map<String, Object> toFridgeObject(Ingredient ingredient, boolean value) {
        Map<String, Object> item = new HashMap<>();
        item.put("id", ingredient.getId());
        item.put("name", ingredient.getName());
        item.put("inFridge", value);
        return item;
    }

    @PATCH
    @Path("/ingredients/{id}")
    public Response updateIngredientStatus(@PathParam("id") Long id, Map<String, Boolean> update) {
        Boolean inFridge = update.get("inFridge");

        if (inFridge == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Missing 'inFridge' field in request body").build();
        }

        Ingredient ingredient;
        if (inFridge) {
            ingredient = fridgeService.addIngredient(id);
        } else {
            ingredient = fridgeService.removeIngredient(id);
        }

        return Response.ok(toFridgeObject(ingredient, inFridge)).build();
    }

    @GET
    @Path("/possible")
    public List<Cocktail> getPossibleCocktails() {
        return fridgeService.getPossibleCocktails();
    }

    @GET
    @Path("/shopping")
    public List<ShoppingModel> getCocktailsWithMissingIngredients() {
        return fridgeService.getCocktailsWithMissingIngredients();
    }

}
