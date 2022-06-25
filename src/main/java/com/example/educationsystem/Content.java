package com.example.educationsystem;

public class Content {
    private String title;
    private String description;
    private int lessonId;
    private int id;
    private String file;
    private static int idCount = 1000;

    public Content(String title, String description,int lessonId, int id, String file) {
        this.title = title;
        this.description = description;
        this.lessonId = lessonId;
        if (id == 0){
            this.id = idCount;
            idCount++;
        }else {
            this.id = id;
        }
        this.file = file;
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

    public String getFile() {
        return file;
    }
}
