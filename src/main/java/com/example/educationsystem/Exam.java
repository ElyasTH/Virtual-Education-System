package com.example.educationsystem;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Exam {
    private String title;
    private int lessonId;
    private int examId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private static int examCount = 1000;
    private ArrayList<Question> questions;

    public Exam(String title, int lessonId, int examId, LocalDateTime startDate, LocalDateTime endDate, ArrayList<Question> questions) {
        this.title = title;
        this.lessonId = lessonId;
        if (lessonId == 0) {
            examCount = Database.getLastId("exams");
            if (examCount == 0) examCount = 1000;
            else examCount++;
            this.examId = examCount;
        }
        else this.examId = examId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.questions = questions;
    }

    public String getTitle() {
        return title;
    }

    public int getExamId() {
        return examId;
    }

    public int getId() {
        return examId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public int getLessonId() {
        return lessonId;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question){
        this.questions.add(question);
    }
}
