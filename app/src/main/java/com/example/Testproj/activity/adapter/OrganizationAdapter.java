package com.example.Testproj.activity.adapter;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.example.Testproj.Entity.OrganizationEntity;
import com.example.Testproj.R;
import com.example.Testproj.activity.RegisterActivity;

import java.io.IOException;
import java.util.ArrayList;
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

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.organizationViewHolder> {
    private ArrayList<OrganizationEntity> datalist;
    private Context context;
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public OrganizationAdapter(Context context, ArrayList<OrganizationEntity> datalist){
        this.context = context;//上下文
        this.datalist = datalist;
        Log.d("xfwa", "OrganizationAdapter: "+datalist);
    }


    @Override
    public organizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.organization_item, parent, false);
        organizationViewHolder holder = new organizationViewHolder(view);
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                OrganizationEntity org = datalist.get(position);
                String tn = org.getOrganizationName();
                Map<String,String> map = new HashMap<>();
                map.put("id","1");
                map.put("teamName",tn);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://8.142.134.155:8234/admin/old/joinTeam")
                        .post(RequestBody.create(MEDIA_TYPE, JSON.toJSONString(map)))
                        .build();
                Log.d("xfwa","map:"+ JSON.toJSONString(map));
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.d("xfwa","onFailure post:"+ e.getMessage());
                        Looper.prepare();
                        Toast.makeText(context,"加入失败！请重试！",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        Log.d("xfwa","onResponse post:"+response.body().string());
                        if(Objects.equals(response.code(),200)){
                            Looper.prepare();
                            Toast.makeText(context,"加入成功！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else{
                            Looper.prepare();
                            Toast.makeText(context,"加入失败！请重试！",Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }

                    }
                });
            }
        });
        Log.d("xfwa", "onCreateViewHolder: 执行oncreateviewholder");
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull organizationViewHolder holder, int position) {
        Log.d("xfwa", "onCreateViewHolder: 执行onbindviewholder");
        int a = 1+1;
        holder.bindData(datalist,position);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class organizationViewHolder extends RecyclerView.ViewHolder {
        final Button btn;
        private final TextView organizationName;
        private final TextView number;
        public organizationViewHolder(@NonNull View itemView) {
            super(itemView);
            btn = (Button) itemView.findViewById(R.id.join_org);
            organizationName = itemView.findViewById(R.id.org_name);
            number = itemView.findViewById(R.id.org_num);
        }

        public void bindData(ArrayList<OrganizationEntity> datalist, int position) {

            OrganizationEntity data = datalist.get(position);
            Log.d("xfwa", "bindData: "+data);
            organizationName.setText(data.getOrganizationName());
            number.setText(data.getNumber()+"");
        }
    }
}
