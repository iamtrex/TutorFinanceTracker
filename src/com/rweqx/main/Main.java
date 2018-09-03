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
        logger.log(this.getClass().getSimpleName(), "Starting Program", LogLevel.D);
        logUI.showWindow();

        logger.log(this.getClass().getSimpleName(), "Loading Model", LogLevel.D);
        loadProgram();
        logger.log(getClass().getSimpleName(), "Done Loading Model", LogLevel.D);

        logger.log(this.getClass().getSimpleName(), "Initializing Root Layout", LogLevel.D);
        initRootLayout();
        logger.log(getClass().getSimpleName(), "Done Loading Root Layout", LogLevel.D);

        logger.log(this.getClass().getSimpleName(), "Setting up Window.", LogLevel.D);
        Scene mainScene = new Scene(root, 1300, 675);
        mainScene.getStylesheets().add(getClass().getResource("/com/rweqx/styles/style.css").toString());

        primaryStage.setTitle("Tutor Student Finance Tracker");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest((e)->{
            Platform.exit();
        });

        logger.log(getClass().getSimpleName(), "Done Initialization, showing window.", LogLevel.D);
        primaryStage.show();
        //primaryStage.setMinWidth(primaryStage.getWidth());
        //primaryStage.setMinHeight(primaryStage.getHeight());

    }

    private void loadProgram() {
        modelManager = new ModelManager();
        sceneModel = new SceneModel();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        modelManager.saveAll();
        logger.log(this.getClass().getSimpleName(), "Shutting down.", LogLevel.D);
        logger.writeLogs();

        System.exit(0);
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
