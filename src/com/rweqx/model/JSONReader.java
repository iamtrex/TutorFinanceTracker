package com.rweqx.model;

import com.google.gson.Gson;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {

    private Gson gson;
    public JSONReader(){
        gson = new Gson();
    }

    public Class readClassFromJson(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
            Logger.getInstance().log("Cannot find file " + fileName, LogLevel.S);
            return null;
        }
        try {
            Reader reader = new FileReader(fileName);
            String json = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");

            Class c = gson.fromJson(json, Class.class);
            return c;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
