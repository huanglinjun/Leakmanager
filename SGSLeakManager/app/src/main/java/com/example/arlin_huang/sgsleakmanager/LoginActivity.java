package com.example.arlin_huang.sgsleakmanager;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.arlin_huang.sgsleakmanager.liteclass.UserLogin;
import com.example.arlin_huang.sgsleakmanager.util.HttpUtils;

import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private LeakManager leakManager;
    private EditText userNameEdit;
    private EditText passwordEdit;
    private Button login;
    private Button loginBack;
    private RadioGroup radioGroup;
    private RadioButton radioButton_online, radioButton_offline;
    private String result;
    private String loginType = "online";
    private String password;
    private String userName;
    private Intent intent;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            Log.d("what", Integer.toString(what));
            if (what == 1) {
                LeakManager str = (LeakManager) msg.obj;
                leakManager.setUserKey(str.userKey);
                leakManager.setCurrentUserId(str.currentUserId);
                leakManager.setCurrentUserName(str.currentUserName);
                leakManager.setCurrentTenantName(str.currentTenantName);
                leakManager.setCurrentPassword(str.currentPassword);
                leakManager.setOnlineLogin(true);
                Log.d("whatleakManager", leakManager.getUserKey());
                Log.d("whatleakManager", leakManager.getCurrentUserName());
                Log.d("whatleakManager", leakManager.getCurrentTenantName());
                Log.d("whatleakManager", leakManager.getCurrentPassword());
                Toast.makeText(LoginActivity.this, "在线登录成功", Toast.LENGTH_SHORT).show();
                saveOnlineUser(str.currentTenantName, userName, password, str.currentUserId);
                setResult(RESULT_OK, intent);
                finish();

            } else {
                String str = (String) msg.obj;
                Toast.makeText(LoginActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        }

        ;
    };

    private void saveOnlineUser(String tenantName, String userName, String password, int userId) {
        UserLogin firstUser = LitePal.where("tenantName = ? and userName = ? and userPassword = ? and userId = ?",
                tenantName, userName, password, String.valueOf(userId)).findFirst(UserLogin.class);
        if (firstUser != null) {
            firstUser.setType("ONLINE_USER");
            firstUser.setTenantName(tenantName);
            firstUser.setUserName(userName);
            firstUser.setUserPassword(password);
            firstUser.setUserId(userId);
            firstUser.setIndexId(tenantName + "#" + userName);
            firstUser.update(firstUser.getId());
        } else {
            UserLogin user = new UserLogin();
            user.setType("ONLINE_USER");
            user.setTenantName(tenantName);
            user.setUserName(userName);
            user.setUserPassword(password);
            user.setUserId(userId);
            user.setIndexId(tenantName + "#" + userName);
            user.save();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        leakManager = (LeakManager) getApplication();
        Log.d("loginleakManager", "" + leakManager.getOnlineLogin());
        intent = getIntent();
        final String tenantName = intent.getStringExtra("tenantName");
        userNameEdit = (EditText) findViewById(R.id.username);
        passwordEdit = (EditText) findViewById(R.id.password);
        userNameEdit.setText(leakManager.getCurrentUserName());
        passwordEdit.setText(leakManager.getCurrentPassword());

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup_line);
        radioButton_online = (RadioButton) findViewById(R.id.online);
        radioButton_offline = (RadioButton) findViewById(R.id.offline);
        if (!leakManager.getOnlineLogin()) {
            radioButton_offline.setChecked(true);
        }
        login = (Button) findViewById(R.id.login);
        loginBack = (Button) findViewById(R.id.title_edit);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if (v.getId() == R.id.login) {
                    if (loginType == "online") {
                        sendRequestWithHttpOKHttp(tenantName, userName, password);
                    } else {
                        UserLogin firstUser = LitePal
                                .where("tenantName = ? and userName = ? and userPassword = ?",
                                        tenantName, userName, password).findFirst(UserLogin.class);
                        if (firstUser != null) {
                            leakManager.setCurrentUserId(firstUser.getUserId());
                            leakManager.setCurrentUserName(firstUser.getUserName());
                            leakManager.setCurrentPassword(firstUser.getUserPassword());
                            leakManager.setCurrentTenantName(firstUser.getTenantName());
                            leakManager.setOnlineLogin(false);
                            Toast.makeText(LoginActivity.this, "离线登录成功", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "离线登录失败", Toast.LENGTH_SHORT).show();
                        }

                    }


                }

            }
        });
        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (R.id.online == checkedId) {
                    loginType = "online";
                } else if (R.id.offline == checkedId) {
                    loginType = "offline";
                }
            }
        });
    }

    private void sendRequestWithHttpOKHttp(final String tenantName, final String userName, final String password) {
        final Message msg = Message.obtain();
        String baseurl = "http://192.168.8.101:9090/api/Account";
        String json = "{TenancyName:'" + tenantName + "',UsernameOrEmailAddress:'" + userName + "'," +
                "Password:'" + password + "'}";

        HttpUtils.sendOkHttpPostJsonRequest(baseurl, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                msg.obj = "在线登录失败，服务器未响应";
                msg.what = 0;
                handler.sendMessage(msg);
                Log.d("logininfo", "onFailure: ");

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                result = parseJSONWithJsonObject(responseData);
                if (result != "null") {

                    String url = "http://192.168.8.101:9090/api/services/app/session/GetCurrentLoginInformations";
                    HttpUtils.sendOkHttpPostRequest(url, result, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("logininfo", "保存在线用户信息失败，服务器未响应");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = response.body().string();
                            LeakManager leakManagers = getUserAndTenantJsonObject(responseData, tenantName);
                            leakManagers.userKey = result;
                            leakManagers.currentPassword = password;
                            msg.obj = leakManagers;
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }
                    });
                } else {
                    msg.obj = "在线登录失败，用户名或密码错误";
                    msg.what = 0;
                    handler.sendMessage(msg);
                    Log.d("logininfo", "psdfailure: ");
                }

            }
        });

    }

    private String parseJSONWithJsonObject(String jsonDatas) {
        String result = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonDatas);
            result = jsonObject.getString("result");
            Boolean isSuccess = jsonObject.getBoolean("success");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
    }

    private LeakManager getUserAndTenantJsonObject(String jsonDatas, String tenantName) {
        try {
            JSONObject jsonObjects = new JSONObject(jsonDatas);
            String result = jsonObjects.getString("result");
            JSONObject jsonObject = new JSONObject(result);
            String jsonUser = jsonObject.getString("user");
            JSONObject user = new JSONObject(jsonUser);
            leakManager.currentUserId = user.getInt("id");
            leakManager.currentUserName = user.getString("userName");
            leakManager.currentTenantName = tenantName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return leakManager;
    }
}
