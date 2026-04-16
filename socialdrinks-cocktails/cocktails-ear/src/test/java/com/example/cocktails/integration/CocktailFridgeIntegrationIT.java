package com.example.cocktails.integration;

import io.restassured.*;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CocktailFridgeIntegrationIT {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost:8080/api";
    }

    @Test
    public void testGetFridgeIngredients() {
        given()
                .when()
                .get("/fridge/ingredients")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    public void testPossibleContainsPinkPowerForIngredients7And30() {
        given()
                .contentType("application/json")
                .body("""
                      {"inFridge":true}
                      """)
                .when()
                .patch("/fridge/ingredients/{id}", 7L)
                .then()
                .statusCode(200);

        given()
                .contentType("application/json")
                .body("""
                      {"inFridge":true}
                      """)
                .when()
                .patch("/fridge/ingredients/{id}", 30L)
                .then()
                .statusCode(200);

        given()
                .when()
                .get("/fridge/possible")
                .then()
                .statusCode(200)
                .body("name", hasItem("Pink Power"));
    }

}
