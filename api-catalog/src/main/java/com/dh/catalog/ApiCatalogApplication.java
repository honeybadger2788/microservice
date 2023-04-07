package com.dh.catalog;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.model.Movie;
import com.dh.catalog.model.Serie;
import com.dh.catalog.repository.MovieRepository;
import com.dh.catalog.repository.SerieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class ApiCatalogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiCatalogApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadSeries(SerieRepository repository) {
        String baseUrl = "www.netflix.com/series";

        return (args) -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }

            //Serie A terror
            List<SerieServiceClient.SerieDto.Season.Chapter> serieASeasonAChapters = List.of(
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter A", 1, baseUrl + "/terror/1/season/1/chapter/1"),
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter B", 2, baseUrl + "/terror/1/season/1/chapter/2")
            );

            List<SerieServiceClient.SerieDto.Season.Chapter> serieASeasonBChapters = List.of(
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter A", 1, baseUrl + "/terror/1/season/2/chapter/1"),
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter B", 2, baseUrl + "/terror/1/season/2/chapter/2")
            );

            List<SerieServiceClient.SerieDto.Season> serieASeasons = List.of(
                    new SerieServiceClient.SerieDto.Season(1, serieASeasonAChapters),
                    new SerieServiceClient.SerieDto.Season(2, serieASeasonBChapters)
            );

            //Serie B comedia
            List<SerieServiceClient.SerieDto.Season.Chapter> serieBSeasonAChapters = List.of(
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter A", 1, baseUrl + "/comedia/1/season/1/chapter/1"),
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter B", 2, baseUrl + "/comedia/1/season/1/chapter/2")
            );

            List<SerieServiceClient.SerieDto.Season.Chapter> serieBSeasonBChapters = List.of(
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter A", 1, baseUrl + "/comedia/1/season/2/chapter/1"),
                    new SerieServiceClient.SerieDto.Season.Chapter("Chapter B", 2, baseUrl + "/comedia/1/season/2/chapter/2")
            );

            List<SerieServiceClient.SerieDto.Season> serieBSeasons = List.of(
                    new SerieServiceClient.SerieDto.Season(1, serieBSeasonAChapters),
                    new SerieServiceClient.SerieDto.Season(2, serieBSeasonBChapters)
            );

            SerieServiceClient.SerieDto serieA = new SerieServiceClient.SerieDto("Serie A", "Terror", serieASeasons);
            SerieServiceClient.SerieDto serieB = new SerieServiceClient.SerieDto("Serie B", "Comedia", serieBSeasons);
            repository.save(serieA);
            repository.save(serieB);

        };
    }

    @Bean
    public CommandLineRunner loadMovies(MovieRepository repository) {
        return (args) -> {
            if (!repository.findAll().isEmpty()) {
                return;
            }

            repository.save(new MovieServiceClient.MovieDto("Pelicula 1", "Terror", "www.netflix.com"));
            repository.save(new MovieServiceClient.MovieDto("Pelicula 2", "Terror", "www.netflix.com"));
            repository.save(new MovieServiceClient.MovieDto("Pelicula 3", "Comedia", "www.netflix.com"));
            repository.save(new MovieServiceClient.MovieDto("Pelicula 4", "Ficcion", "www.netflix.com"));
        };
    }

}
