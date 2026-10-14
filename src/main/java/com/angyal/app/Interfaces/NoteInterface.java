package com.angyal.app.Interfaces;

import java.util.List;

import com.angyal.app.Models.Note;

public interface NoteInterface {
    public Note createNewNote(String content);
    public List<Note> getArrayOfNotes();
    public List<Note> addNoteToGroup(Note note);
    public Note findNoteById(int id);
    public Note removeNoteById(int id);


    
}
