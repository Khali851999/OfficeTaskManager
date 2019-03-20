package com.example.officetaskmanager.modal;

public class data {
    String Title;
    String Note;
    String Date;
    String Id;
    public data()
    {

    }
    public data(String title, String note, String date, String id) {
        Title = title;
        Note = note;
        Date = date;
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }



}
