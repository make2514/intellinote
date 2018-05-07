package com.intellinote.api;

import com.google.cloud.language.v1.*;

import com.intellinote.keyword.Keyword;
import org.apache.tomcat.jni.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * @author Markus
 */
public class NaturalLanguage {

    LanguageServiceClient language;
    NewsApi newsApi;

    /**
     * Uses natural language API to find relevant keywords from parameter text
     * @return list of keyword objects created from the api response
     */
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
                Keyword keyword = new Keyword(e.getName());
                keyword.setSalience(e.getSalience());
                keywords.add(keyword);
            }
            return keywords;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds articles to the keywords
     * @param keywords
     */
    public void updateArticles(List<Keyword> keywords) {
        newsApi = new NewsApi();
        for (Keyword k : keywords) {
            k.setArticles(newsApi.getArticles(k.getWord()));
        }
    }

    public void trimListBySalience(List<Keyword> keywords, float salience) {
        for (Keyword k : keywords) {
            if (k.getSalience() < salience) {
                keywords.remove(k);
            }
        }
    }

    /**
     * Returns a trimmed list of keywords that are filtered by keyword's
     * salience value.
     * @param proportion is value between 0-1 and represents the percentage
     *                   value of how many keywords are left to the returned list
     */
    public List<Keyword> trimListByProportion(List<Keyword> keywords, float proportion) {
        if (keywords.isEmpty()) {
            System.out.println("trimListByProportion: List is empty");
            return null;
        } else if (proportion > 1 || proportion < 0) {
            System.out.println("Proportion value must be in between 0-1");
            return null;
        }
        int ammount = (int)(keywords.size() * proportion);
        System.out.println(ammount);
        Collections.sort(keywords);
        List<Keyword> newList = new ArrayList();
        for (int i = 0; i < ammount; i++) {
            newList.add(keywords.get(i));
        }
        return newList;
    }
}
