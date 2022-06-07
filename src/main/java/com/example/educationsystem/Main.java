package com.example.educationsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginPage = new FXMLLoader(Main.class.getResource("login_page.fxml"));
        Scene scene = new Scene(loginPage.load(), 700, 500);
        stage.setTitle("Education System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}