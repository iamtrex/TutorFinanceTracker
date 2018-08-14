package com.rweqx.main;

import com.rweqx.controller.ViewNavigator;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Logger logger;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        logger = Logger.getInstance();

        initRootLayout();

        primaryStage.setTitle("Tutor Student Finance Tracker");
        primaryStage.setScene(new Scene(root, 1200, 675));
        primaryStage.show();
        primaryStage.setOnCloseRequest((e)->{
            Platform.exit();
        });

        logger.log("Done init", LogLevel.D);

    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Terminated program!");
    }


    private void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/root.fxml"));
            root = loader.load();
            ViewNavigator.setRootController(loader.getController());

        }catch(IOException e){

        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
