/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.note;

import com.intellinote.article.Article;
import com.intellinote.article.ArticleRespository;
import com.intellinote.user.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author minhdao
 */
@RestController
@RequestMapping("/users/{userId}/notes")
public class NoteController {
    
    @Autowired
    private NoteRespository nr;
    
    @Autowired
    private ArticleRespository ar;
    
    @GetMapping
    public List<Note> getAllNotes(@PathVariable int userId){
        List<Note> notes = new ArrayList<>();
        nr.findByUserId(userId).forEach(notes::add);
        return notes;
    }
    
    @GetMapping("/{id}")
    public Optional<Note> getNote(@PathVariable int id){
        return nr.findById(id);
    }
    
    @PostMapping
    public int addNote(@RequestBody Note note, @PathVariable int userId){
        note.setUser(new User(userId, "", ""));
        nr.save(note);
        return note.getId();
    }
    
    @PutMapping("/{id}")
    public void updateNote(@RequestBody Note note, @PathVariable int id){
        Note n = nr.getOne(id);
        note.setId(id);
        note.setUser(n.getUser());
        nr.save(note);
    }
    
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable int id){
        nr.deleteById(id);
    }
}
