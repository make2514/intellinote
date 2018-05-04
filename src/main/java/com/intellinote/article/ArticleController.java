/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.article;

import com.intellinote.note.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author minhdao
 */
@RestController
@RequestMapping("/auth/users/{username}/notes/{noteId}/articles")
public class ArticleController {
    
    @Autowired
    private ArticleRespository ar;
    
    @Autowired
    private NoteRespository nr;
    
    @GetMapping
    public List<Article> getAllArticles(@PathVariable int noteId){
        List<Article> articles = new ArrayList<>();
        nr.getOne(noteId).getArticles().forEach(articles::add);
        return articles;
    }
    
    @GetMapping("/{id}")
    public Optional<Article> getArticle(@PathVariable int id){
        return ar.findById(id);
    }
    
    @PostMapping
    public void addArticle(@RequestBody List<Article> articles, @PathVariable int noteId){
        Note note = nr.getOne(noteId);
        if(articles.size() > 0){
            articles.forEach(article -> {
                Article a = ar.findByUrl(article.getUrl());
                if(a == null){
                    ar.save(article);
                    note.getArticles().add(article);
                }else{
                    note.getArticles().add(a);
                }
            });
            nr.save(note);
        }
    }
    
    @PostMapping("/remove")
    public void removeArticleFromNote(@RequestBody List<Article> articles, @PathVariable int userId, @PathVariable int noteId){
        Note note = nr.getOne(noteId);
        if(articles.size() > 0){        
            articles.forEach(article -> {
                Article a = ar.findByUrl(article.getUrl());
                if(a != null){
                    note.getArticles().remove(a);
                }
            });
            nr.save(note);
        }
    }
    
    @PutMapping("/{id}")
    public void updateArticle(@RequestBody Article article, @PathVariable int id){
        article.setId(id);
        ar.save(article);
    }
    
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable int id){
        ar.deleteById(id);
    }
}
