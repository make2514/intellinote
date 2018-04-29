package com.intellinote.api;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NewsApiTest {

    NewsApi newsApi;

    @Before
    public void beforeTest() {
        newsApi = new NewsApi("d93f2e0084744846ad8736d6f363a8fe");
    }

    @Test
    public void getArticlesTest() {
        String responseString = newsApi.getArticles("key");
        assertTrue(!responseString.isEmpty());
    }
}
