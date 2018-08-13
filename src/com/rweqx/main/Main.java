package com.rweqx.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/com/rweqx/ui/sample.fxml"));
        primaryStage.setTitle("Tutor Student Finance Tracker");
        primaryStage.setScene(new Scene(root, 1200, 675));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
