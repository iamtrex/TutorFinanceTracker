package com.rweqx.model;

import com.rweqx.logger.LogLevel;
import com.rweqx.logger.Logger;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassFeeManager {
    private Map<Student, List<Pair<String, Double>>> studentFeeRatesMap;

    public ClassFeeManager(){
        studentFeeRatesMap = new HashMap<>();

    }

    public void registerStudent(Student s, List<Pair<String, Double>> feeRates){
        studentFeeRatesMap.put(s, feeRates);
    }

    public Double getRateForStudent(Student s, String classType){
        List<Pair<String, Double>> types = studentFeeRatesMap.get(s);
        if(types != null){
            for(Pair<String, Double> pair : types){
                if(pair.getKey().equals(classType)){
                    return pair.getValue();
                }
            }
        }
        Logger.getInstance().log("Could not find feetype " + classType + " for student " + s.getName(), LogLevel.S);
        return 0.0;
    }
}
