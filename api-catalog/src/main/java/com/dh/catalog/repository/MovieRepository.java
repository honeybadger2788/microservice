package com.dh.catalog.repository;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends MongoRepository<MovieServiceClient.MovieDto, Object> {
    List<MovieServiceClient.MovieDto> findAllByGenre(String genre);
}