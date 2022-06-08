package com.example.Testproj.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.Testproj.Entity.EventEntity;
import com.example.Testproj.R;
import com.example.Testproj.Utils.MyImageView;
import com.example.Testproj.activity.ActInfoActivity;

import java.util.ArrayList;
import java.util.Map;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.eventViewHolder>{
    private final ArrayList<EventEntity> datalist;
    private final Context context;

    public EventAdapter(Context context, ArrayList<EventEntity> datalist){
        this.context = context;//上下文
        this.datalist = datalist;
    }

    @Override
    public eventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item,parent,false);
        final eventViewHolder holder = new eventViewHolder(view);
        holder.eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                EventEntity event = datalist.get(position);
                Intent intent = new Intent(context, ActInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("actInfo",event);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull eventViewHolder holder, int position) {
        holder.bindData(datalist,position);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public static class eventViewHolder extends RecyclerView.ViewHolder {
        View eventView;
        private final MyImageView activityImg;
        private final TextView activityName;
        private final TextView people;
        private final TextView number;
        public eventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventView = itemView;
            activityImg = itemView.findViewById(R.id.home_image);
            activityName = itemView.findViewById(R.id.home_details);
            people = itemView.findViewById(R.id.home_people);
            number = itemView.findViewById(R.id.home_number);
        }

        public void bindData(ArrayList<EventEntity> datalist, int position) {
            EventEntity data = datalist.get(position);
            activityImg.setImageURL(data.getImageUrl());
            activityName.setText(data.getActivityName());
            people.setText(data.getPeople()+"");
            number.setText(data.getNumber()+"");
        }
    }
}
