package com.intellinote.api;

import com.intellinote.keyword.Keyword;
import org.junit.Test;

import java.util.List;

public class NewsGenerationTest {

    @Test
    public void handleKeywordsFromTextTest() {
        NewsToKeywordsController nkc = new NewsToKeywordsController();
        List<Keyword> keywords = nkc.handleKeywordsFromText("");
        System.out.println(keywords);
    }
}
