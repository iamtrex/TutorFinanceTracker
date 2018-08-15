package com.rweqx.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rweqx.constants.Constants;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter {

    private GsonBuilder builder;
    private Gson gson;

    public JSONWriter(){
        builder = new GsonBuilder();
        builder.setPrettyPrinting();

        gson = builder.create();
    }

    public void writeClassToFile(Class c, String classFile){
        System.out.println("Writing class " + c);

        String fileName = Constants.SAVE_FOLDER + classFile;

        System.out.println("Writing to " + fileName);
        checkFile(fileName);

        try {
            Writer writer = new FileWriter(fileName);


            String json = gson.toJson(c);
            System.out.println("Writing " + json);

            writer.write(json);

            //writer.append("Hello World");

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void checkFile(String fileName) {
        File f = new File(fileName);
        if(!f.exists()){
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getInstance().log("Cannot create file " + fileName, LogLevel.S);
            }
        }
    }

    public void writePaymentToFile(Payment p, String paymentFile){

    }

    public void writeStudentProfileToJSON(Student s){


    }


}
