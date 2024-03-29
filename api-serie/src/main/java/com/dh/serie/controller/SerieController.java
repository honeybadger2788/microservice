package com.dh.serie.controller;

import com.dh.serie.event.NewSerieEventProducer;
import com.dh.serie.model.Serie;
import com.dh.serie.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/series")
public class SerieController {
    @Autowired
    SerieService serieService;

    @GetMapping
    public List<Serie> getAll() {
        return serieService.getAll();
    }

    @GetMapping("/{genre}")
    public List<Serie> getSerieByGenre(@PathVariable String genre) {
        return serieService.getSeriesByGenre(genre);
    }

    @PostMapping("/save")
    public ResponseEntity<Serie> create(@RequestBody Serie serie) {
        return  ResponseEntity.ok().body(serieService.create(serie));
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestBody String id) {
        serieService.delete(id);
    }
}
