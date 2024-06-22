package com.alura.demo.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBooksSearch(
        @JsonAlias("count") Integer countBooks,
        @JsonAlias("results") List<DataBook> results

) {
}
