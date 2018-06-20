package com.example.arlin_huang.sgsleakmanager.liteclass;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UploadTaskData {
    public UUID projectId;
    public int testtime ;
    public UUID taskId ;
    public List<UploadTaskComponentDataInput> uploadComponents;
    public String instrumentCode;
    public static class UploadTaskComponentDataInput{
        public UUID componentId;
        public double detectionValue ;
        public double backvalue ;
        public Timestamp detectionDate ;
        public Timestamp detectionStartDate;
        public Timestamp detectionEndDate ;
    }
}

