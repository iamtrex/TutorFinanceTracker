package com.rweqx.logger;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class LoggerUI {

    @FXML
    private TextArea tLog;

    private Stage stage;

    public LoggerUI(){
        stage = new Stage();

        stage.setTitle("Logger");
        try {
            FXMLLoader logLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/logger.fxml"));
            logLoader.setController(this);
            Parent root = logLoader.load();

            Logger.getInstance().getLogList().addListener((ListChangeListener<String>) c->{
                if(c.next()) {
                    if (c.wasPermutated() || c.wasUpdated()) {

                    } else {
                        for(String s : c.getAddedSubList()){
                            tLog.appendText(s);
                        }
                    }
                }
            });

            stage.setScene(new Scene(root, 400, 500));
            stage.setX(0); //puts window on topleft corner of the screen.
            stage.setY(0);

        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Can't even load root :(");
        }

    }

    public void showWindow(){
        if(stage != null) {
            Logger.getInstance().log(this.getClass().getSimpleName(), "Showing Log Window ", LogLevel.D);
            stage.show();
        }
    }
}
