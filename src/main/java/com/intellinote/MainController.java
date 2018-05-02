/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote;

import com.intellinote.user.User;
import com.intellinote.user.UserRespository;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.springframework.security.core.userdetails.User;

/**
 *
 * @author minhdao
 */
@Controller
public class MainController {
    
    @Autowired
    private UserRespository ur;
    
    @RequestMapping(value={"", "/", "/login"})
    public String getLoginForm(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    
    @GetMapping("/registration")
    public String getSignUpForm(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }
    
    @GetMapping("/hello")
    public String hello(Model model, Principal principal){
        org.springframework.security.core.userdetails.User loginedUser = (org.springframework.security.core.userdetails.User) 
                ((Authentication) principal).getPrincipal();
        return "redirect:/users/"+loginedUser.getUsername()+"/notes";
    }
    
    @PostMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }
    
    
}
