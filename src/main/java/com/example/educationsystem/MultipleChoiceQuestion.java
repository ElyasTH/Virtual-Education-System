package com.example.educationsystem;

public class MultipleChoiceQuestion extends Question{
    private String[] options;
    private int correctOption;

    public MultipleChoiceQuestion(int lessonId, int questionId, float score, String question, String[] options, int correctOption) {
        super(lessonId, questionId, score, question);
        this.options = options;
        this.correctOption = correctOption;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}
