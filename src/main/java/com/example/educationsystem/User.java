package com.example.educationsystem;

import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<String> lessons = new ArrayList<>();

    public User(String firstname, String lastname, String major, String id, String email, String phone, String role, String picture, String username, String password, String lessons) {
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
        this.lessons = new ArrayList<String>(Arrays.asList(lessons.split(",")));
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getLessons() {
        return lessons;
    }

    public void addLesson(String lessonName){
        lessons.add(lessonName);
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
}
