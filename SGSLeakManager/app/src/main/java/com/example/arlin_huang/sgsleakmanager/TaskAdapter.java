package com.example.arlin_huang.sgsleakmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arlin_huang.sgsleakmanager.activity.TaskIndexActivity;
import com.example.arlin_huang.sgsleakmanager.liteclass.AllocationImage;
import com.example.arlin_huang.sgsleakmanager.liteclass.PlanTask;


import org.litepal.LitePal;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {
    private PlanTask mPlanTask;

    public PlanTask getPlanTask() {
        return mPlanTask;
    }

    public void setPlanTask(PlanTask mPlanTask) {
        this.mPlanTask = mPlanTask;
    }

    private List<PlanTask> mPlanTasks;
    private Context mContext;
    private LeakManager leakManager;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView createTime;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.task_item);
            taskName = (TextView) itemView.findViewById(R.id.task_name);
            createTime = (TextView) itemView.findViewById(R.id.createTime);
        }
    }
    public TaskAdapter(List<PlanTask> planTasks, Context context) {
        mPlanTasks = planTasks;
        mContext = context;
    }

    public void updateTask(PlanTask planTask, Double backvalue) {
        Log.d("editId", "id is : " + planTask.getId());
        PlanTask planTasks = new PlanTask();
        planTasks.setBackValue(backvalue);
        planTasks.update(planTask.getId());
    }

    //删除任务
    public void removeItem(PlanTask planTask) {
        LitePal.delete(PlanTask.class, planTask.getId());
        LitePal.deleteAll(AllocationImage.class, "taskId = ?", planTask.getMid());
        Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();
        String taskDir = leakManager.getProjectImageUrl() + planTask.getMid() + "/";
        String bootPath= Environment.getExternalStorageDirectory().getPath() + File.separator + taskDir;
        File dir = new File(bootPath);
        deleteDirWihtFile(dir);
        Log.d("taskDir", taskDir);
    }
    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        leakManager = (LeakManager) mContext.getApplicationContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        final TaskAdapter.ViewHolder holder = new TaskAdapter.ViewHolder(view);
        holder.layout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int mPosition = holder.getAdapterPosition();
                mPlanTask = mPlanTasks.get(mPosition);
                menu.setHeaderTitle(mPlanTask.getTaskName() + "同步操作");
                menu.add(0, 0, 0, "下载任务图片");
                menu.add(0, 1, 0, "上传任务数据");
                menu.add(0, 2, 0, "设置背景值");
                menu.add(0, 3, 0, "删除");
                menu.add(0, 4, 0, "取消");

            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                PlanTask planTask = mPlanTasks.get(position);
                leakManager.setCurrentTask(planTask);
                Intent intent = new Intent(mContext, TaskIndexActivity.class);
                intent.putExtra("taskId", planTask.getMid());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(TaskAdapter.ViewHolder holder, int position) {
        PlanTask planTask = mPlanTasks.get(position);
        String name = planTask.getTaskName() + "(背景值:" + planTask.getBackValue() + ",仪器:"
                + planTask.getInstrumentCode() + ",响应时间:" + planTask.getInstrumentResponseTime() + ")";
        holder.taskName.setText(name);
        SimpleDateFormat dateFormatter = new SimpleDateFormat(
                "yyyy-MM-dd");
        String strDate = dateFormatter.format(planTask.getCreationTime());
        String text = "创建日期:";
        holder.createTime.setText(text + strDate);
    }

    @Override
    public int getItemCount() {
        return mPlanTasks.size();
    }
}
