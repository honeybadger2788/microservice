package com.dh.catalog.controller;

import com.dh.catalog.client.MovieServiceClient;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Catalog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	private final MovieServiceClient movieServiceClient;

	private final SerieServiceClient serieServiceClient;

	public CatalogController(MovieServiceClient movieServiceClient, SerieServiceClient serieServiceClient) {
		this.movieServiceClient = movieServiceClient;
		this.serieServiceClient = serieServiceClient;
	}

	@GetMapping("/{genre}")
	ResponseEntity<Catalog> getGenre(@PathVariable String genre) {
		Catalog catalog = new Catalog(
				genre,
				movieServiceClient.getMovieByGenre(genre),
				serieServiceClient.getSerieByGenre(genre)
		);
		return ResponseEntity.ok(catalog);
	}

}
