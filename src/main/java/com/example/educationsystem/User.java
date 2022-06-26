package com.example.educationsystem;

import java.util.ArrayList;

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

    public User(String firstname, String lastname, String major, String id, String email, String phone, String role, String picture,
                String username, String password, String lessonIds, boolean getLessons) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.major = major;
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.picture = picture;
        this.username = username;
        this.password = password;
        if (lessonIds != null && getLessons) {
            for (String lessonId : lessonIds.split(",")) {
                this.lessons.add(Database.getLesson(Integer.parseInt(lessonId)));
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
}
