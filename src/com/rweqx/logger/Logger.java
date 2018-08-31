package com.rweqx.logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Logger {

    private static ObservableList<String> observableList;


    public ObservableList getLogList(){
        return observableList;
    }

    private static Logger ourInstance;

    public static Logger getInstance() {
        if(ourInstance == null){
            ourInstance = new Logger();
        }
        return ourInstance;
    }

    private Logger() {
        observableList = FXCollections.observableArrayList();
    }


    public void log(String fromClass, String message, LogLevel level){
        String log = "[" + level.getLogType()+ "][" + fromClass + "] " + message + "\n";
        observableList.add(log);
        System.out.println(log);
    }

}
