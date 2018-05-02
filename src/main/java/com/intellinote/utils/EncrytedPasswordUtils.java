/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.utils;

/**
 *
 * @author minhdao
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
public class EncrytedPasswordUtils {
 
    // Encryte Password with BCryptPasswordEncoder
    public String encrytePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }
    
}
