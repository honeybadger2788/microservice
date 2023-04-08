package com.dh.catalog.event;

import com.dh.catalog.config.RabbitMQConfig;
import com.dh.catalog.dto.SerieDto;
import com.dh.catalog.model.Serie;
import com.dh.catalog.repository.SerieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NewSerieEventConsumer {
    @Autowired
    SerieRepository serieRepository;

    @Autowired
    ObjectMapper mapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_SERIE)
    public void listen(SerieDto message){
        System.out.print("NOMBRE DE SERIE "+ message.getName());
        var newSerie = mapper.convertValue(message,Serie.class);
        serieRepository.save(newSerie);
    }
}
