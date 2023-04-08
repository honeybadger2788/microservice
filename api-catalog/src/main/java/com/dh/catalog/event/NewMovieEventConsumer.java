package com.dh.catalog.event;

import com.dh.catalog.client.MovieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {

    @Autowired
    MovieRepository movieRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void listen(NewMovieEventConsumer.MovieDto message){
        System.out.print("NOMBRE DE MOVIE "+ message.name);
        var newMovie = new MovieServiceClient.MovieDto(message.name,message.genre, message.urlStream);
        movieRepository.save(newMovie);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class MovieDto{
        private String name;

        private String genre;

        private String urlStream;
    }
}
