package com.example.arlin_huang.sgsleakmanager.activity;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlin_huang.sgsleakmanager.LeakManager;
import com.example.arlin_huang.sgsleakmanager.R;
import com.example.arlin_huang.sgsleakmanager.TaskIndexAdapter;
import com.example.arlin_huang.sgsleakmanager.liteclass.AllocationImage;
import com.example.arlin_huang.sgsleakmanager.liteclass.Component;
import com.example.arlin_huang.sgsleakmanager.liteclass.Image;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class TaskIndexActivity extends AppCompatActivity {
    private LeakManager leakManager;
    private String userKey;
    private EditText quotationNumber1;
    private EditText quotationNumber2;
    private EditText quotationNumber3;
    private String taskId;
    private List<AllocationImage> taskImages;
    private TaskIndexAdapter taskIndexAdapter;
    private List<Component> components;
    private Button scanCode;
    private Button searchCode;
    private String quotationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_index);
        leakManager = (LeakManager) getApplication();
        userKey = leakManager.getUserKey();
        Intent intent = getIntent();
        taskId = intent.getStringExtra("taskId");
        quotationNumber1 = (EditText) findViewById(R.id.quotationNumber1);
        quotationNumber2 = (EditText) findViewById(R.id.quotationNumber2);
        quotationNumber3 = (EditText) findViewById(R.id.quotationNumber3);
        quotationNumber1.setCursorVisible(false);
        quotationNumber1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    quotationNumber1.setCursorVisible(true);// 再次点击显示光标
                }
                return false;

            }
        });
        searchCode = (Button) findViewById(R.id.search_code);
        searchCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quotationNumber = quotationNumber1.getText().toString() + "-" +
                        quotationNumber2.getText().toString() + "-" +
                        quotationNumber3.getText().toString();
                List<AllocationImage> taskImages = LitePal.where("taskId = ? and quotationNumber = ?", taskId, quotationNumber).find(AllocationImage.class);
                if (taskImages.size() == 0) {
                    Toast.makeText(TaskIndexActivity.this, "不存在挂牌编号" + quotationNumber, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(TaskIndexActivity.this, TaskImageActivity.class);
                    intent.putExtra("id", taskImages.get(0).getId());
                    startActivity(intent);
                }
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_index_toolbar);
        TextView tasktitle = (TextView) findViewById(R.id.task_index_title);
        toolbar.setTitle("");
        String title = "检测图像";
        tasktitle.setText(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getTaskImageList(taskId);
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

    public void getTaskImageList(String taskId) {
        taskImages = LitePal.where("taskId = ?", taskId)
                .order("unitAbbr,unitCode,areaAbbr,areaCode,lineAbbr,unitCode,floor,serialNumber")
                .find(AllocationImage.class);

        int coms = 0;
        int hascoms = 0;
        for (AllocationImage taskImage : taskImages) {
            List<Component> components = taskImage.getComs();
            coms = coms + components.size();
            for (Component com : components) {
                if (com.getDetectionValue() != null && com.getReadValue() != null) {
                    hascoms++;
                }
            }
        }
        TextView imageNumber = (TextView) findViewById(R.id.image_number);
        TextView comNumber = (TextView) findViewById(R.id.com_number);
        TextView hascomNumber = (TextView) findViewById(R.id.hascom_number);
        imageNumber.setText("图片总数：" + taskImages.size());
        comNumber.setText("元件总数：" + coms);
        hascomNumber.setText("已录入元件总数：" + hascoms);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.task_index_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        taskIndexAdapter = new TaskIndexAdapter(taskImages, this);
        recyclerView.setAdapter(taskIndexAdapter);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
