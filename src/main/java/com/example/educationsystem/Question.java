package com.example.educationsystem;

public abstract class Question {
    private int lessonId;
    private int examId;
    private  int questionId;
    private String question;
    private double score;
    private static int questionCount = 10000;

    public Question(int lessonId, int examId, double score, String question) {
        this.lessonId = lessonId;
        this.examId = examId;
        this.questionId = questionCount;
        this.score = score;
        this.question = question;
        questionCount++;
    }
}
