package com.intellinote.api;

import com.google.cloud.language.v1.*;

import com.intellinote.keyword.Keyword;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NaturalLanguage {

    LanguageServiceClient language;
    NewsApi newsApi;

    public List<Keyword> searchKeywords(String text) {
        try {
            language = LanguageServiceClient.create();
            //Make the text into document
            Document doc = Document.newBuilder()
                    .setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            // Detects the sentiment of the text
            AnalyzeEntitiesResponse sr = language.analyzeEntities(doc, EncodingType.UTF8);

            ArrayList<Keyword> keywords = new ArrayList<>();
            for (Entity e : sr.getEntitiesList()) {
                keywords.add(new Keyword(e.getName()));
            }
            return keywords;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateArticles(List<Keyword> keywords) {
        newsApi = new NewsApi("newsKey");
        for (Keyword k : keywords) {
            k.setArticles(newsApi.getArticles(k.getWord()));
        }
    }
}
