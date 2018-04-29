package com.intellinote.article;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intellinote.api.NewsApi;

public class Article {

    @JsonProperty("author")
    private String author;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("url")
    private String url;
    @JsonProperty("urlToImage")
    private String urlToImage;
    @JsonProperty("publishedAt")
    private String publishedAt;

    public Article() {

    }

    @Override
    public String toString() {
        return "Title: " + title;
    }
}
