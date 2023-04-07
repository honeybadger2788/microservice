package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfigMovie;
import com.dh.catalog.config.RabbitMQConfigSerie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NewMovieEventConsumer {

    @RabbitListener(queues = RabbitMQConfigMovie.QUEUE_NEW_MOVIE)
    public void listen(NewMovieEventConsumer.MovieDto message){
        System.out.print("NOMBRE DE MOVIE "+ message.name);
        //procesamiento
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    class MovieDto{
        private Long id;

        private String name;

        private String genre;

        private String urlStream;
    }
}
