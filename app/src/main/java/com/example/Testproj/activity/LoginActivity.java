package com.example.Testproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Testproj.Entity.EventEntity;
import com.example.Testproj.R;
import com.example.Testproj.Utils.HttpUtils;
import com.example.Testproj.Utils.PostHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    boolean ismanager = false;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView goRegister = findViewById(R.id.register_tv);
        Button back = findViewById(R.id.login_back);
        Button login = findViewById(R.id.login_btn);
        RadioGroup rg = findViewById(R.id.identity_login);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.volunteer_login:
                        ismanager=false;
                        break;
                    case R.id.manager_login:
                        ismanager=true;
                        break;
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ismanager){
                    loginManager();
                }else{
                    loginVolunteer();
                }
            }
        });

    }
    private void storeUserInfo(String Info,int identify){
        Log.d("xfwa", "storeUserInfo: "+Info);
        JSONObject data = JSONObject.parseObject(Info);
        Log.d("xfwa", "storeUserInfo: "+data);
        SharedPreferences user = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = user.edit();
        Object id = data.get("id");
        Object username = data.get("username");
        Object phone = data.get("phone");
        editor.putInt("id",Integer.parseInt(id+""));
        editor.putString("username",username+"");
        editor.putString("phone",phone+"");
        editor.putInt("identify",identify);
        editor.apply();
    }
    private void loginManager(){
        String url = "http://8.142.134.155:8234/admin/old/managementLogin";
        EditText name = findViewById(R.id.login_username);
        EditText password = findViewById(R.id.login_password);
        Map<String,String> map = new HashMap<>();
        map.put("mname",name.getText().toString());
        map.put("password",password.getText().toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://8.142.134.155:8234/admin/old/managementLogin")
                .post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(map)))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("xfwa","onFailure post:"+ e.getMessage());
                Looper.prepare();
                Toast.makeText(LoginActivity.this,"登陆失败！请重试！",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String data = response.body().string();
                Log.d("xfwa","onResponse post:"+data);
                if(Objects.equals(response.code(),200)){
                    Looper.prepare();
                    storeUserInfo(data,1);
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"登录失败！请重试！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
    private void loginVolunteer(){
        EditText name = findViewById(R.id.login_username);
        EditText password = findViewById(R.id.login_password);
        Map<String,String> map = new HashMap<>();
        map.put("username",name.getText().toString());
        map.put("password",password.getText().toString());
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://8.142.134.155:8234/admin/old/userLogin")
                .post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(map)))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("xfwa","onFailure post:"+ e.getMessage());
                Looper.prepare();
                Toast.makeText(LoginActivity.this,"登陆失败！请重试！",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Log.d("xfwa","onResponse post:"+response.body().string());
                if(Objects.equals(response.code(),200)){
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"登录成功！",Toast.LENGTH_SHORT).show();
                    LoginActivity.this.finish();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"登录失败！请重试！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
}