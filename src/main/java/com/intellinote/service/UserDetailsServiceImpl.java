/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.service;

import com.intellinote.user.User;
import com.intellinote.user.UserRespository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author minhdao
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
    @Autowired
    private UserRespository ur;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = ur.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Can not find user with username " + username);
        }
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantList);
    }
}
