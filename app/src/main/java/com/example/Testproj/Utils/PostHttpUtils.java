package com.example.Testproj.Utils;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PostHttpUtils {
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static String postStringByOkhttp(String path, Map<String,String> map){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(map))).url(path).build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                return body.string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
