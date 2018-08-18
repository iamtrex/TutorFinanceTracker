package com.rweqx.main;

import com.rweqx.controller.RootController;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
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

        loadProgram();
        initRootLayout();

        primaryStage.setTitle("Tutor Student Finance Tracker");
        primaryStage.setScene(new Scene(root, 1200, 675));
        primaryStage.show();
        primaryStage.setOnCloseRequest((e)->{
            Platform.exit();
        });

        logger.log("Done init", LogLevel.D);

        /*   //TODO STYLESHEET?
            Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
            StyleManager.getInstance().addUserAgentStylesheet(getClass().getResource("/style.css").toString());
         */

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
