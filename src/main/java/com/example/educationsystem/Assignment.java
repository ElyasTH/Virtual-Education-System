package com.example.educationsystem;

import java.io.File;
import java.time.LocalDate;

public class Assignment {
    private String title;
    private String description;
    private int lessonId;
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;

    public Assignment(String title, String description, int lessonId, int id, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.lessonId = lessonId;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
