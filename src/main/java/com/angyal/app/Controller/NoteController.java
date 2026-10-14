package com.angyal.app.Controller;

import com.angyal.app.Models.Note;
import com.angyal.app.Service.NoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/*@CrossOrigin(origins = "http://localhost:4200")*/
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;


    @GetMapping("/")
    public ResponseEntity<List<Note>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable int id){
        Note searchedNote = noteService.findNoteById(id);
        if(searchedNote == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(searchedNote);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Note> deleteNoteGroup(@PathVariable int id) {
        Note deletedNote = noteService.removeNoteById(id);
        if (deletedNote == null) {
            return ResponseEntity.notFound().build();
    }
        return ResponseEntity.ok(deletedNote);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<Note> updateNoteContent(@PathVariable int id, @RequestBody Note note) throws JsonProcessingException {

        String newNoteContent = note.getContent();
        Note noteToUpdate = noteService.updateNoteContent(id, newNoteContent);
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(note);
        System.out.println(json);*/
        if (noteToUpdate == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found");
        }

        return ResponseEntity.ok(noteToUpdate);
    }
}