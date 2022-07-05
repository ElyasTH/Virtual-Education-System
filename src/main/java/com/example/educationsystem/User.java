package com.example.educationsystem;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class User {
    private String firstname;
    private String lastname;
    private String major;
    private String id;
    private String email;
    private String phone;
    private String role;
    private String picture;
    private String username;
    private String password;
    private ArrayList<Lesson> lessons = new ArrayList<>();
    private HashMap<Integer, ArrayList<Object>> assignmentsContent = new HashMap<>();
    private HashMap<Integer, ArrayList<Object>> examsContent = new HashMap<>();

    public User(String firstname, String lastname, String major, String id, String email, String phone, String role, String picture,
                String username, String password, String lessonIds, String assignmentsContent, String examsContent, boolean getLessons) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.major = major;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.picture = picture.replace("file:/", "");
        this.username = username;
        this.password = password;
        if (lessonIds != null && getLessons && lessonIds.length()>1) {
            for (String lessonId : lessonIds.split(",")) {
                this.lessons.add(Database.getLesson(Integer.parseInt(lessonId)));
            }
        }
        if (assignmentsContent != null && assignmentsContent.length() > 1) {
            for (String assignmentContent : assignmentsContent.split("\n")) {
                String[] content = assignmentContent.split("/,/");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                addAssignmentContent(Integer.parseInt(content[0]), Float.parseFloat(content[1]), content[2],
                        LocalDateTime.parse(content[3].split("\\.")[0].replace("T", " "), formatter));
            }
        }
        if (examsContent != null && examsContent.length() > 1) {
            for (String examContent : examsContent.split("\n")) {
                String[] content = examContent.split("/,/");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                HashMap<Integer, Object> answers = new HashMap<>();
                for (String answer : content[2].split("/@/")) {
                    Question question = Database.getQuestion(Integer.parseInt(answer.split("/:/")[0]));
                    if (question instanceof DescriptiveQuestion) {
                        answers.put(question.getQuestionId(), answer.split("/:/")[1]);
                    } else if (question instanceof MultipleChoiceQuestion) {
                        answers.put(question.getQuestionId(), Integer.parseInt(answer.split("/:/")[1]));
                    } else if (question instanceof TrueFalseQuestion) {
                        answers.put(question.getQuestionId(), Boolean.parseBoolean(answer.split("/:/")[1]));
                    }
                }
                addExamContent(Integer.parseInt(content[0]), Float.parseFloat(content[1]), answers,
                        LocalDateTime.parse(content[3].split("\\.")[0].replace("T", " "), formatter));
            }
        }
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMajor() {
        return major;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public String getPicture() {
        return picture;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<Integer, ArrayList<Object>> getAssignmentsContent() {
        return assignmentsContent;
    }

    public HashMap<Integer, ArrayList<Object>> getExamsContent() {
        return examsContent;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void addLesson(Lesson lesson){
        lessons.add(lesson);
        Database.updateUser(this);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void addAssignmentContent(int assignmentId, float score, String answerFile, LocalDateTime uploadTime){
        ArrayList<Object> content = new ArrayList<>();
        content.add(score);
        content.add(answerFile);
        content.add(uploadTime);
        this.assignmentsContent.put(assignmentId, content);
        Database.updateUser(this);
    }

    public void addExamContent(int examId, float score, HashMap<Integer, Object> answers,  LocalDateTime uploadTime){
        ArrayList<Object> content = new ArrayList<>();
        content.add(score);
        content.add(answers);
        content.add(uploadTime);
        this.examsContent.put(examId, content);
        Database.updateUser(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this.id.equals(((User) obj).getId());
    }
}
