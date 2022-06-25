package com.example.educationsystem;

import java.util.ArrayList;

public class Lesson {
    private String title;
    private int lessonId;
    private User teacher;
    private int capacity;
    private ArrayList<User> students =new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Content> content = new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();
    private ArrayList<Notice> notices = new ArrayList<>();
    private static int lessonCount = 100;

    public Lesson(String name, int lessonId, String teacherId, int capacity, String studentIds, String assignmentIds,
                  String contentIds, String examIds, String noticeIds) {
        this.title = name;
        if (lessonId == 0) {
            this.lessonId = lessonCount;
            lessonCount++;
        }
        else this.lessonId = lessonId;
        this.teacher = Database.getUser(teacherId, true);
        this.capacity = capacity;
        if (studentIds != null) setStudents(studentIds);
        if (assignmentIds != null) setAssignments(assignmentIds);
        if (contentIds != null) setContent(contentIds);
        if (examIds != null) setExams(examIds);
        if (noticeIds != null) setNotices(noticeIds);
    }

    public void setStudents(String studentIds) {
        String[] ids = studentIds.split(",");

        for (String id : ids) {
            this.students.add(Database.getUser(id, false));
        }
    }

    public void setAssignments(String assignmentIds) {
        String[] ids = assignmentIds.split(",");

        for (String id : ids) {
            this.assignments.add(Database.getAssignment(Integer.parseInt(id)));
        }
    }

    public void setContent(String contentIds) {
        String[] ids = contentIds.split(",");

        for (String id : ids) {
            this.assignments.add(Database.getAssignment(Integer.parseInt(id)));
        }
    }

    public void setExams(String examIds) {
        String[] ids = examIds.split(",");

        for (String id : ids) {
            this.exams.add(Database.getExam(Integer.parseInt(id)));
        }
    }

    public void setNotices(String noticeIds) {
        String[] ids = noticeIds.split(",");

        for (String id : ids) {
           this.notices.add(Database.getNotice(Integer.parseInt(id)));
        }
    }

    public String getTitle() {
        return title;
    }

    public int getLessonId() {
        return lessonId;
    }

    public User getTeacher() {
        return teacher;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<User> getStudents() {
        return students;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public ArrayList<Content> getContent() {
        return content;
    }

    public ArrayList<Exam> getExams() {
        return exams;
    }

    public ArrayList<Notice> getNotices() {
        return notices;
    }
}
