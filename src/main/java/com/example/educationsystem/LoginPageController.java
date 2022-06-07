package com.example.educationsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable{

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Pane registerPane;

    @FXML
    private Button imageUploadButton;

    @FXML
    private ComboBox roleBox;

    @FXML
    public void onRegisterButtonClicked(){
        System.out.println("Register Clicked");
        registerButton.setVisible(false);
        loginButton.setVisible(!loginButton.isVisible());
        registerPane.setVisible(true);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> rollItems = FXCollections.observableArrayList("Student", "Teacher");
        roleBox.setItems(rollItems);
    }
}