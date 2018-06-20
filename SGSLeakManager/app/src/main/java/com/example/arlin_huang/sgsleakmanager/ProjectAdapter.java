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

import com.example.arlin_huang.sgsleakmanager.activity.FactoryActivity;
import com.example.arlin_huang.sgsleakmanager.activity.ProjectActivity;
import com.example.arlin_huang.sgsleakmanager.activity.TaskActivity;
import com.example.arlin_huang.sgsleakmanager.liteclass.Project;

import java.io.File;
import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {
    private Context mContext;
    private LeakManager leakManager;
    private List<Project> mProjects;
    private Project mProject;

    public Project getProject() {
        return mProject;
    }

    public void setProject(Project project) {
        this.mProject = project;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView projectName;
        TextView intervalTime;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.project_item);
            projectName = (TextView) itemView.findViewById(R.id.project_name);
            intervalTime = (TextView) itemView.findViewById(R.id.intervalTime);
        }
    }

    public ProjectAdapter(List<Project> projects, Context context) {
        mProjects = projects;
        mContext = context;
    }

    public void updateProject(Project project, Double intervalTime) {
        Log.d("editId", "id is : " + project.getId());
        Project projects = new Project();
        projects.setIntervalTime(intervalTime);
        projects.update(project.getId());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.project_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int mPosition = holder.getAdapterPosition();
                mProject = mProjects.get(mPosition);
                menu.setHeaderTitle(mProject.getProjectName() + "设置操作");
                menu.add(0, 2, 0, "设置密封点间隔时间");
                menu.add(0, 3, 0, "一检操作");
                menu.add(0, 4, 0, "二检操作");
                menu.add(0, 5, 0, "三检操作");

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Project project = mProjects.get(position);
        String text = "密封点间隔时间:";
        holder.projectName.setText(project.getProjectName());
        holder.intervalTime.setText(text + project.getIntervalTime());
    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }
}
