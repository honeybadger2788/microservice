package com.dh.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "Series")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Serie {
    private String name;
    private String genre;
    private List<Season> seasons = new ArrayList<>();

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class Season {

        private Integer seasonNumber;
        private List<Chapter> chapters = new ArrayList<>();

        @AllArgsConstructor
        @NoArgsConstructor
        @Setter
        @Getter
        public static class Chapter {

          private String name;
            private Integer number;
            private String urlStream;


        }

    }

}
