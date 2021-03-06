package com.rweqx.io;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rweqx.constants.Constants;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import com.rweqx.util.DateUtil;
import javafx.collections.ObservableList;
import org.hildan.fxgson.FxGson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class JSONWriter {

    private Gson gson;

    public JSONWriter(){
        gson = FxGson.coreBuilder().setPrettyPrinting().create();

    }


    public void writeStudentsToFile(List<Student> students, String studentFile){
        System.out.println("Writing students" + students);
        String fileName = Constants.SAVE_FOLDER + studentFile;
        checkFile(fileName);

        String json = gson.toJson(students);
        write(fileName, json);

        String backupName = getFileBackupName(fileName);
        write(backupName, json);
    }

    public void writePaymentToFile(List<Payment> paymentList, String paymentFile){
        System.out.println("Writing payments" + paymentList);

        String fileName = Constants.SAVE_FOLDER + paymentFile;
        String backupName = getFileBackupName(fileName);

        checkFile(fileName);

        String json = gson.toJson(paymentList);
        write(fileName, json);
        write(backupName, json);
    }

    private String getFileBackupName(String fileName) {
        File f = new File(fileName);
        return fileName + "_" + DateUtil.getNameFromCurrentDate(LocalDateTime.now());
    }

    private void write(String fileName, String toWrite){

        try {
            Writer writer = new FileWriter(fileName);

            writer.write(toWrite);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void append(String fileName, String toWrite){

        try {
            Writer writer = new FileWriter(fileName, true);
            writer.append(toWrite);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeClassesToFile(List<Class> classList, String classFile){
        System.out.println("Writing class " + classList);

        String fileName = Constants.SAVE_FOLDER + classFile;

        System.out.println("Writing to " + fileName);
        checkFile(fileName);

        String json = gson.toJson(classList);
        write(fileName, json);


        String backupName = getFileBackupName(fileName);
        write(backupName, json);

    }

    private void checkFile(String fileName) {
        File f = new File(fileName);
        if(!f.exists()){
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getInstance().log(getClass().getSimpleName(),"Cannot create file " + fileName, LogLevel.S);
            }
        }
    }


    public void writeLogsToFile(ObservableList<String> logs, String logSaveFile) {
        System.out.println("Writing logs ");
        String fileName = Constants.SAVE_FOLDER + logSaveFile;
        checkFile(fileName);

        logs.add("Log Date " + LocalDate.now().toString() + "\n");

        String json = gson.toJson(logs);
        append(fileName, json);

        String backupName = getFileBackupName(fileName);
        write(backupName, json);
        //write(fileName, json, false);
    }
}
