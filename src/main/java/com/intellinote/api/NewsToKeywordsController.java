package com.intellinote.api;

import com.intellinote.keyword.Keyword;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsToKeywordsController {

    NaturalLanguage nl = new NaturalLanguage();

    @PostMapping
    public @ResponseBody List<Keyword> handleKeywordsFromText (@RequestBody String text) {
        List<Keyword> articleList = nl.searchKeywords(text);
        nl.updateArticles(articleList);
        return articleList;
    }
}
