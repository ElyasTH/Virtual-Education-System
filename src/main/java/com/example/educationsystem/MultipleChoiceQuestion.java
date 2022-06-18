package com.example.educationsystem;

public class MultipleChoiceQuestion extends Question{
    private String[] options;
    private int correctOption;


    public MultipleChoiceQuestion(int lessonId, int examId, double score, String question, String[] options, int correctOption) {
        super(lessonId, examId, score, question);
        this.options = options;
        this.correctOption = correctOption;
    }
}
