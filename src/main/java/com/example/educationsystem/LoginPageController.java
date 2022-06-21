package com.example.educationsystem;

import Exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
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
    private Label registerMessageLabel;

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
        errorLabel.setVisible(false);
   }

   @FXML
   public void onSignInButtonClicked() {
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
            try {
                HomePageController.setUser(user);
                Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("home_page.fxml")).load()));

            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (RuntimeException e){
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }
   }

    @FXML
    public void onSignUpButtonClicked() {

        errorLabel.setVisible(false);
        User newUser = null;

        try {
            if (firstnameField.getText().length() > 15 || !firstnameField.getText().matches("[a-zA-Z ]+")) throw new InvalidFirstnameException();
            else if (lastnameField.getText().length() > 15 || !lastnameField.getText().matches("[a-zA-Z ]+")) throw new InvalidLastnameException();
            else if (majorField.getText().length() > 15 || !majorField.getText().matches("[a-zA-Z ]+")) throw new InvalidMajorException();
            else if (usernameField.getText().length() < 5 || usernameField.getText().length() > 12 ||
                    !usernameField.getText().matches("[a-zA-Z0-9]+")) throw new InvalidUsernameException();
            else if (passwordField.getText().length() < 8 || passwordField.getText().length() > 12 ||
                    !passwordField.getText().matches("[a-zA-Z0-9]+")) throw new InvalidPasswordException();

            String firstPart = "", secondPart = "", thirdPart = "";
            for (int i = 0; i < emailField.getText().indexOf("@"); i++) {
                firstPart += emailField.getText().charAt(i);
            }
            for (int i = emailField.getText().indexOf("@") + 1; i < emailField.getText().lastIndexOf("."); i++) {
                secondPart += emailField.getText().charAt(i);
            }
            for (int i = emailField.getText().lastIndexOf(".") + 1; i < emailField.getText().length(); i++) {
                thirdPart += emailField.getText().charAt(i);
            }
            if (!(firstPart.matches("[a-zA-Z0-9.]+") && firstPart.length() <= 15 &&
                    secondPart.matches("[a-z0-9.-]+") && secondPart.length() <= 8 &&
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
                    emailField.getText(), phoneField.getText(), roleBox.getValue().toString(), profileImage.getImage().getUrl().toString(),
                    usernameField.getText(), passwordField.getText(), "");
            Database.check_user_info_existence(newUser.getId(), newUser.getUsername(), newUser.getEmail(), newUser.getPhone());
            Database.newUser(newUser);
            registerMessageLabel.setText("New user '" + newUser.getUsername() + "' registered." );
            registerMessageLabel.setVisible(true);

        } catch (RuntimeException e) {
            errorLabel.setVisible(true);
            errorLabel.setText(e.getMessage());
        }

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