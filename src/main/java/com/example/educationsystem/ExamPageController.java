package com.example.educationsystem;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ExamPageController implements Initializable {
    private static User user;
    private static Exam exam;
    private HashMap<Question, Object> answerFields = new HashMap<>();

    @FXML
    private GridPane questionsPane;

    @FXML
    private Label hoursTimer;

    @FXML
    private Label minutesTimer;

    @FXML
    private Label secondsTimer;

    Map<Integer, String> numberMap;
    Integer currSeconds;
    Thread thread;

    @FXML
    public void onSubmitButtonClicked(){
        thread.interrupt();
        try {
            ExamInformationController.setExam(exam);
            ExamInformationController.setUser(user);
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("exam_information_page.fxml")).load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<Integer, Object> selectedAnswer = new HashMap<>();
        for (Map.Entry<Question, Object> question: answerFields.entrySet()){
            if (question.getKey() instanceof DescriptiveQuestion){
                selectedAnswer.put(question.getKey().getQuestionId(), ((TextArea)question.getValue()).getText());
            }
            if (question.getKey() instanceof MultipleChoiceQuestion){
                if (((ToggleGroup) question.getValue()).getSelectedToggle() != null){
                    selectedAnswer.put(question.getKey().getQuestionId(), Integer.parseInt(((RadioButton)((ToggleGroup)question.getValue())
                            .getSelectedToggle()).getText().split("\\.")[0]));
                }else {
                    selectedAnswer.put(question.getKey().getQuestionId(), -1);
                }
            }
            if (question.getKey() instanceof TrueFalseQuestion){
                if(((ToggleGroup) question.getValue()).getSelectedToggle() != null){
                    selectedAnswer.put(question.getKey().getQuestionId(), Boolean.parseBoolean(((RadioButton)((ToggleGroup)question.getValue())
                            .getSelectedToggle()).getText()));
                }else {
                    selectedAnswer.put(question.getKey().getQuestionId(), -1);
                }
            }
        }
        user.addExamContent(exam.getExamId(), 0, selectedAnswer, LocalDateTime.now());
        try {
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("exam_information_page.fxml")).load()));
            ExamInformationController.setExam(exam);
            ExamInformationController.setUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer hmsToSeconds(Integer h, Integer m, Integer s){
        Integer hToseconds = h * 3600;
        Integer mToseconds = m * 60;
        Integer total = hToseconds + mToseconds + s;
        return total;
    }
    public LinkedList<Integer> secondsToHms(Integer currSeconds){
        Integer hours = currSeconds / 3600;
        currSeconds = currSeconds%3600;
        Integer minutes = currSeconds / 60;
        currSeconds = currSeconds%60;
        Integer seconds = currSeconds;
        LinkedList<Integer> answer = new LinkedList<>();
        answer.add(hours);
        answer.add(minutes);
        answer.add(seconds);
        return answer;
    }

    public void startCountdown() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true){
                        setOutput();
                        Thread.sleep(1000);
                        if(currSeconds == 0){
                            System.out.println("Finished.");
                            thread.interrupt();
                        }
                        currSeconds -= 1;
                    }
                }catch (Exception e){

                }
            }
        });
        thread.start();
    }

    public void setOutput(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                LinkedList<Integer> currHms = secondsToHms(currSeconds);
                hoursTimer.setText(numberMap.get(currHms.get(0)));
                minutesTimer.setText(numberMap.get(currHms.get(1)));
                secondsTimer.setText(numberMap.get(currHms.get(2)));
            }
        });
    }

    public static void setUser(User user) {
        ExamPageController.user = user;
    }

    public static void setExam(Exam exam) {
        ExamPageController.exam = exam;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberMap =new TreeMap<Integer, String>();
        for (int i = 0; i <= 60; i++){
            if (0 <= i && i <= 9){
                numberMap.put(i, "0" + i);
            }else {
                numberMap.put(i, String.valueOf(i));
            }
        }
        LocalDateTime end = exam.getEndDate();
        LocalDateTime start = exam.getStartDate();
        LocalDateTime temp = LocalDateTime.from(start);
        long hour = temp.until(end, ChronoUnit.HOURS);
        temp = temp.plusHours(hour);
        long minute = temp.until(end, ChronoUnit.MINUTES);
        temp = temp.plusMinutes(minute);
        long second = temp.until(end, ChronoUnit.SECONDS);
        currSeconds = hmsToSeconds((int) hour, (int) minute, (int) second);
        hour = 0;
        minute = 0;
        second = 0;

        startCountdown();

        ArrayList<Question> questions = Database.getExam(exam.getExamId()).getQuestions();
        int i = 0;
        int questionCount = 1;
        for (Question question : questions){
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
                answerFields.put(question, answerAreaField);
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
                answerFields.put(question, trueFalseToggleGroup);
            }
            if (question.getQuestionType().equals("MultipleChoiceQuestion")){
                ToggleGroup multipleChoiceToggleGroup = new ToggleGroup();
                int num = 100;
                int count = 1;
                for (String option : ((MultipleChoiceQuestion)question).getOptions()){
                    RadioButton radioButton = new RadioButton(count +". "+ option);
                    count++;
                    radioButton.setToggleGroup(multipleChoiceToggleGroup);
                    questionPane.getChildren().add(radioButton);
                    radioButton.setLayoutX(10);
                    radioButton.setLayoutY(num);
                    radioButton.setFont(new Font("System", 16));
                    num = num + 30;
                }
                answerFields.put(question, multipleChoiceToggleGroup);
            }
            questionsPane.setPrefHeight(questionsPane.getPrefHeight()+328);
            questionsPane.addRow(i++, questionPane);
        }
    }
}
