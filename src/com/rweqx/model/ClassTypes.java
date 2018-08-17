package com.rweqx.model;

import java.util.List;

public class ClassTypes {
    private List<String> classTypes;

    public void setClassTypes(List<String> classTypes){
        this.classTypes = classTypes;
    }

    public void addClassType(String classType){
        this.classTypes.add(classType);
    }

    public List<String> getClassTypes(){
        return classTypes;
    }

    public int getClassKeyCodeByName(String s){
        for(int i=0; i<classTypes.size(); i++){
            if(s.equalsIgnoreCase(classTypes.get(i))){
                return i;
            }
        }
        return -1;
    }

    public String getClassTypeByID(int i){
        return classTypes.get(i);
    }
}
