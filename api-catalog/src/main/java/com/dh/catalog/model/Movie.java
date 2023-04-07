package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import org.springframework.data.annotation.Id;

@Document(collection = "Series")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Movie implements Serializable {

    @Id
    private Long id;

    private String name;

    private String genre;

    private String urlStream;

}