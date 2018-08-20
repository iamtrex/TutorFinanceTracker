package com.rweqx.managers;

import java.time.LocalDate;
import java.util.*;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.model.Class;
import com.rweqx.model.Event;
import com.rweqx.model.StuDurPaid;
import com.rweqx.model.Student;
import com.rweqx.util.DateUtil;

public class ClassManager {


    private List<Class> classes; //Contain all classes
    private Map<Long, Class> classMap; // Obtain classes by ID in O(1) time.

    private ModelManager modelManager;

    public ClassManager(ModelManager modelManager){
        this.modelManager = modelManager;

        classMap = new HashMap<>();
        classes = new ArrayList<>();


    }

    public List<Class> getAllClasses() {
        return classes;
    }

    public void deleteClass(long classID){
        Class c = classMap.get(classID);
        if(c == null){
            Logger.getInstance().log("Cannot find class with ID " + classID + " when trying to remove", LogLevel.S);
            return;
        }
        classes.remove(c);
        classMap.remove(c);


        for(StuDurPaid sdp : c.getAllData()){
            long pid = sdp.getPaidID();
            modelManager.getPaymentManager().removePaymentByID(pid);
        }
    }

    public void addClasses(List<Class> newClasses){
        newClasses.forEach(this::addClass);
    }

    public void addClass(Class c){
        classes.add(c);
        classMap.put(c.getID(), c);
    }


    public Class getClassByID(long classID) {
        Class c = classMap.get(classID);
        if(c == null){
            Logger.getInstance().log("Cannot find class with ID " + classID + " it doesn't exit", LogLevel.S);
        }
        return c;
    }

    public List<Class> getClassesOnDate(LocalDate date) {
        List<Class> todayClasses = new ArrayList<>();

        for(Class c : classes){
            LocalDate d = c.getDate();
            if(DateUtil.sameDate(d, date))
                todayClasses.add(c);
        }
        return todayClasses;
    }

    public List<Class> getAllClassesBy(long id) {
        List<Class> allClasses = new ArrayList<>();
        for(Class c : classes){
            if(c.containsStudent(id)){
                allClasses.add(c);
            }
        }
        return allClasses;
    }

    public List<Class> getAllClassesByInMonth(long id, int month) {
        List<Class> allClasses = new ArrayList<>();
        for(Class c : classes){
            if(c.containsStudent(id)){
                if(month == c.getDate().getMonthValue()) {
                    allClasses.add(c);
                }
            }
        }
        return allClasses;
    }

    public void overwriteClass(Class currentlyEditingClass, Class currentClass) {
    }
}
