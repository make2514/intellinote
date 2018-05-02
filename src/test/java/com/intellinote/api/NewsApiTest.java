package com.intellinote.api;

import com.intellinote.article.Article;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class NewsApiTest {

    NewsApi newsApi;

    @Before
    public void setup() {
        newsApi = new NewsApi("newsKey");
    }

    @Test
    public void getArticlesJsonString() {
        String jsonArray = newsApi.getArticlesJsonString("key");
        assertTrue(!jsonArray.isEmpty());
    }

    @Test
    public void getArticlesTest() {
        List<Article> response = newsApi.getArticles("key");
        assertEquals(response.size(), 6);
    }
}
