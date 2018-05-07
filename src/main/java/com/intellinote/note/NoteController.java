/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.note;

import com.intellinote.article.ArticleRespository;
import com.intellinote.service.StorageService;
import com.intellinote.user.User;
import com.intellinote.user.UserRespository;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
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
public class NoteController {
    
    @Autowired
    private NoteRespository nr;
    
    @Autowired
    private UserRespository ur;
    
     @Autowired
    private StorageService ss;
    
    @GetMapping("/users/{username}/notes")
    public String getAllNotes(@PathVariable String username, Model model){
        ss.init();
        List<Note> notes = new ArrayList<>();
        nr.findByUserUsername(username).forEach(notes::add);
        model.addAttribute("notes", notes);
        return "home";
    }
    
    @GetMapping("/users/{username}/notes/{id}")
    public String getNote(@PathVariable int id, Model model) throws FileNotFoundException{
        Note n = nr.getOne(id);
        String content = ss.readFileToString(n.getPath());
        model.addAttribute("name", n.getName());
        model.addAttribute("content", content);
        model.addAttribute("articles", n.getArticles());
        model.addAttribute("update", "true");
        return "note";
    }
    
    @GetMapping("/users/{username}/notes/newnote")
    public String getNewNotePage(Model model){
        model.addAttribute("name", "Untitled");
        model.addAttribute("content", "");
        model.addAttribute("save", "true");
        return "note";
    }
    
    @PostMapping("/auth/users/{username}/notes/newnote")
    public @ResponseBody String addNote(@RequestBody Note note, @PathVariable String username) throws IOException{
        Date now = new Date();
        User u = ur.findByUsername(username);
        List<Note> notes = nr.findByName(note.getName());
        note.setUser(u);
        note.setCreationDate(now);
        if(notes != null){
            note.setPath(ss.store(note.getName()+"("+notes.size()+")", note.getPath()));
        }else{
            note.setPath(ss.store(note.getName(), note.getPath()));
        }
        nr.save(note);
        return ""+note.getId();
    }
    
    @PutMapping("/auth/users/{username}/notes/{id}")
    public @ResponseBody void updateNote(@RequestBody Note note, @PathVariable int id) throws IOException{
        Date now = new Date();
        Note n = nr.getOne(id);
        note.setId(id);
        note.setUser(n.getUser());
        note.setArticles(n.getArticles());
         note.setCreationDate(now);
        note.setPath(ss.store(note.getName(), note.getPath()));
        nr.save(note);
    }
    
    @DeleteMapping("/auth/users/{username}/notes/{id}")
    public @ResponseBody void deleteNote(@PathVariable int id){
        Note n = nr.getOne(id);
        if(ss.removeFile(n.getPath())){
            nr.deleteById(id);
        }
    }
}
