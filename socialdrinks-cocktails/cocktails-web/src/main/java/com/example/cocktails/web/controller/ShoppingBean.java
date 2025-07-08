package com.example.cocktails.web.controller;

import jakarta.enterprise.context.*;
import jakarta.inject.*;

import java.io.*;
import java.util.*;

@Named
@RequestScoped
public class ShoppingBean implements Serializable {

    public Set<String> getShoppingList() {
        // TODO Aufgabe 6:
        // Wähle hier einen Scope, der die Einkaufsliste nur im aktuellen Browser-Tab hält.
        // Ein anderer Tab soll eine eigene Liste sehen.
        return Collections.emptySet();
    }

    public void addIngredients(List<String> ingredients) {
        // TODO Aufgabe 6:
        // Speichere hier die fehlenden Zutaten in der Einkaufsliste des aktuellen Tabs.
    }

    public void removeIngredient(String ingredient) {
        // TODO Aufgabe 6:
        // Entferne hier eine Zutat aus der Einkaufsliste des aktuellen Tabs.
    }

    public void clearShoppingList() {
        // TODO Aufgabe 6:
        // Leere hier die Einkaufsliste des aktuellen Tabs.
    }

}
