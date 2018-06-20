package com.example.arlin_huang.sgsleakmanager.liteclass;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class AllocationImage extends LitePalSupport {

    private int id;
    private String mid;
    private String code;
    private String taskId;
    private String imageData;
    private String imageId;
    private String quotationNumber;
    private String areaCode;
    private String areaAbbr;
    private String lineCode;
    private String lineAbbr;
    private String unitCode;
    private String unitAbbr;
    private String serialNumber;
    private String displayBranchFactory;
    private String displayProcessUnit;
    private String displayUnitArea;
    private String displayPollutionSource;
    private int floor;
    private List<Component> components = new ArrayList<Component>();
    private Image detectImage;
    private String imageUrl;
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

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDisplayBranchFactory() {
        return displayBranchFactory;
    }

    public void setDisplayBranchFactory(String displayBranchFactory) {
        this.displayBranchFactory = displayBranchFactory;
    }

    public String getDisplayProcessUnit() {
        return displayProcessUnit;
    }

    public void setDisplayProcessUnit(String displayProcessUnit) {
        this.displayProcessUnit = displayProcessUnit;
    }

    public String getQuotationNumber() {
        return quotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        this.quotationNumber = quotationNumber;
    }

    public String getDisplayUnitArea() {
        return displayUnitArea;
    }

    public void setDisplayUnitArea(String displayUnitArea) {
        this.displayUnitArea = displayUnitArea;
    }

    public String getDisplayPollutionSource() {
        return displayPollutionSource;
    }

    public void setDisplayPollutionSource(String displayPollutionSource) {
        this.displayPollutionSource = displayPollutionSource;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<Component> getComponents() {
        return components;
    }
    public List<Component> getComs() {
        return LitePal.where("allocationImage_id = ?",String.valueOf(id)).find(Component.class);
    }

    public void setComponents(List<Component> components) {
        this.components=components;
    }

    public Image getDetectImage() {
        return detectImage;
    }
    public Image getImage() {
        return LitePal.where("allocationImage_id = ?", String.valueOf(id)).findFirst(Image.class);
    }

    public void setDetectImage(Image detectImage) {
        this.detectImage = detectImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
