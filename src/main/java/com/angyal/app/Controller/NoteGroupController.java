package com.angyal.app.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.angyal.app.Models.Note;
import com.angyal.app.Models.NoteGroup;
import com.angyal.app.Service.NoteGroupService;

/*@CrossOrigin(origins = "http://localhost:4200")*/
@RestController
@RequestMapping("/note-groups")
public class NoteGroupController {
    @Autowired
    private NoteGroupService noteGroupService;

    @GetMapping
    public ResponseEntity<List<NoteGroup>> getAllNoteGroups() {
        return ResponseEntity.ok(noteGroupService.getAllNoteGroups());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteGroup> getNoteGroupById(@PathVariable int id) {
        NoteGroup noteGroup = noteGroupService.findNoteGroupById(id);
        return ResponseEntity.ok(noteGroup);
    }

    @PostMapping({"", "/"})  // Akceptuj oboje /api/note-groups a /api/note-groups/
    public ResponseEntity<NoteGroup> createNoteGroup(@RequestBody NoteGroup noteGroup) {
        /*String name = noteGroup.getName();*/

        return ResponseEntity.ok(noteGroupService.createNoteGroup(noteGroup));
    }

    @PatchMapping("/")
    public ResponseEntity<NoteGroup> updateName(@RequestBody NoteGroup noteGroup) {
        NoteGroup updatedGroup = noteGroupService.updateNoteGroup(noteGroup);
        return ResponseEntity.ok(updatedGroup);
    }


    @DeleteMapping("/")
   public ResponseEntity<NoteGroup> deleteNoteGroup(@RequestParam int id) {
        NoteGroup noteGroup = new NoteGroup();
        noteGroup.setId(id);
        NoteGroup removedGroup = noteGroupService.removeNoteGroup(noteGroup);
        return ResponseEntity.status(HttpStatus.OK).body(removedGroup);
    }

    @GetMapping("/unique-creation-dates")
    public ResponseEntity<Set<Date>> getUniqueCreationDates() {

        return ResponseEntity.ok(noteGroupService.getUniqueCreationTime());
    }


}