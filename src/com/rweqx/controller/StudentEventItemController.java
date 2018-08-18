package com.rweqx.controller;

import com.rweqx.components.EventItemController;
import com.rweqx.model.Student;

public class StudentEventItemController extends EventItemController {

    protected Student student;

    public Student getStudent(){
        return student;
    }

    public void setStudent(Student s){
        this.student = s;
    }

}
