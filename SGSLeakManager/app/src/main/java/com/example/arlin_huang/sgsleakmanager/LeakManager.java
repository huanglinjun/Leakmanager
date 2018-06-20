package com.example.arlin_huang.sgsleakmanager;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.arlin_huang.sgsleakmanager.liteclass.PlanTask;
import com.example.arlin_huang.sgsleakmanager.liteclass.Project;
import com.inuker.bluetooth.library.BluetoothClient;

import org.litepal.LitePalApplication;

public class LeakManager extends LitePalApplication {

    BluetoothClient mClient = new BluetoothClient(context);
//    public String currentUser;
//    public String currentTenant;
    public int currentUserId;
    public String currentUserName;
    public String currentTenantName;
    public Boolean isOnlineLogin;
    public String userKey;
    public String currentFactory;
    public String currentPassword;
    public String baseImageUrl;
    public String factoryImageUrl;
    public String projectImageUrl;

    public Project currentProject;

    public Project getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(Project currentProject) {
        this.currentProject = currentProject;
    }

    protected PlanTask currentTask;

    public PlanTask getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(PlanTask currentTask) {
        this.currentTask = currentTask;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
    public String getProjectImageUrl() {
        return projectImageUrl;
    }

    public void setProjectImageUrl(String projectImageUrl) {
        this.projectImageUrl = projectImageUrl;
    }

    public String getBaseImageUrl() {
        return baseImageUrl;
    }

    public void setBaseImageUrl(String baseImageUrl) {
        this.baseImageUrl = baseImageUrl;
    }

    public String getFactoryImageUrl() {
        return factoryImageUrl;
    }

    public void setFactoryImageUrl(String factoryImageUrl) {
        this.factoryImageUrl = factoryImageUrl;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getCurrentFactory() {
        return currentFactory;
    }

    public void setCurrentFactory(String currentFactory) {
        this.currentFactory = currentFactory;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public int getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(int currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getCurrentUserName() {
        return currentUserName;
    }

    public void setCurrentUserName(String currentUserName) {
        this.currentUserName = currentUserName;
    }

    public String getCurrentTenantName() {
        return currentTenantName;
    }

    public void setCurrentTenantName(String currentTenantName) {
        this.currentTenantName = currentTenantName;
    }

    public Boolean getOnlineLogin() {
        return isOnlineLogin;
    }

    public void setOnlineLogin(Boolean onlineLogin) {
        isOnlineLogin = onlineLogin;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUserKey("");
        setCurrentUserId(0);
        setCurrentFactory("");
        setCurrentUserName("");
        setCurrentTenantName("");
        setOnlineLogin(true);
        setFactoryImageUrl("");
        setBaseImageUrl("sgs_leakmanagerapp/images/");
        setProjectImageUrl("");
        setCurrentProject(null);
        setCurrentTask(null);

    }
}
