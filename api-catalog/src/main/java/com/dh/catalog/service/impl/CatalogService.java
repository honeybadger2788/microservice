package com.dh.catalog.service.impl;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
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
    public Catalog getCatalog(String genre) {
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
    @CircuitBreaker(name = "catalog", fallbackMethod = "getMoviesFallBack")
    private List<MovieServiceClient.MovieDto> getMovies(String genre) {
        return movieServiceClient.getMovieByGenre(genre);
    }

    public List<MovieServiceClient.MovieDto> getMoviesFallBack(String genre, Throwable t) throws Exception {
        return movieRepository.findAllByGenre(genre);
    }

    @Retry(name = "series")
    @CircuitBreaker(name = "catalog", fallbackMethod = "getSeriesFallBack")
    private List<SerieServiceClient.SerieDto> getSeries(String genre) {
        return serieServiceClient.getSerieByGenre(genre);
    }

    public List<SerieServiceClient.SerieDto> getSeriesFallBack(String genre, Throwable t) throws Exception {
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
