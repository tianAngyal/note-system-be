    package com.angyal.app.Interfaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.angyal.app.Models.Note;
import com.angyal.app.Models.NoteGroup;

public interface NoteGroupInterface {
    
    List<NoteGroup> getNoteGroups();
    NoteGroup createEmptyGroup(NoteGroup noteGroup);
    NoteGroup createGroupWithNotes(NoteGroup noteGroup);
    NoteGroup findNoteGroupById(int id);
    NoteGroup removeNoteGroupById(NoteGroup noteGroup);
}
