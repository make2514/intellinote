/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

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
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRespository ur;
    
    @GetMapping
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        ur.findAll().forEach(users::add);
        return users;
    }
    
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable int id){
        return ur.findById(id);
    }
    
    @PostMapping
    public void addUser(@RequestBody User user){
        ur.save(user);
    }
    
    @PutMapping("/{id}")
    public void updateUser(@RequestBody User user, @PathVariable int id){
        user.setId(id);
        ur.save(user);
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        ur.deleteById(id);
    }
}
