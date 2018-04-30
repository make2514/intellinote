package com.intellinote.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NewsApiTest {

    NewsApi newsApi;

    @Before
    public void setup() {
        newsApi = new NewsApi("newsKey");
    }

    @Test
    public void getArticlesTest() {
        String responseString = newsApi.getArticles("key");
        assertTrue(!responseString.isEmpty());
    }
}
