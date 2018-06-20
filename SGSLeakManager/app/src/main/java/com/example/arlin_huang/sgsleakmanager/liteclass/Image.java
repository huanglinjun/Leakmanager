package com.example.arlin_huang.sgsleakmanager.liteclass;

import org.litepal.crud.LitePalSupport;

public class Image extends LitePalSupport {
    private int id;
    private String mid;
    private String areaId;
    private String code;
    private String displayArea;
    private String displayBranchFactory;
    private String displayLine;
    private String displayPollutionSource;
    private String displayProcessUnit;
    private String displayUnit;
    private String displayUnitArea;
    private String equipmentId;
    private int floor;
    private String imageData;
    private String imageUrl;
    private Boolean isAudited;
    private Boolean isEnabled;
    private String areaName;
    private String areaCode;
    private String areaAbbr;
    private String lineName;
    private String lineCode;
    private String lineAbbr;
    private String unitName;
    private String unitCode;
    private String unitAbbr;
    private String pipelineId;
    private String pollutionSourceId;
    private String quotationNumber;
    private String remark;
    private String serialNumber;
//    private String allocationImageId;
//
//    public String getAllocationImageId() {
//        return allocationImageId;
//    }
//
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


    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayArea() {
        return displayArea;
    }

    public void setDisplayArea(String displayArea) {
        this.displayArea = displayArea;
    }

    public String getDisplayBranchFactory() {
        return displayBranchFactory;
    }

    public void setDisplayBranchFactory(String displayBranchFactory) {
        this.displayBranchFactory = displayBranchFactory;
    }
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaAbbr() {
        return areaAbbr;
    }

    public void setAreaAbbr(String areaAbbr) {
        this.areaAbbr = areaAbbr;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public String getLineAbbr() {
        return lineAbbr;
    }

    public void setLineAbbr(String lineAbbr) {
        this.lineAbbr = lineAbbr;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitAbbr() {
        return unitAbbr;
    }

    public void setUnitAbbr(String unitAbbr) {
        this.unitAbbr = unitAbbr;
    }

    public String getDisplayLine() {
        return displayLine;
    }

    public void setDisplayLine(String displayLine) {
        this.displayLine = displayLine;
    }

    public String getDisplayPollutionSource() {
        return displayPollutionSource;
    }

    public void setDisplayPollutionSource(String displayPollutionSource) {
        this.displayPollutionSource = displayPollutionSource;
    }

    public String getDisplayProcessUnit() {
        return displayProcessUnit;
    }

    public void setDisplayProcessUnit(String displayProcessUnit) {
        this.displayProcessUnit = displayProcessUnit;
    }

    public String getDisplayUnit() {
        return displayUnit;
    }

    public void setDisplayUnit(String displayUnit) {
        this.displayUnit = displayUnit;
    }

    public String getDisplayUnitArea() {
        return displayUnitArea;
    }

    public void setDisplayUnitArea(String displayUnitArea) {
        this.displayUnitArea = displayUnitArea;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getAudited() {
        return isAudited;
    }

    public void setAudited(Boolean audited) {
        isAudited = audited;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
