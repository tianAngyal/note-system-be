package com.angyal.app.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.angyal.app.Models.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angyal.app.Models.NoteGroup;
import com.angyal.app.Repository.NoteGroupRepository;
import com.angyal.app.Repository.NoteRepository;

@Service
public class NoteGroupService {

    @Autowired
    private NoteGroupRepository noteGroupRepository;

    @Autowired
    private NoteRepository noteRepository;

    public List<NoteGroup> getAllNoteGroups() {
        return noteGroupRepository.getNoteGroups();
    }


    public NoteGroup createNoteGroup(NoteGroup noteGroup) {

        ArrayList<Note> notes = noteGroup.getNotes();

        if(!notes.isEmpty()){
            return noteGroupRepository.createGroupWithNotes(noteGroup);
        }else{
            return noteGroupRepository.createEmptyGroup(noteGroup);
        }
        /*NoteGroup newGroup = new NoteGroup(currentId++, name);*/  // ✅ new je OK
        /*if(noteGroup.getNotes())*/
        /*noteGroupRepository.addToGroup(newGroup);*/
        /*return newGroup;*/
    }

    public NoteGroup findNoteGroupById(int id) {
        NoteGroup group = noteGroupRepository.findNoteGroupById(id);
        if (group == null) {
            throw new IllegalArgumentException("NoteGroup with id " + id + " not found.");
        }
        return group;
    }

    public NoteGroup removeNoteGroup(NoteGroup noteGroup) {
        return noteGroupRepository.removeNoteGroupById(noteGroup);
    }

    public NoteGroup updateNoteGroup(NoteGroup noteGroup) {
        return noteGroupRepository.updateNoteGroup(noteGroup);
    }

    public Set<Date> getUniqueCreationTime(){
        return noteGroupRepository.getFilters();
    }

}
