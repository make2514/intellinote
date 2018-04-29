/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.note;

import com.intellinote.article.ArticleRespository;
import com.intellinote.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 * @author minhdao
 */
@Controller
@RequestMapping("/users/{username}/notes")
public class NoteController {
    
    @Autowired
    private NoteRespository nr;
    
    @GetMapping
    public String getAllNotes(@PathVariable String username, Model model){
        List<Note> notes = new ArrayList<>();
        nr.findByUserUsername(username).forEach(notes::add);
        model.addAttribute("notes", notes);
        return "home";
    }
    
    @GetMapping("/{id}")
    public @ResponseBody Optional<Note> getNote(@PathVariable int id){
        return nr.findById(id);
    }
    
    @PostMapping
    public @ResponseBody int addNote(@RequestBody Note note, @PathVariable int userId){
        note.setUser(new User(userId, "", ""));
        nr.save(note);
        return note.getId();
    }
    
    @PutMapping("/{id}")
    public @ResponseBody void updateNote(@RequestBody Note note, @PathVariable int id){
        Note n = nr.getOne(id);
        note.setId(id);
        note.setUser(n.getUser());
        nr.save(note);
    }
    
    @DeleteMapping("/{id}")
    public @ResponseBody void deleteNote(@PathVariable int id){
        nr.deleteById(id);
    }
}
