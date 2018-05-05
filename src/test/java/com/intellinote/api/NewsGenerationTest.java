package com.intellinote.api;

import com.intellinote.keyword.Keyword;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class NewsGenerationTest {

    /* DOENSN'T WORK ON TRAVIS
    @Test
    public void handleKeywordsFromTextTest() {
        NewsToKeywordsController nkc = new NewsToKeywordsController();
        List<Keyword> keywords = nkc.handleKeywordsFromText("barack obama");
        System.out.println(keywords);
        assertEquals(keywords.size(), 1);
    }
    */
}
