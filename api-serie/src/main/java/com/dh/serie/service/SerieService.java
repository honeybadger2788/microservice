package com.dh.serie.service;

import com.dh.serie.event.NewSerieEventProducer;
import com.dh.serie.model.Serie;
import com.dh.serie.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    @Autowired
    SerieRepository repository;
    @Autowired
    NewSerieEventProducer newSerieEventProducer;

    public List<Serie> getAll() {
        return repository.findAll();
    }

    public List<Serie> getSeriesByGenre(String genre) {
        return repository.findAllByGenre(genre);
    }

    public Serie create(Serie serie) {
        var newSerie = new NewSerieEventProducer.SerieDto(serie.getName(),serie.getGenre(),serie.getSeasons());
        newSerieEventProducer.publishNewMovieEvent(newSerie);
        return repository.save(serie);
    }
}
