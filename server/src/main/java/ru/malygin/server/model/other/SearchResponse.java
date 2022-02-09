package ru.malygin.server.model.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    private String path;
    private String title;
    private String snippet;
    private Double relevance;

}
