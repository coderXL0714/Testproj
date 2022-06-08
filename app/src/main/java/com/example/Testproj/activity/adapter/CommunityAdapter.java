package com.example.Testproj.activity.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Testproj.Entity.CommunityEntity;
import com.example.Testproj.R;

import java.util.ArrayList;

public class CommunityAdapter extends RecyclerView.Adapter<CommunityAdapter.myViewHolder> {
    private final ArrayList<CommunityEntity> datalist;
    private final Context context;

    public CommunityAdapter(Context context, ArrayList<CommunityEntity> datalist){
        this.context = context;//上下文
        this.datalist = datalist;
    }

    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("xfwa", "onCreateViewHolder: 执行oncreateviewholder1");
        return new myViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Log.d("xfwa", "onCreateViewHolder: 执行onbindviewholder1");
        holder.bindData(datalist,position);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        private final ImageView avatar;
        private final TextView username;
        private final TextView time;
        private final TextView content;
        private final ImageView img;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.c_avatar);
            username = itemView.findViewById(R.id.c_username);
            time = itemView.findViewById(R.id.c_time);
            content = itemView.findViewById(R.id.c_content);
            img = itemView.findViewById(R.id.c_img);
        }


        public void bindData(ArrayList<CommunityEntity> datalist, int position) {
            CommunityEntity data = datalist.get(position);
            avatar.setBackgroundResource(data.getAvatarId());
            username.setText(data.getUsername());
            time.setText(data.getTime());
            content.setText(data.getContent());
            img.setImageResource(data.getImgId());
        }
    }
}
