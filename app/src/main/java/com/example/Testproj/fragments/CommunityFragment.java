package com.example.Testproj.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.Testproj.Entity.CommunityEntity;
import com.example.Testproj.R;
import com.example.Testproj.activity.adapter.CommunityAdapter;

import java.util.ArrayList;


public class CommunityFragment extends Fragment {

    private ArrayList<CommunityEntity> datalist = new ArrayList<CommunityEntity>();
    private View view;
    private RecyclerView communityRecycleView;
    private CommunityAdapter cadapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_community, container, false);
        initRecycleView();
        initData();
        // Inflate the layout for this fragment
        return view;
    }
    private void initData(){
        CommunityEntity data1 = new CommunityEntity(R.drawable.user0,"唐心怡","今天11：55","敬老从心开始，助老从我做起。充满意义且充实的一天",R.drawable.activity3);
        datalist.add(data1);
        CommunityEntity data2 = new CommunityEntity(R.drawable.user1,"唐心怡","今天11：55","敬老从心开始，助老从我做起。充满意义且充实的一天",R.drawable.activity4);
        datalist.add(data2);
    }
    private void initRecycleView(){
        communityRecycleView = (RecyclerView)view.findViewById(R.id.community_content);
        Log.d("xfwa", "initRecycleView: "+datalist);
        cadapter = new CommunityAdapter(this.getActivity(),datalist);
        communityRecycleView.setAdapter(cadapter);
        communityRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
    }
}