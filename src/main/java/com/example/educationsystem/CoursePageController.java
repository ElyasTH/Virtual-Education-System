package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class CoursePageController implements Initializable {

    private static User user;

    @FXML
    private Label lessonNameLabel;

    public static void setUser(User user) {
        CoursePageController.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
