package com.rweqx.managers;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentManager {
    private List<Student> students;

    private Map<Long, Student> studentIDMap;

    public StudentManager(){
        students = new ArrayList<>();
        studentIDMap = new HashMap<>();

        Map<String, Double> classRates = new HashMap<>();

        //TEMP CODE, REMOVE LATER.
        List<Student> students = new ArrayList<>();
        students.add(new Student(1, "Rex", classRates));
        students.add(new Student(2, "Howard Lin", classRates));
        students.add(new Student(3, "Sammy", classRates));
        students.add(new Student(4, "Joshua Pak", classRates));
        students.add(new Student(20, "Howard Lin", classRates));
        students.add(new Student(10, "Rex v2", classRates));
        students.add(new Student(300, "Sammy is a pig", classRates));
        addStudents(students);
    }

    public void addStudents(List<Student> newStudents){
        newStudents.forEach(this::addStudent);
    }

    public void addStudent(Student newStudent){
        students.add(newStudent);
        studentIDMap.put(newStudent.getID(), newStudent);

    }
    public Student getStudentByID(long id) {
        if(studentIDMap.get(id) != null){
            return studentIDMap.get(id);
        }
        Logger.getInstance().log("Cannot find student with ID " + id, LogLevel.S);
        return null;
    }


    public List<Student> getStudents() {
        return students;
    }
}
