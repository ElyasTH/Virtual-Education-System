package com.example.educationsystem;

public class TrueFalseQuestion extends Question{
    private Boolean correctAnswer;

    public TrueFalseQuestion(int lessonId, int questionId, float score, String question, Boolean correctAnswer ) {
        super(lessonId, questionId, score, question);
        this.correctAnswer = correctAnswer;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }
}
