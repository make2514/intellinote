
package com.intellinote.note;

import com.intellinote.keyword.Keyword;
import com.intellinote.user.User;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author minhdao
 */
@Entity
public class Note {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;
    
    @ManyToOne
    private User user;
    
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="note_keyword", joinColumns = @JoinColumn(name = "note_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(name = "keyword_id", referencedColumnName = "id"))
    private Set<Keyword> keywords = new HashSet<>();
    
    public Note(){
        
    }

    public Note(int id, String name, String path, User user) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<Keyword> keywords) {
        this.keywords = keywords;
    }
    
    
}
