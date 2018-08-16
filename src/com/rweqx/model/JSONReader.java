package com.rweqx.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hildan.fxgson.FxGson;


public class JSONReader {

    private Gson gson;
    public JSONReader(){
        gson = FxGson.coreBuilder().setPrettyPrinting().create();


    }

    public Set<Payment> readPaymentFromJson(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
            Logger.getInstance().log("Payment Save File does not exist, will be created later...", LogLevel.W);
            return new HashSet<>();
        }
        try {

            String json = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
            if(json.trim().equals("")){
                Logger.getInstance().log("Empty Payment Save File...", LogLevel.W);
                return new HashSet<>();
            }

            Type listType = new TypeToken<HashSet<Payment>>(){}.getType();

            Set<Payment> listOfPayments = gson.fromJson(json, listType);
            System.out.println("Payments Found " + listOfPayments.size());
            System.out.println(listOfPayments);
            return listOfPayments;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new HashSet<>();
    }

    public Set<Class> readClassFromJson(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
            Logger.getInstance().log("Classes Save File does not exist, will be created later...", LogLevel.W);

            return new HashSet<>();
        }
        try {

            String json = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
            if(json.trim().equals("")){
                Logger.getInstance().log("Empty Classes Save File...", LogLevel.W);
                return new HashSet<>();
            }

            Type listType = new TypeToken<HashSet<Class>>(){}.getType();

            Set<Class> listOfClases = gson.fromJson(json, listType);
            System.out.println("Classes Found " + listOfClases.size());
            System.out.println(listOfClases);
            return listOfClases;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new HashSet<>();
    }
}
