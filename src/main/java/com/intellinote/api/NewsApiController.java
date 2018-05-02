package com.intellinote.api;

import com.intellinote.article.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsApiController {

    private NewsApi newsApi = new NewsApi("newsKey");

    @GetMapping("/{keyword}")
    public @ResponseBody List<Article> getArticlesByKeyword(@PathVariable String keyword) {
        List<Article> response = newsApi.getArticles(keyword);
        return response;
    }
}
