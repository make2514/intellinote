/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.user;

import com.intellinote.utils.EncrytedPasswordUtils;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 *
 * @author minhdao
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRespositoryTest {
    @Autowired
    private TestEntityManager em;
 
    @Autowired
    private UserRespository ur;
    
    @Test
    public void whenFindByUsername_thenReturnUser() {
        // given
        User testuser = new User();
        testuser.setUsername("testuser");
        
        em.persist(testuser);
//        em.flush();
//        ur.save(testuser);

        // when
        User found = ur.findByUsername(testuser.getUsername());

        // then
        assertEquals(found.getUsername(), testuser.getUsername());
    }
}
