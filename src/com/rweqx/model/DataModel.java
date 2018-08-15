package com.rweqx.model;

public class DataModel {


    private ClassManager classManager;
    private PaymentModel paymentModel;
    private StudentsModel studentsModel;


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
    }

}
