package com.dh.catalog.event;

import com.dh.catalog.client.SerieServiceClient;
import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.repository.SerieRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewSerieEventConsumer {

    @Autowired
    SerieRepository serieRepository;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listen(NewSerieEventConsumer.SerieDto message){
        System.out.print("NOMBRE DE SERIE "+ message.name);
        var newSerie = new SerieServiceClient.SerieDto(message.name,message.genre,message.seasons);
        serieRepository.save(newSerie);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class SerieDto{
        private String name;
        private String genre;
        private List<SerieServiceClient.SerieDto.Season> seasons = new ArrayList<>();

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
