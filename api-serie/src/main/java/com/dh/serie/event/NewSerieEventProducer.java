package com.dh.serie.event;

import com.dh.serie.config.RabbitMQConfig;
import com.dh.serie.model.Serie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieEventProducer {
    private final RabbitTemplate rabbitTemplate;

    public NewSerieEventProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void publishNewMovieEvent(SerieDto message){
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,RabbitMQConfig.NEW_SERIE,message);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static
    class SerieDto{
        private String name;
        private String genre;
        private List<Serie.Season> seasons = new ArrayList<>();

        @Setter
        @Getter
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Season {

            private Integer seasonNumber;
            private List<Chapter> chapters = new ArrayList<>();

            @Setter
            @Getter
            public static class Chapter {

                private String name;
                private Integer number;
                private String urlStream;
            }

        }
    }

}
