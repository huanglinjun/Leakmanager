package com.example.arlin_huang.sgsleakmanager.activity;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlin_huang.sgsleakmanager.LeakManager;
import com.example.arlin_huang.sgsleakmanager.R;
import com.example.arlin_huang.sgsleakmanager.liteclass.AllocationImage;
import com.example.arlin_huang.sgsleakmanager.liteclass.Component;
import com.example.arlin_huang.sgsleakmanager.liteclass.PlanTask;
import com.example.arlin_huang.sgsleakmanager.liteclass.Project;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.litepal.LitePal;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TaskImageActivity extends AppCompatActivity implements View.OnTouchListener {
    protected static final int MSG_WHAT = 0;
    private LeakManager leakManager;
    private TextView unit;
    private TextView area;
    private TextView line;
    private TextView pollution;
    private TextView floor;
    private TextView quotationNumber;
    private ImageView imageView;
    private TextView code;
    private int currentComponentIndex = 0;
    private TextView size;
    private TextView shortTime;
    private String url;
    private int bitWidth;
    private AllocationImage taskImage;
    private PlanTask currentTask;
    private Project currentProject;
    private int id;
    private Button btnStart;
    private Button btnEnd;
    private Button btnDeleteCom;
    private Button btnScanCode;
    private TextView startDate;
    private TextView endDate;
    private Timer timer;
    private int keepTime;
    private Component comFirst;
    private SimpleDateFormat formatDate;
    private EditText editBackValue;
    private EditText editReadValue;
    private EditText editDetectionValue;
    private GestureDetector mGestureDetector;
    private static final int FLING_MIN_DISTANCE = 50;   //最小距离
    private static final int FLING_MIN_VELOCITY = 0;  //最小速度

    private float windowHeight;
    private String title;
    private TextView taskImageTitle;
    private int position;
    private int length;
    private int comLength;
    private List<AllocationImage> taskImages;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            btnEnd.setText("结束(" + keepTime + ")");
            switch (msg.what) {
                case MSG_WHAT:
                    keepTimeIntervalHandler();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        return mGestureDetector.onTouchEvent(event);
    }

    public void keepTimeIntervalHandler() {
        keepTime++;
        if (keepTime - 1 > comFirst.getShortTime()) {
            btnEnd.setEnabled(true);
        }
        if (keepTime >= 1800) {
            end();
            cancelKeepTime();
        }
    }

    public void cancelKeepTime() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            keepTime = 0;
        }
    }

    public void start() {

        //获取当前时间
        Date startdate = new Date(System.currentTimeMillis());
        startDate.setText("开始检测时间: " + formatDate.format(startdate));
        btnStart.setVisibility(View.GONE);
        btnEnd.setVisibility(View.VISIBLE);
        btnEnd.setEnabled(false);
        comFirst.setDetectionStartDate(formatDate.format(startdate));
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    mHandler.sendEmptyMessage(MSG_WHAT);

                }
            }, 0, 1000);
        } else {

            return;
        }
    }

    public void end() {
        btnEnd.setVisibility(View.GONE);
        btnStart.setVisibility(View.VISIBLE);
        if (comFirst.getDetectionStartDate() == null) {
            Toast.makeText(TaskImageActivity.this, "未开始", Toast.LENGTH_SHORT).show();
            return;
        }
        cancelKeepTime();

        //获取当前时间
        Date enddate = new Date(System.currentTimeMillis());

        int size = comFirst.getSize();


        if (size > 0) {
            try {
                Date startdate = formatDate.parse(comFirst.getDetectionStartDate());
                long diff = enddate.getTime() - startdate.getTime();
                if (diff < currentProject.getIntervalTime() * 1000) {
                    Toast.makeText(TaskImageActivity.this, "未达到响应时间需求,需要" + comFirst.getShortTime() + "秒", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        comFirst.setDetectionEndDate(formatDate.format(enddate));
        endDate.setText("结束检测时间: " + formatDate.format(enddate));
        saveTaskImage(comFirst);

    }

    public void saveTaskImage(Component comFirst) {
        Component component = new Component();
        component.setBackValue(comFirst.getBackValue());
        component.setReadValue(comFirst.getReadValue());
        component.setDetectionValue(comFirst.getDetectionValue());
        component.setDetectionStartDate(comFirst.getDetectionStartDate());
        component.setDetectionEndDate(comFirst.getDetectionEndDate());
        component.update(comFirst.getId());
        Component c = LitePal.where("id = ?", String.valueOf(comFirst.getId())).findFirst(Component.class);
        Logger.d(c.getDetectionStartDate());
        Logger.d(c.getDetectionEndDate());
        Logger.d(c.getBackValue());
        Logger.d("getReadValue" + c.getReadValue());
        Logger.d(c.getDetectionValue());
    }

    //改变背景值
    public void changeBackValue() {
        double backValue = Double.parseDouble(editBackValue.getText().toString());
        comFirst.setBackValue(backValue);
        if (comFirst.getReadValue() == null || comFirst.getDetectionValue() == null) {
            saveTaskImage(comFirst);
        } else {
            double detection = comFirst.getDetectionValue() == null ? 0 : comFirst.getDetectionValue();
            double read = backValue + detection;
            BigDecimal re = new BigDecimal(read).setScale(1, BigDecimal.ROUND_HALF_UP);
            comFirst.setReadValue(re.doubleValue());
            saveTaskImage(comFirst);
            editReadValue.setText(re.toString());
        }

    }

    //改变检测值
    public void changeReadValue() {
        double readValue = Double.parseDouble(editReadValue.getText().toString());
        comFirst.setReadValue(readValue);
        if (comFirst.getBackValue() == null || comFirst.getDetectionValue() == null) {
            saveTaskImage(comFirst);
        } else {
            double detection = readValue - comFirst.getBackValue();
            BigDecimal de = new BigDecimal(detection).setScale(1, BigDecimal.ROUND_HALF_UP);
            comFirst.setDetectionValue(de.doubleValue());
            saveTaskImage(comFirst);
            editDetectionValue.setText(de.toString());
        }


    }

    //改变净检值
    public void changeDetectionValue() {
        double detectionValue = Double.parseDouble(editDetectionValue.getText().toString());
        comFirst.setDetectionValue(detectionValue);
        double back = comFirst.getBackValue() == null ? 0 : comFirst.getBackValue();
        double read = detectionValue + back;
        BigDecimal re = new BigDecimal(read).setScale(1, BigDecimal.ROUND_HALF_UP);
        comFirst.setReadValue(re.doubleValue());
        saveTaskImage(comFirst);
        editReadValue.setText(re.toString());

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_image);
        Logger.addLogAdapter(new AndroidLogAdapter());
        leakManager = (LeakManager) getApplication();
        currentTask = leakManager.getCurrentTask();
        currentProject = leakManager.getCurrentProject();
        formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        position = intent.getIntExtra("position", 0);
        length = intent.getIntExtra("taskLength", 0);
        taskImages = LitePal.where("taskId = ?", currentTask.getMid())
                .order("unitAbbr,unitCode,areaAbbr,areaCode,lineAbbr,unitCode,floor,serialNumber")
                .find(AllocationImage.class);


        //获取屏幕高度
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        windowHeight = outMetrics.heightPixels;
        mGestureDetector = new GestureDetector(this, myGestureListener);
        LinearLayout mLayout = (LinearLayout) findViewById(R.id.task_image_drawer);//布局的主容器
        mLayout.setOnTouchListener(this);//将主容器的监听交给本activity，本activity再交给mGestureDetector
        mLayout.setLongClickable(true);   //必需设置这为true 否则也监听不到手势

        InitData();

    }

    public void InitData() {
        // taskImage = LitePal.where("id = ?", String.valueOf(id)).findFirst(AllocationImage.class);
        taskImage = taskImages.get(position);
        Toolbar toolbar = (Toolbar) findViewById(R.id.task_image_toolbar);
        taskImageTitle = (TextView) findViewById(R.id.task_image_title);
        toolbar.setTitle("");
        title = position + 1 + "/" + length + " -- " + currentTask.getTaskName();
        taskImageTitle.setText(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        unit = (TextView) findViewById(R.id.task_image_unit);
        area = (TextView) findViewById(R.id.task_image_area);
        line = (TextView) findViewById(R.id.task_image_line);
        pollution = (TextView) findViewById(R.id.task_image_pollution);
        floor = (TextView) findViewById(R.id.task_image_floor);
        quotationNumber = (TextView) findViewById(R.id.task_image_quotationNumber);
        size = (TextView) findViewById(R.id.size);
        shortTime = (TextView) findViewById(R.id.shortTime);
        unit.setText("装置：" + taskImage.getImage().getDisplayBranchFactory());
        area.setText("区域：" + taskImage.getImage().getDisplayProcessUnit());
        line.setText("管线：" + taskImage.getImage().getDisplayUnitArea());
        pollution.setText("污染源：" + taskImage.getImage().getDisplayPollutionSource());
        floor.setText("楼层：" + String.valueOf(taskImage.getFloor()));
        quotationNumber.setText("挂牌编号：" + taskImage.getQuotationNumber());

        code = (TextView) findViewById(R.id.task_image_code);
        double intervalTime;
        List<Component> components = taskImage.getComs();
        for (Component component : components) {
            int comSize = component.getSize();
            intervalTime = (3.14 * comSize) / 100 + 2 * currentTask.getInstrumentResponseTime();
            component.setShortTime(intervalTime);
            if (component.getBackValue() == null) {
                if (currentTask != null) {
                    component.setBackValue(currentTask.getBackValue());
                }
            }
        }
        comLength = components.size();
        comFirst = components.get(currentComponentIndex);
        shortTime.setText("最短运行时： " + comFirst.getShortTime() + "秒");
        size.setText("元件尺寸： " + comFirst.getSize());
        String isDetection = comFirst.getDetection() ? "(不可达)" : "(可达)";
        code.setText(comFirst.getCode() + isDetection);
        double projectIntervalTime = currentProject.getIntervalTime();
        imageView = (ImageView) findViewById(R.id.task_image);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);
        startDate.setText("开始检测时间: " + comFirst.getDetectionStartDate());
        endDate.setText("结束检测时间: " + comFirst.getDetectionEndDate());
        btnStart = (Button) findViewById(R.id.btn_start);
        btnEnd = (Button) findViewById(R.id.btn_end);
        btnDeleteCom = (Button) findViewById(R.id.deleteCom);
        btnScanCode =(Button) findViewById(R.id.scan_code);
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end();

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        btnDeleteCom.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Snackbar.make(v, "确认重置数据", Snackbar.LENGTH_LONG)
                        .setAction("确定重置当前密封点数据吗", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                resetComponent();
                            }
                        })
                        .show();

            }
        });
        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); //初始化连接
                if (mBluetoothAdapter == null) {
                    Toast.makeText(TaskImageActivity.this, "设备不支持蓝牙功能", Toast.LENGTH_SHORT).show();
                }
                if (!mBluetoothAdapter.isEnabled()) {
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                   // startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                }


            }
        });

        url = Environment.getExternalStorageDirectory().getPath() + File.separator + leakManager.getProjectImageUrl() +
                taskImage.getTaskId() + "/" + taskImage.getImage().getCode() + ".jpeg";

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TaskImageActivity.this, LargeImageActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
            }
        });
        editBackValue = (EditText) findViewById(R.id.backValue);
        editReadValue = (EditText) findViewById(R.id.readValue);
        editDetectionValue = (EditText) findViewById(R.id.detectionValue);
        if (comFirst.getDetection()) {
            editBackValue.setText("ND");
            editDetectionValue.setText("ND");
            editReadValue.setText("ND");
            editBackValue.setEnabled(false);
            editDetectionValue.setEnabled(false);
            editReadValue.setEnabled(false);
        } else {
            editBackValue.setText(comFirst.getBackValue().toString());
            editDetectionValue.setText(comFirst.getDetectionValue().toString());
            editReadValue.setText(comFirst.getReadValue().toString());

        }

        editBackValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    changeBackValue();
                }
            }
        });
        editReadValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    changeReadValue();

                }
            }
        });

        editDetectionValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    changeDetectionValue();
                }
            }
        });
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                end();

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
        url = Environment.getExternalStorageDirectory().getPath() + File.separator + leakManager.getProjectImageUrl() +
                taskImage.getTaskId() + "/" + taskImage.getImage().getCode() + ".jpeg";

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(TaskImageActivity.this, LargeImageActivity.class);
                intent1.putExtra("id", id);
                startActivity(intent1);
            }
        });
        editBackValue = (EditText) findViewById(R.id.backValue);
        editReadValue = (EditText) findViewById(R.id.readValue);
        editDetectionValue = (EditText) findViewById(R.id.detectionValue);
        if (comFirst.getDetection()) {
            editBackValue.setText("ND");
            editDetectionValue.setText("ND");
            editReadValue.setText("ND");
            editBackValue.setEnabled(false);
            editDetectionValue.setEnabled(false);
            editReadValue.setEnabled(false);
        } else {
            editBackValue.setText(comFirst.getBackValue().toString());
            editDetectionValue.setText(comFirst.getDetectionValue().toString());
            editReadValue.setText(comFirst.getReadValue().toString());

        }

        editBackValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    changeBackValue();
                }
            }
        });
        editReadValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    changeReadValue();

                }
            }
        });

        editDetectionValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    changeDetectionValue();
                }
            }
        });
        //Canvas画图
        Bitmap bitmap = BitmapFactory.decodeFile(url).copy(Bitmap.Config.ARGB_8888, true);
        int i = 0;
        bitWidth = bitmap.getWidth();
        int w1 = dp2px(this, 400);
        if (bitWidth < 600) {
            i = bitWidth - 625;
        }
        Canvas canvas = new Canvas(bitmap);//创建一个空画布，并给画布设置位图
        Paint p = new Paint();
        for (Component com : taskImage.getComs()) {
            float rectX = (float) com.getRectX();
            float rectY = (float) com.getRectY();
            float circleX = (float) com.getCircleX();
            float circleY = (float) com.getCircleY();
            // 画线
            p.setColor(Color.BLUE);
            p.setStrokeWidth(2);
            canvas.drawLine(rectX + i, rectY, circleX + i, circleY, p);
            // 画矩形
            p.setColor(Color.GREEN);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(rectX + i, rectY, rectX + i + 30, rectY + 20, p);
            // 画文字
            p.setColor(Color.WHITE);
            p.setTextSize(20);
            canvas.drawText(String.valueOf(com.getIndex()), rectX + i + 5, rectY + 15, p);
            // 画圆形
            p.setColor(Color.RED);
            canvas.drawCircle(circleX + i, circleY, 5, p);
            imageView.setImageBitmap(bitmap);//给ImageView设置新的图片
        }
    }
    //重置当前密封点
    public void resetComponent(){
        comFirst.setDetectionStartDate("");
        comFirst.setDetectionEndDate("");
        comFirst.setReadValue(0.0);
        comFirst.setDetectionValue(0.0);
        comFirst.setBackValue(0.0);
        saveTaskImage(comFirst);
        InitData();

    }

    //下一张图片
    public void nextImage() {
        position++;
        int max = length - 1;
        if (position > max) {
            position = max;
            Toast.makeText(TaskImageActivity.this, "已是最后一张图片", Toast.LENGTH_SHORT).show();
        }
        InitData();
    }

    //上一张图片
    public void prevImage() {
        position--;
        int max = length - 1;
        if (position < 0) {
            position = 0;
            Toast.makeText(TaskImageActivity.this, "已是第一张图片", Toast.LENGTH_SHORT).show();
        }
        InitData();
    }

    //下一个密封点
    public void nextComponent() {
        currentComponentIndex++;
        int max = comLength - 1;
        if (currentComponentIndex > max) {
            currentComponentIndex = max;
            Toast.makeText(TaskImageActivity.this, "已是最后一个密封点", Toast.LENGTH_SHORT).show();
        }
        InitData();
    }

    //上一个密封点
    public void prevComponent() {
        currentComponentIndex--;
        int max = comLength - 1;
        if (currentComponentIndex < 0) {
            currentComponentIndex = 0;
            Toast.makeText(TaskImageActivity.this, "已是第一个密封点", Toast.LENGTH_SHORT).show();
        }
        InitData();
    }

    //监听左右滑动手势
    GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener() {

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float y = windowHeight - e1.getY();
            float x = e1.getX() - e2.getX();
            float x2 = e2.getX() - e1.getX();
            if (y >= 0 && x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                prevImage();

            } else if (y >= 0 && x2 > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                nextImage();
            } else if (y < 0 && x > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                prevComponent();

            } else if (y < 0 && x2 > FLING_MIN_DISTANCE && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                nextComponent();
            }
            return false;
        }

    };

    //dp转换成px
    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转换成dp
     */
    private int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
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
}
