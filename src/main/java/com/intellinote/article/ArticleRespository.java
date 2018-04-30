/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.article;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author minhdao
 */
public interface ArticleRespository extends JpaRepository<Article, Integer> {
//    List<Article> findByNotes_Id(Integer id);
    Article findByLink(String link); 
}
