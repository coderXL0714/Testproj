package com.example.Testproj.activity;

import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.example.Testproj.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {
    boolean ismanager = false;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Button back = findViewById(R.id.register_back);
        Button register = findViewById(R.id.register_btn);
        RadioGroup rg = findViewById(R.id.identity_register);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ismanager){
                    registerManager();
                    Log.d("xfwa","click1");
                }else{
                    registerVolunteer();
                    Log.d("xfwa","click2");
                }
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.volunteer_register:
                        ismanager=false;
                        break;
                    case R.id.manager_register:
                        ismanager=true;
                        break;
                }
            }
        });

    }
    private void registerVolunteer(){
        Log.d("xfwa","clickvol");

                EditText username = findViewById(R.id.register_username);
                EditText password = findViewById(R.id.register_password);
                EditText phone = findViewById(R.id.register_phone);
                Map <String,String>map = new HashMap<>();
                map.put("username",username.getText().toString());
                map.put("password",password.getText().toString());
                map.put("phone",phone.getText().toString());
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.142.134.155:8234/admin/old/userRegist")
                        .post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(map)))
                        .build();
        Log.d("xfwa","map:"+ JSON.toJSONString(map));
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("xfwa","onFailure post:"+ e.getMessage());
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this,"注册失败！请重试！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("xfwa","onResponse post:"+response.body().string());
                        if(Objects.equals(response.code(),200)){
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            RegisterActivity.this.finish();
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"注册失败！请重试！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                    }
                });

    }
    private void registerManager(){
                EditText mname = findViewById(R.id.register_username);
                EditText password = findViewById(R.id.register_password);
                EditText phone = findViewById(R.id.register_phone);
                Map <String,String>map = new HashMap<>();
                map.put("mname",mname.getText().toString());
                map.put("password",password.getText().toString());
                map.put("phone",phone.getText().toString());
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.142.134.155:8234/admin/old/managementRegist")
                        .post(RequestBody.create(MEDIA_TYPE,JSON.toJSONString(map)))
                        .build();
        Log.d("xfwa","map:"+ JSON.toJSONString(map));
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("xfwa","onFailure post:"+ e.getMessage());
                        Looper.prepare();
                        Toast.makeText(RegisterActivity.this,"注册失败！请重试！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("xfwa","onResponse post:"+response.body().string());
                        if(Objects.equals(response.code(),200)){
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            RegisterActivity.this.finish();
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(RegisterActivity.this,"注册失败！请重试！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                });

    }
}
