package com.example.educationsystem;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerPageController implements Initializable {

    Server server;
    Client client;
    private static User user;

    @FXML
    private ScrollPane messagePane;
    @FXML
    private VBox messageBox;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;


    public static void setUser(User user){
        MessengerPageController.user = user;
    }

    public static User getUser() {
        return user;
    }

    @FXML
    public void onSendButtonClicked(){
        String message = messageField.getText();
        if (!message.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text = new Text(message);
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                    "-fx-background-color: rgb(15,125,242); " +
                    "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            messageBox.getChildren().add(hBox);

            client.sendMessage(message);
            messageField.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            this.client = new Client(new Socket("localhost", 2831), user);
        }catch (IOException e){
            e.printStackTrace();
        }

        messageBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                messagePane.setVvalue((Double) newValue);
            }
        });

        client.listenForMessage(messageBox);
    }

    public static void addLabel(String message, VBox messageBox){
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setPadding(new Insets(5,5,5,10));

        Text text = new Text(message);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5,10,5,10));
        hbox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageBox.getChildren().add(hbox);
            }
        });
    }
}
