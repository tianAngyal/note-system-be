package com.angyal.app.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.angyal.app.Interfaces.NoteInterface;
import com.angyal.app.Models.Note;

@Repository
public class NoteRepository implements NoteInterface {

    private List<Note> arrayOfNotes = new ArrayList<>();
    private int currentId = 4;

    public NoteRepository() {
        arrayOfNotes.add(new Note(1, "This is the content of the first note."));
        arrayOfNotes.add(new Note(2, "This is the content of the second note."));
        arrayOfNotes.add(new Note(3, "This is the content of the third note."));
    }

    

    @Override
    public Note createNewNote(String content) {

        int id = currentId++;
        Note newNote = new Note(id, content);
        arrayOfNotes.add(newNote);
        return newNote;
    }

    @Override
    public Note findNoteById(int id) {
        return arrayOfNotes.stream().filter(note -> note.getId() == id).findFirst().orElse(null);
        
    }

    @Override
    public Note removeNoteById(int id) {
        Note noteToRemove = findNoteById(id);
        if (noteToRemove != null) {
            arrayOfNotes.remove(noteToRemove);
        }
        return noteToRemove;
    }

    @Override
    public List<Note> getArrayOfNotes() {
        return arrayOfNotes;
    }



    @Override
    public List<Note> addNoteToGroup(Note note) {
        arrayOfNotes.add(note);
        return arrayOfNotes;
    }


    public Note updateNoteContent(int id, String newContent) {
        Note noteToUpdate = findNoteById(id);
        if (noteToUpdate != null) {
            noteToUpdate.setContent(newContent);
        }
        return noteToUpdate;
    }
}



