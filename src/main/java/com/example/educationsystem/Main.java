package com.example.educationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginPage = new FXMLLoader(Main.class.getResource("login_page.fxml"));
        Scene scene = new Scene(loginPage.load());
        stage.setTitle("Education System");
        stage.setScene(scene);
        stage.show();
        Main.stage = stage;
    }

    public static void changeScene(Scene newScene){
        stage.setScene(newScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}