package com.example.educationsystem;

public class MultipleChoiceQuestion extends Question{
    private String[] options;
    private int correctOption;


    public MultipleChoiceQuestion(int lessonId, int examId, int questionId, double score, String question, String[] options, int correctOption) {
        super(lessonId, examId, questionId, score, question);
        this.options = options;
        this.correctOption = correctOption;
    }
}
