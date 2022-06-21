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

    public Exam(String title, int lessonId,int examId, LocalDate startDate, LocalDate endDate, ArrayList<Question> questions) {
        this.title = title;
        this.lessonId = lessonId;
        if (examId == 0){
            this.examId = examCount;
            examCount++;
        }else{
            this.examId = examId;
        }
        this.startDate = startDate;
        this.endDate = endDate;
        this.questions = questions;
    }
}
