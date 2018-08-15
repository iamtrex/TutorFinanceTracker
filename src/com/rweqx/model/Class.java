package com.rweqx.model;

import java.util.*;

public class Class {

    private long classID;
    private Date date;
    private String classType;

    private Set<Student> students;

    private List<StudentInClassElement> stuElts;



    public Class(long classID, String classType, Date date){
        this.classID = classID;
        this.classType = classType;
        this.date = date;
        students = new HashSet<>();
        stuElts = new ArrayList<>();
    }

    public void addStudent(StudentInClassElement stuElt){
        stuElts.add(stuElt);
    }

    public List<StudentInClassElement> getStuElts(){
        return stuElts;
    }

    public Date getDate(){
        return date;
    }

    public String getClassType(){
        return classType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Class aClass = (Class) o;
        return classID == aClass.classID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classID);
    }

    @Override
    public String toString() {
        return "Class{" +
                "classID=" + classID +
                ", date=" + date +
                ", classType='" + classType + '\'' +
                ", students=" + students +
                ", stuElts=" + stuElts +
                '}';
    }

    public Long getClassID() {
        return classID;
    }
}
