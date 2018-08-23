package com.rweqx.model;

public class StuDurPaid {
    public long getStuID() {
        return stuID;
    }

    public void setStuID(long stuID) {
        this.stuID = stuID;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public long getPaidID() {
        return paidID;
    }

    public void setPaidID(long paidID) {
        this.paidID = paidID;
    }

    public double getCustomRate() {
        return customRate;
    }

    public void setCustomRate(double customRate) {
        this.customRate = customRate;
    }

    private long stuID;
    private double duration;
    private long paidID;
    private double customRate;

    public StuDurPaid(long stuID, double duration, long paidID){
        this.stuID = stuID;
        this.duration = duration;
        this.paidID = paidID;
        this.customRate = -1;

    }
    public StuDurPaid(long stuID, double duration, long paidID, double customRate){
        this.stuID = stuID;
        this.duration = duration;
        this.paidID = paidID;
        this.customRate = customRate;

    }


}
