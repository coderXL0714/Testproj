package com.example.Testproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.Testproj.Entity.EventEntity;
import com.example.Testproj.R;
import com.example.Testproj.Utils.MyImageView;

import org.json.JSONArray;

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

public class ActInfoActivity extends AppCompatActivity {
    TextView act_name;
    TextView startTime;
    TextView endTime;
    TextView people;
    TextView number;
    TextView position;
    TextView org_name;
    TextView details;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_info);
        Intent intent = getIntent();
        EventEntity data = (EventEntity) intent.getSerializableExtra("actInfo");
        act_name = findViewById(R.id.info_act_actname);
        act_name.setText(data.getActivityName());
        String[] ss = data.getCreateTime().split(" ");
        startTime = findViewById(R.id.info_act_startTime);
        startTime.setText(ss[0].replace("-","/"));
        String[] sss = data.getEndTime().split(" ");
        endTime = findViewById(R.id.info_act_endTime);
        endTime.setText(sss[0].replace("-","/"));
        people = findViewById(R.id.info_act_people);
        people.setText(data.getPeople()+"");
        number = findViewById(R.id.info_act_number);
        number.setText(data.getNumber()+"");
        position = findViewById(R.id.info_act_position);
        position.setText(data.getAddress());
        org_name = findViewById(R.id.info_act_orgname);
        org_name.setText(data.getTeamName());
        details = findViewById(R.id.info_act_details);
        details.setText(data.getDetails());
        MyImageView miv = findViewById(R.id.activity_picture);
        miv.setImageURL(data.getImageUrl());
        ImageView iv = findViewById(R.id.act_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActInfoActivity.this.finish();
            }
        });
        Button btn = findViewById(R.id.btn_add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postActivity(data);
            }
        });
    }
    public void postActivity(EventEntity e){
        SharedPreferences userInfo = getSharedPreferences("user", Context.MODE_PRIVATE);
        Map<String,String> map = new HashMap<>();
        map.put("activityId",e.getId()+"");
        map.put("activityName",e.getActivityName());
        map.put("userId",userInfo.getInt("id",0)+"");
        map.put("username",userInfo.getString("username",""));
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://8.142.134.155:8234/admin/old/join/activity")
                .post(RequestBody.create(MEDIA_TYPE,JSON.toJSONString(map)))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Looper.prepare();
                Toast.makeText(ActInfoActivity.this,"报名失败！请重试！",Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(Objects.equals(response.code(),200)){
                    Looper.prepare();
                    Toast.makeText(ActInfoActivity.this,"报名成功！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Looper.prepare();
                    Toast.makeText(ActInfoActivity.this,"报名失败！请重试！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
}