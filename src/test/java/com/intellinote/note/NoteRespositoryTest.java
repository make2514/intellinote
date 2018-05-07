/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.note;

import com.intellinote.user.User;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author minhdao
 */

@RunWith(SpringRunner.class)
@DataJpaTest
public class NoteRespositoryTest {
    @Autowired
    private TestEntityManager em;
 
    @Autowired
    private NoteRespository nr;
    
    User u1, u2;
    Note n1, n2, n3;
    
    @Before
    public void setup() {
        u1 = new User();
        u1.setUsername("testuser1");
        em.persist(u1);
        
        u2 = new User();
        u2.setUsername("testuser2");
        em.persist(u2);
//        em.flush();
        
        n1 = new Note();
        n1.setName("note1");
        n1.setContent("my first note");
        n1.setUser(u1);
        em.persist(n1);
        
        n2 = new Note();
        n2.setName("note2");
        n2.setContent("my second note");
        n2.setUser(u1);
        em.persist(n2);
        
        n3 = new Note();
        n3.setName("note1");
        n3.setContent("my third note");
        n3.setUser(u2);
        em.persist(n3);

//        em.flush();
    }
    
    @Test
    public void whenFindByUsername_thenReturnNotes() {
        // when
        List<Note> notes = nr.findByUserUsername(u1.getUsername());

        // then
        assertEquals(notes.size(), 2);
        assertEquals(notes.get(0).getName(), n1.getName());
        assertEquals(notes.get(1).getName(), n2.getName());
    }
    
    @Test
    public void whenFindByName_thenReturnNotes(){
        List<Note> notes = nr.findByName("note1");
        
        assertEquals(notes.size(), 2);
        assertEquals(notes.get(0).getContent(), n1.getContent());
        assertEquals(notes.get(1).getContent(), n3.getContent());
    }
}
