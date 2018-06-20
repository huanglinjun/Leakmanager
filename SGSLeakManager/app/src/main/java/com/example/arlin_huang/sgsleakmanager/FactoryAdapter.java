package com.example.arlin_huang.sgsleakmanager;

import android.app.Activity;
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
import com.example.arlin_huang.sgsleakmanager.liteclass.Factories;

import org.litepal.LitePal;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Arlin_Huang on 2018/3/27.
 */

public class FactoryAdapter extends RecyclerView.Adapter<FactoryAdapter.ViewHolder> {
    private List<Factories> mFactories;
    private Context mContext;
    private LeakManager leakManager;
    private Factories mFactory;
    private String currentTenantName;

    public FactoryAdapter(List<Factories> factories, Context context) {
        mFactories = factories;
        mContext = context;
    }

    public Factories getFactories() {
        return mFactory;
    }

    public void setFactories(Factories factories) {
        this.mFactory = factories;
    }

    //删除工厂
    public void removeItem(Factories factory) {
        LitePal.delete(Factories.class, factory.getId());
    }

    //编辑工厂
    public void editItem(Factories factory) {
        Intent intent = new Intent(mContext, FactoryActivity.class);
        intent.putExtra("mFactory", factory);
//        intent.putExtra(FactoryActivity.FACTORY_ID, mFactory.getId());
//        mContext.startActivity(intent);
        ((Activity) mContext).startActivityForResult(intent, 2);
    }

    //长按登录
    public void loginItem(Factories factory) {
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra("tenantName", factory.getTenantName());
        ((Activity) mContext).startActivityForResult(intent, 3);
//        mContext.startActivity(intent);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        leakManager = (LeakManager) mContext.getApplicationContext();
        currentTenantName = leakManager.getCurrentTenantName();

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.factory_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.layout.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                int mPosition = holder.getAdapterPosition();
                mFactory = mFactories.get(mPosition);
                menu.setHeaderTitle(mFactory.getName() + " 操作");
                menu.add(0, 0, 0, "登录用户");
                menu.add(0, 1, 0, "编辑");
                menu.add(0, 2, 0, "删除");
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                mFactory = mFactories.get(position);
                String factoryIamgeUrl = mFactory.getAttr() + '/';
                Log.d("testtime",factoryIamgeUrl);
                leakManager.setFactoryImageUrl(factoryIamgeUrl);
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {
                    String main = Environment.getExternalStorageDirectory().getPath() + File.separator + leakManager.getBaseImageUrl()+factoryIamgeUrl;
                    File destDir = new File(main);
                    if (!destDir.exists()) {
                        destDir.mkdirs();//在根创建了文件夹
                    }
                }

                Intent intent = new Intent(mContext, ProjectActivity.class);
                intent.putExtra(FactoryActivity.FACTORY_NAME, mFactory.getName());
                intent.putExtra(FactoryActivity.FACTORY_TENENTNAME, mFactory.getTenantName());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public int getItemCount() {
        return mFactories.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Factories factories = mFactories.get(position);
        String name = factories.getName();
        String a = factories.getTenantName();
        if (a.equals(currentTenantName)&& a.length()!=0) {
            Log.d("onBind", "" + leakManager.getOnlineLogin());
            String isonline = leakManager.getOnlineLogin() ? "在线" : "离线";
            name = name + " 当前登录:" + leakManager.getCurrentUserName() + "(" + isonline + ")";
        }
        holder.factoryName.setText(name);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView factoryName;
        LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.factory_item);
            factoryName = (TextView) itemView.findViewById(R.id.factory_name);
        }
    }
}
