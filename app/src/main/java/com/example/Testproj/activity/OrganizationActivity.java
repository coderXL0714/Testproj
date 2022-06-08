package com.example.Testproj.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Testproj.Entity.OrganizationEntity;
import com.example.Testproj.R;
import com.example.Testproj.activity.adapter.OrganizationAdapter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrganizationActivity extends AppCompatActivity {
//    private ArrayList<OrganizationEntity> datalist = new ArrayList<OrganizationEntity>();
//    private ArrayList<OrganizationEntity> testlist = new ArrayList<OrganizationEntity>();
//    private RecyclerView organizationRecyclerView;
//    private OrganizationAdapter oadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        ImageView iv = findViewById(R.id.org_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrganizationActivity.this.finish();
            }
        });


    }



}