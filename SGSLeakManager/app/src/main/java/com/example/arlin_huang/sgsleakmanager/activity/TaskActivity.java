package com.example.arlin_huang.sgsleakmanager.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlin_huang.sgsleakmanager.LeakManager;
import com.example.arlin_huang.sgsleakmanager.R;
import com.example.arlin_huang.sgsleakmanager.TaskAdapter;
import com.example.arlin_huang.sgsleakmanager.liteclass.AllocationImage;
import com.example.arlin_huang.sgsleakmanager.liteclass.Component;
import com.example.arlin_huang.sgsleakmanager.liteclass.Image;
import com.example.arlin_huang.sgsleakmanager.liteclass.JsonBean;
import com.example.arlin_huang.sgsleakmanager.liteclass.Location;
import com.example.arlin_huang.sgsleakmanager.liteclass.PlanTask;
import com.example.arlin_huang.sgsleakmanager.liteclass.UploadTaskData;
import com.example.arlin_huang.sgsleakmanager.util.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TaskActivity extends AppCompatActivity {
    private LeakManager leakManager;
    private TaskAdapter taskAdapter;
    private List<PlanTask> planTasks;
    private String projectId;
    private int testtime;
    private String main;
    private String taskId;
    private String userKey;
    private PlanTask planTask;

    //异步消息处理机制
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(TaskActivity.this, "下载任务图片失败，服务器未响应", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    String str = (String) msg.obj;
                    parseJsonWithGsonAndJson(str);
                    Toast.makeText(TaskActivity.this, "下载任务图片成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(TaskActivity.this, "判断项目是否完成失败，服务器未响应", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    String responseStr = (String) msg.obj;
                    Boolean result = JsonWithGson(responseStr);
                    if (result) {
                        Toast.makeText(TaskActivity.this, "该项目已完成，不能再上传数据", Toast.LENGTH_SHORT).show();

                    } else {
                        uploadTaskData();
                    }
                    break;
                case 4:
                    Toast.makeText(TaskActivity.this, "上传任务数据失败，服务器未响应", Toast.LENGTH_SHORT).show();
                    break;
                case 5:
                    Toast.makeText(TaskActivity.this, "上传数据成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(TaskActivity.this, "上传数据失败", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        leakManager = (LeakManager) getApplication();
        userKey = leakManager.getUserKey();
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_toolbar);
        TextView tasktitle = (TextView) findViewById(R.id.task_title);
        toolbar.setTitle("");
        Intent intent = getIntent();
        projectId = intent.getStringExtra("projectId");
        testtime = intent.getIntExtra("testtime", 1);
        String title = testtime + "检 检测任务";
        tasktitle.setText(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getTaskList(projectId, testtime);

//        DataSupport.deleteAll(Component.class);
//        DataSupport.deleteAll(AllocationImage.class);
//        DataSupport.deleteAll(Image.class);

        List<Component> components = LitePal.findAll(Component.class);
        for (Component task : components) {
            Log.d("AllocationImage", "Component id is " + task.getId());
            Log.d("AllocationImage", "Component AreaId is " + task.getAreaId());
            Log.d("AllocationImage", "Component code is " + task.getCode());
            Log.d("AllocationImage", "Component getRectX is " + task.getRectX());
            Log.d("AllocationImage", "Component getRectY is " + task.getRectY());
            Log.d("AllocationImage", "Component getCircleX is " + task.getCircleX());
            Log.d("AllocationImage", "Component getCircleY is " + task.getCircleY());
            Log.d("AllocationImage", "Component mid is " + task.getMid());
        }
        List<AllocationImage> tasks = LitePal.findAll(AllocationImage.class, true);
        for (AllocationImage task : tasks) {
            Log.d("AllocationImage", "AllocationImage id is " + task.getId());
            Log.d("AllocationImage", "AllocationImage code is " + task.getCode());
            Log.d("AllocationImage", "AllocationImage getComponents is " + task.getComponents());
            Log.d("AllocationImage", "AllocationImage mid is " + task.getMid());
        }
        List<Image> images = LitePal.findAll(Image.class);
        for (Image image : images) {

            Log.d("AllocationImage", "Image id is " + image.getId());
            Log.d("AllocationImage", "Image area is " + image.getAreaAbbr());
            Log.d("AllocationImage", "Image code is " + image.getCode());
            Log.d("AllocationImage", "Image areaId is " + image.getAreaId());
            Log.d("AllocationImage", "Image mid is " + image.getMid());
        }
        List<PlanTask> aaa = LitePal.findAll(PlanTask.class);
        for (PlanTask task : aaa) {
            Log.d("responseData", "task id is " + task.getId());
            Log.d("responseData", "task name is " + task.getTaskName());
            Log.d("responseData", "task getCreationTime is " + task.getCreationTime());
            Log.d("responseData", "task IntervalTime is " + task.getInstrumentResponseTime());
            Log.d("responseData", "task mid is " + task.getMid());
        }

    }

    public void getTaskList(String projectId, int testtime) {
        planTasks = LitePal.where("projectId = ? and testtime= ? and executiveStaffId = ?", projectId, String.valueOf(testtime), String.valueOf(leakManager.getCurrentUserId())).find(PlanTask.class);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.task_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        taskAdapter = new TaskAdapter(planTasks, this);
        recyclerView.setAdapter(taskAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                taskId = taskAdapter.getPlanTask().getMid();
                downImage(taskId);
                return true;
            case 1:
                planTask = taskAdapter.getPlanTask();
                projectIsComplete(planTask);
                return true;
            case 2:
                planTask=taskAdapter.getPlanTask();
                AlertDialog.Builder builder = new AlertDialog.Builder(TaskActivity.this);
                builder.setTitle("输入背景值");    //设置对话框标题
                final EditText edit = new EditText(TaskActivity.this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(edit);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("edit", edit.getText().toString());
                        Double backvalue = Double.valueOf(edit.getText().toString());

                        taskAdapter.updateTask(planTask, backvalue);
                        getTaskList(projectId, testtime);
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(TaskActivity.this, "你点了取消", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
                AlertDialog dialog = builder.create();  //创建对话框
                dialog.setCanceledOnTouchOutside(false); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
                dialog.show();
                getTaskList(projectId, testtime);
                return true;
            case 3:
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(TaskActivity.this);
                deleteDialog.setTitle("确认删除任务");
                deleteDialog.setMessage("确定删除该任务信息吗?该操作会同时删除任务图像和任务数据");
                deleteDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        taskAdapter.removeItem(taskAdapter.getPlanTask());
                        getTaskList(projectId, testtime);
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                deleteDialog.setCancelable(false).create();
                deleteDialog.show();
                return true;
            case 4:
                return true;
        }
        return false;
    }

    //上传任务数据
    public void uploadTaskData() {
        final Message msg = Message.obtain();
        List<AllocationImage> allImages = LitePal.where("taskId = ?", planTask.getMid()).find(AllocationImage.class);
        ArrayList<Component> components = new ArrayList<Component>();
        for (AllocationImage allocationImage : allImages) {
            List<Component> coms = allocationImage.getComs();
            for (Component component : coms) {
                if (component.getDetectionStartDate() != null) {
                    components.add(component);
                }

            }
        }
        UploadTaskData uploadTaskData = new UploadTaskData();
        uploadTaskData.projectId = UUID.fromString(planTask.getProjectId());
        uploadTaskData.testtime = planTask.getTesttime();
        uploadTaskData.taskId = UUID.fromString(planTask.getMid());
        uploadTaskData.instrumentCode = planTask.getInstrumentCode();
        uploadTaskData.uploadComponents= new ArrayList<UploadTaskData.UploadTaskComponentDataInput>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        for (Component component : components) {
            UploadTaskData.UploadTaskComponentDataInput input = new UploadTaskData.UploadTaskComponentDataInput();
            input.componentId = UUID.fromString(component.getMid());
            input.detectionValue = component.getDetectionValue();
            input.backvalue = component.getBackValue();
            //input.detectionDate=component.getDetectionStartDate();
            String startDate = dateFormatter.format(component.getDetectionStartDate());
            String endDate = dateFormatter.format(component.getDetectionEndDate());
            input.detectionStartDate = Timestamp.valueOf(startDate);
            input.detectionEndDate = Timestamp.valueOf(endDate);
            uploadTaskData.uploadComponents.add(input);
        }
        String json = new Gson().toJson(uploadTaskData);
        String projectIsCompleteUrl = "http://192.168.8.101:9090/api/services/app/projectImage/UploadTaskImageData";
        HttpUtils.sendOkHttpPostJsonWithRequest(projectIsCompleteUrl, json, userKey, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onResponse", "onResponse: ");
                msg.what = 4;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("onResponse", "onResponse: " + responseData);
                Boolean success = JsonWithGsonSuccess(responseData);
                if(success)
                {
                    msg.what = 5;
                    handler.sendMessage(msg);
                }
                else {
                    msg.what = 6;
                    handler.sendMessage(msg);
                }
            }
        });

    }

    //判断项目是否完成
    public void projectIsComplete(final PlanTask task) {

        if (userKey == "") {
            AlertDialog.Builder dialog = new AlertDialog.Builder(TaskActivity.this);
            dialog.setTitle("提示!");
            dialog.setMessage("当前无用户登陆，请先登录");
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
            return;
        }
        final Message msg = Message.obtain();
        String json = "{id:'" + task.getProjectId() + "'}";
        String projectIsCompleteUrl = "http://192.168.8.101:9090/api/services/app/projectImage/ProjectIsComplete";
        HttpUtils.sendOkHttpPostJsonWithRequest(projectIsCompleteUrl, json, userKey, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                msg.what = 2;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("responseDatas", responseData);
                msg.obj = responseData;
                msg.what = 3;
                handler.sendMessage(msg);


            }
        });

    }

    //下载任务图像
    public void downImage(String mid) {
        String taskdir = leakManager.getProjectImageUrl() + mid + "/";
        Log.d("taskdir", taskdir);
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            main = Environment.getExternalStorageDirectory().getPath() + File.separator + taskdir;
            Log.d("taskdir", main);
            File destDir = new File(main);
            if (!destDir.exists()) {
                destDir.mkdirs();//在根创建了文件夹
            }
        }
        if (userKey == "") {
            AlertDialog.Builder dialog = new AlertDialog.Builder(TaskActivity.this);
            dialog.setTitle("提示!");
            dialog.setMessage("当前无用户登陆，请先登录");
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
            return;
        }
        final Message msg = Message.obtain();
        String json = "{id:'" + mid + "'}";
        String baseurl = "http://192.168.8.101:9090/api/services/app/projectPlan/DownloadTaskImage";
        HttpUtils.sendOkHttpPostJsonWithRequest(baseurl, json, userKey, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                msg.what = 0;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                msg.obj = responseData;
                msg.what = 1;
                handler.sendMessage(msg);
                int segmentSize = 3 * 1024;
                long length = responseData.length();
                if (length <= segmentSize) {// 长度小于等于限制直接打印
                    Log.d("responseDatas", responseData);
                } else {
                    while (responseData.length() > segmentSize) {// 循环分段打印日志
                        String logContent = responseData.substring(0, segmentSize);
                        responseData = responseData.replace(logContent, "");
                        Log.d("responseDatas", logContent);
                    }
                    Log.d("responseDatas", responseData);// 打印剩余日志
                }
            }
        });

    }

    //解析Json-result
    public Boolean JsonWithGson(String jsonData) {
        Boolean result = false;
        try {
            JSONObject jsonObjects = new JSONObject(jsonData);
            result = jsonObjects.getBoolean("result");
            Log.d("responseDatas", "" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //解析Json-success
    public Boolean JsonWithGsonSuccess(String jsonData) {
        Boolean result = false;
        try {
            JSONObject jsonObjects = new JSONObject(jsonData);
            result = jsonObjects.getBoolean("success");
            Log.d("responseDatas", "" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void parseJsonWithGsonAndJson(String jsonData) {
        String result = null;
        try {
            JSONObject jsonObjects = new JSONObject(jsonData);
            result = jsonObjects.getString("result");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        List<JsonBean> downloadTaskImages = gson.fromJson(result, new TypeToken<List<JsonBean>>() {
        }.getType());
        for (JsonBean taskImage : downloadTaskImages) {
            base64ToFile(taskImage.getDetectImage().getImageData(), taskImage.getDetectImage().getCode());
            for (JsonBean.Components components : taskImage.getComponents()) {
                if (components.getBackvalue() != null && components.getDetectionValue() != null) {
                    if (components.getBackvalue() == null) {
                        double detectionValue = components.getDetectionValue() + components.getBackvalue();
                        detectionValue = Math.floor(detectionValue * 10) / 10;
                        BigDecimal big = new BigDecimal(detectionValue).setScale(1, BigDecimal.ROUND_DOWN);
                        components.setReadValue(big.doubleValue());
                    }
                }
            }
        }
        addTaskImage(downloadTaskImages, taskId);
    }

    public void addTaskImage(List<JsonBean> images, String taskId) {
        for (JsonBean image : images) {
            AllocationImage allocationImage = new AllocationImage();
            AllocationImage firstAll = LitePal.where("mid = ?", image.getDetectImage().getId() + taskId).findFirst(AllocationImage.class);
            Image detectImage = new Image();
            detectImage.setMid(image.detectImage.id);
            detectImage.setAreaId(image.detectImage.areaId);
            detectImage.setCode(image.detectImage.code);
            detectImage.setDisplayArea(image.detectImage.displayArea);
            detectImage.setDisplayBranchFactory(image.detectImage.displayBranchFactory);
            detectImage.setDisplayLine(image.detectImage.displayLine);
            detectImage.setDisplayPollutionSource(image.detectImage.displayPollutionSource);
            detectImage.setDisplayProcessUnit(image.detectImage.displayProcessUnit);
            detectImage.setDisplayUnit(image.detectImage.displayUnit);
            detectImage.setDisplayUnitArea(image.detectImage.displayUnitArea);
            detectImage.setEquipmentId(image.detectImage.equipmentId);
            detectImage.setFloor(image.detectImage.floor);
            detectImage.setImageData(null);
            detectImage.setImageUrl(image.detectImage.imageUrl);
            detectImage.setAudited(image.detectImage.isAudited);
            detectImage.setEnabled(image.detectImage.isEnabled);
            detectImage.setPipelineId(image.detectImage.pipelineId);
            detectImage.setPollutionSourceId(image.detectImage.pollutionSourceId);
            detectImage.setQuotationNumber(image.detectImage.quotationNumber);
            detectImage.setRemark(image.detectImage.remark);
            detectImage.setSerialNumber(image.detectImage.serialNumber);
            detectImage.setAreaName(image.detectImage.area.getName());
            detectImage.setAreaCode(image.detectImage.area.getCode());
            detectImage.setAreaAbbr(image.detectImage.area.getAbbr());
            detectImage.setLineName(image.detectImage.line.getName());
            detectImage.setLineCode(image.detectImage.line.getCode());
            detectImage.setLineAbbr(image.detectImage.line.getAbbr());
            detectImage.setUnitName(image.detectImage.unit.getName());
            detectImage.setUnitCode(image.detectImage.unit.getCode());
            detectImage.setUnitAbbr(image.detectImage.unit.getAbbr());
            Image firstImg = null;
            if (firstAll != null) {
                firstImg = LitePal.where("allocationImage_id = ? and mid = ?", String.valueOf(firstAll.getId()), image.getDetectImage().getId()).findFirst(Image.class);
            }
            if (firstImg == null) {
                detectImage.save();
            } else {
                detectImage.updateAll("allocationImage_id = ? and mid = ?", String.valueOf(firstAll.getId()), image.getDetectImage().getId());
            }
            allocationImage.setDetectImage(detectImage);
            allocationImage.setMid(image.getDetectImage().getId() + taskId);
            allocationImage.setImageId(image.getDetectImage().getId());
            allocationImage.setTaskId(taskId);
            allocationImage.setQuotationNumber(image.detectImage.quotationNumber);
            allocationImage.setSerialNumber(image.detectImage.serialNumber);
            allocationImage.setAreaCode(image.detectImage.area.getCode());
            allocationImage.setAreaAbbr(image.detectImage.area.getAbbr());
            allocationImage.setLineCode(image.detectImage.line.getCode());
            allocationImage.setLineAbbr(image.detectImage.line.getAbbr());
            allocationImage.setUnitCode(image.detectImage.unit.getCode());
            allocationImage.setUnitAbbr(image.detectImage.unit.getAbbr());
            allocationImage.setFloor(image.detectImage.floor);
            if (firstImg == null) {
                allocationImage.save();
            } else {
                allocationImage.updateAll("mid = ?", image.getDetectImage().getId() + taskId);
            }
            for (JsonBean.Components components : image.getComponents()) {
                Component component = new Component();
                component.setMid(components.getId());
                component.setAreaEquipmentNameCodeName(components.getAreaEquipmentNameCodeName());
                component.setAreaId(components.getAreaId());
                component.setBackValue(components.getBackvalue());
                component.setBuildDate(components.getBuildDate());
                component.setCode(components.getCode());
                component.setComponentTypeName(components.getComponentTypeName());
                component.setComponentTypeId(components.getComponentTypeId());
                component.setDetectionEndDate(components.getDetectionEndDate());
                component.setDetectionStartDate(components.getDetectionStartDate());
                component.setDetectImageId(components.getDetectImageId());
                component.setDetectionValue(components.getDetectionValue());
                component.setDisplayArea(components.getDisplayArea());
                component.setDisplayComponentType(components.getDisplayComponentType());
                component.setDisplayFluidType(components.getDisplayFluidType());
                component.setDisplayLine(components.getDisplayLine());
                component.setDisplayPolltionSource(components.getDisplayPolltionSource());
                component.setDisplayUnit(components.getDisplayUnit());
                component.setEquipmentId(components.getEquipmentId());
                component.setEquipmentNameCodeName(components.getEquipmentNameCodeName());
                component.setExemption(components.getExemption());
                component.setFloor(components.getFloor());
                component.setFluidComposition(components.getFluidComposition());
                component.setFluidTypeId(components.getFluidTypeId());
                component.setFluidTypeName(components.getFluidTypeName());
                component.setIndex(components.getIndex());
                component.setAudited(components.getAudited());
                component.setDetection(components.getDetection());
                component.setEnabled(components.getEnabled());
                component.setPipelineEquipmentNameCodeName(components.getPipelineEquipmentNameCodeName());
                component.setPipelineId(components.getPipelineId());
                component.setPollutionSourceId(components.getPollutionSourceId());
                component.setPollutionSourceName(components.getPollutionSourceName());
                component.setReadValue(components.getReadValue());
                component.setSerialNumber(components.getSerialNumber());
                component.setSize(components.getSize());
                component.setAllocationImage(allocationImage);
                component.setRectX(components.getLocation().getRectX());
                component.setRectY(components.getLocation().getRectY());
                component.setCircleX(components.getLocation().getCircleX());
                component.setCircleY(components.getLocation().getCircleY());
                Component firstCom = null;
                if (firstAll != null) {
                    firstCom = LitePal.where("allocationImage_id = ? and mid = ?", String.valueOf(firstAll.getId()), components.getId()).findFirst(Component.class);
                }

                if (firstCom == null) {
                    component.save();
                } else {
                    component.updateAll("allocationImage_id = ? and mid = ?", String.valueOf(firstAll.getId()), components.getId());
                }

            }
        }
    }

    //将base64编码字符串转化为图片保存
    public boolean base64ToFile(String base64Str, String fileName) {
        String imgFilePath = main + fileName + ".jpeg";
        byte[] data = Base64.decode(base64Str, Base64.DEFAULT);
        for (int i = 0; i < data.length; i++) {
            if (data[i] < 0) {
                //调整异常数据
                data[i] += 256;
            }
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(imgFilePath);
            os.write(data);
            os.flush();
            os.close();
            Log.d("bytedatas", "data is" + imgFilePath);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

}

