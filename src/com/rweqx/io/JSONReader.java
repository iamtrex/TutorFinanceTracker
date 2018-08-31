package com.rweqx.io;

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

import com.rweqx.model.Class;
import com.rweqx.model.Payment;
import com.rweqx.model.Student;
import org.hildan.fxgson.FxGson;


public class JSONReader {

    private Gson gson;
    public JSONReader(){
        gson = FxGson.coreBuilder().setPrettyPrinting().create();


    }

    public List<Payment> readPaymentFromJson(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
            Logger.getInstance().log(getClass().getSimpleName(),"Payment Save File does not exist, will be created later...", LogLevel.W);
            return new ArrayList<>();
        }
        try {

            String json = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
            if(json.trim().equals("")){
                Logger.getInstance().log(getClass().getSimpleName(),"Empty Payment Save File...", LogLevel.W);
                return new ArrayList<>();
            }

            Type listType = new TypeToken<ArrayList<Payment>>(){}.getType();

            List<Payment> listOfPayments = gson.fromJson(json, listType);
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
        return new ArrayList<>();
    }

    public List<Class> readClassFromJson(String fileName){
        File f = new File(fileName);
        if(!f.exists()){
            Logger.getInstance().log(getClass().getSimpleName(),"Classes Save File does not exist, will be created later...", LogLevel.W);

            return new ArrayList<>();
        }
        try {

            String json = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
            if(json.trim().equals("")){
                Logger.getInstance().log(getClass().getSimpleName(),"Empty Classes Save File...", LogLevel.W);
                return new ArrayList<>();
            }

            Type listType = new TypeToken<ArrayList<Class>>(){}.getType();

            List<Class> listOfClases = gson.fromJson(json, listType);
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
        return new ArrayList<>();
    }

    public List<Student> readStudentFromJson(String fileName) {
        File f = new File(fileName);
        if(!f.exists()){
            Logger.getInstance().log(getClass().getSimpleName(),"Students Save File does not exist, will be created later...", LogLevel.W);

            return new ArrayList<>();
        }
        try {

            String json = new String(Files.readAllBytes(Paths.get(fileName)), "UTF-8");
            if(json.trim().equals("")){
                Logger.getInstance().log(getClass().getSimpleName(),"Empty Students Save File...", LogLevel.W);
                return new ArrayList<>();
            }

            Type listType = new TypeToken<ArrayList<Student>>(){}.getType();

            List<Student> listOfStudents = gson.fromJson(json, listType);
            System.out.println("Students Found " + listOfStudents.size());
            System.out.println(listOfStudents);
            return listOfStudents;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
