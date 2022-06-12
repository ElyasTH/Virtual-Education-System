package com.example.educationsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.temporal.Temporal;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    private static User user;

    @FXML
    private Pane homepagePane;

    @FXML
    private Pane profilePane;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextField firstnameField;

    @FXML
    private TextField lastnameField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField IdField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField roleField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ImageView profileImage;

    @FXML
    private Button profileButton;

    @FXML
    private Button messengerButton;

    @FXML
    private Button exitButton;

    @FXML
    public void onProfileButtonClicked(){
        homepagePane.setVisible(false);
        profilePane.setVisible(true);
        firstnameField.setText(user.getFirstname());
        lastnameField.setText(user.getLastname());
        majorField.setText(user.getMajor());
        IdField.setText(user.getId());
        emailField.setText(user.getEmail());
        phoneField.setText(user.getPhone());
        usernameField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        roleField.setText(user.getRole());

    }

    @FXML
    public void onExitButtonClicked(){
        homepagePane.setVisible(false);
        try {
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("login_page.fxml")).load()));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    public void onSaveButtonClicked(){
        
    }


    @FXML
    public void onUploadImageButtonClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File image = fileChooser.showOpenDialog(profileImage.getScene().getWindow());

        if (image != null){
            profileImage.setImage(new Image(image.toURI().toString()));
        }
    }

    public static void setUser(User user){
        HomePageController.user = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getUsername());
    }
}
