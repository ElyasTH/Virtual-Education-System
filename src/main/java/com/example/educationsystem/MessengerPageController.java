package com.example.educationsystem;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MessengerPageController implements Initializable {

    private static Client client;
    private static User user;
    private static MessengerGroup group;
    private static String lastSentMessage;
    private static String lastReceivedMessage;

    @FXML
    private ScrollPane messagePane;
    @FXML
    private VBox messageBox;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;
    @FXML
    private Label titleLabel;

    public static void setUser(User user){
        MessengerPageController.user = user;
    }

    public static User getUser() {
        return user;
    }

    public static MessengerGroup getGroup() {
        return group;
    }

    public static void setGroup(MessengerGroup group) {
        MessengerPageController.group = group;
    }

    @FXML
    public void onSendButtonClicked(){
        String message = messageField.getText();
        if (!message.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text = new Text(message + "      " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                    "-fx-background-color: rgb(15,125,242); " +
                    "-fx-background-radius: 20px;");
            textFlow.setPadding(new Insets(5,10,5,10));
            text.setFill(Color.color(0.934, 0.945, 0.996));

            hBox.getChildren().add(textFlow);
            messageBox.getChildren().add(hBox);

            client.sendMessage(message);
            if (lastSentMessage == null || !lastSentMessage.equals(user.getUsername() + ": " + message + "      " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) +
                    "/,/" + user.getId())) {
                group.addMessage(user.getUsername() + ": " + message + "      " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) +
                        "/,/" + user.getId());
                lastSentMessage = user.getUsername() + ": " + message + "      " + LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) +
                        "/,/" + user.getId();
            }

            messageField.clear();
        }
    }

    @FXML
    public void onExitButtonClicked(){
        try {
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("groups_page.fxml")).load()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                client = new Client(new Socket("localhost", 2831), user, group);
            } catch (IOException e) {
                e.printStackTrace();
            }

            messageBox.heightProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                    messagePane.setVvalue((Double) newValue);
                }
            });

            titleLabel.setText(group.getName());

            messagePane.setFitToWidth(true);
            String lastMessage = "";
            for (String message : group.getMessageList()) {
                if (lastMessage.equals(message.split("/,/")[0])) continue;
                else lastMessage = message.split("/,/")[0];
                if (message.split("/,/")[1].equals(user.getId())) {
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    hBox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(message.split("/,/")[0].replace(user.getUsername() + ": ", ""));
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
                            "-fx-background-color: rgb(15,125,242); " +
                            "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    text.setFill(Color.color(0.934, 0.945, 0.996));

                    hBox.getChildren().add(textFlow);
                    messageBox.getChildren().add(hBox);
                } else {
                    HBox hbox = new HBox();
                    hbox.setAlignment(Pos.CENTER_LEFT);

                    hbox.setPadding(new Insets(5, 5, 5, 10));

                    Text text = new Text(message.split("/,/")[0]);
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                            "-fx-background-radius: 20px;");
                    textFlow.setPadding(new Insets(5, 10, 5, 10));
                    hbox.getChildren().add(textFlow);
                    messageBox.getChildren().add(hbox);
                }
            }
            client.listenForMessage(messageBox);
    }

    public static void addLabel(String message, VBox messageBox, MessengerGroup messengerGroup){
        if (group != messengerGroup || !message.split("/,/")[2].equals(String.valueOf(group.getLessonId()))) return;
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);

        hbox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(message.split("/,/")[0]);
        TextFlow textFlow = new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px;");
        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hbox.getChildren().add(textFlow);
        if (lastReceivedMessage == null || !lastReceivedMessage.equals(message)) {
            group.addMessage(message);
            lastReceivedMessage = message;
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                messageBox.getChildren().add(hbox);
            }
        });
    }
}
