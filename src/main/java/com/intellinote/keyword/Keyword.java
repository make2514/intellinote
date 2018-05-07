package com.intellinote.keyword;

import com.intellinote.article.Article;

import java.util.List;

public class Keyword {

    private String word;
    private List<Article> articles;
    private float salience;

    public Keyword(String word) {
        this.word = word;
    }

    public Keyword(String word, List<Article> articles) {
        this.word = word;
        this.articles = articles;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public float getSalience() {
        return salience;
    }

    public void setSalience(float salience) {
        this.salience = salience;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Keyword: ");
        sb.append(word);
        for (Article a : articles) {
            sb.append(a);
        }
        return sb.toString();
    }
}
