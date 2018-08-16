package com.rweqx.controller;

import com.rweqx.controller.RootController;
import com.rweqx.model.DataModel;
import com.rweqx.model.Event;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public class ViewNavigator {

    private static RootController rc;


    public static void setRootController(RootController rc){
        ViewNavigator.rc = rc;
    }
    public static void loadScene(String fxml){
        rc.loadScene(fxml);
    }
    public static void loadScene(Pane p){
        rc.loadScene(p);
    }

}
