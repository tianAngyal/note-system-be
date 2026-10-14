package com.angyal.app.Models;

import java.util.ArrayList;
import java.util.Date;

public class NoteGroup {
    private Integer id;
    private String name;
    private ArrayList<Note> notes;
    private Date createdAt;

    public NoteGroup() {
        this.notes = new ArrayList<>();
        this.createdAt = new Date();
    }

    public NoteGroup(int id, String name, ArrayList<Note> notes) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.createdAt = new Date();
    }

    public NoteGroup(int id, String name, Date createdAt) {
        this.id = id;
        this.name = name;
        this.notes = new ArrayList<>();
        this.createdAt = createdAt;
    }

    public NoteGroup(int id, String name) {
        this.id = id;
        this.name = name;
        this.notes = new ArrayList<>();
        this.createdAt = new Date();
    }

    public Integer getId() {
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

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
