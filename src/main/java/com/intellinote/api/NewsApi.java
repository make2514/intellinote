package com.intellinote.api;

import com.intellinote.article.Article;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsApi {

    public enum Lang {
        ARABIC("ar"),GERMAN("de"),ENGLISH("en"),SPANISH("es"),FRANCE("fr"),
        HEBREW("he"),ITALIAN("it"),DUTCH("nl"),NORWEGIAN("no"),PORTUGUESE("pt"),
        RUSSIAN("ru"),NORTHERN_SAMI("se"),CHINESE("zh");
        //ud missing

        private String lang;

        Lang(String lang) { this.lang = lang; }

        public String value() { return lang; }
    }

    public enum Sort {
        RELEVANCY("relevancy"),
        POPULARITY("popularity"),
        PUBLISHED_AT("publishedAt");

        private String by;

        Sort(String by) { this.by = by; }

        public String value() { return by; }
    }

    private static final String ROOT_URL = "https://newsapi.org/v2/everything?";

    private String apiKey;
    private Lang language;
    private Sort sortBy;
    private int pageSize;

    public NewsApi(String apiKey) {
        this.apiKey = apiKey;
        this.language = Lang.ENGLISH;
        this.sortBy = Sort.RELEVANCY;
        this.pageSize = 6;
    }

    /**
    *   Fetches news articles by keyword. Returns a string formatted JSON
    *   @return Signified JSON of news articles
     */
    public String getArticles(String keyword) {
        try {
            //Retrieve news articles from api
            URL url = new URL(ROOT_URL + "language=" + language.value() + "&q=" + keyword +
                    "&sortBy=" + sortBy.value() + "&pageSize=" + pageSize );
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestProperty("x-api-key",apiKey);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(huc.getInputStream())
            );
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            huc.disconnect();
            return content.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
