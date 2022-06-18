package com.example.educationsystem;

public class Notice {
    private String title;
    private String description;
    private int lessonId;
    private int id;

    public Notice(String title, String description, int lessonId, int id) {
        this.title = title;
        this.description = description;
        this.lessonId = lessonId;
        this.id = id;
    }
}
