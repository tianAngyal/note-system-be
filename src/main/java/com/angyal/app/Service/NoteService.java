package com.angyal.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angyal.app.Models.Note;
import com.angyal.app.Repository.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    public Note createNewNote(String content) {
        return noteRepository.createNewNote(content);
    }

    public Note findNoteById(int id) {
        return noteRepository.findNoteById(id);
    }

    public Note removeNoteById(int id) {
        return noteRepository.removeNoteById(id);
    }

    public Note updateNoteContent(int id, String newContent) {
        return noteRepository.updateNoteContent(id, newContent);
    }

    public List<Note> getAllNotes() {
        return noteRepository.getArrayOfNotes();
    }
}
