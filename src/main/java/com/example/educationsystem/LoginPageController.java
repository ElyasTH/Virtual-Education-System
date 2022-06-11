package com.example.educationsystem;

import Exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable{

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Pane registerPane;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField passwordRepField;

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
    private Button imageUploadButton;

    @FXML
    private ImageView profileImage;

    @FXML
    private ComboBox roleBox;

    @FXML
    private Pane loginPane;

    @FXML
    private TextField loginIdField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private Label botCodeLabel;

    @FXML
    private TextField botCodeField;

    @FXML
    private Button SignInButton;

    @FXML
    private CheckBox checkRobotBox;

    @FXML
    private Label errorLabel;

    @FXML
    private Button backButton;

    @FXML
    public void onRegisterButtonClicked(){
        backButton.setVisible(true);
        registerButton.setVisible(false);
        loginButton.setVisible(!loginButton.isVisible());
        registerPane.setVisible(true);
        profileImage.setImage(new Image("/avatar.png"));
    }

    @FXML
    public void onLoginButtonClicked(){
        backButton.setVisible(true);
        loginButton.setVisible(false);
        registerButton.setVisible(!registerButton.isVisible());
        loginPane.setVisible(true);
   }

   @FXML
   public void onImNotARobotClicked(){
       System.out.println("Checked");
       checkRobotBox.setDisable(true);
       botCodeLabel.setVisible(true);
       botCodeField.setVisible(true);
       Random rand = new Random();
       int num = rand.nextInt(10000,100000);
       botCodeLabel.setText(String.valueOf(num));
   }

   @FXML
   public void onBackButtonClicked(){
        loginPane.setVisible(false);
        registerPane.setVisible(false);
        loginButton.setVisible(true);
        registerButton.setVisible(true);
        backButton.setVisible(false);
   }

   @FXML
   public void onSignInButtonClicked() {
        backButton.setVisible(false);
        User user;
        try{
            user = Database.getUser(loginIdField.getText());
            if(botCodeField.getText().equals("")){
                throw new EmptyBotCodeException();
            }
            if (!botCodeField.getText().equals(botCodeLabel.getText())){
                throw new WrongBotCodeException();
            }
            if (user != null){
                if (loginPasswordField.getText().equals(user.getPassword())){
                    System.out.println("User " + user.getUsername() + " logged in.");
                }
                else throw new InvalidPasswordException();
            }
            loginPane.setVisible(false);
            errorLabel.setVisible(false);
        } catch (RuntimeException e){
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
       backButton.setVisible(false);
   }

    @FXML
    public void onSignUpButtonClicked() {

        errorLabel.setVisible(false);
        User newUser = null;

        try {
            String firstPart = "", secoundPart = "", thirdPart = "";
            for (int i = 0; i < emailField.getText().indexOf("@"); i++) {
                firstPart += emailField.getText().charAt(i);
            }
            for (int i = emailField.getText().indexOf("@") + 1; i < emailField.getText().lastIndexOf("."); i++) {
                secoundPart += emailField.getText().charAt(i);
            }
            for (int i = emailField.getText().lastIndexOf(".") + 1; i < emailField.getText().length(); i++) {
                thirdPart += emailField.getText().charAt(i);
            }
            if (!(firstPart.matches("[a-zA-Z0-9.]+") && firstPart.length() <= 15 &&
                    secoundPart.matches("[a-z0-9.-]+") && secoundPart.length() <= 8 &&
                    thirdPart.matches("[a-z]+") && thirdPart.length() <= 4)) {
                throw new InvalidEmailException();
            }
            String value = (String) roleBox.getValue();
            if (value == null || value.equals("")) {
                throw new InvalidRoleException();
            }
            if (!IdField.getText().matches("[0-9]+") || (value.equals("Student") && IdField.getText().length() != 10) ||
                    (value.equals("Teacher") && IdField.getText().length() != 6)){
                throw new InvalidIdException();
            }
            if(! (phoneField.getText().length() == 11 && phoneField.getText().startsWith("09"))){
                throw new InvalidPhoneNumberException();
            }
            if(! (passwordField.getText().equals(passwordRepField.getText()))){
                throw new WrongRepeatPasswordException();
            }
            newUser = new User(firstnameField.getText(), lastnameField.getText(), majorField.getText(), IdField.getText(),
                    emailField.getText(), phoneField.getText(), roleBox.getValue().toString(), "test",
                    usernameField.getText(), passwordField.getText());
            Database.check_user_info_existence(newUser.getId(), newUser.getUsername(), newUser.getEmail(), newUser.getPhone());
            Database.newUser(newUser);
        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
        if (newUser != null) {
            System.out.println("New user '" + newUser.getUsername() + "' registered.");
        }
        backButton.setVisible(false);
    }

    @FXML
    public void onUploadImageButtonClicked(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Profile Image");

        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg");
        fileChooser.getExtensionFilters().add(fileExtensions);
        File image = fileChooser.showOpenDialog(registerPane.getScene().getWindow());

        if (image != null){
            profileImage.setImage(new Image(image.toURI().toString()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> roleItems = FXCollections.observableArrayList("Student", "Teacher" , "");
        roleBox.setItems(roleItems);
    }
}