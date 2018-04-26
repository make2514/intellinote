package com.intellinote.note;

import com.intellinote.keyword.Keyword;
import java.util.LinkedList;
import java.util.List;

public class Note {

    private String name;
    private String path;
    private List<Keyword> keywords;

    public Note(String name, String path) {
        this.name = name;
        this.path = path;
        keywords = new LinkedList<>();
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

    public List<Keyword> getKeywords() {
        return keywords;
    }


}
