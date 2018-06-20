package com.example.arlin_huang.sgsleakmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.arlin_huang.sgsleakmanager.activity.TaskImageActivity;
import com.example.arlin_huang.sgsleakmanager.liteclass.AllocationImage;
import com.example.arlin_huang.sgsleakmanager.liteclass.Component;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TaskIndexAdapter extends RecyclerView.Adapter<TaskIndexAdapter.ViewHolder> {
    private Context mContext;
    private LeakManager leakManager;
    private List<AllocationImage> mTaskImages;
    public TaskIndexAdapter(List<AllocationImage> taskImages, Context context) {
        mTaskImages = taskImages;
        mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout;
        TextView imageCode;
        TextView imageUnit;
        TextView imageArea;
        TextView imageLine;
        TextView pollutionSource;
        TextView floor;
        TextView componentCount;
        TextView hasEntering;
        TextView quotationNumber;
        ImageView image;
        public ViewHolder(View itemView){
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.task_index_item);
            imageCode = (TextView) itemView.findViewById(R.id.image_code);
            imageUnit = (TextView) itemView.findViewById(R.id.image_unit);
            imageArea = (TextView) itemView.findViewById(R.id.image_area);
            imageLine = (TextView) itemView.findViewById(R.id.image_line);
            pollutionSource = (TextView) itemView.findViewById(R.id.pollution_source);
            floor = (TextView) itemView.findViewById(R.id.image_floor);
            componentCount = (TextView) itemView.findViewById(R.id.component_count);
            hasEntering = (TextView) itemView.findViewById(R.id.has_entering);
            quotationNumber = (TextView) itemView.findViewById(R.id.quotation_number);
            image = (ImageView) itemView.findViewById(R.id.image_url);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        leakManager = (LeakManager) mContext.getApplicationContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.taskindex_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                AllocationImage taskImage = mTaskImages.get(position);
                Intent intent = new Intent(mContext, TaskImageActivity.class);
                intent.putExtra("id", taskImage.getId());
                intent.putExtra("position", position);
                intent.putExtra("taskLength", mTaskImages.size());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AllocationImage allocationImage=mTaskImages.get(position);
        String code=allocationImage.getImage().getCode();
        String unitName="装置:"+allocationImage.getImage().getUnitName();
        String areaName="区域:"+allocationImage.getImage().getAreaName();
        String lineName="设备管线:"+allocationImage.getImage().getLineName();
        String polltionSource="污染源:"+allocationImage.getImage().getDisplayPollutionSource();
        String floor="楼层:"+allocationImage.getImage().getFloor();
        String comCount="元件数目:"+allocationImage.getComs().size();
        List<Component> components =new ArrayList<Component>();
        for(Component com :allocationImage.getComs())
        {
            if(com.getDetectionValue() !=null &&com.getReadValue() !=null){
                components.add(com);
            }
        }
        String hasEnteringCount="已录入数目:"+components.size();
        String quotationNumber="挂牌编号:"+allocationImage.getImage().getQuotationNumber();
        holder.imageCode.setText(code);
        holder.imageUnit.setText(unitName);
        holder.imageArea.setText(areaName);
        holder.imageLine.setText(lineName);
        holder.pollutionSource.setText(polltionSource);
        holder.floor.setText(floor);
        holder.componentCount.setText(comCount);
        holder.hasEntering.setText(hasEnteringCount);
        holder.quotationNumber.setText(quotationNumber);
        String url= Environment.getExternalStorageDirectory().getPath() + File.separator +  leakManager.getProjectImageUrl() + allocationImage.getTaskId() + "/"+allocationImage.getImage().getCode()+".jpeg";
        Glide.with(mContext).load(url).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return mTaskImages.size();
    }
}
