package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import com.rweqx.util.DateUtil;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ClassManager {


    private Set<Class> classes;
    private Map<Long, Class> classMap;

    private DataModel model;

    public ClassManager(DataModel model){
        classMap = new HashMap<>();
        classes = new HashSet<>();
        this.model = model;
        readSavedClasses();
    }

    private void readSavedClasses() {
        // TODO
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

    public void addClasses(Set<Class> classes) {
        classes.forEach(this::addClass);
    }

    public void addClass(Class cl) {
        if(classes.contains(cl)){
            Logger.getInstance().log("ALREADY AHS THIS CLASS ERROR!!!!", LogLevel.S);

        }
        this.classMap.put(cl.getEventID(), cl);
        this.classes.add(cl);
    }

    public List<Class> getClassesOnDate(Date date) {
        List<Class> todayClasses = new ArrayList<>();

        for(Class c : classes){
            Date d = c.getDate();
            if(DateUtil.sameDate(d, date))
                todayClasses.add(c);
        }
        return todayClasses;
    }
}
