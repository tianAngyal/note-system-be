package com.angyal.app.Repository;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import com.angyal.app.Interfaces.NoteGroupInterface;
import com.angyal.app.Models.Note;
import com.angyal.app.Models.NoteGroup;

@Repository
public class NoteGroupRepository implements NoteGroupInterface {

    private int currentId = 3;
    
    private List<NoteGroup> noteGroups = new ArrayList<>();

    @Autowired
    private NoteRepository noteRepository;
    
    public NoteGroupRepository(){
        // Konštruktor ostáva prázdny - injektáž sa deje v @PostConstruct
    }
    
    @PostConstruct
    public void init(){
        // Tu je noteRepository už dostupný
        ArrayList<Note> notesG1 = new ArrayList<>();
        ArrayList<Note> notesG2 = new ArrayList<>();

        notesG1.add(noteRepository.findNoteById(1));
        notesG1.add(noteRepository.findNoteById(2));
        notesG2.add(noteRepository.findNoteById(3));

        Date date1 = new Date();

        NoteGroup noteGroup1 = new NoteGroup(1,"Group 1", date1);
        NoteGroup noteGroup2 = new NoteGroup(2,"Group 2", date1);

        noteGroup1.setNotes(notesG1);
        noteGroup2.setNotes(notesG2);
        
        noteGroups.add(noteGroup1);
        noteGroups.add(noteGroup2);
    }


    public List<NoteGroup> getNoteGroups() {
        // // Synchronizujeme obsah notiek s aktuálnym stavom v NoteRepository
        // for (NoteGroup group : noteGroups) {
        //     if (group.getNotes() != null) {
        //         for (Note note : group.getNotes()) {
        //             Note currentNote = noteRepository.findNoteById(note.getId());
        //             if (currentNote != null) {
        //                 note.setContent(currentNote.getContent());
        //             }
        //         }
        //     }
        // }
        return noteGroups;
    }


    public NoteGroup createEmptyGroup(NoteGroup noteGroup) {

        String groupName = noteGroup.getName();
        NoteGroup newGroup = new NoteGroup(currentId++,groupName);
        noteGroups.add(newGroup);
        return newGroup;
    }

    public NoteGroup createGroupWithNotes(NoteGroup noteGroup) {

        String groupName = noteGroup.getName();
        ArrayList<Note> notes = noteGroup.getNotes();


        for(int i = 0; i < notes.size(); i++){
            Note note = notes.get(i);
            /*System.out.println(note.getContent());*/
            Note newNoteWithId = noteRepository.createNewNote(note.getContent());
            notes.set(i, newNoteWithId); // Aktualizujeme původní seznam notek s novými ID

        }

        NoteGroup newGroup = new NoteGroup(currentId++, groupName, notes);
        noteGroups.add(newGroup);  // ✅ PRIDAJ SKUPINU DO ZOZNAMU!
        return newGroup;

    }

    public NoteGroup findNoteGroupById(int id) {
        return noteGroups.stream().filter(group -> group.getId() == id).findFirst().orElse(null);
    }

    public NoteGroup removeNoteGroupById(NoteGroup noteGroup) {
        Integer id = noteGroup.getId();
        NoteGroup groupToRemove = findNoteGroupById(id);
        if (groupToRemove != null) {
            noteGroups.remove(groupToRemove);
            return groupToRemove;
        }else {
            throw new IllegalArgumentException("NoteGroup with id " + id + " not found.");
        }
    }

    public NoteGroup updateNoteGroup(NoteGroup noteGroup) {
        Integer id = noteGroup.getId();
        System.out.println("Updating NoteGroup with id: " + id);
        NoteGroup groupToUpdate = findNoteGroupById(id);
        if (groupToUpdate != null) {
            groupToUpdate.setName(noteGroup.getName());
            groupToUpdate.setNotes(noteGroup.getNotes());
            return groupToUpdate;
        }else{
            throw new IllegalArgumentException("NoteGroup with id " + id + " not found.");
        }

    }          

    public Set<Date> getFilters(){
       /* Set<Date> filters = new HashSet<>();
        getNoteGroups().forEach(group -> filters.add(group.getCreatedAt()));
        return filters;*/

        List<NoteGroup> noteGroups = getNoteGroups();
        return noteGroups.stream()
                .map(NoteGroup::getCreatedAt)
                .collect(Collectors.toSet());
    }

    public void helloWorld(){
        System.out.println("Hello from NoteGroupRepository!");
    }
}
    