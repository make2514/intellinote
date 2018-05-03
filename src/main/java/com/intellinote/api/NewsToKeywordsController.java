package com.intellinote.api;

import com.intellinote.keyword.Keyword;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsToKeywordsController {

    NaturalLanguage nl = new NaturalLanguage();

    /**
     * Receives text that is first sent to google natural language api.
     * api returns a list of keywords from the text. Lastly news articles are updated to the keywords
     * and the whole set is sent back as response
     * @param text
     * @return List of keywords with articles
     */
    @PostMapping
    public @ResponseBody List<Keyword> handleKeywordsFromText (@RequestBody String text) {
        List<Keyword> articleList = nl.searchKeywords(text);
        nl.updateArticles(articleList);
        return articleList;
    }
}
