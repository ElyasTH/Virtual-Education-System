package com.example.educationsystem;

import java.util.ArrayList;

public class Lesson {
    private String name;
    private int lessonId;
    private User teacher;
    private int capacity;
    private ArrayList<User> students =new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Content> content =new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();
    private ArrayList<Notice> notices = new ArrayList<>();
    private static int lessonCount = 100;

    public Lesson(String name, int lessonId, User teacher, int capacity, ArrayList<User> students, ArrayList<Assignment> assignments,
                  ArrayList<Content> content, ArrayList<Exam> exams, ArrayList<Notice> notices) {
        this.name = name;
        if (lessonId == 0 ){
            this.lessonId = lessonCount;
            lessonCount++;
        }else{
            this.lessonId = lessonId;
        }
        this.teacher = teacher;
        this.capacity = capacity;
        this.students = students;
        this.assignments = assignments;
        this.content = content;
        this.exams = exams;
        this.notices = notices;
        lessonCount++;
    }
}
