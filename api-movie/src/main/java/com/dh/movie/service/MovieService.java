package com.dh.movie.service;


import com.dh.movie.event.NewMovieEventProducer;
import com.dh.movie.model.Movie;
import com.dh.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    NewMovieEventProducer newMovieEventProducer;

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public Movie save(Movie movie) {
        var newMovie = new NewMovieEventProducer.MovieDto(movie.getName(),movie.getGenre(),movie.getUrlStream());
        newMovieEventProducer.publishNewMovieEvent(newMovie);
        return movieRepository.save(movie);
    }
}
