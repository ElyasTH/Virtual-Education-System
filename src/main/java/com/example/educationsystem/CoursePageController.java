package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursePageController implements Initializable {

    private static User user;
    private static Lesson lesson;

    @FXML
    private Label lessonNameLabel;

    @FXML
    private GridPane noticePane;

    @FXML
    private GridPane contentPane;

    @FXML
    private GridPane assignmentPane;

    @FXML
    private GridPane examPane;

    public static void setUser(User user) {
        CoursePageController.user = user;
    }

    public static void setLesson(Lesson lesson) {
        CoursePageController.lesson = lesson;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lessonNameLabel.setText("Lesson : " + lesson.getName());
        for(int i = 0 , noticeNumber = 0 ; i <= 20 ; i++){
            if(noticeNumber < lesson.getNotices().size()){
                Button noticeButton = new Button(lesson.getNotices().get(noticeNumber).getTitle());
                noticePane.add(noticeButton , 0 , i);
                noticeNumber++;
            }
        }
        for(int i = 0 , contentNumber = 0 ; i <= 20 ; i++){
            if(contentNumber < lesson.getContent().size()){
                Button contentButton = new Button(lesson.getContent().get(contentNumber).getTitle());
                contentPane.add(contentButton , 0 , i);
                contentNumber++;
            }
        }
        for(int i = 0 , assignmentNumber = 0; i <= 20 ; i++){
            if(assignmentNumber < lesson.getAssignments().size()){
                Button assignmentButton = new Button(lesson.getAssignments().get(assignmentNumber).getTitle());
                assignmentPane.add(assignmentButton , 0 , i);
                assignmentNumber++;
            }
        }
        for(int i = 0 , examNumber = 0; i <= 20 ; i++){
            if(examNumber < lesson.getExams().size()){
                Button examButton = new Button(lesson.getExams().get(examNumber).getTitle());
                examPane.add(examButton , 0 , i);
                examNumber++;
            }
        }
    }

}
