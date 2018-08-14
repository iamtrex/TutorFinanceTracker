package com.rweqx.model;

public class DataModel {

    private ClassesModel classesModel;
    private PaymentModel paymentModel;
    private StudentsModel studentsModel;


    public StudentsModel getStudentsModel() {
        return studentsModel;
    }

    public PaymentModel getPaymentModel() {
        return paymentModel;
    }

    public ClassesModel getClassesModel() {
        return classesModel;
    }


    public DataModel(){
        classesModel = new ClassesModel();
        paymentModel = new PaymentModel();
        studentsModel = new StudentsModel();
    }
}
