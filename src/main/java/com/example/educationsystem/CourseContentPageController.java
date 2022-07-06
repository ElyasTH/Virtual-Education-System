package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
    private Label assignmentTitleLabel;

    @FXML
    private Label examTitleLabel;

    @FXML
    private Label examRemainingTime;

    @FXML
    private Label examStatusLabel;

    @FXML
    private Label examDurationLabel;

    @FXML
    private Label examScoreLabel;

    @FXML
    private Label contentDescriptionLabel;

    @FXML
    private Label contentTitleLabel;

    @FXML
    private Label errorExamLabel;

    @FXML
    private Button startExamButton;


    @FXML
    public void onBackButton(){
        try {
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("course_page.fxml")).load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onStartExamButtonClicked(){
        if(!(user.getExamsContent().containsKey(contentId))){
            if(LocalDateTime.now().isBefore(Database.getExam(contentId).getEndDate()) &&
                    LocalDateTime.now().isAfter(Database.getExam(contentId).getStartDate())){
                try {
                    ExamPageController.setExam(Database.getExam(contentId));
                    ExamPageController.setUser(user);
                    Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("exam_page.fxml")).load()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                if (LocalDateTime.now().isBefore(Database.getExam(contentId).getEndDate())) {
                    errorExamLabel.setText("The time for this exam is over.");
                    errorExamLabel.setVisible(true);
                    startExamButton.setDisable(true);
                }else if (LocalDateTime.now().isAfter(Database.getExam(contentId).getStartDate())){
                    errorExamLabel.setText("This exam has not started yet.");
                    errorExamLabel.setVisible(true);
                    startExamButton.setDisable(true);
                }
            }
        }else{
            errorExamLabel.setText("You have already taken this exam.");
            errorExamLabel.setVisible(true);
        }
    }

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

    @FXML
    public void onOpenContentFileClicked(){
        File file = new File(Database.getContent(contentId).getFile().replace("file:/", "").replace("%20", " "));
        try{
            Desktop.getDesktop().open(file);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if (contentType == ContentType.Content){
            Content content = Database.getContent(contentId);
            contentPane.setVisible(true);
            contentTitleLabel.setText(content.getTitle());
            contentDescriptionLabel.setText(content.getDescription());
        }
        if (contentType == ContentType.Exam){
            Exam exam =Database.getExam(contentId);
            examPane.setVisible(true);
            examTitleLabel.setText(exam.getTitle());
            LocalDateTime from = exam.getStartDate();
            LocalDateTime to = LocalDateTime.now();
            LocalDateTime fromTemp = LocalDateTime.from(from);
            long years = fromTemp.until(to, ChronoUnit.YEARS);
            fromTemp = fromTemp.plusYears(years);
            long months =fromTemp.until(to, ChronoUnit.MONTHS);
            fromTemp =fromTemp.plusMonths(months);
            long days = fromTemp.until(to, ChronoUnit.DAYS);
            fromTemp = fromTemp.plusDays(days);
            long hours = fromTemp.until(to, ChronoUnit.HOURS);
            fromTemp = fromTemp.plusHours(hours);
            long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
            if (years >= 0 && months >= 0 && days >= 0 && hours >= 0 && minutes >= 0){
                examRemainingTime.setText(years + " years, " + months + " months, " + days + " days, " + hours + " hours, " + minutes + " minutes" + " have passed.");
            }else if (years <= 0 && months <= 0 && days <= 0 && hours <= 0 && minutes <= 0){
                years = -1 * years;
                months = -1 * months;
                days = -1 * days;
                hours = -1 * hours;
                minutes = -1 * minutes;
                examRemainingTime.setText(years + " years, " + months + " months, " + days + " days, " + hours + " hours, " + minutes + " minutes " + "left.");
            }

            LocalDateTime end = exam.getEndDate();
            LocalDateTime start = exam.getStartDate();
            LocalDateTime temp = LocalDateTime.from(start);
            long hour = temp.until(end, ChronoUnit.HOURS);
            temp = temp.plusHours(hour);
            long minute = temp.until(end, ChronoUnit.MINUTES);
            examDurationLabel.setText(hour + " hour, " + minute + " minute");

            if (user.getExamsContent().get(exam.getId()) != null){
                examStatusLabel.setText("Uploaded");
            }else{
                examStatusLabel.setText("Not Uploaded");
            }
            if (user.getExamsContent().get(exam.getId()) != null){
                try {
                    examScoreLabel.setText(user.getAssignmentsContent().get(exam.getId()).get(0).toString());
                }catch (NullPointerException e){
                    examScoreLabel.setText("-");
                }
            }else {
                examScoreLabel.setText("-");
            }
        }
        if (contentType == ContentType.Assignment){
            Assignment assignment = Database.getAssignment(contentId);
            assignmentPane.setVisible(true);
            assignmentTitleLabel.setText(assignment.getTitle());
            LocalDateTime from = assignment.getEndDate();
            LocalDateTime to = LocalDateTime.now();
            LocalDateTime fromTemp = LocalDateTime.from(from);
            long years = fromTemp.until(to, ChronoUnit.YEARS);
            fromTemp = fromTemp.plusYears(years);
            long months =fromTemp.until(to, ChronoUnit.MONTHS);
            fromTemp =fromTemp.plusMonths(months);
            long days = fromTemp.until(to, ChronoUnit.DAYS);
            fromTemp = fromTemp.plusDays(days);
            long hours = fromTemp.until(to, ChronoUnit.HOURS);
            fromTemp = fromTemp.plusHours(hours);
            long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
            fromTemp = fromTemp.plusMinutes(minutes);
            if (years >= 0 && months >= 0 && days >= 0 && hours >= 0 && minutes >= 0){
                assignmentRemainingTime.setText(years + " years, " + months + " months, " + days + " days, " + hours + " hours, " + minutes + " minutes " + "have passed.");
            }else if (years <= 0 && months <= 0 && days <= 0 && hours <= 0 && minutes <= 0){
                years = -1 * years;
                months = -1 * months;
                days = -1 * days;
                hours = -1 * hours;
                minutes = -1 * minutes;
                assignmentRemainingTime.setText(years + " years, " + months + " months, " + days + " days, " + hours + " hours, " + minutes + " minutes " + "left.");
            }
            if (user.getAssignmentsContent().get(assignment.getId()) != null){
                assignmentStatusLabel.setText("Uploaded");
            }else{
                assignmentStatusLabel.setText("Not Uploaded");
            }
            if ( user.getAssignmentsContent().get(assignment.getId()) != null){
                assignmentScoreLabel.setText(user.getAssignmentsContent().get(assignment.getId()).get(0).toString());
            }else {
                assignmentScoreLabel.setText("-");
            }
        }
        if (contentType == ContentType.Notice){
            Notice notice = Database.getNotice(contentId);
            noticePane.setVisible(true);
            noticeTitleLabel.setText(notice.getTitle());
            noticeDescriptionLabel.setText(notice.getDescription());
        }
    }
}
