package com.example.expense_tracker_oop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage;
    private static FXMLLoader loader;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        loader = new FXMLLoader(HelloApplication.class.getResource("logIn_page.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 400);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        loader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
    }

    public static FXMLLoader getLoader() {
        return loader;
    }

    public static void main(String[] args) {
        launch();
    }
}