package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.util.*;

public class ClassManager {

    private Random idGenerator;

    private Set<Class> classes;

    private DataModel model;

    public ClassManager(DataModel model){
        idGenerator = new Random();
        classes = new HashSet<>();

        this.model = model;

        readSavedClasses();
    }

    private void readSavedClasses() {
        // TODO
    }

    public long createAndAddClass(String classType, Date date, List<StudentInClassElement> studentElts){

        long id = getNewID();

        Class cl = new Class(id, classType, date);

        for(StudentInClassElement stuElt : studentElts){
            Student stu = model.getStudentsModel().getStudentByName(stuElt.getStudentName());
            cl.addStudent(stu, stuElt.getDuration(), stuElt.getPaid());
        }

        Logger.getInstance().log("Added new class" + cl.toString() , LogLevel.D);

        classes.add(cl);

        return id;
    }

    private long getNewID() {
        long l = idGenerator.nextLong();

        while(classes.contains(new Class(l, null, null))){ //If ID is already used.
            l = idGenerator.nextLong();
        }

        return l;
    }
}
