package com.sticollegeiloilo.aqualify;

public class Notes {
    String titleNote;
    String deadline;
    String contentNote;
    String noteId;

    public Notes(){

    }
    public Notes(String titleNote, String deadline, String contentNote, String noteId) {
        this.titleNote = titleNote;
        this.deadline = deadline;
        this.contentNote = contentNote;
        this.noteId = noteId;
    }

    public String getTitleNote() {
        return titleNote;
    }

    public void setNoteTitle(String titleNote) {
        this.titleNote = titleNote;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadlineNote(String deadline) {
        this.deadline = deadline;
    }

    public String getContentNote() {
        return contentNote;
    }

    public void setContentNote(String contentNote) {
        this.contentNote = contentNote;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
