package com.example.educationsystem;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GroupsPageController implements Initializable {

    private static User user;

    @FXML
    private VBox groupsBox;

    public static void setUser(User user){
        GroupsPageController.user = user;
    }

    @FXML
    public void onBackButtonClicked(){
        try {
            Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("home_page.fxml")).load()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Lesson lesson: user.getLessons()){
            MessengerGroup group = Database.getMessengerGroup(lesson.getLessonId());
            Hyperlink groupLink = new Hyperlink(group.getName());
            groupsBox.getChildren().add(groupLink);
            groupLink.setOnAction(new EventHandler<>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try{
                        MessengerPageController.setUser(user);
                        MessengerPageController.setGroup(group);
                        Main.changeScene(new Scene(new FXMLLoader(Main.class.getResource("messenger_page.fxml")).load()));
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
