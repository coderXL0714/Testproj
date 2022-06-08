package com.example.Testproj.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Testproj.MainActivity;
import com.example.Testproj.R;
import com.example.Testproj.activity.EditInfoActivity;
import com.example.Testproj.activity.LoginActivity;
import com.example.Testproj.activity.OrganizationActivity;
import com.example.Testproj.activity.PostActivity;

import java.util.Objects;


public class MeFragment extends Fragment {
    String username = "立即登录";
    Integer identify = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_me,container,false);
        TextView uname = view.findViewById(R.id.me_username);
        uname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(username,"立即登录")){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        ImageView test = view.findViewById(R.id.go_ogz);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), OrganizationActivity.class);
                startActivity(intent);
            }
        });
        ImageView edit = view.findViewById(R.id.set_info);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Objects.equals(username,"立即登录")){
                    Looper.prepare();
                    Toast.makeText(getActivity(),"登陆后才能进行该操作！",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else{
                    Intent intent = new Intent(getActivity(), EditInfoActivity.class);
                    startActivity(intent);
                }
            }
        });
        ImageView goPost = view.findViewById(R.id.post_activity);
        goPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PostActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences userInfo = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        username = userInfo.getString("username","立即登录");
        TextView uname = getView().findViewById(R.id.me_username);
        uname.setText(username);
        identify = userInfo.getInt("identify",0);
    }
    private void getUserInfo(){

    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        TextView uname = getActivity().findViewById(R.id.me_username);
//        uname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });
//    }
}
