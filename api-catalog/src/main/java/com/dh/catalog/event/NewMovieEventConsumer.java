package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.dto.MovieDto;
import com.dh.catalog.model.Movie;
import com.dh.catalog.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ObjectMapper mapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_MOVIE)
    public void listen(MovieDto message){
        System.out.print("NOMBRE DE MOVIE "+ message.getName());
        var newMovie = mapper.convertValue(message,Movie.class);
        movieRepository.save(newMovie);
    }
}
