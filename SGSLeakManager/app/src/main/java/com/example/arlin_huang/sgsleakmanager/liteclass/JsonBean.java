package com.example.arlin_huang.sgsleakmanager.liteclass;

import java.util.Date;
import java.util.List;


public class JsonBean {

    public List<Components> components;
    public DetectImage detectImage;

    public List<Components> getComponents() {
        return components;
    }

    public void setComponents(List<Components> components) {
        this.components = components;
    }

    public DetectImage getDetectImage() {
        return detectImage;
    }

    public void setDetectImage(DetectImage detectImage) {
        this.detectImage = detectImage;
    }

    public static  class Components {
        public String id;
        public int index;
        public String componentTypeId;
        public String fluidTypeId;
        public String fluidComposition;
        public String	areaEquipmentNameCodeName;
        public String areaId;
        public Double backvalue;
        public Date buildDate;
        public String code;
        public String componentTypeName;
        public String detectionEndDate;
        public String detectionStartDate;
        public String detectImageId;
        public Double detectionValue;
        public String displayArea;
        public String displayComponentType;
        public String displayFluidType;;
        public String displayLine;
        public String displayPolltionSource;
        public String displayUnit;
        public String equipmentId;
        public String equipmentNameCodeName;
        public Boolean exemption;
        public String floor;
        public String fluidTypeName;
        public Boolean isAudited;
        public Boolean isDetection;
        public Boolean isEnabled;
        public Location location;
        public String pipelineEquipmentNameCodeName;
        public String pipelineId	;
        public String pollutionSourceId;
        public String	pollutionSourceName;
        public Double readValue;
        public String serialNumber;
        public int size;
        public AllocationImage allocationImage;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public String getComponentTypeId() {
            return componentTypeId;
        }

        public void setComponentTypeId(String componentTypeId) {
            this.componentTypeId = componentTypeId;
        }

        public String getFluidTypeId() {
            return fluidTypeId;
        }

        public void setFluidTypeId(String fluidTypeId) {
            this.fluidTypeId = fluidTypeId;
        }

        public String getFluidComposition() {
            return fluidComposition;
        }

        public void setFluidComposition(String fluidComposition) {
            this.fluidComposition = fluidComposition;
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

        public Double getBackvalue() {
            return backvalue;
        }

        public void setBackvalue(Double backvalue) {
            this.backvalue = backvalue;
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

        public String getFluidTypeName() {
            return fluidTypeName;
        }

        public void setFluidTypeName(String fluidTypeName) {
            this.fluidTypeName = fluidTypeName;
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

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
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
    }
    public static class DetectImage {
        public String imageData;
        public UnitDto unit;
        public UnitDto area;
        public UnitDto line;
        public String id;
        public String areaId;
        public String code;
        public String displayArea;
        public String displayBranchFactory;
        public String displayLine;
        public String displayPollutionSource;
        public String displayProcessUnit;
        public String displayUnit;
        public String displayUnitArea;
        public String equipmentId;
        public int floor;
        public String imageUrl;
        public Boolean isAudited;
        public Boolean isEnabled;
        public String pipelineId	;
        public String pollutionSourceId;
        public String quotationNumber;
        public String remark;
        public String serialNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public UnitDto getArea() {
            return area;
        }

        public void setArea(UnitDto area) {
            this.area = area;
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

        public UnitDto getLine() {
            return line;
        }

        public void setLine(UnitDto line) {
            this.line = line;
        }

        public UnitDto getUnit() {
            return unit;
        }

        public void setUnit(UnitDto unit) {
            this.unit = unit;
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

}
