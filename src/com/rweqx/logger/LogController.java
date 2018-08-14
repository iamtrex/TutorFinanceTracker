package com.rweqx.logger;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LogController {
    @FXML
    private TextArea tLog;

    public void appendLog(String s){
        tLog.appendText(s);
    }
}
