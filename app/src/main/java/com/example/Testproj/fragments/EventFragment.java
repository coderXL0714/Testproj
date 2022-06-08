package com.example.Testproj.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.Testproj.Entity.EventEntity;
import com.example.Testproj.R;
import com.example.Testproj.Utils.HttpUtils;
import com.example.Testproj.activity.adapter.EventAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventFragment extends Fragment {
    private ArrayList<EventEntity> datalist = new ArrayList<EventEntity>();
    private View view;
    private RecyclerView eventRecycleView;
    private EventAdapter eadapter;
    private String url = "http://8.142.134.155:8234/admin/old/query/activity";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_event,container,false);
//        getActList();
        initRecycleView();
        loadWebData(url);
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
                    ArrayList<EventEntity> beanList = parseJSONWithJSONObject(s);
//                  获取到网络数据，并且解析了，添加数据到数据源当中
//                    数据源：适配器当中传递的集合对象，就是数据源
                    datalist.addAll(beanList);
//                    提示adapter更新数据
                    eadapter.notifyDataSetChanged();
                }
            }
        }.execute();
    }

    private void getActList() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://8.142.134.155:8234/admin/old/query/activity")
                .build();
        client.newCall(request).enqueue(new Callback() {
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

    private ArrayList<EventEntity> parseJSONWithJSONObject(String jsonData) {
        ArrayList<EventEntity> testlist = new ArrayList<EventEntity>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            Log.d("xfwa", "parseJSONWithJSONObject: "+jsonArray);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String createTime = jsonObject.getString("createTime");
                String updateTime = jsonObject.getString("updateTime");
                int isDeleted = jsonObject.getInt("isDeleted");
                String activityName = jsonObject.getString("activityName");
                String address = jsonObject.getString("address");
                String startTime = jsonObject.getString("startTime");
                String endTime = jsonObject.getString("endTime");
                String details = jsonObject.getString("details");
                String imageUrl = jsonObject.getString("imageUrl");
                String teamName = jsonObject.getString("teamName");
                int people = jsonObject.getInt("people");
                int number = jsonObject.getInt("number");
                testlist.add(new EventEntity(id,createTime,updateTime,isDeleted,activityName,address,startTime,endTime,details,imageUrl,people,number, teamName));
                Log.d("xfwa", "parseJSONWithJSONObject: "+testlist.toString());
            }
            return testlist;
        }catch (Exception e){
            e.printStackTrace();
        }
        return testlist;
    }

    private void initRecycleView() {
        eventRecycleView = (RecyclerView) view.findViewById(R.id.event_content);
        eadapter = new EventAdapter(this.getActivity(),datalist);
        eventRecycleView.setAdapter(eadapter);
        eventRecycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
    }


}
