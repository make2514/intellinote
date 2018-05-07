/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.article;

import static org.junit.Assert.assertEquals;
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
public class ArticleRespositoryTest {
    @Autowired
    private TestEntityManager em;
 
    @Autowired
    private ArticleRespository ar;
    
    @Test
    public void whenFindByUrl_thenReturnArticle() {
        // given
        Article a = new Article();
        a.setUrl("testurl.com");
        em.persist(a);
        em.flush();

        // when
        Article found = ar.findByUrl(a.getUrl());

        // then
        assertEquals(found.getUrl(), a.getUrl());
    }
}
