package com.example.educationsystem;

public class TrueFalseQuestion extends Question{
    private Boolean correctAnswer;

    public TrueFalseQuestion(int lessonId, int examId, int questionId, double score, String question, Boolean correctAnswer ) {
        super(lessonId, examId, questionId, score, question);
        this.correctAnswer = correctAnswer;
    }
}
