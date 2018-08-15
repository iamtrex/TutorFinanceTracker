package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;

import java.util.*;

public class ClassManager {

    private Random idGenerator;

    private Set<Class> classes;
    private Map<Long, Class> classMap;

    private DataModel model;

    public ClassManager(DataModel model){
        idGenerator = new Random();

        classMap = new HashMap<>();
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
            //Student stu = model.getStudentsModel().getStudentByName(stuElt.getStudentName());
            //cl.addStudent(stu, stuElt.getDuration(), stuElt.getPaid());
            cl.addStudent(stuElt);
        }

        Logger.getInstance().log("Added new class" + cl.toString() , LogLevel.D);

        classes.add(cl);
        classMap.put(id, cl);

        return id;
    }

    private long getNewID() {
        long l = idGenerator.nextLong();

        while(classes.contains(new Class(l, null, null))){ //If ID is already used.
            l = idGenerator.nextLong();
        }

        return l;
    }

    public Class getClassByID(long classID) {
        Class c = classMap.get(classID);
        if(c == null){
            Logger.getInstance().log("Cannot find class with ID " + classID + " it doesn't exit", LogLevel.S);
        }

        return c;

    }

    public Set<Class> getAllClasses() {
        return classes;
    }

    public void addClass(Class c) {
        classes.add(c);
        classMap.put(c.getClassID(), c);

    }
}
