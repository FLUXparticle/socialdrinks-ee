package com.example.cocktails.cocktail.ejb.service;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.cocktail.ejb.dao.*;
import com.example.cocktails.model.entity.*;
import jakarta.ejb.*;

import java.util.*;

@Stateless
@Remote(RecipeServiceRemote.class)
public class RecipeService implements RecipeServiceRemote {

    @EJB
    private CocktailDAO cocktailDAO;

    @EJB
    private IngredientDAO ingredientDAO;

    public IngredientDetails getAllCocktailsWithIngredient(Long ingredientId) {
        Ingredient ingredient = ingredientDAO.findById(ingredientId);

        // Get cocktails with the specified ingredient
        SortedSet<Cocktail> cocktailsWithIngredient = getAllCocktailsWithIngredients(Collections.singleton(ingredientId));

        // Create new Cocktail instances without loading instructions
        SortedSet<Cocktail> cocktailsWithoutInstructions = new TreeSet<>();
        copyWithoutInstructions(cocktailsWithoutInstructions, cocktailsWithIngredient);

        return new IngredientDetails(ingredient.getName(), cocktailsWithoutInstructions);
    }

    public Collection<Ingredient> getAllIngredients() {
        return ingredientDAO.findAll();
    }

    public Collection<Cocktail> getAllCocktails(boolean withInstructions) {
        Collection<Cocktail> cocktails = cocktailDAO.findAll();
        if (withInstructions) {
            // Explicitly load instructions for each cocktail
            for (Cocktail cocktail : cocktails) {
                forceLoadingInstructions(cocktail);
            }
            return cocktails;
        } else {
            Collection<Cocktail> result = new ArrayList<>();
            copyWithoutInstructions(result, cocktails);
            return result;
        }
    }

    public Cocktail getCocktail(Long id) {
        Cocktail cocktail = cocktailDAO.findById(id);
        if (cocktail != null) {
            // Explicitly load instructions
            forceLoadingInstructions(cocktail);
        }
        return cocktail;
    }

    public Ingredient getIngredient(Long id) {
        return ingredientDAO.findById(id);
    }

    public Collection<Cocktail> search(String query) {
        Collection<Cocktail> cocktailsWithName = cocktailDAO.findByNameContains(query);

        Collection<Ingredient> ingredientsWithName = ingredientDAO.findByNameContains(query);

        Set<Long> ingredientIDs = new HashSet<>();
        for (Ingredient ingredient : ingredientsWithName) {
            ingredientIDs.add(ingredient.getId());
        }

        SortedSet<Cocktail> result = new TreeSet<>();
        copyWithoutInstructions(result, getAllCocktailsWithIngredients(ingredientIDs));
        copyWithoutInstructions(result, cocktailsWithName);

        return result;
    }

    private SortedSet<Cocktail> getAllCocktailsWithIngredients(Set<Long> ingredientIDs) {
        SortedSet<Cocktail> result = new TreeSet<>();

        for (Cocktail cocktail : getAllCocktails(true)) {
            for (Instruction instruction : cocktail.getInstructions()) {
                if (ingredientIDs.contains(instruction.getIngredient().getId())) {
                    result.add(cocktail);
                    break;
                }
            }
        }

        return result;
    }

    private void forceLoadingInstructions(Cocktail cocktail) {
        cocktail.getInstructions().size(); // Force loading of instructions
    }

    private void copyWithoutInstructions(Collection<Cocktail> dest, Collection<Cocktail> src) {
        for (Cocktail cocktail : src) {
            // Create a new Cocktail with the same ID and name, but without loading instructions
            dest.add(new Cocktail(cocktail.getId(), cocktail.getName(), null));
        }
    }

}
