package com.intellinote.api;

import com.intellinote.keyword.Keyword;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
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
     * @throws IOException
     */
    @PostMapping
    public @ResponseBody List<Keyword> handleKeywordsFromText (@RequestBody String text) throws IOException {
        if (isKeyFileEmpty()) {
            String keyFilePath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
            try (
                FileOutputStream keyFile = new FileOutputStream(keyFilePath);
                Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                        keyFile, "utf-8"))) {
                System.out.print("Write GOOGLE_API_CREDENTIAL_CONTENT to key file");
                writer.write(System.getenv("GOOGLE_API_CREDENTIAL_CONTENT"));
                writer.close();
                return getArticleList(text);
            }
        } else {
            return getArticleList(text);
        }
    }

    private Boolean isKeyFileEmpty() {
        String keyFilePath = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        File file = new File(keyFilePath);
        if (file.length() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private List<Keyword> getArticleList(String text) {
        List<Keyword> articleList = nl.searchKeywords(text);
        nl.updateArticles(articleList);
        return articleList;
    }
}
