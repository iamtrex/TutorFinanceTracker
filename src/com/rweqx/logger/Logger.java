package com.rweqx.logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class Logger {
    private static Logger ourInstance = new Logger();

    public static Logger getInstance() {
        return ourInstance;
    }

    private Stage stage;
    private LogController logController;


    private Logger() {
        stage = new Stage();

        stage.setTitle("Logger");
        try{
            FXMLLoader logLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/logger.fxml"));

            Parent root = logLoader.load();
            logController = logLoader.getController();

            stage.setScene(new Scene(root, 300, 400));

        }catch(IOException io){
            io.printStackTrace();
            System.out.println("Can't even load root :(");
        }

        stage.show();
    }

    public void log(String message, LogLevel level){
        String log = "[" + level.getLogType()+ "] " + message;
        logController.appendLog(log);
        System.out.println(log);
    }

}
