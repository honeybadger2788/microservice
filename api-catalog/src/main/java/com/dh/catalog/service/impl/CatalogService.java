package com.dh.catalog.service.impl;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.service.ICatalogService;
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

    @Override
    public Catalog getCatalog(String genre) {
        var series = serieServiceClient.getSerieByGenre(genre);
        var movies = movieServiceClient.getMovieByGenre(genre);
        Catalog catalog = new Catalog(
                genre,
                movies,
                series
        );
        return catalog;
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
