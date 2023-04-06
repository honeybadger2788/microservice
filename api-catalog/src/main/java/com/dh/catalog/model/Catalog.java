package com.dh.catalog.model;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Catalog {
    private String genre;

    private List<MovieServiceClient.MovieDto> movies = new ArrayList<>();

    private List<SerieServiceClient.SerieDto> series = new ArrayList<>();

}
