package com.intellinote.article;

import com.intellinote.keyword.Keyword;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Article {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String header;
    private String link;
    private String description;
    @ManyToOne
    private Keyword keyword;

    public Article() {
    }

    public Article(int id, String header, String link, String description, Keyword keyword) {
        this.id = id;
        this.header = header;
        this.link = link;
        this.description = description;
        this.keyword = keyword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return header;
    }

    public void setName(String header) {
        this.header = header;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }
    
    
}
