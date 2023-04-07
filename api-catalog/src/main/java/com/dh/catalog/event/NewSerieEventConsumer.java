package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfigSerie;
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

    @RabbitListener(queues = RabbitMQConfigSerie.QUEUE_NEW_SERIE)
    public void listen(NewSerieEventConsumer.SerieDto message){
        System.out.print("NOMBRE DE SERIE "+ message.name);
        //procesamiento
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class SerieDto{
        private String name;
        private String genre;
        private List<Season> seasons = new ArrayList<>();

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
