package com.example.educationsystem;

import java.time.LocalDate;
import java.util.ArrayList;

public class Exam {
    private String title;
    private int lessonId;
    private int examId;
    private LocalDate startDate;
    private LocalDate endDate;
    private static int examCount = 1000;
    private ArrayList<Question> questions = new ArrayList<>();

    public Exam(String title, int lessonId, LocalDate startDate, LocalDate endDate, ArrayList<Question> questions) {
        this.title = title;
        this.lessonId = lessonId;
        this.examId = examCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.questions = questions;
        examCount++;
    }
}
