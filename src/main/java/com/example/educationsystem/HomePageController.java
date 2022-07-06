package com.example.educationsystem;

import Exceptions.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    private static User user;

    @FXML
    private Pane homepagePane;

    @FXML
    private Pane profilePane;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField IdField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField roleField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button saveButton;

    @FXML
    private Label errorLabel;

    @FXML
    private ImageView homepageProfile;

    @FXML
    private Label updateLabel;

    @FXML
    private GridPane coursePane;

    @FXML
    private Button profileButton;

    @FXML
    private Button messengerButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button addNewCourseButton;

    @FXML
    private Pane addCoursePane;

    @FXML
    private TextField studentIdField;

    @FXML
    private Button addStudentButton;

    @FXML
    private TableView studentsTableView;

    @FXML
    private Label studentErrorLabel;

    @FXML
    private TextField titleField;

    @FXML
    private TextField capacityField;

    @FXML
    private Label errorLessonLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private VBox eventBox;

    ObservableList<User> studentsList = FXCollections.observableArrayList();

    @FXML
    public void onProfileButtonClicked(){
        homepagePane.setVisible(false);
        profilePane.setVisible(true);
        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        majorField.setText(user.getMajor());
        IdField.setText(user.getId());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        roleField.setText(user.getRole());
        File image = new File(user.getPicture());
        profileImage.setImage(new Image(image.toURI().toString()));
    }

    @FXML
    public void onExitButtonClicked(){
        homepagePane.setVisible(false);
        try {
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("login_page.fxml")).load()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void onSaveButtonClicked(){
        try{
            if (passwordField.getText().length() < 8 || passwordField.getText().length() > 12 ||
                    !passwordField.getText().matches("[a-zA-Z0-9]+")) throw new InvalidPasswordException();
            String firstPart = "", secondPart = "", thirdPart = "";
            for (int i = 0; i < emailField.getText().indexOf("@"); i++) {
                firstPart += emailField.getText().charAt(i);
            }
            for (int i = emailField.getText().indexOf("@") + 1; i < emailField.getText().lastIndexOf("."); i++) {
                secondPart += emailField.getText().charAt(i);
            }
            for (int i = emailField.getText().lastIndexOf(".") + 1; i < emailField.getText().length(); i++) {
                thirdPart += emailField.getText().charAt(i);
            }
            if (!(firstPart.matches("[a-zA-Z0-9.]+") && firstPart.length() <= 15 &&
                    secondPart.matches("[a-z0-9.-]+") && secondPart.length() <= 8 &&
                    thirdPart.matches("[a-z]+") && thirdPart.length() <= 4)) {
                throw new InvalidEmailException();
            }
            if(! (phoneField.getText().length() == 11 && phoneField.getText().startsWith("09"))){
                throw new InvalidPhoneNumberException();
            }
            user.setPassword(passwordField.getText());
            user.setEmail(emailField.getText());
            user.setPhone(phoneField.getText());
            user.setPicture(profileImage.getImage().getUrl().replace("file:/", ""));
            Database.updateUser(user);
            updateLabel.setVisible(true);
            saveButton.setDisable(true);
        }catch (RuntimeException e){
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    @FXML
    public void onBackButtonClicked(){
        errorLabel.setVisible(false);
        updateLabel.setVisible(false);
        profilePane.setVisible(false);
        addCoursePane.setVisible(false);
        homepagePane.setVisible(true);
    }

    @FXML
    public void onLessonButtonClicked(Lesson lesson){
        try {
            CoursePageController.setUser(user);
            CoursePageController.setLesson(lesson);
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("course_page.fxml")).load()));
        }catch (IOException e ){
            e.printStackTrace();
        }
    }

    @FXML
    public void onUploadImageButtonClicked(){
        saveButton.setDisable(false);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File image = fileChooser.showOpenDialog(profileImage.getScene().getWindow());

        if (image != null){
            profileImage.setImage(new Image(image.toURI().toString()));
        }
    }

    @FXML
    public void onAddNewCourseButtonClicked(){
        homepagePane.setVisible(false);
        addCoursePane.setVisible(true);
    }

    @FXML
    public void onDoneButtonClicked(){
        try {
            if (titleField.getText().equals("") || capacityField.getText().equals("")){
                throw new InvalidLessonException();
            }
            StringBuilder studentIds = new StringBuilder();
            for (User student: studentsList){
                studentIds.append(student.getId()).append(",");
            }
            Lesson newLesson = new Lesson(titleField.getText(), 0, user.getId(), Integer.parseInt(capacityField.getText()),
                    String.valueOf(studentIds), null, null, null, null);
            Database.addLesson(newLesson);
            StringBuilder memberIds = new StringBuilder();
            for (String memberId: newLesson.getStudentIds()){
                memberIds.append(memberId).append(",");
            }
            Database.addMessengerGroup(new MessengerGroup(newLesson.getTitle(), newLesson.getLessonId(), String.valueOf(memberIds),
                    newLesson.getTeacherId(), null));
            user = Database.getUser(user.getId(), true);
            initialize(null, null);
            addCoursePane.setVisible(false);
            homepagePane.setVisible(true);
        }catch (RuntimeException e){
            e.printStackTrace();
            errorLessonLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void onAddNewStudentButtonClicked(){
        studentIdField.setVisible(true);
        addStudentButton.setVisible(true);
    }

    @FXML
    public void onAddStudentButtonClicked(){
        try {
            if(studentsList.contains(Database.getUser(studentIdField.getText() , false))){
                throw new StudentAlreadyExistsException();
            }
            studentsList.add(Database.getUser(studentIdField.getText() , false));
        }catch (RuntimeException e){
            studentErrorLabel.setVisible(true);
            studentErrorLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void onMessengerButtonClicked(){
        try {
            GroupsPageController.setUser(user);
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("groups_page.fxml")).load()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void setUser(User user){
        HomePageController.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"+"\n"+"HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        saveButton.setDisable(true);
        usernameLabel.setText(user.getUsername());
        passwordField.textProperty().addListener(((observableValue, s, t1) -> {
            saveButton.setDisable(false);
        }));
        emailField.textProperty().addListener(((observableValue, s, t1) -> {
            saveButton.setDisable(false);
        }));
        phoneField.textProperty().addListener(((observableValue, s, t1) -> {
            saveButton.setDisable(false);
        }));
        File image = new File(user.getPicture());
        homepageProfile.setImage(new Image(image.toURI().toString()));

        for (int i = 0, lessonNumber = 0; i < 10; i++){
            for (int j = 0; j < 4; j++){
                if (lessonNumber < user.getLessons().size()) {
                    Button lessonButton = new Button(user.getLessons().get(lessonNumber).getTitle());
                    final int lessonId = user.getLessons().get(lessonNumber).getLessonId();
                    lessonButton.setOnAction(e -> onLessonButtonClicked(Database.getLesson(lessonId)));
                    coursePane.add(lessonButton, j, i);
                    lessonNumber++;
                }
            }
        }
        if(user.getRole().equals("Teacher")){
            addNewCourseButton.setVisible(true);
        }

        for(Lesson lesson: user.getLessons()){
            for (Assignment assignment: lesson.getAssignments()){
                if (ChronoUnit.DAYS.between(assignment.getEndDate(), LocalDateTime.now()) <= 14){
                    Label eventLabel = new Label("Assignment: " + assignment.getTitle() + "(" + lesson.getTitle() + ")" +
                            "\nDeadline: " + assignment.getEndDate().toString().replace("T", " "));
                    eventLabel.setWrapText(true);
                    eventLabel.setFont(new Font("System", 15));
                    eventBox.getChildren().add(eventLabel);
                }
            }

            for (Exam exam: lesson.getExams()){
                if (ChronoUnit.DAYS.between(exam.getStartDate(), LocalDateTime.now()) <= 14){
                    Label eventLabel = new Label("Exam: " + exam.getTitle() + "(" + lesson.getTitle() + ")" +
                            "\nStart Time: " + exam.getStartDate().toString().replace("T", " "));
                    eventLabel.setWrapText(true);
                    eventLabel.setFont(new Font("System", 15));
                    eventBox.getChildren().add(eventLabel);
                }
            }
        }
        studentsTableView.getColumns().clear();
        TableColumn<User, String> idColumn = new TableColumn<>("Student ID");
        idColumn.setMinWidth(100);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<User, String> firstnameColumn = new TableColumn<>("Firstname");
        firstnameColumn.setMinWidth(100);
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));

        TableColumn<User, String> lastnameColumn = new TableColumn<>("Lastname");
        lastnameColumn.setMinWidth(100);
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        studentsTableView.getColumns().addAll(idColumn, firstnameColumn , lastnameColumn);

        studentsTableView.setItems(studentsList);
    }
}
