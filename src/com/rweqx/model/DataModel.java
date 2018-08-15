package com.rweqx.model;

import com.rweqx.constants.Constants;

public class DataModel {


    private ClassManager classManager;
    private PaymentModel paymentModel;
    private StudentsModel studentsModel;

    private JSONWriter saveWriter;
    private JSONReader saveReader;

    public StudentsModel getStudentsModel() {
        return studentsModel;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public ClassManager getClassManager() {
        return classManager;
    }


    public DataModel(){
        classManager = new ClassManager(this);
        paymentModel = new PaymentModel();
        studentsModel = new StudentsModel();
        saveWriter = new JSONWriter();
        saveReader = new JSONReader();
        readSaves();

    }

    private void readSaves() {
        Class c = saveReader.readClassFromJson(Constants.SAVE_FOLDER + Constants.CLASS_SAVE_FILE);
        classManager.addClass(c);
    }

    public void saveAll() {
        for(Class c : classManager.getAllClasses()) {
            saveWriter.writeClassToFile(c, Constants.CLASS_SAVE_FILE);
        }
    }


}
