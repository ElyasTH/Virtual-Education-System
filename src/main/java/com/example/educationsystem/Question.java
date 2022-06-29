package com.example.educationsystem;

public abstract class Question {
    private int lessonId;
    private  int questionId;
    private String question;
    private float score;
    private static int questionCount = 10000;

    public Question(int lessonId, int questionId, float score, String question) {
        this.lessonId = lessonId;
        if (questionId == 0){
            questionCount = Database.getLastId("questions");
            if (questionCount == 0) questionCount = 10000;
            else questionCount++;
            this.questionId = questionCount;
            questionCount++;
        }
        else this.questionId = questionId;
        this.score = score;
        this.question = question;
        questionCount++;
    }

    public int getLessonId() {
        return lessonId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestion() {
        return question;
    }

    public float getScore() {
        return score;
    }
}
