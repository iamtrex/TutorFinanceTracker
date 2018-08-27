package com.rweqx.controller;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class RootController extends BaseController{

    private Map<String, Pane> subSceneMap;
    private Map<Pane, BaseController> subSceneControllerMap;


    @FXML
    private GridPane rootGrid;

    @FXML
    private HBox toolBar; //TODO Implement better navigation. .

    @FXML
    private StackPane paneHolder;

    private Stack<Pane> history;
    //private Stack<String> history;


    public RootController(){
        subSceneMap = new HashMap<>();
        subSceneControllerMap = new HashMap<>();

        history = new Stack<>();

    }

    private void addPaneAndController(String name, String file) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource(file));
        Pane pane = loader.load();

        BaseController bc = loader.getController();
        bc.initModel(this.modelManager, this.sceneModel);

        subSceneMap.put(name, pane);
        subSceneControllerMap.put(pane, bc);
    }

    private void switchSceneBack() {

        System.out.println("Root Controller attempting to swap scene back");
        Pane p = history.pop();
        if(p == null){
            Logger.getInstance().log("Tried to back without history pane, doing nothing", LogLevel.W);
            return;
        }

        System.out.println(p);
        //TELL SCENE IT'S BEING LOADED FIRST.
        BaseController bc = subSceneControllerMap.get(p);
        if(bc == null)
            throw new IllegalStateException();
        bc.sceneLoaded();

        System.out.println(bc.getClass().getSimpleName());
        paneHolder.getChildren().setAll(p);

    }

    private void switchSceneTo(String sceneName) {
        Pane p = subSceneMap.get(sceneName);
        if(p != null){

            //TELL SCENE IT'S BEING LOADED FIRST.
            BaseController bc = subSceneControllerMap.get(p);
            if(bc == null)
                throw new IllegalStateException();
            bc.sceneLoaded();


            if(paneHolder.getChildren().size() > 0) {
                history.push((Pane) paneHolder.getChildren().get(0));

            }

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

        sceneModel.getBackProperty().addListener((obs, oldVal, newVal)->{
            System.out.println("Back property is now " + newVal);
            if(newVal){
                switchSceneBack();
                sceneModel.getBackProperty().set(false);
            }
        });

        //Add:
        try {
            addPaneAndController(OverviewController.class.getSimpleName(), "/com/rweqx/ui/overview.fxml");
            addPaneAndController(AddEditClassController.class.getSimpleName(), "/com/rweqx/ui/add-edit-class.fxml");
            addPaneAndController(AddEditPaymentController.class.getSimpleName(), "/com/rweqx/ui/add-edit-payment.fxml");
            addPaneAndController(StudentProfilesListController.class.getSimpleName(), "/com/rweqx/ui/student-profiles-list.fxml");
            addPaneAndController(DayViewController.class.getSimpleName(), "/com/rweqx/ui/day-view.fxml");



            addPaneAndController(StudentProfileController.class.getSimpleName(), "/com/rweqx/ui/student-profile.fxml");
            addPaneAndController(AddEditStudentController.class.getSimpleName(), "/com/rweqx/ui/add-edit-student.fxml");

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
