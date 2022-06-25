package com.example.educationsystem;

public class Notice {
    private String title;
    private String description;
    private int lessonId;
    private int id;
    private static int idCount = 1000;

    public Notice(String title, String description, int lessonId, int id) {
        this.title = title;
        this.description = description;
        this.lessonId = lessonId;
        if (id == 0){
            this.id = idCount;
            idCount++;
        }else{
            this.id =id;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getLessonId() {
        return lessonId;
    }

    public int getId() {
        return id;
    }
}
