package com.example.Testproj.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Testproj.Entity.OrganizationEntity;
import com.example.Testproj.R;
import com.example.Testproj.Utils.HttpUtils;
import com.example.Testproj.activity.adapter.OrganizationAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OrganizationFragment extends Fragment {
    private ArrayList<OrganizationEntity> datalist = new ArrayList<OrganizationEntity>();
//    private ArrayList<OrganizationEntity> testlist = new ArrayList<OrganizationEntity>();
    private View view;
    private RecyclerView organizationRecyclerView;
    private OrganizationAdapter oadapter;
    public String url = "http://8.142.134.155:8234/admin/old/query/organization";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("xfwa", "onCreateView: orgcreateview");
        view = inflater.inflate(R.layout.fragment_organization, container, false);


        initRecycleView();
        loadWebData(url);
        getOrganization();
//        initData();
        // Inflate the layout for this fragment
        return view;
    }

    private void loadWebData(final String url) {
        new AsyncTask<Void,Void,String>(){
            @Override
            protected String doInBackground(Void... params) {
//                执行网络请求
                String s = HttpUtils.getStringByOkhttp(url);
                return s;
            }
            @Override
            protected void onPostExecute(String s) {
                if (s!=null&&!s.isEmpty()) {
//                    解析数据 ：解析成功之后要什么，解析之后的数据给RecyclerView来显示
                    ArrayList<OrganizationEntity> beanList = parseJSONWithJSONObject(s);
//                  获取到网络数据，并且解析了，添加数据到数据源当中
//                    数据源：适配器当中传递的集合对象，就是数据源
                    datalist.addAll(beanList);
//                    提示adapter更新数据
                    oadapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    private void initData() {
        OrganizationEntity data1 = new OrganizationEntity(1,"123",1);
        datalist.add(data1);
        OrganizationEntity data2 = new OrganizationEntity(2,"1234",2);
        datalist.add(data2);
    }

    private void getOrganization(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://8.142.134.155:8234/admin/old/query/organization")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("xfwa", "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseData = response.body().string();
                parseJSONWithJSONObject(responseData);
                Log.d("xfwa", "onResponse: "+responseData);
            }
        });
    }
    private ArrayList<OrganizationEntity> parseJSONWithJSONObject(String jsonData){
        ArrayList<OrganizationEntity> testlist = new ArrayList<OrganizationEntity>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            Log.d("xfwa", "parseJSONWithJSONObject: "+jsonArray);
//            OrganizationEntity data1 = new OrganizationEntity(1,"123",1);
//            testlist.add(data1);
//            OrganizationEntity data2 = new OrganizationEntity(2,"1234",2);
//            testlist.add(data2);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String organizationName = jsonObject.getString("organizationName");
                int number = jsonObject.getInt("number");
                testlist.add(new OrganizationEntity(id,organizationName,number));
                Log.d("xfwa", "parseJSONWithJSONObject: "+testlist.toString());
            }
            return testlist;
        }catch (Exception e){
            e.printStackTrace();
        }
        return testlist;
    }
    private void initRecycleView() {
        organizationRecyclerView = (RecyclerView)view.findViewById(R.id.organization_content);
//        LinearLayoutManager layoutManager =
        oadapter = new OrganizationAdapter(this.getActivity(),datalist);
        organizationRecyclerView.setAdapter(oadapter);
        organizationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    }
}