package com.example.arlin_huang.sgsleakmanager.util;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Arlin_Huang on 2018/4/3.
 */

public class HttpUtils {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static void sendOkHttpPostJsonRequest(String address ,String json , Callback callback){
        OkHttpClient client =new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(address)
                .post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpPostRequest(String address ,String userKey, Callback callback){
        OkHttpClient client =new OkHttpClient();
        RequestBody requestBody =new FormBody.Builder().build();
        Request request = new Request.Builder().url(address)
                .addHeader("Authorization","Bearer " + userKey)
                .post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpPostJsonWithRequest(String address ,String json ,String userKey, Callback callback){
        OkHttpClient client =new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(address)
                .addHeader("Authorization","Bearer " + userKey)
                .post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }
    public static void sendOkHttpRequest(String address ,Callback callback){
        OkHttpClient client =new OkHttpClient();
        Request request = new Request.Builder().url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
