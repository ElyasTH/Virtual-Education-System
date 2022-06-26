package com.example.educationsystem;

import java.time.LocalDate;

public class Assignment {
    private String title;
    private String description;
    private String file;
    private int lessonId;
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private static int idCount = 1000;

    public Assignment(String title, String description, String file, int lessonId, int id, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.file = file;
        this.lessonId = lessonId;
        if (id == 0) {
            idCount = Database.getLastId("assignments");
            if (idCount == 0) idCount = 1000;
            else idCount++;
            this.lessonId = idCount;
        }
        else this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getFile() {
        return file;
    }

    public int getLessonId() {
        return lessonId;
    }

    public int getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
