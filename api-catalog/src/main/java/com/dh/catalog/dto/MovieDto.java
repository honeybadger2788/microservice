package com.dh.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MovieDto {
    private String name;

    private String genre;

    private String urlStream;
}
