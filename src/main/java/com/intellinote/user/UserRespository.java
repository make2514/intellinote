/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.user;

import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author minhdao
 */
public interface UserRespository extends CrudRepository<User, Integer> {
    User findByUsername(String username);
}
