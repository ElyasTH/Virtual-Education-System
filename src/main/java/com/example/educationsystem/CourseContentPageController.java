package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class CourseContentPageController implements Initializable {

    private static User user;
    private static Lesson lesson;
    private static ContentType contentType;
    private static int contentId;

    @FXML
    private Pane noticePane;

    @FXML
    private Pane assignmentPane;

    @FXML
    private Pane contentPane;

    @FXML
    private Pane examPane;

    @FXML
    private Label noticeTitleLabel;

    @FXML
    private Label noticeDescriptionLabel;

    @FXML
    private Label assignmentRemainingTime;

    @FXML
    private Label assignmentStatusLabel;

    @FXML
    private Label assignmentScoreLabel;

    @FXML
    private Button assignmentFileButton;



    public static void setLesson(Lesson lesson) {
        CourseContentPageController.lesson = lesson;
    }

    public static void setUser(User user) {
        CourseContentPageController.user = user;
    }

    public static void setContentId(int contentId) {
        CourseContentPageController.contentId = contentId;
    }

    public static void setContentType(ContentType contentType) {
        CourseContentPageController.contentType = contentType;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (contentType == ContentType.Content){
            Content content = Database.getContent(contentId);
            contentPane.setVisible(true);
        }
        if (contentType == ContentType.Exam){
            Exam exam =Database.getExam(contentId);
            examPane.setVisible(true);
        }
        if (contentType == ContentType.Assignment){
            Assignment assignment = Database.getAssignment(contentId);
            assignmentPane.setVisible(true);
        }
        if (contentType == ContentType.Notice){
            Notice notice = Database.getNotice(contentId);
            noticePane.setVisible(true);
            noticeTitleLabel.setText(notice.getTitle());
            noticeDescriptionLabel.setText(notice.getDescription());
        }
    }
}
