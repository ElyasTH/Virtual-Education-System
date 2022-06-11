package com.example.educationsystem;

import Exceptions.EmptyBotCodeException;
import Exceptions.InvalidPasswordException;
import Exceptions.WrongBotCodeException;
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
    public void onRegisterButtonClicked(){
        registerButton.setVisible(false);
        loginButton.setVisible(!loginButton.isVisible());
        registerPane.setVisible(true);
        profileImage.setImage(new Image("/avatar.png"));
    }

    @FXML
    public void onLoginButtonClicked(){
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
   public void onSignInButtonClicked() {
        User user;
        try{
            user = Database.getUser(loginIdField.getText());
            if(botCodeField.getText().equals("")){
                throw new EmptyBotCodeException();
            }else if (!botCodeField.getText().equals(botCodeLabel.getText())){
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
        }catch(EmptyBotCodeException e){
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }catch(WrongBotCodeException e){
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }catch (RuntimeException e){
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
   }

    @FXML
    public void onSignUpButtonClicked(){
        User newUser = new User(firstnameField.getText(), lastnameField.getText(), majorField.getText(), IdField.getText(),
                emailField.getText(), phoneField.getText(), roleBox.getValue().toString(), profileImage.getImage().getUrl(),
                usernameField.getText(), passwordField.getText());

        try {
            Database.check_user_info_existence(newUser.getId(), newUser.getUsername(), newUser.getEmail(), newUser.getPhone());
            Database.newUser(newUser);
        } catch (RuntimeException e){
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
        System.out.println("New user '" + newUser.getUsername() + "' registered.");
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
        ObservableList<String> roleItems = FXCollections.observableArrayList("Student", "Teacher");
        roleBox.setItems(roleItems);
    }
}