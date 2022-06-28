package com.example.educationsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Assignment {
    private String title;
    private String description;
    private String file;
    private int lessonId;
    private int id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private static int idCount = 1000;

    public Assignment(String title, String description, String file, int lessonId, int id, LocalDateTime startDate, LocalDateTime endDate) {
        this.title = title;
        this.description = description;
        this.file = file;
        this.lessonId = lessonId;
        if (id == 0) {
            idCount = Database.getLastId("assignments");
            if (idCount == 0) idCount = 1000;
            else idCount++;
            this.id = idCount;
        }
        else this.id = id;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
