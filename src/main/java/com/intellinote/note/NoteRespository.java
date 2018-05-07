/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.intellinote.note;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author minhdao
 */
public interface NoteRespository extends JpaRepository<Note, Integer> {
    public List<Note> findByUserUsername(String username);
    public List<Note> findByName(String name);
}
