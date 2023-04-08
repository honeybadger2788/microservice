package com.dh.catalog.service;

import com.dh.catalog.dto.MovieDto;
import com.dh.catalog.dto.SerieDto;

import java.util.List;

public interface ICatalogService {
    List<SerieDto> getSeries(String genre) throws Exception;

    List<MovieDto> getMovies(String genre) throws Exception;
    List<SerieDto> getSeriesOffline(String genre) throws Exception;

    List<MovieDto> getMoviesOffline(String genre) throws Exception;
}
