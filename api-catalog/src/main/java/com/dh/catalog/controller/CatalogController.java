package com.dh.catalog.controller;

import com.dh.catalog.service.impl.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/catalog")
public class CatalogController {

	@Autowired
	CatalogService catalogService;

	@GetMapping("/{genre}")
	ResponseEntity<?> getGenre(@PathVariable String genre) throws Exception{
		return ResponseEntity.ok(catalogService.getCatalog(genre));
	}

}
