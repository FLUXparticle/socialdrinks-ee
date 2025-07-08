package com.example.cocktails.fridge.ejb.service;

import com.example.cocktails.fridge.api.model.*;
import com.example.cocktails.fridge.api.service.*;
import com.example.cocktails.fridge.ejb.model.*;
import com.example.cocktails.model.entity.*;
import jakarta.annotation.*;
import jakarta.ejb.*;
import org.slf4j.*;

import java.security.*;
import java.util.*;
import java.util.concurrent.*;

@Singleton
@Remote(FridgeServiceRemote.class)
public class FridgeService implements FridgeServiceRemote {

    private static final Logger LOGGER = LoggerFactory.getLogger(FridgeService.class);

    private final Map<String, Fridge> userFridges = new ConcurrentHashMap<>();

    @Resource
    private SessionContext context;

    /**
     * Gets the fridge for the specified user, creating it if it doesn't exist.
     * 
     * @return the user's fridge
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
        // TODO Aufgabe 7:
        // Injiziere hier das RecipeServiceRemote in dieses EJB und
        // ergänze bei Bedarf die Abhängigkeit auf cocktails-recipe-api im Modul cocktails-fridge-ejb.
        // Lade dann die Zutat über den Rezept-Service, bevor sie in den Fridge kommt.
        return null;
    }

    @Override
    public Ingredient removeIngredient(Long ingredientId) {
        // TODO Aufgabe 7:
        // Entferne hier eine Zutat über dieselbe EJB-Verbindung aus dem Fridge.
        return null;
    }

    @Override
    public List<Ingredient> getIngredientsNotInFridge() {
        // TODO Aufgabe 7:
        // Nutze den Rezept-Service, um alle Zutaten zu laden und mit dem Fridge zu vergleichen.
        return Collections.emptyList();
    }

    @Override
    public List<Cocktail> getPossibleCocktails() {
        // TODO Aufgabe 7:
        // Ermittle hier die Cocktails, die mit dem aktuellen Fridge möglich sind.
        return Collections.emptyList();
    }

    @Override
    public List<ShoppingModel> getCocktailsWithMissingIngredients() {
        // TODO Aufgabe 7:
        // Baue hier die Shopping-Ansicht aus dem Rezept-Service und dem Fridge auf.
        return Collections.emptyList();
    }

}
