package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import org.springframework.data.annotation.Id;

@Document(collection = "Movies")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Movie {

    @Id
    private String id;

    private String name;

    private String genre;

    private String urlStream;
}
