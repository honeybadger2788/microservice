package com.dh.catalog.service.impl;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import com.dh.catalog.service.ICatalogService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService implements ICatalogService {

    @Autowired
    MovieServiceClient movieServiceClient;

    @Autowired
    SerieServiceClient serieServiceClient;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    SerieRepository serieRepository;

    @Override
    public Catalog getCatalog(String genre) throws Exception {
        var series = getSeries(genre);
        var movies = getMovies(genre);
        Catalog catalog = new Catalog(
                genre,
                movies,
                series
        );
        return catalog;
    }

    @Retry(name = "movies")
    @CircuitBreaker(name = "movies", fallbackMethod = "getMoviesFallBack")
    private List<MovieServiceClient.MovieDto> getMovies(String genre) throws Exception {
        return movieServiceClient.getMovieByGenre(genre);
    }

    public List<MovieServiceClient.MovieDto> getMoviesFallBack(String genre) {
        System.out.println("Entrando al fallback de movies");
        return movieRepository.findAllByGenre(genre);
    }

    @Retry(name = "series")
    @CircuitBreaker(name = "series", fallbackMethod = "getSeriesFallBack")
    private List<SerieServiceClient.SerieDto> getSeries(String genre) throws Exception {
        return serieServiceClient.getSerieByGenre(genre);
    }

    public List<SerieServiceClient.SerieDto> getSeriesFallBack(String genre) {
        return serieRepository.findAllByGenre(genre);
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public class Catalog {
        private String genre;

        private List<MovieServiceClient.MovieDto> movies;

        private List<SerieServiceClient.SerieDto> series;

    }
}
