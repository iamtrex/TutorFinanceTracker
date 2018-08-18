package com.rweqx.controller;

import com.rweqx.managers.ModelManager;
import com.rweqx.model.SceneModel;

public abstract class BaseController {

    protected ModelManager modelManager;
    protected SceneModel sceneModel;

    public void initModel(ModelManager mm, SceneModel sm){
        this.modelManager = mm;
        this.sceneModel = sm;
    }




}
