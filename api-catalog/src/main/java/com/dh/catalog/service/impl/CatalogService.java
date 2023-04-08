package com.dh.catalog.service.impl;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.dto.MovieDto;
import com.dh.catalog.dto.SerieDto;
import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import com.dh.catalog.service.ICatalogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    ObjectMapper mapper;

    @Override
    public Catalog getCatalog(String genre) throws Exception {
        var series = getSeries(genre);
        var movies = getMovies(genre);
        return new Catalog(
                genre,
                movies,
                series
        );
    }

    @Retry(name = "catalog")
    @CircuitBreaker(name = "catalog", fallbackMethod = "getMoviesFallBack")
    private List<MovieDto> getMovies(String genre) throws Exception {
        return movieServiceClient.getMovieByGenre(genre);
    }

    public List<MovieDto> getMoviesFallBack(String genre) {
        System.out.println("Entrando al fallback de movies");
        List<Movie> movies = movieRepository.findAllByGenre(genre);
        List<MovieDto> moviesDto = new ArrayList<>();
        for (Movie movie :
                movies) {
            moviesDto.add(mapper.convertValue(movie, MovieDto.class));
        }
        return moviesDto;
    }

    @Retry(name = "catalog")
    @CircuitBreaker(name = "catalog", fallbackMethod = "getSeriesFallBack")
    private List<SerieDto> getSeries(String genre) throws Exception {
        return serieServiceClient.getSerieByGenre(genre);
    }

    public List<SerieDto> getSeriesFallBack(String genre) {
        System.out.println("Entrando al fallback de series");
        List<Serie> series = serieRepository.findAllByGenre(genre);
        List<SerieDto> seriesDto = new ArrayList<>();
        for (Serie serie :
                series) {
            seriesDto.add(mapper.convertValue(serie, SerieDto.class));
        }
        return seriesDto;
    }

    @Setter
    @Getter
    @AllArgsConstructor
    public class Catalog {
        private String genre;

        private List<MovieDto> movies;

        private List<SerieDto> series;

    }
}
