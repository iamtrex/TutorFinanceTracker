package com.rweqx.managers;

import com.rweqx.model.ClassTypes;
import com.rweqx.model.PaymentTypes;

public class ModelManager {

    private ClassTypes classTypes;
    private PaymentTypes paymentTypes;
    private StudentManager studentManager;
    private ClassManager classManager;
    private PaymentManager paymentManager;

    public ModelManager(){
        classTypes = new ClassTypes();
        paymentTypes = new PaymentTypes();
        studentManager = new StudentManager();
        classManager = new ClassManager(this);
        paymentManager = new PaymentManager();


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
