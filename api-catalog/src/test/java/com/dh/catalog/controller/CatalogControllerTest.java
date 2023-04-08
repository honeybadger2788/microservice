package com.dh.catalog.controller;

import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


class CatalogControllerTest {

    static Movie newMovie = new Movie("Pelicula Test", "Accion", "www.netflix.com");

    static Serie newSerie = new Serie("Serie Test", "Accion", List.of(new Serie.Season(1, List.of(new Serie.Season.Chapter("Chapter A", 1, "www.netflix.com/accion/1/season/2/chapter/1")))));

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    public void createData(){
        given()
                .contentType(ContentType.JSON)
                .body(newMovie)
                .when()
                .post("/api/v1/movies/save")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(newMovie.getName()))
                .body("genre", equalTo(newMovie.getGenre()));

        given()
                .contentType(ContentType.JSON)
                .body(newSerie)
                .when()
                .post("/api/v1/series/save")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(newSerie.getName()))
                .body("genre", equalTo(newSerie.getGenre()));
    }

    @Test
    public void testGetMovieOnline() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newMovie.getGenre())
                .get("/api/v1/catalog/{genre}")
                .then()
                .statusCode(200)
                .body("movies.name", hasItem(newMovie.getName()));
    }

    @Test
    public void testGetSerieOnline() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newSerie.getGenre())
                .get("/api/v1/catalog/{genre}")
                .then()
                .statusCode(200)
                .body("series.name", hasItem(newSerie.getName()));
    }
    @Test
    public void testGetMovieOffline() {

        //Thread.sleep(10000);

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newMovie.getGenre())
                .get("/api/v1/catalog/{genre}")
                .then()
                .statusCode(200)
                .body("movies.name", hasItem(newMovie.getName()));

    }

    @Test
    public void testGetSerieOffline() {

        given()
                .contentType(ContentType.JSON)
                .when()
                .pathParam("genre",newSerie.getGenre())
                .get("/api/v1/catalog/{genre}")
                .then()
                .statusCode(200)
                .body("series.name", hasItem(newSerie.getName()));
    }
}