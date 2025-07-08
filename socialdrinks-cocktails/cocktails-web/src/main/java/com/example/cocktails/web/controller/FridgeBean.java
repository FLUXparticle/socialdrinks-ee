package com.example.cocktails.web.controller;

import com.example.cocktails.model.entity.*;
import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.io.*;
import java.util.*;

@Named
@RequestScoped
public class FridgeBean implements Serializable {

    // TODO Aufgabe 5:
    // Wähle hier einen Scope, der den Kühlschrank für den ganzen Browser hält.
    // Ein anderer Browser soll einen anderen Kühlschrank sehen.

    // Hinzufügen einer Zutat zum Kühlschrank
    public void addIngredient(Long ingredientId) {
        // TODO Aufgabe 5:
        // Binde das FridgeServiceRemote aus dem Modul cocktails-fridge-api ein,
        // ergänze dafür bei Bedarf die Abhängigkeit im cocktails-web-Modul
        // und delegiere den Aufruf an das EJB.
    }

    // Entfernen einer Zutat aus dem Kühlschrank
    public void removeIngredient(Long ingredientId) {
        // TODO Aufgabe 5:
        // Binde das FridgeServiceRemote aus dem Modul cocktails-fridge-api ein
        // und delegiere den Aufruf an das EJB.
    }

    // Getter für die Zutaten im Kühlschrank
    public Set<Ingredient> getFridgeIngredients() {
        // TODO Aufgabe 5:
        // Liefere hier die Zutaten aus dem FridgeServiceRemote.
        return Collections.emptySet();
    }

    // Getter für die Zutaten, die nicht im Kühlschrank sind
    public List<Ingredient> getIngredientsNotInFridge() {
        // TODO Aufgabe 5:
        // Liefere hier die fehlenden Zutaten aus dem FridgeServiceRemote.
        // Der Kühlschrank braucht dafür die vollständige Zutatenliste aus dem Rezept-Service.
        return Collections.emptyList();
    }

    // Getter für mögliche Cocktails
    public List<Cocktail> getPossibleCocktails() {
        // TODO Aufgabe 5:
        // Liefere hier die möglichen Cocktails aus dem FridgeServiceRemote.
        return Collections.emptyList();
    }

    // Getter für Cocktails mit fehlenden Zutaten
    public List<Map<String, Object>> getCocktailsWithMissingIngredients() {
        // TODO Aufgabe 5:
        // Liefere hier die Shopping-Daten aus dem FridgeServiceRemote.
        return Collections.emptyList();
    }

}
