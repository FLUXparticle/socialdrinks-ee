package com.example.cocktails.fridge.ejb.service;

import com.example.cocktails.cocktail.api.service.*;
import com.example.cocktails.fridge.api.model.*;
import com.example.cocktails.fridge.api.service.*;
import com.example.cocktails.fridge.ejb.model.*;
import com.example.cocktails.model.entity.*;
import com.example.cocktails.model.exception.*;
import jakarta.annotation.*;
import jakarta.ejb.*;
import org.slf4j.*;

import java.security.*;
import java.util.*;
import java.util.concurrent.*;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

@Singleton
@Remote(FridgeServiceRemote.class)
public class FridgeService implements FridgeServiceRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(FridgeService.class);

    private final Map<String, Fridge> userFridges = new ConcurrentHashMap<>();

    @EJB
    private RecipeServiceRemote cocktailService;

    @Resource
    private SessionContext context;

    /**
     * Gets the fridge for the specified user, creating it if it doesn't exist.
     * 
     * @return the user's fridge
     * @throws NotAuthenticatedException if the username is null or empty
     */
    private Fridge getUserFridge() {
        Principal principal = context.getCallerPrincipal();
        String username;
        if (principal == null) {
            username = "anonymous";
        } else {
            username = principal.getName();
        }

        LOGGER.info("getUserFridge username: {}", username);
        return userFridges.computeIfAbsent(username, Fridge::new);
    }

    @Override
    public Set<Ingredient> getFridgeIngredients() {
        return getUserFridge().getIngredients();
    }

    @Override
    public Ingredient addIngredient(Long ingredientId) {
        Ingredient ingredient = cocktailService.getIngredient(ingredientId);
        if (ingredient != null) {
            getUserFridge().addIngredient(ingredient);
        }
        return ingredient;
    }

    @Override
    public Ingredient removeIngredient(Long ingredientId) {
        Ingredient ingredient = cocktailService.getIngredient(ingredientId);
        if (ingredient != null) {
            getUserFridge().removeIngredient(ingredient);
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> getIngredientsNotInFridge() {
        Set<Ingredient> currentFridge = getFridgeIngredients();
        return cocktailService.getAllIngredients().stream()
                .filter(ingredient -> !currentFridge.contains(ingredient))
                .collect(toList());
    }

    @Override
    public List<Cocktail> getPossibleCocktails() {
        Set<Ingredient> fridgeIngredients = getFridgeIngredients();
        List<Cocktail> possibleCocktails = new ArrayList<>();
        for (Cocktail cocktail : cocktailService.getAllCocktails(true)) {
            boolean allIngredientsPresent = true;
            for (Instruction instruction : cocktail.getInstructions()) {
                if (!fridgeIngredients.contains(instruction.getIngredient())) {
                    allIngredientsPresent = false;
                    break;
                }
            }
            if (allIngredientsPresent) {
                possibleCocktails.add(new Cocktail(cocktail.getId(), cocktail.getName(), null));
            }
        }
        return possibleCocktails;
    }

    @Override
    public List<ShoppingModel> getCocktailsWithMissingIngredients() {
        Set<Ingredient> currentFridge = getFridgeIngredients();
        List<ShoppingModel> shoppingList = new ArrayList<>();

        for (Cocktail cocktail : cocktailService.getAllCocktails(true)) {
            List<String> present = new ArrayList<>();
            List<String> missing = new ArrayList<>();

            for (Instruction instruction : cocktail.getInstructions()) {
                Ingredient ingredient = instruction.getIngredient();
                if (currentFridge.contains(ingredient)) {
                    present.add(ingredient.getName());
                } else {
                    missing.add(ingredient.getName());
                }
            }

            if (!missing.isEmpty()) {
                shoppingList.add(new ShoppingModel(cocktail.getName(), present, missing));
            }
        }

        // Sortieren nach Anzahl der fehlenden Zutaten
        shoppingList.sort(comparingInt(m -> m.getMissingIngredients().size()));

        return shoppingList;
    }

}
