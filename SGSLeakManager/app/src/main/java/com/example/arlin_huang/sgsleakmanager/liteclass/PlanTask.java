package com.example.arlin_huang.sgsleakmanager.liteclass;

import org.litepal.crud.LitePalSupport;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class PlanTask extends LitePalSupport {
    private int id;
    private String mid;
    private int componentCount;
    private Date creationTime;
    private long executiveStaffId;
    private int imageCount;
    private String imageStartAndEndNumber;
    private String instrumentCode;
    private String instrumentId;
    private int instrumentResponseTime;
    private Boolean isFinished;
    private String projectId;
    private String remark;
    private String taskName;
    private int testtime;
    private double backValue;

    public double getBackValue() {
        return backValue;
    }

    public void setBackValue(double backValue) {
        this.backValue = backValue;
    }

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

    public int getComponentCount() {
        return componentCount;
    }

    public void setComponentCount(int componentCount) {
        this.componentCount = componentCount;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public long getExecutiveStaffId() {
        return executiveStaffId;
    }

    public void setExecutiveStaffId(long executiveStaffId) {
        this.executiveStaffId = executiveStaffId;
    }

    public int getImageCount() {
        return imageCount;
    }

    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    public String getImageStartAndEndNumber() {
        return imageStartAndEndNumber;
    }

    public void setImageStartAndEndNumber(String imageStartAndEndNumber) {
        this.imageStartAndEndNumber = imageStartAndEndNumber;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public String getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(String instrumentId) {
        this.instrumentId = instrumentId;
    }

    public int getInstrumentResponseTime() {
        return instrumentResponseTime;
    }

    public void setInstrumentResponseTime(int instrumentResponseTime) {
        this.instrumentResponseTime = instrumentResponseTime;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTesttime() {
        return testtime;
    }

    public void setTesttime(int testtime) {
        this.testtime = testtime;
    }
}
