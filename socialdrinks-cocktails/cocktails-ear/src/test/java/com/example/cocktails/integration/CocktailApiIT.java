package com.example.cocktails.integration;

import io.restassured.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CocktailApiIT {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void testGetAllCocktails() {
        given()
        .when()
           .get("/cocktails")
        .then()
            .statusCode(200)
            .body("size()", greaterThan(0));
    }

    @Test
    void testGetCocktailById() {
        // Testfall: GET /api/cocktails/{id}
        // Abnahmekriterien:
        // 1) Bei gueltiger ID kommt HTTP 200.
        // 2) Die Antwort enthaelt mindestens die Felder "name" und "instructions".
        // 3) Bonus: Bei ungueltiger ID kommt HTTP 404.
        given()
                .when()
                .get("/cocktails/{id}", 1L)
                .then()
                .statusCode(200)
                .body("$", hasKey("name"))
                .body("$", hasKey("instructions"));
    }

    @Test
    void testSearchCocktails() {
        // Testfall: GET /api/cocktails/search?query=...
        // Abnahmekriterien:
        // 1) Bei query mit Treffer kommt HTTP 200 und mindestens ein Ergebnis.
        // 2) Bei leerer query kommt HTTP 200 und eine fachlich sinnvolle Antwort (z. B. alle Cocktails).
        // 3) Bonus: Jeder Treffer passt zum Suchbegriff (Name oder Zutat), ohne Duplikate.
        given()
                .queryParam("query", "Milch")
                .when()
                .get("/cocktails/search")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void testGetAllIngredients() {
        // Testfall: GET /api/ingredients
        // Abnahmekriterien:
        // 1) HTTP 200.
        // 2) Antwortliste ist nicht leer.
        // 3) Bonus: Elemente enthalten mindestens id und name und sind stabil sortiert.
        given()
                .when()
                .get("/ingredients")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    void testGetIngredientById() {
        // Testfall: GET /api/ingredients/{id}
        // Abnahmekriterien:
        // 1) Bei gueltiger ID kommt HTTP 200 mit passender ID.
        // 2) Bei ungueltiger ID kommt HTTP 404.
        // 3) Bonus: Die Antwort enthaelt einen nicht-leeren Namen.
        given()
                .when()
                .get("/ingredients/{id}", 1L)
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    void testGetCocktailsByIngredient() {
        // Testfall: GET /api/ingredients/{id}/cocktails
        // Abnahmekriterien:
        // 1) Bei gueltiger Ingredient-ID kommt HTTP 200.
        // 2) Antwort enthaelt das Feld "cocktails".
        // 3) Bonus: Bei ungueltiger Ingredient-ID kommt HTTP 404.
        given()
                .when()
                .get("/ingredients/{id}/cocktails", 1L)
                .then()
                .statusCode(200)
                .body("$", hasKey("cocktails"));
    }

}
