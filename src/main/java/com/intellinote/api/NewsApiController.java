package com.intellinote.api;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/news")
public class NewsApiController {

    private NewsApi newsApi = new NewsApi("newsKey");

    @GetMapping("/{keyword}")
    public @ResponseBody String getArticlesByKeyword(@PathVariable String keyword) {
        String response = newsApi.getArticles(keyword);
        return response;
    }
}
