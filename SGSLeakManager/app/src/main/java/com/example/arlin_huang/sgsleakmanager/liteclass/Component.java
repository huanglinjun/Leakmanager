package com.example.arlin_huang.sgsleakmanager.liteclass;

import org.litepal.crud.LitePalSupport;

import java.util.Date;

public class Component extends LitePalSupport {
    private int id;
    private String mid;
    private String	areaEquipmentNameCodeName;
    private String areaId;
    private Double backValue;
    private Date buildDate;
    private String code;
    private String componentTypeName;
    private String componentTypeId;
    private String detectionEndDate;
    private String detectionStartDate;
    private String detectImageId;
    private Double detectionValue;
    private String displayArea;
    private String displayComponentType;
    private String displayFluidType;;
    private String displayLine;
    private String displayPolltionSource;
    private String displayUnit;
    private String equipmentId;
    private String equipmentNameCodeName;
    private Boolean exemption;
    private String floor;
    private String fluidComposition;
    private String fluidTypeId;
    private String fluidTypeName;
    private int index;
    private Boolean isAudited;
    private Boolean isDetection;
    private Boolean isEnabled;
    private double rectX;
    private double rectY;
    private double circleX;
    private double circleY;

    private String pipelineEquipmentNameCodeName;
    private String pipelineId	;
    private String pollutionSourceId;
    private String	pollutionSourceName;
    private Double readValue;
    private String serialNumber;
    private int size;
    private AllocationImage allocationImage;
    private double shortTime;


    public double getShortTime() {
        return shortTime;
    }

    public void setShortTime(double shortTime) {
        this.shortTime = shortTime;
    }

    //    private String allocationImageId;
//
//    public String getAllocationImageId() {
//        return allocationImageId;
//    }
//    public void setAllocationImageId(String allocationImageId) {
//        this.allocationImageId = allocationImageId;
//    }
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

    public String getAreaEquipmentNameCodeName() {
        return areaEquipmentNameCodeName;
    }

    public void setAreaEquipmentNameCodeName(String areaEquipmentNameCodeName) {
        this.areaEquipmentNameCodeName = areaEquipmentNameCodeName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public Double getBackValue() {
        return backValue;
    }

    public void setBackValue(Double backValue) {
        this.backValue = backValue;
    }

    public Date getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Date buildDate) {
        this.buildDate = buildDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComponentTypeName() {
        return componentTypeName;
    }

    public void setComponentTypeName(String componentTypeName) {
        this.componentTypeName = componentTypeName;
    }

    public String getComponentTypeId() {
        return componentTypeId;
    }

    public void setComponentTypeId(String componentTypeId) {
        this.componentTypeId = componentTypeId;
    }

    public String getDetectionEndDate() {
        return detectionEndDate;
    }

    public void setDetectionEndDate(String detectionEndDate) {
        this.detectionEndDate = detectionEndDate;
    }

    public String getDetectionStartDate() {
        return detectionStartDate;
    }

    public void setDetectionStartDate(String detectionStartDate) {
        this.detectionStartDate = detectionStartDate;
    }

    public String getDetectImageId() {
        return detectImageId;
    }

    public void setDetectImageId(String detectImageId) {
        this.detectImageId = detectImageId;
    }

    public Double getDetectionValue() {
        return detectionValue;
    }

    public void setDetectionValue(Double detectionValue) {
        this.detectionValue = detectionValue;
    }

    public String getDisplayArea() {
        return displayArea;
    }

    public void setDisplayArea(String displayArea) {
        this.displayArea = displayArea;
    }

    public String getDisplayComponentType() {
        return displayComponentType;
    }

    public void setDisplayComponentType(String displayComponentType) {
        this.displayComponentType = displayComponentType;
    }

    public String getDisplayFluidType() {
        return displayFluidType;
    }

    public void setDisplayFluidType(String displayFluidType) {
        this.displayFluidType = displayFluidType;
    }

    public String getDisplayLine() {
        return displayLine;
    }

    public void setDisplayLine(String displayLine) {
        this.displayLine = displayLine;
    }

    public String getDisplayPolltionSource() {
        return displayPolltionSource;
    }

    public void setDisplayPolltionSource(String displayPolltionSource) {
        this.displayPolltionSource = displayPolltionSource;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentNameCodeName() {
        return equipmentNameCodeName;
    }

    public void setEquipmentNameCodeName(String equipmentNameCodeName) {
        this.equipmentNameCodeName = equipmentNameCodeName;
    }

    public Boolean getExemption() {
        return exemption;
    }

    public void setExemption(Boolean exemption) {
        this.exemption = exemption;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getFluidComposition() {
        return fluidComposition;
    }

    public void setFluidComposition(String fluidComposition) {
        this.fluidComposition = fluidComposition;
    }

    public String getFluidTypeId() {
        return fluidTypeId;
    }

    public void setFluidTypeId(String fluidTypeId) {
        this.fluidTypeId = fluidTypeId;
    }

    public String getFluidTypeName() {
        return fluidTypeName;
    }

    public void setFluidTypeName(String fluidTypeName) {
        this.fluidTypeName = fluidTypeName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Boolean getAudited() {
        return isAudited;
    }

    public void setAudited(Boolean audited) {
        isAudited = audited;
    }

    public Boolean getDetection() {
        return isDetection;
    }

    public void setDetection(Boolean detection) {
        isDetection = detection;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }


    public String getPipelineEquipmentNameCodeName() {
        return pipelineEquipmentNameCodeName;
    }

    public void setPipelineEquipmentNameCodeName(String pipelineEquipmentNameCodeName) {
        this.pipelineEquipmentNameCodeName = pipelineEquipmentNameCodeName;
    }

    public String getPipelineId() {
        return pipelineId;
    }

    public void setPipelineId(String pipelineId) {
        this.pipelineId = pipelineId;
    }

    public String getPollutionSourceId() {
        return pollutionSourceId;
    }

    public void setPollutionSourceId(String pollutionSourceId) {
        this.pollutionSourceId = pollutionSourceId;
    }

    public String getPollutionSourceName() {
        return pollutionSourceName;
    }

    public void setPollutionSourceName(String pollutionSourceName) {
        this.pollutionSourceName = pollutionSourceName;
    }

    public Double getReadValue() {
        return readValue;
    }

    public void setReadValue(Double readValue) {
        this.readValue = readValue;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public AllocationImage getAllocationImage() {
        return allocationImage;
    }

    public void setAllocationImage(AllocationImage allocationImage) {
        this.allocationImage = allocationImage;
    }

    public double getRectX() {
        return rectX;
    }

    public void setRectX(double rectX) {
        this.rectX = rectX;
    }

    public double getRectY() {
        return rectY;
    }

    public void setRectY(double rectY) {
        this.rectY = rectY;
    }

    public double getCircleX() {
        return circleX;
    }

    public void setCircleX(double circleX) {
        this.circleX = circleX;
    }

    public double getCircleY() {
        return circleY;
    }

    public void setCircleY(double circleY) {
        this.circleY = circleY;
    }
}
