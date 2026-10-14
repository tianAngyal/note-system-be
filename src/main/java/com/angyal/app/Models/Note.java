package com.angyal.app.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


  public class Note {
    private Integer id;
    private String content;
    private Map<Integer, String> noteTags = new HashMap<>();

    // Default constructor pre deserializáciu JSON
    public Note() {
    }

    public Note(Integer id, String content) {
        this.id = id;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<Integer, String> getNoteTags() {
        return noteTags;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNoteTags(Map<Integer, String> noteTags) {
        this.noteTags = noteTags;
    }
}