package com.example.arlin_huang.sgsleakmanager.liteclass;

import org.litepal.crud.LitePalSupport;


public class Project extends LitePalSupport {
    private int id;
    private String mid;
    private double intervalTime;
    private String projectName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public double getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(double intervalTime) {
        this.intervalTime = intervalTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

}
