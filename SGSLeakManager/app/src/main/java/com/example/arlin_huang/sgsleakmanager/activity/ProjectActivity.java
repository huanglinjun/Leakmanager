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
import android.util.Log;
import android.view.ContextMenu;

import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.arlin_huang.sgsleakmanager.LeakManager;
import com.example.arlin_huang.sgsleakmanager.ProjectAdapter;
import com.example.arlin_huang.sgsleakmanager.R;

import com.example.arlin_huang.sgsleakmanager.liteclass.PlanTask;
import com.example.arlin_huang.sgsleakmanager.liteclass.Project;
import com.example.arlin_huang.sgsleakmanager.util.HttpUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ProjectActivity extends AppCompatActivity {
    private LeakManager leakManager;
    private ProjectAdapter projectAdapter;
    private List<Project> projectLists = new ArrayList<>();
    ;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String str = (String) msg.obj;
            int what = msg.what;
            if (what == 1) {
                getProjectList();
            }
            Toast.makeText(ProjectActivity.this, str, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        leakManager = (LeakManager) getApplication();
        Toolbar toolbar = (Toolbar) findViewById(R.id.project_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Button btnDown = (Button) findViewById(R.id.btn_downPro);
        btnDown.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("同步操作");
                menu.add(0, 0, 0, "下载项目基础信息");
                menu.add(0, 1, 0, "取消");
            }
        });
        getProjectList();

    }

    public void getProjectList() {
        projectLists = LitePal.findAll(Project.class);
//        for (Project project : projectLists) {
//            Log.d("responseDatas", "project id is " + project.getId());
//            Log.d("responseDatas", "project name is " + project.getProjectName());
//            Log.d("responseDatas", "project getCreationTime is " + project.getIntervalTime());
//            Log.d("responseDatas", "project mid is " + project.getMid());
//        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.project_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        projectAdapter = new ProjectAdapter(projectLists, this);
        recyclerView.setAdapter(projectAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                downloadProject();
                return true;
            case 1:
                return true;
            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(ProjectActivity.this);
                builder.setTitle("输入密封点间隔时间");    //设置对话框标题
                final EditText edit = new EditText(ProjectActivity.this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(edit);
                builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("edit", edit.getText().toString());
                        Double intervalTime = Double.valueOf(edit.getText().toString());
                        projectAdapter.updateProject(projectAdapter.getProject(), intervalTime);
                        getProjectList();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ProjectActivity.this, "你点了取消", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setCancelable(true);    //设置按钮是否可以按返回键取消,false则不可以取消
                AlertDialog dialog = builder.create();  //创建对话框
                dialog.setCanceledOnTouchOutside(false); //设置弹出框失去焦点是否隐藏,即点击屏蔽其它地方是否隐藏
                dialog.show();
                return true;
            case 3:
                detection(projectAdapter.getProject(), 1);
                return true;
            case 4:
                detection(projectAdapter.getProject(), 2);
                return true;
            case 5:
                detection(projectAdapter.getProject(), 3);
                return true;
        }
        return false;
    }

    //一/二/三检操作
    public void detection(Project project, int testtime) {

        String name = leakManager.getBaseImageUrl() + leakManager.getFactoryImageUrl() + project.getProjectName()
                + '_' + testtime + '/';
        leakManager.setProjectImageUrl(name);
        leakManager.setCurrentProject(project);
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            String main = Environment.getExternalStorageDirectory().getPath() + File.separator + name;
            File destDir = new File(main);
            if (!destDir.exists()) {
                destDir.mkdirs();//在根创建了文件夹
            }
        }
        Intent intent = new Intent(ProjectActivity.this, TaskActivity.class);
        intent.putExtra("projectId", project.getMid());
        intent.putExtra("testtime", testtime);
        startActivity(intent);

    }

    //下载项目基础信息
    private void downloadProject() {
        final Message msg = Message.obtain();
        leakManager = (LeakManager) getApplication();
        String userKey = leakManager.getUserKey();
        if (userKey == "") {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ProjectActivity.this);
            dialog.setTitle("提示!");
            dialog.setMessage("当前无用户登陆，请先登录");
            dialog.setCancelable(false);
            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    finish();
                }
            });
            dialog.show();
            return;
        }
        String baseurl = "http://192.168.8.101:9090/api/services/app/projectPlan/DownProjectAndTasks";
        HttpUtils.sendOkHttpPostRequest(baseurl, userKey, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                msg.obj = "下载项目信息失败，服务器未响应";
                msg.what = 0;
                handler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Log.d("responseData", responseData);
                parseJsonWithGsonProject(responseData);
                msg.obj = "下载基础信息成功";
                msg.what = 1;
                handler.sendMessage(msg);

            }
        });

    }

    //解析Json并新增Project PlanTask
    private void parseJsonWithGsonProject(String jsonData) {

        try {
            JSONObject jsonObjects = new JSONObject(jsonData);
            String result = jsonObjects.getString("result");
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonProject = jsonArray.getJSONObject(i);
                String projectName = jsonProject.getString("projectName");
                String mid = jsonProject.getString("id");
                Project project = new Project();
                project.setProjectName(projectName);
                Project firstProject = LitePal.where("mid = ?", mid).findFirst(Project.class);
                if (firstProject != null) {
                    project.updateAll("mid= ?", mid);
                } else {
                    project.setMid(mid);
                    project.setIntervalTime(1);
                    project.save();
                }
                String taskResult = jsonProject.getString("planTaskDtos");
                JSONArray jsonArrayTask = new JSONArray(taskResult);
                for (int j = 0; j < jsonArrayTask.length(); j++) {
                    JSONObject jsonTask = jsonArrayTask.getJSONObject(j);
                    String taskName = jsonTask.getString("taskName");
                    int testtime = jsonTask.getInt("testtime");
                    String projectId = jsonTask.getString("projectId");
                    String remark = jsonTask.getString("remark");
                    long executiveStaffId = jsonTask.getLong("executiveStaffId");
                    String instrumentId = jsonTask.getString("instrumentId");
                    String instrumentCode = jsonTask.getString("instrumentCode");
                    String imageStartAndEndNumber = jsonTask.getString("imageStartAndEndNumber");
                    int instrumentResponseTime = jsonTask.getInt("instrumentResponseTime");
                    String strDateTime = jsonTask.getString("creationTime");
                    SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.CANADA.ENGLISH);//输入的被转化的时间格式
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    Date str1 = (Date) dff.parse(strDateTime);
                    String datetime = format.format(str1);
                    Date creationTime = (Date) format.parse((datetime));
                    int componentCount = jsonTask.getInt("componentCount");
                    int imageCount = jsonTask.getInt("imageCount");
                    Boolean isFinished = jsonTask.getBoolean("isFinished");
                    String taskId = jsonTask.getString("id");
                    PlanTask planTask = new PlanTask();
                    planTask.setMid(taskId);
                    planTask.setTaskName(taskName);
                    planTask.setTesttime(testtime);
                    planTask.setProjectId(projectId);
                    planTask.setRemark(remark);
                    planTask.setExecutiveStaffId(executiveStaffId);
                    planTask.setInstrumentId(instrumentId);
                    planTask.setInstrumentCode(instrumentCode);
                    planTask.setImageStartAndEndNumber(imageStartAndEndNumber);
                    planTask.setInstrumentResponseTime(instrumentResponseTime);
                    planTask.setCreationTime(creationTime);
                    planTask.setComponentCount(componentCount);
                    planTask.setImageCount(imageCount);
                    planTask.setFinished(isFinished);
                    PlanTask firstTask = LitePal.where("mid = ?", taskId).findFirst(PlanTask.class);
                    if (firstTask != null) {
                        planTask.updateAll("mid= ?", taskId);
                    } else {
                        planTask.save();
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //    private void setDialog() {
//        Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
//        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
//                R.layout.bottom_dialog, null);
//        //初始化视图
//        root.findViewById(R.id.btn_choose_img).setOnClickListener(this);
//        root.findViewById(R.id.btn_open_camera).setOnClickListener(this);
//        root.findViewById(R.id.btn_cancel).setOnClickListener(this);
//        mCameraDialog.setContentView(root);
//        Window dialogWindow = mCameraDialog.getWindow();
//        dialogWindow.setGravity(Gravity.BOTTOM);
////        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
//        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
//        lp.x = 0; // 新位置X坐标
//        lp.y = 0; // 新位置Y坐标
//        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
//        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
//
//        lp.alpha = 9f; // 透明度
//        dialogWindow.setAttributes(lp);
//        mCameraDialog.show();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
