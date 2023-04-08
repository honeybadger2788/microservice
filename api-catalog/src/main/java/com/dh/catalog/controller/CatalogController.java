package com.dh.catalog.controller;

import com.dh.catalog.dto.MovieDto;
import com.dh.catalog.dto.SerieDto;
import com.dh.catalog.service.impl.CatalogService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	@Autowired
	CatalogService catalogService;

	@GetMapping("/{genre}")
	ResponseEntity<?> getGenre(@PathVariable String genre) throws Exception{
		var series = catalogService.getSeries(genre);
		var movies = catalogService.getMovies(genre);
		return ResponseEntity.ok(new CatalogResponse(genre,movies,series));
	}

	@Setter
	@Getter
	@AllArgsConstructor
	public class CatalogResponse {
		private String genre;

		private List<MovieDto> movies;

		private List<SerieDto> series;
	}
}
