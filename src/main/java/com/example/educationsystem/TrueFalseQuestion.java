package com.example.educationsystem;

public class TrueFalseQuestion extends Question{
    private Boolean correctAnswer;

    public TrueFalseQuestion(int lessonId, int questionId, float score, String question, QuestionType questionType, Boolean correctAnswer ) {
        super(lessonId, questionId, score, question, questionType);
        this.correctAnswer = correctAnswer;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }
}
