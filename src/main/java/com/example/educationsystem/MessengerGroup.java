package com.example.educationsystem;

import java.util.ArrayList;
import java.util.Arrays;

public class MessengerGroup {
    private String name;
    private int lessonId;
    private ArrayList<String> memberIds = new ArrayList<>();
    private String adminId;
    private ArrayList<String> messageList = new ArrayList<>();

    public MessengerGroup(String name, int lessonId, String memberIds, String adminId, ArrayList<String> messageList) {
        this.name = name;
        this.lessonId = lessonId;
        this.adminId = adminId;
        this.memberIds.addAll(Arrays.asList(memberIds.split(",")));
        if (messageList != null) this.messageList = messageList;
    }

    public String getName() {
        return name;
    }

    public int getLessonId() {
        return lessonId;
    }

    public ArrayList<String> getMemberIds() {
        return memberIds;
    }

    public String getAdminId() {
        return adminId;
    }

    public ArrayList<String> getMessageList() {
        return messageList;
    }

    public void addMessage(String message){
        this.messageList.add(message);
        Database.updateMessengerGroup(this);
    }
}
