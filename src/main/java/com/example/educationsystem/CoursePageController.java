package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
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

    @FXML
    private Pane coursePagePane;

    public static void setUser(User user) {
        CoursePageController.user = user;
    }

    public static void setLesson(Lesson lesson) {
        CoursePageController.lesson = lesson;
    }

    @FXML
    public void onContentButtonClicked(ContentType contentType, int id){
        try {
            CourseContentPageController.setUser(user);
            CourseContentPageController.setLesson(lesson);
            CourseContentPageController.setContentType(contentType);
            CourseContentPageController.setContentId(id);
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("course_content_page.fxml")).load()));
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lessonNameLabel.setText(lesson.getTitle());
        for(int i = 0 , noticeNumber = 0 ; i <= 20 ; i++){
            if(noticeNumber < lesson.getNotices().size()){
                Button noticeButton = new Button(lesson.getNotices().get(noticeNumber).getTitle());
                final int noticeNum = noticeNumber;
                noticeButton.setOnAction(e -> onContentButtonClicked(ContentType.Notice, lesson.getNotices().get(noticeNum).getId()));
                noticePane.add(noticeButton , 0 , i);
                noticeNumber++;
            }else{
                if(user.getRole().equals("Teacher")){
                    Button addButton = new Button("Add new notice");
                    noticePane.add(addButton , 0 , i++);
                }
                break;
            }
        }
        for(int i = 0 , contentNumber = 0 ; i <= 20 ; i++){
            if(contentNumber < lesson.getContent().size()){
                Button contentButton = new Button(lesson.getContent().get(contentNumber).getTitle());
                final int contentNum = contentNumber;
                contentButton.setOnAction(e -> onContentButtonClicked(ContentType.Content, lesson.getContent().get(contentNum).getId()));
                contentPane.add(contentButton , 0 , i);
                contentNumber++;
            }else{
                if(user.getRole().equals("Teacher")){
                    Button addButton = new Button("Add new content");
                    contentPane.add(addButton , 0 , i++);
                }
                break;
            }
        }
        for(int i = 0 , assignmentNumber = 0; i <= 20 ; i++){
            if(assignmentNumber < lesson.getAssignments().size()){
                Button assignmentButton = new Button(lesson.getAssignments().get(assignmentNumber).getTitle());
                final int assignmentNum = assignmentNumber;
                assignmentButton.setOnAction(e -> onContentButtonClicked(ContentType.Assignment, lesson.getAssignments().get(assignmentNum).getId()));
                assignmentPane.add(assignmentButton , 0 , i);
                assignmentNumber++;
            }else{
                if(user.getRole().equals("Teacher")){
                    Button addButton = new Button("Add new assignment");
                    assignmentPane.add(addButton , 0 , i++);
                }
                break;
            }
        }
        for(int i = 0 , examNumber = 0; i <= 20 ; i++){
            if(examNumber < lesson.getExams().size()){
                Button examButton = new Button(lesson.getExams().get(examNumber).getTitle());
                final int examNum = examNumber;
                examButton.setOnAction(e -> onContentButtonClicked(ContentType.Exam,lesson.getExams().get(examNum).getExamId()));
                examPane.add(examButton , 0 , i);
                examNumber++;
            }else{
                if(user.getRole().equals("Teacher")){
                    Button addButton = new Button("Add new exam");
                    examPane.add(addButton , 0 , i++);
                }
                break;
            }
        }
    }

}