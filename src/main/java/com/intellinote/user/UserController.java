/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.user;

import com.intellinote.utils.EncrytedPasswordUtils;
import com.intellinote.validation.UserValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.core.Authentication; 


/**
 *
 * @author minhdao
 */
@Controller
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserRespository ur;
    
    @Autowired
    private UserValidator uv;

    @Autowired
    protected AuthenticationManager authenticationManager;
    
    public EncrytedPasswordUtils eu = new EncrytedPasswordUtils();
    
    @GetMapping
    public @ResponseBody List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        ur.findAll().forEach(users::add);
        return users;
    }
      
    @GetMapping("/{id}")
    public @ResponseBody Optional<User> getUser(@PathVariable int id){
        return ur.findById(id);
    }
    
    @PostMapping("/registration")
    public String addUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpServletRequest request) {
        String pass = user.getPassword();
        uv.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            bindingResult
                .getFieldErrors()
                .stream()
                .forEach(f -> errors.add(f.getDefaultMessage()));
            model.addAttribute("errors", errors);
            return "registration";
        }
        user.setPassword(eu.encrytePassword(user.getPassword()));
        ur.save(user);
        authenticateUserAndSetSession(user.getUsername(), pass, request);
        return "redirect:/hello";
    }
    
    @PutMapping("/{id}")
    public @ResponseBody void updateUser(@RequestBody User user, @PathVariable int id){
        user.setId(id);
        ur.save(user);
    }
    
    @DeleteMapping("/{id}")
    public @ResponseBody void deleteUser(@PathVariable int id){
        ur.deleteById(id);
    }
    
    private void authenticateUserAndSetSession(String username, String password, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

        // generate session if one doesn't exist
        request.getSession();

        token.setDetails(new WebAuthenticationDetails(request));
        Authentication authenticatedUser = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
