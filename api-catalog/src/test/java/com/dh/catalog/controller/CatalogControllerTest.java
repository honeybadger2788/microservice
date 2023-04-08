package com.dh.catalog.controller;

import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;


class CatalogControllerTest {

    static Movie newMovie = new Movie("Pelicula Test", "Accion", "www.netflix.com");

    static Serie newSerie = new Serie("Serie Test", "Accion", List.of(new Serie.Season(1, List.of(new Serie.Season.Chapter("Chapter A", 1, "www.netflix.com/accion/1/season/2/chapter/1")))));


    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void testSeries() throws InterruptedException {
        var newSerieId = given()
                .contentType(ContentType.JSON)
                .body(newSerie)
                .when()
                .post("/api/v1/series/save")
                .as(Map.class);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newSerie.getGenre())
                .get("/api/v1/catalog/{genre}")
                .then()
                .statusCode(200)
                .body("series.name", hasItem(newSerie.getName()));

        TimeUnit.SECONDS.sleep(10);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newSerie.getGenre())
                .get("/api/v1/catalog/offline/{genre}")
                .then()
                .statusCode(200)
                .body("series.name", hasItem(newSerie.getName()));

        given()
                .contentType(ContentType.JSON)
                .body(newSerieId.get("id"))
                .when()
                .post("/api/v1/series/delete")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

    @Test
    public void testMovies() throws InterruptedException {
        var newMovieId = given()
                .contentType(ContentType.JSON)
                .body(newMovie)
                .when()
                .post("/api/v1/movies/save")
                .as(Map.class);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newMovie.getGenre())
                .get("/api/v1/catalog/{genre}")
                .then()
                .statusCode(200)
                .body("movies.name", hasItem(newMovie.getName()));

        TimeUnit.SECONDS.sleep(10);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newMovie.getGenre())
                .get("/api/v1/catalog/offline/{genre}")
                .then()
                .statusCode(200)
                .body("movies.name", hasItem(newMovie.getName()));

        given()
                .contentType(ContentType.JSON)
                .body(newMovieId.get("id"))
                .when()
                .post("/api/v1/movies/delete")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value());
    }

}