package com.example.educationsystem;

import Exceptions.EmptyBotCodeException;
import Exceptions.WrongBotCodeException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.Random;
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
    private Pane loginPane;

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
        System.out.println("Register Clicked");
        registerButton.setVisible(false);
        loginButton.setVisible(!loginButton.isVisible());
        registerPane.setVisible(true);

    }

    @FXML
    public void onLoginButtonClicked(){
        System.out.println("Login clicked");
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
        try{
            if(botCodeField.getText().equals(botCodeLabel.getText())){
                loginPane.setVisible(false);
                errorLabel.setVisible(false);
            }else if(botCodeField.getText().equals("")){
                throw new EmptyBotCodeException();
            }else{
                throw new WrongBotCodeException();
            }
        }catch(EmptyBotCodeException e){
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }catch(WrongBotCodeException e){
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
   }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> rollItems = FXCollections.observableArrayList("Student", "Teacher");
        roleBox.setItems(rollItems);
    }
}