package com.example.arlin_huang.sgsleakmanager.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arlin_huang.sgsleakmanager.LeakManager;
import com.example.arlin_huang.sgsleakmanager.R;
import com.example.arlin_huang.sgsleakmanager.liteclass.AllocationImage;
import com.example.arlin_huang.sgsleakmanager.liteclass.Component;

import org.litepal.LitePal;

import java.io.File;

public class LargeImageActivity extends AppCompatActivity {
    private LeakManager leakManager;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_large_image);
        leakManager = (LeakManager) getApplication();

        ImageView imageView = (ImageView) findViewById(R.id.large_image);
        final Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        AllocationImage taskImage = LitePal.where("id = ?", String.valueOf(id)).findFirst(AllocationImage.class);
        title =(TextView) findViewById(R.id.large_image_title);
        title.setText("放大图像--"+taskImage.getImage().getCode());
        String url = Environment.getExternalStorageDirectory().getPath() + File.separator + leakManager.getProjectImageUrl() + taskImage.getTaskId() + "/" + taskImage.getImage().getCode() + ".jpeg";
        Bitmap bitmap = BitmapFactory.decodeFile(url).copy(Bitmap.Config.ARGB_8888, true);

        int i = 0;
        int bitWidth = bitmap.getWidth();
        int w=getWindowManager().getDefaultDisplay().getWidth();
        int h=getWindowManager().getDefaultDisplay().getHeight();
        int w1 = dp2px(this, 800);
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
            p.setColor(Color.BLUE);
            p.setStrokeWidth(2);
            canvas.drawLine(rectX + i, rectY, circleX + i, circleY, p);// 画线

            p.setColor(Color.GREEN);
            p.setStyle(Paint.Style.FILL);
            canvas.drawRect(rectX + i, rectY, rectX + i + 30, rectY + 20, p);

            p.setColor(Color.WHITE);
            p.setTextSize(20);
            canvas.drawText(String.valueOf(com.getIndex()), rectX + i + 5, rectY + 15, p);

            p.setColor(Color.RED);
            canvas.drawCircle(circleX + i, circleY, 5, p);
            imageView.setImageBitmap(bitmap);//给ImageView设置新的图片
        }
    }
    //dp转换成px
    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
