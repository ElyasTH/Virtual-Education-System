package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ExamInformationController implements Initializable {

    private static User user;
    private static Exam exam;

    @FXML
    private Pane informationPane;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label uploadTimeLabel;

    @FXML
    private Label correctionStatusLabel;

    @FXML
    private Button backButton;

    @FXML
    private Label totalScoreLabel;

    @FXML
    private GridPane questionsPane;

    @FXML
    public void onReviewClicked(){
        informationPane.setVisible(false);
        scoreLabel.setVisible(false);
        backButton.setVisible(false);
        ArrayList<Question> questions = Database.getExam(exam.getExamId()).getQuestions();
        int i = 0;
        int questionCount = 1;
        for (Question question : questions){
            String answerToQuestion = null;
            for (Map.Entry<Integer, Object> answer: ((HashMap<Integer, Object>) user.getExamsContent().get(exam.getExamId()).get(1)).entrySet()){
                if (answer.getKey().equals(String.valueOf(question.getQuestionId()))){
                    answerToQuestion = (String) answer.getValue();
                }
            };
            Pane questionPane = new Pane();
            questionPane.setPrefSize(916, 328);
            Label questionLabel = new Label("  Question " + questionCount +" : "+ question.getQuestion());
            questionCount++;
            questionPane.getChildren().add(questionLabel);
            questionLabel.setFont(new Font("System", 18));
            if (question.getQuestionType().equals("DescriptiveQuestion")){
                TextArea answerAreaField =new TextArea();
                answerAreaField.setPromptText("Answer");
                answerAreaField.setPrefSize(800, 200);
                questionPane.getChildren().add(answerAreaField);
                answerAreaField.setLayoutX(10);
                answerAreaField.setLayoutY(70);
                answerAreaField.setText(answerToQuestion);
            }
            if (question.getQuestionType().equals("TrueFalseQuestion")){
                ToggleGroup trueFalseToggleGroup = new ToggleGroup();
                RadioButton radioButton1 = new RadioButton("true");
                RadioButton radioButton2 = new RadioButton("false");
                radioButton1.setToggleGroup(trueFalseToggleGroup);
                radioButton2.setToggleGroup(trueFalseToggleGroup);
                questionPane.getChildren().add(radioButton1);
                radioButton1.setLayoutX(10);
                radioButton1.setLayoutY(150);
                radioButton1.setFont(new Font("System", 16));
                questionPane.getChildren().add(radioButton2);
                radioButton2.setLayoutX(10);
                radioButton2.setLayoutY(180);
                radioButton2.setFont(new Font("System", 16));
                if (radioButton1.getText().equals(answerToQuestion)){
                    radioButton1.setSelected(true);
                }
                if (radioButton2.getText().equals(answerToQuestion)){
                    radioButton2.setSelected(true);
                }
                radioButton1.setDisable(true);
                radioButton2.setDisable(true);
            }
            if (question.getQuestionType().equals("MultipleChoiceQuestion")){
                ToggleGroup multipleChoiceToggleGroup = new ToggleGroup();
                int num = 100;
                int count = 1;
                for (String option : ((MultipleChoiceQuestion)question).getOptions()) {
                    RadioButton radioButton = new RadioButton(count + ". " + option);
                    if (Integer.parseInt(answerToQuestion) == count) radioButton.setSelected(true);
                    count++;
                    radioButton.setToggleGroup(multipleChoiceToggleGroup);
                    questionPane.getChildren().add(radioButton);
                    radioButton.setLayoutX(10);
                    radioButton.setLayoutY(num);
                    radioButton.setFont(new Font("System", 16));
                    num = num + 30;
                    if (radioButton.getText().equals(answerToQuestion)){
                        radioButton.setSelected(true);
                    }
                    radioButton.setDisable(true);
                }
            }
            questionsPane.setPrefHeight(questionsPane.getPrefHeight()+328);
            questionsPane.addRow(i++, questionPane);
        }
    }

    @FXML
    public void onBackButton(){
        try {
            ExamPageController.setUser(user);
            ExamPageController.setExam(exam);
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("exam_page.fxml")).load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUser(User user) {
        ExamInformationController.user = user;
    }

    public static void setExam(Exam exam) {
        ExamInformationController.exam = exam;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user.getExamsContent().containsKey(exam.getExamId())){
            scoreLabel.setText("The score You got in this exam is " + user.getExamsContent().get(exam.getExamId()).get(0) + ".");
            uploadTimeLabel.setText("Uploaded time : " + user.getExamsContent().get(exam.getExamId()).get(2));
            if (((float)user.getExamsContent().get(exam.getExamId()).get(0)) < 0 ){
                uploadTimeLabel.setText("Correction status : Corrected");
            }else{
                uploadTimeLabel.setText("Correction status : Uncorrected");
            }
            ArrayList<Question> questions = exam.getQuestions();
            int totalScore = 0;
            for(Question question : questions){
                totalScore += question.getScore();
            }
            totalScoreLabel.setText("Total score : " + totalScore);

        }
    }
}
