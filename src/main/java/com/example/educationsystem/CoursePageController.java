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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    @FXML
    private Button addNewNoticeButton;

    @FXML
    private Button addNewContentButton;

    @FXML
    private Button addNewAssignmentButton;

    @FXML
    private Button addNewExamButton;

    @FXML
    private Pane newNoticePane;

    @FXML
    private Pane newContentPane;

    @FXML
    private Pane newAssignmentPane;

    @FXML
    private Pane newExamPane;

    @FXML
    private Button backButton;

    @FXML
    private TextField newNoticeTitleField;

    @FXML
    private TextArea newNoticeDescriptionField;

    @FXML
    private Label errorNewContentLabel;

    @FXML
    private TextField newContentTitleField;

    @FXML
    private TextArea newContentDescriptionField;

    @FXML
    private TextField newContentFileNameField;

    @FXML
    private TextField newAssignmentTitleField;

    @FXML
    private TextArea newAssignmentDescriptionField;

    @FXML
    private TextField newAssignmentFileNameField;

    @FXML
    private TextField startHourAssignmentField;

    @FXML
    private TextField startMinuteAssignmentField;

    @FXML
    private TextField endHourAssignmentField;

    @FXML
    private TextField endMinuteAssignmentField;

    @FXML
    private DatePicker startDateAssignment;

    @FXML
    private DatePicker endDateAssignment;

    @FXML
    private TextField newExamTitleField;

    @FXML
    private DatePicker startDateExam;

    @FXML
    private DatePicker endDateExam;

    @FXML
    private TextField startHourExamField;

    @FXML
    private TextField startMinuteExamField;

    @FXML
    private TextField endHourExamField;

    @FXML
    private TextField endMinuteExamField;

    @FXML
    private ComboBox questionTypeBox;

    @FXML
    private TextArea questionTitleArea;

    @FXML
    private TextField scoreField;

    @FXML
    private TextField answerField;

    @FXML
    private TextField optionField;

    @FXML
    private Pane addQuestionPane;

    @FXML
    private TableView questionsTable;

    @FXML
    private ListView optionsListView;

    @FXML
    private Label timeLabel;

    @FXML
    private Button addOptionButton;

    @FXML
    private Label optionsLabel;

    ObservableList<Question> questionsList = FXCollections.observableArrayList();

    ArrayList<String> optionsList = new ArrayList<>();

    public static void setUser(User user) {
        CoursePageController.user = user;
    }

    public static void setLesson(Lesson lesson) {
        CoursePageController.lesson = lesson;
    }

    @FXML
    public void onContentHyperlinkClicked(ContentType contentType, int id){
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

    @FXML
    public void onBackBtnClicked(){
        try {
            HomePageController.setUser(user);
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("home_page.fxml")).load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onBackButton(){
        newNoticePane.setVisible(false);
        newContentPane.setVisible(false);
        newAssignmentPane.setVisible(false);
        newExamPane.setVisible(false);
        coursePagePane.setVisible(true);
        backButton.setVisible(false);
        optionsListView.getItems().clear();
        addQuestionPane.setVisible(false);
        errorNewContentLabel.setVisible(false);
    }


    @FXML
    public void onAddNewNoticeButtonClicked(){
        coursePagePane.setVisible(false);
        newNoticePane.setVisible(true);
        backButton.setVisible(true);
    }

    @FXML
    public void onAddNewContentButtonClicked(){
        coursePagePane.setVisible(false);
        newContentPane.setVisible(true);
        backButton.setVisible(true);
    }

    @FXML
    public void onAddNewAssignmentButtonClicked(){
        coursePagePane.setVisible(false);
        newAssignmentPane.setVisible(true);
        backButton.setVisible(true);
    }

    @FXML
    public void onAddExamButtonClicked(){
        coursePagePane.setVisible(false);
        newExamPane.setVisible(true);
        backButton.setVisible(true);
        Question.loadLastId();
        ObservableList<String> questionTypeItems = FXCollections.observableArrayList("DescriptiveQuestion", "MultipleChoiceQuestion", "TrueFalseQuestion");
        questionTypeBox.setItems(questionTypeItems);
    }

    @FXML
    public void onNewNoticeDoneButtonClicked(){
        try {
            if(newNoticeTitleField.getText().equals("") || newNoticeDescriptionField.getText().equals("")){
                throw new InvalidNoticeException();
            }
            Database.addNotice(new Notice(newNoticeTitleField.getText(), newNoticeDescriptionField.getText(),
                    lesson.getLessonId(), 0));
            newNoticePane.setVisible(false);
            coursePagePane.setVisible(true);
            backButton.setVisible(false);
        }catch (RuntimeException e){
            errorNewContentLabel.setText(e.getMessage());
            errorNewContentLabel.setVisible(true);
        }
    }

    @FXML
    public void onNewContentDoneButtonClicked(){
        try {
            if (newContentTitleField.getText().equals("") || newContentDescriptionField.getText().equals("")){
                throw new InvalidContentException();
            }
            Database.addContent(new Content(newContentTitleField.getText(), newContentDescriptionField.getText(),
                    lesson.getLessonId(), 0, newContentFileNameField.getText()));
            newContentPane.setVisible(false);
            coursePagePane.setVisible(true);
            backButton.setVisible(false);
        }catch (RuntimeException e){
            errorNewContentLabel.setText(e.getMessage());
            errorNewContentLabel.setVisible(true);
        }
    }

    @FXML
    public void onQuestionTypeBoxClicked(){
        addQuestionPane.setVisible(true);
        if (questionTypeBox.getValue().toString().equals("DescriptiveQuestion")){
            answerField.setDisable(true);
            optionField.setDisable(true);
            addOptionButton.setDisable(true);
            optionsLabel.setVisible(false);
            optionsListView.setVisible(false);
        }else if (questionTypeBox.getValue().toString().equals("MultipleChoiceQuestion")){
            answerField.setDisable(false);
            optionField.setDisable(false);
            addOptionButton.setDisable(false);
            optionsLabel.setVisible(true);
            optionsListView.setVisible(true);
        }else if (questionTypeBox.getValue().toString().equals("TrueFalseQuestion")) {
            answerField.setDisable(false);
            optionField.setDisable(true);
            addOptionButton.setDisable(true);
            optionsLabel.setVisible(false);
            optionsListView.setVisible(false);
        }
        questionTitleArea.clear();
        scoreField.clear();
        answerField.clear();
        optionField.clear();
        optionsListView.getItems().clear();
    }

    @FXML
    public void onNewAssignmentDoneButtonClicked(){
        try {
            if(newAssignmentTitleField.getText().equals("") || startDateAssignment.equals(null) || endDateAssignment.equals(null) ||
                        startHourAssignmentField.getText().equals("") || startMinuteAssignmentField.getText().equals("") || endHourAssignmentField.getText().equals("")||
                        endMinuteAssignmentField.getText().equals("")){
                throw new InvalidAssignmentException();
            }
            Database.addAssignment(new Assignment(newAssignmentTitleField.getText(), newAssignmentDescriptionField.getText(),
                    newAssignmentFileNameField.getText(), lesson.getLessonId(), 0,
                    LocalDateTime.of(startDateAssignment.getValue().getYear(), startDateAssignment.getValue().getMonthValue(),
                            startDateAssignment.getValue().getDayOfMonth(), Integer.parseInt(startHourAssignmentField.getText()),
                            Integer.parseInt(startMinuteAssignmentField.getText()), 0),
                    LocalDateTime.of(endDateAssignment.getValue().getYear(), endDateAssignment.getValue().getMonthValue(),
                            endDateAssignment.getValue().getDayOfMonth(), Integer.parseInt(endHourAssignmentField.getText()),
                            Integer.parseInt(endMinuteAssignmentField.getText()), 0)));
            newAssignmentPane.setVisible(false);
            coursePagePane.setVisible(true);
            backButton.setVisible(false);
        }catch (RuntimeException e){
            errorNewContentLabel.setText(e.getMessage());
            errorNewContentLabel.setVisible(true);
        }
    }

    @FXML
    public void onNewExamDoneButtonClicked(){
        try {
            if (newExamTitleField.getText().equals("") || startDateExam.equals(null) || endDateExam.equals(null) || startHourExamField.getText().equals("") ||
                        startMinuteExamField.getText().equals("") || endHourExamField.getText().equals("") || endMinuteExamField.getText().equals("")){
                throw new InvalidExamException();
            }
            StringBuilder questionIds = new StringBuilder();
            for (Question question: questionsList){
                questionIds.append(question.getQuestionId()).append(",");
            }
            Database.addExam(new Exam(newExamTitleField.getText(), lesson.getLessonId(), 0, LocalDateTime.of(startDateExam.getValue().getYear(),
                    startDateExam.getValue().getMonthValue(), startDateExam.getValue().getDayOfMonth(), Integer.parseInt(startHourExamField.getText()),
                    Integer.parseInt(startMinuteExamField.getText()), 0), LocalDateTime.of(endDateExam.getValue().getYear(), endDateExam.getValue().getMonthValue(),
                    endDateExam.getValue().getDayOfMonth(), Integer.parseInt(endHourExamField.getText()),
                    Integer.parseInt(endMinuteExamField.getText()), 0 )), questionsList);
            newExamPane.setVisible(false);
            coursePagePane.setVisible(true);
            backButton.setVisible(false);
        }catch (RuntimeException e){
            e.printStackTrace();
            errorNewContentLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void onAddNewQuestionButton(){
        try {
            addQuestionPane.setVisible(true);
            if (questionTypeBox.getValue().toString().equals("DescriptiveQuestion")){
                answerField.setDisable(true);
                optionField.setDisable(true);
                addOptionButton.setDisable(true);
                optionsLabel.setVisible(false);
                optionsListView.setVisible(false);
            }else if (questionTypeBox.getValue().toString().equals("MultipleChoiceQuestion")){
                answerField.setDisable(false);
                optionField.setDisable(false);
                addOptionButton.setDisable(false);
                optionsLabel.setVisible(true);
                optionsListView.setVisible(true);
            }else if (questionTypeBox.getValue().toString().equals("TrueFalseQuestion")){
                answerField.setDisable(false);
                optionField.setDisable(true);
                addOptionButton.setDisable(true);
                optionsLabel.setVisible(false);
                optionsListView.setVisible(false);
            }else {
                throw new InvalidQuestionTypeException();
            }
        }catch (RuntimeException e){
            errorNewContentLabel.setText(e.getMessage());
            errorNewContentLabel.setVisible(true);
        }
    }

    @FXML
    public void onAddQuestionButtonClicked(){
        if (questionTypeBox.getValue().toString().equals("DescriptiveQuestion")){
            questionsList.add(new DescriptiveQuestion(lesson.getLessonId(),0, Float.parseFloat(scoreField.getText()),
                    questionTitleArea.getText(), "DescriptiveQuestion" ));
        }else if (questionTypeBox.getValue().toString().equals("MultipleChoiceQuestion")){
            questionsList.add(new MultipleChoiceQuestion(lesson.getLessonId(), 0, Float.parseFloat(scoreField.getText()),
                    questionTitleArea.getText(), "MultipleChoiceQuestion", optionsList, Integer.parseInt(answerField.getText())));
        }else if (questionTypeBox.getValue().toString().equals("TrueFalseQuestion")){
            questionsList.add(new TrueFalseQuestion(lesson.getLessonId(), 0, Float.parseFloat(scoreField.getText()),
                    questionTitleArea.getText(), "TrueFalseQuestion", Boolean.parseBoolean(answerField.getText())));
        }
        addQuestionPane.setVisible(false);
        questionTitleArea.clear();
        scoreField.clear();
        answerField.clear();
        optionField.clear();
        optionsListView.getItems().clear();
    }

    @FXML
    public void onAddOptionButtonClicked(){
        optionsList.add(optionField.getText());
        optionsListView.getItems().add(optionField.getText());
        optionField.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e ->
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"+"\n"+"HH:mm:ss")))),
                new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        if(user.getRole().equals("Teacher")){
            addNewNoticeButton.setVisible(true);
            addNewContentButton.setVisible(true);
            addNewAssignmentButton.setVisible(true);
            addNewExamButton.setVisible(true);
        }
        lessonNameLabel.setText(lesson.getTitle());
        for(int i = 0 , noticeNumber = 0 ; i <= 20 ; i++){
            if(noticeNumber < lesson.getNotices().size()){
                Hyperlink noticeHyperlink = new Hyperlink(lesson.getNotices().get(noticeNumber).getTitle());
                final int noticeNum = noticeNumber;
                noticeHyperlink.setOnAction(e -> onContentHyperlinkClicked(ContentType.Notice,
                        lesson.getNotices().get(noticeNum).getId()));
                noticePane.add(noticeHyperlink , 0 , i);
                noticeNumber++;
            }
        }
        for(int i = 0 , contentNumber = 0 ; i <= 20 ; i++){
            if(contentNumber < lesson.getContent().size()){
                Hyperlink contentHyperlink = new Hyperlink(lesson.getContent().get(contentNumber).getTitle());
                final int contentNum = contentNumber;
                contentHyperlink.setOnAction(e -> onContentHyperlinkClicked(ContentType.Content,
                        lesson.getContent().get(contentNum).getId()));
                contentPane.add(contentHyperlink , 0 , i);
                contentNumber++;
            }
        }
        for(int i = 0 , assignmentNumber = 0; i <= 20 ; i++){
            if(assignmentNumber < lesson.getAssignments().size()){
                Hyperlink assignmentHyperlink = new Hyperlink(lesson.getAssignments().get(assignmentNumber).getTitle());
                final int assignmentNum = assignmentNumber;
                assignmentHyperlink.setOnAction(e -> onContentHyperlinkClicked(ContentType.Assignment,
                        lesson.getAssignments().get(assignmentNum).getId()));
                assignmentPane.add(assignmentHyperlink , 0 , i);
                assignmentNumber++;
            }
        }
        for(int i = 0 , examNumber = 0; i <= 20 ; i++){
            if(examNumber < lesson.getExams().size()){
                Hyperlink examHyperlink = new Hyperlink(lesson.getExams().get(examNumber).getTitle());
                final int examNum = examNumber;
                examHyperlink.setOnAction(e -> onContentHyperlinkClicked(ContentType.Exam,
                        lesson.getExams().get(examNum).getExamId()));
                examPane.add(examHyperlink , 0 , i);
                examNumber++;
            }
        }

        questionsTable.getColumns().clear();
        TableColumn<Question, String> questionTitleColumn = new TableColumn<>("Question Title");
        questionTitleColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        TableColumn<Question, Float> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        TableColumn<Question, String> questionTypeColumn = new TableColumn<>("Question type");
        questionTypeColumn.setCellValueFactory(new PropertyValueFactory<>("questionType"));


        questionsTable.getColumns().addAll(questionTitleColumn, scoreColumn, questionTypeColumn);

        questionsTable.setItems(questionsList);


    }
}