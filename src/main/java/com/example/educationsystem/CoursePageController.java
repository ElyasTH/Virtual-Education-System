package com.example.educationsystem;

import Exceptions.InvalidAssignmentException;
import Exceptions.InvalidContentException;
import Exceptions.InvalidExamException;
import Exceptions.InvalidNoticeException;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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

    ObservableList<Question> questionsList = FXCollections.observableArrayList();

    ArrayList<String> optionsList = new ArrayList<>();

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
        }catch (RuntimeException e){
            errorNewContentLabel.setText(e.getMessage());
            errorNewContentLabel.setVisible(true);
        }
    }

    @FXML
    public void onNewAssignmentDoneButtonClicked(){
        try {
            if(newAssignmentTitleField.getText().equals("") || startDateAssignment.equals(null) || endDateAssignment.equals(null) ||
                        startHourAssignmentField.getText().equals("") || startMinuteAssignmentField.getText().equals("") || endHourAssignmentField.getText().equals("")||
                        endMinuteAssignmentField.getText().equals("") || newAssignmentFileNameField.getText().equals("")){
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
                    endDateExam.getValue().getDayOfMonth(), Integer.parseInt(startHourExamField.getText()),
                    Integer.parseInt(startMinuteExamField.getText()), 0 )), questionsList);
        }catch (RuntimeException e){
            e.printStackTrace();
            errorNewContentLabel.setText(e.getMessage());
        }
    }

    @FXML
    public void onAddNewQuestionButton(){
        addQuestionPane.setVisible(true);
    }

    @FXML
    public void onAddQuestionButtonClicked(){
        if (questionTypeBox.getValue().toString().equals("DescriptiveQuestion")){
            questionsList.add(new DescriptiveQuestion(lesson.getLessonId(),0, Float.parseFloat(scoreField.getText()),
                    questionTitleArea.getText(), QuestionType.DescriptiveQuestion ));
        }else if (questionTypeBox.getValue().toString().equals("MultipleChoiceQuestion")){
            questionsList.add(new MultipleChoiceQuestion(lesson.getLessonId(), 0, Float.parseFloat(scoreField.getText()),
                    questionTitleArea.getText(), QuestionType.MultipleChoiceQuestion, optionsList, Integer.parseInt(answerField.getText())));
        }else if (questionTypeBox.getValue().toString().equals("TrueFalseQuestion")){
            questionsList.add(new TrueFalseQuestion(lesson.getLessonId(), 0, Float.parseFloat(scoreField.getText()),
                    questionTitleArea.getText(), QuestionType.TrueFalseQuestion, Boolean.parseBoolean(answerField.getText())));
        }
    }

    @FXML
    public void onAddOptionButtonClicked(){
        optionsList.add(optionField.getText());
        optionsListView.getItems().add(optionField.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(user.getRole().equals("Teacher")){
            addNewNoticeButton.setVisible(true);
            addNewContentButton.setVisible(true);
            addNewAssignmentButton.setVisible(true);
            addNewExamButton.setVisible(true);
        }
        lessonNameLabel.setText(lesson.getTitle());
        for(int i = 0 , noticeNumber = 0 ; i <= 20 ; i++){
            if(noticeNumber < lesson.getNotices().size()){
                Button noticeButton = new Button(lesson.getNotices().get(noticeNumber).getTitle());
                final int noticeNum = noticeNumber;
                noticeButton.setOnAction(e -> onContentButtonClicked(ContentType.Notice,
                        lesson.getNotices().get(noticeNum).getId()));
                noticePane.add(noticeButton , 0 , i);
                noticeNumber++;
            }
        }
        for(int i = 0 , contentNumber = 0 ; i <= 20 ; i++){
            if(contentNumber < lesson.getContent().size()){
                Button contentButton = new Button(lesson.getContent().get(contentNumber).getTitle());
                final int contentNum = contentNumber;
                contentButton.setOnAction(e -> onContentButtonClicked(ContentType.Content,
                        lesson.getContent().get(contentNum).getId()));
                contentPane.add(contentButton , 0 , i);
                contentNumber++;
            }
        }
        for(int i = 0 , assignmentNumber = 0; i <= 20 ; i++){
            if(assignmentNumber < lesson.getAssignments().size()){
                Button assignmentButton = new Button(lesson.getAssignments().get(assignmentNumber).getTitle());
                final int assignmentNum = assignmentNumber;
                assignmentButton.setOnAction(e -> onContentButtonClicked(ContentType.Assignment,
                        lesson.getAssignments().get(assignmentNum).getId()));
                assignmentPane.add(assignmentButton , 0 , i);
                assignmentNumber++;
            }
        }
        for(int i = 0 , examNumber = 0; i <= 20 ; i++){
            if(examNumber < lesson.getExams().size()){
                Button examButton = new Button(lesson.getExams().get(examNumber).getTitle());
                final int examNum = examNumber;
                examButton.setOnAction(e -> onContentButtonClicked(ContentType.Exam,
                        lesson.getExams().get(examNum).getExamId()));
                examPane.add(examButton , 0 , i);
                examNumber++;
            }
        }

        questionsTable.getColumns().clear();
        TableColumn<Question, String> questionTitleColumn = new TableColumn<>("Question Title");
        questionTitleColumn.setCellValueFactory(new PropertyValueFactory<>("question"));

        TableColumn<Question, Float> scoreColumn = new TableColumn<>("Score");
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

//        TableColumn<Question, QuestionType> questionTypeColumn = new TableColumn<>("Question type");
//        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("questionType"));


        questionsTable.getColumns().addAll(questionTitleColumn, scoreColumn);

        questionsTable.setItems(questionsList);
    }
}