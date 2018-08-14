package com.rweqx.logger;

public enum LogLevel {
    S("SEVERE"), W("WARNING"), D("DEBUG");


    public String getLogType() {
        return logType;
    }

    private String logType;
    LogLevel(String s){
        this.logType = s;
    }
}
