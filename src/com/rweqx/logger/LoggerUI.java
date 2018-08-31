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

            stage.setScene(new Scene(root, 300, 400));
        } catch (IOException io) {
            io.printStackTrace();
            System.out.println("Can't even load root :(");
        }


        stage.show();}
}
