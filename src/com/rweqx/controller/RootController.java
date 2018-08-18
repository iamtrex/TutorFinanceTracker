package com.rweqx.controller;

import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

public class RootController extends BaseController{

    private Map<String, Pane> subSceneMap;

    @FXML
    private GridPane rootGrid;

    @FXML
    private HBox toolBar; //TODO Implement better navigation. .

    @FXML
    private StackPane paneHolder;

    private Stack<Pane> history;

    public RootController(){
        subSceneMap = new HashMap<>();
        history = new Stack<>();

    }

    private void addPaneAndController(String name, String file) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        Pane pane = loader.load();

        BaseController bc = loader.getController();
        bc.initModel(this.modelManager, this.sceneModel);

        subSceneMap.put(name, pane);
    }

    private void switchSceneTo(String sceneName) {
        Pane p = subSceneMap.get(sceneName);
        if(p != null){
            if(paneHolder.getChildren().size() > 0)
                history.add((Pane) paneHolder.getChildren().get(0));

            paneHolder.getChildren().setAll(p);
        }

    }

    @Override
    public void initModel(ModelManager modelManager, SceneModel sceneModel){
        super.initModel(modelManager, sceneModel);

        sceneModel.getSceneNameProperty().addListener((obs, oldVal, newVal) ->{
            if(newVal != null){
                switchSceneTo(newVal);
            }
        });

        //Add:
        try {
            addPaneAndController(AddEditClassController.class.getSimpleName(), "/com/rweqx/ui/add-edit-class.fxml");
            addPaneAndController(AddEditPaymentController.class.getSimpleName(), "/com/rweqx/ui/add-edit-payment.fxml");
            addPaneAndController(StudentProfilesListController.class.getSimpleName(), "/com/rweqx/ui/student-profiles-list.fxml");

            //SETUP LEFT PANE.
            FXMLLoader leftPaneLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/left-pane.fxml"));
            Pane leftPane = leftPaneLoader.load();
            LeftPaneController lpc = leftPaneLoader.getController();
            lpc.initModel(modelManager, sceneModel);

            rootGrid.getChildren().add(leftPane);
            GridPane.setRowIndex(leftPane, 0);
            GridPane.setColumnIndex(leftPane, 0);
            System.out.println(rootGrid.getChildren());
        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
