package com.example.educationsystem;

import java.util.ArrayList;
import java.util.Arrays;

public class Lesson {
    private String title;
    private int lessonId;
    private String teacherId;
    private int capacity;
    private ArrayList<String> studentIds =new ArrayList<>();
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Content> content = new ArrayList<>();
    private ArrayList<Exam> exams = new ArrayList<>();
    private ArrayList<Notice> notices = new ArrayList<>();
    private static int lessonCount = 100;

    public Lesson(String name, int lessonId, String teacherId, int capacity, String studentIds, String assignmentIds,
                  String contentIds, String examIds, String noticeIds) {

        this.title = name;
        if (lessonId == 0) {
            lessonCount = Database.getLastId("lessons");
            if (lessonCount == 0) lessonCount = 100;
            else lessonCount++;
            this.lessonId = lessonCount;
        }
        else this.lessonId = lessonId;
        this.teacherId = teacherId;
        this.capacity = capacity;
        if (studentIds != null && studentIds.length() > 1) setStudents(studentIds);
        if (assignmentIds != null && assignmentIds.length() > 1) setAssignments(assignmentIds);
        if (contentIds != null && contentIds.length() > 1) setContent(contentIds);
        if (examIds != null && examIds.length() > 1) setExams(examIds);
        if (noticeIds != null && noticeIds.length() > 1) setNotices(noticeIds);
    }

    public void setStudents(String studentIds) {
        this.studentIds.addAll(Arrays.asList(studentIds.split(",")));
    }

    public void setAssignments(String assignmentIds) {
        String[] ids = assignmentIds.split(",");
        try{
            for (String id : ids) {
                this.assignments.add(Database.getAssignment(Integer.parseInt(id)));
            }
        }catch (RuntimeException ignored){}
    }

    public void setContent(String contentIds) {
        String[] ids = contentIds.split(",");
        try {
            for (String id : ids) {
                this.content.add(Database.getContent(Integer.parseInt(id)));
            }
        }catch (RuntimeException ignored){};
    }

    public void setExams(String examIds) {
        String[] ids = examIds.split(",");
        try {
            for (String id : ids) {
                this.exams.add(Database.getExam(Integer.parseInt(id)));
            }
        } catch (RuntimeException ignored){};
    }

    public void setNotices(String noticeIds) {
        String[] ids = noticeIds.split(",");
        try {
            for (String id : ids) {
                this.notices.add(Database.getNotice(Integer.parseInt(id)));
            }
        }catch (RuntimeException ignored){};
    }

    public String getTitle() {
        return title;
    }

    public int getLessonId() {
        return lessonId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getStudentIds() {
        return studentIds;
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

    public void addNotice(Notice notice){
        this.notices.add(notice);
    }

    public void addAssignment(Assignment assignment){
        this.assignments.add(assignment);
    }

    public void addContent(Content content){
        this.content.add(content);
    }

    public void addExam(Exam exam){
        this.exams.add(exam);
    }
}
