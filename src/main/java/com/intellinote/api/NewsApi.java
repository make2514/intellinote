package com.intellinote.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellinote.article.Article;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Requires api from: <a href="https://newsapi.org/">https://newsapi.org/</a> <br>
 * Store the key into project root folder into file "newsKey"
*/
 public class NewsApi {

    public enum Lang {
        ARABIC("ar"),GERMAN("de"),ENGLISH("en"),SPANISH("es"),FRANCE("fr"),
        HEBREW("he"),ITALIAN("it"),DUTCH("nl"),NORWEGIAN("no"),PORTUGUESE("pt"),
        RUSSIAN("ru"),NORTHERN_SAMI("se"),CHINESE("zh");
        //language ud missing

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

    /**
     *
     * Requires api key from: <a href="https://newsapi.org/">https://newsapi.org/</a> <br>
     * Default parameters are:<br>
     * language: English<br> sort: relevancy<br> pageSize: 6
     * @param pathToKey Give path to file were newsAPI key is stored
     */
    public NewsApi(String pathToKey) {
        loadKey(pathToKey);
        this.language = Lang.ENGLISH;
        this.sortBy = Sort.RELEVANCY;
        this.pageSize = 6;
    }

    /**
     *
     * Requires api key from: <a href="https://newsapi.org/">https://newsapi.org/</a>
     * @param pathToKey Give path to file were newsAPI key is stored
     */
    public NewsApi(String pathToKey,Lang language, Sort sortBy, int pageSize) {
        loadKey(pathToKey);
        this.language = language;
        this.sortBy = sortBy;
        this.pageSize = pageSize;
    }

    public void loadKey(String pathToFile) {
        File keyFile = new File(pathToFile);
        if (keyFile.exists()) {
            try {
                byte[] encoded = Files.readAllBytes(Paths.get("newsKey"));
                apiKey = new String(encoded, Charset.defaultCharset());
                apiKey = apiKey.trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No newsKey file found");
        }
    }

    /**
    *   Fetches news articles by keyword. Returns a string formatted JSON array of articles
    *   @return Signified JSON array of news articles
     */
    public String getArticlesJsonString(String keyword) {
        try {
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

            //Extract
            JSONArray jsonArray = new JSONObject(content.toString()).getJSONArray("articles");

            return jsonArray.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *   Fetches news articles by keyword. Returns a list of Article objects
     *   @return Signified JSON array of news articles
     */
    public List<Article> getArticles(String keyword) {
        Gson gson = new Gson();
        String jsonArray = getArticlesJsonString(keyword);
        Article[] articles = gson.fromJson(jsonArray, Article[].class);
        return new ArrayList<>(Arrays.asList(articles));
    }
}
