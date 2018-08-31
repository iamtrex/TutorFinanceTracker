package com.rweqx.main;

import com.rweqx.controller.OverviewController;
import com.rweqx.controller.RootController;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.logger.LoggerUI;
import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage primaryStage;
    private Logger logger;
    private Pane root;

    private ModelManager modelManager;
    private SceneModel sceneModel;


    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        logger = Logger.getInstance();
        LoggerUI logUI = new LoggerUI();


        loadProgram();
        initRootLayout();

        Scene mainScene = new Scene(root, 1300, 675);
        mainScene.getStylesheets().add(getClass().getResource("/com/rweqx/styles/style.css").toString());

        primaryStage.setTitle("Tutor Student Finance Tracker");
        primaryStage.setScene(mainScene);
        primaryStage.show();
        primaryStage.setMinWidth(primaryStage.getWidth());
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setOnCloseRequest((e)->{
            Platform.exit();
        });

        logger.log("Done init", LogLevel.D);

    }

    private void loadProgram() {
        modelManager = new ModelManager();
        sceneModel = new SceneModel();

        logger.log("Done Loading Model", LogLevel.D);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        modelManager.saveAll();

        System.out.println("Terminated program!");
    }


    private void initRootLayout(){
        try{
            FXMLLoader rootLoader = new FXMLLoader(getClass().getResource("/com/rweqx/ui/root.fxml"));
            root = rootLoader.load();

            RootController rc = rootLoader.getController();
            rc.initModel(modelManager, sceneModel);

            sceneModel.setScene(OverviewController.class.getSimpleName());


        }catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);

    }
}
