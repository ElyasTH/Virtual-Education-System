package com.example.educationsystem;

import java.util.ArrayList;

public class MultipleChoiceQuestion extends Question{
    private ArrayList<String> options;
    private int correctOption;

    public MultipleChoiceQuestion(int lessonId, int questionId, float score, String question, QuestionType questionType, ArrayList<String> options, int correctOption) {
        super(lessonId, questionId, score, question, questionType);
        this.options = options;
        this.correctOption = correctOption;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}
