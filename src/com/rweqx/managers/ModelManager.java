package com.rweqx.managers;

import com.rweqx.constants.Constants;
import com.rweqx.io.JSONReader;
import com.rweqx.io.JSONWriter;
import com.rweqx.model.Class;
import com.rweqx.model.ClassTypes;
import com.rweqx.model.Payment;
import com.rweqx.model.PaymentTypes;
import com.rweqx.model.Student;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ModelManager {

    private ClassTypes classTypes;
    private PaymentTypes paymentTypes;
    private StudentManager studentManager;
    private ClassManager classManager;
    private PaymentManager paymentManager;

    private JSONReader saveReader;
    private JSONWriter saveWriter;

    public ModelManager(){
        saveReader = new JSONReader();
        saveWriter = new JSONWriter();
        classTypes = new ClassTypes();
        paymentTypes = new PaymentTypes();
        studentManager = new StudentManager();
        classManager = new ClassManager(this);
        paymentManager = new PaymentManager();

        loadSavedData();

    }

    private void loadSavedData() {
        List<Class> classes = saveReader.readClassFromJson(Constants.SAVE_FOLDER + Constants.CLASS_SAVE_FILE);
        classManager.addClasses(classes);

        List<Payment> payments = saveReader.readPaymentFromJson(Constants.SAVE_FOLDER + Constants.PAYMENT_SAVE_FILE);
        paymentManager.addPayments(payments);

        //Read students...  //TODO REMOVE TEMP CODE.
        Pair<String, Double> a = new Pair<>("1 on 1", 75.0);
        Pair<String, Double> b = new Pair<>("Group", 25.0);
        Pair<String, Double> c = new Pair<>("1 on 2", 75.0);
        Pair<String, Double> d = new Pair<>("1 on 3", 75.0);
        List<Pair<String, Double>> list = new ArrayList<>(List.of(a, b, c, d));


    }

    /**
     * Saves all data on program close.
     */
    public void saveAll() {


    }


    public ClassTypes getClassTypes() {
        return classTypes;
    }

    public void setClassTypes(ClassTypes classTypes) {
        this.classTypes = classTypes;
    }

    public PaymentTypes getPaymentTypes() {
        return paymentTypes;
    }

    public void setPaymentTypes(PaymentTypes paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public StudentManager getStudentManager() {
        return studentManager;
    }

    public void setStudentManager(StudentManager studentManager) {
        this.studentManager = studentManager;
    }

    public ClassManager getClassManager() {
        return classManager;
    }

    public void setClassManager(ClassManager classManager) {
        this.classManager = classManager;
    }

    public PaymentManager getPaymentManager() {
        return paymentManager;
    }

    public void setPaymentManager(PaymentManager paymentManager) {
        this.paymentManager = paymentManager;
    }
}
