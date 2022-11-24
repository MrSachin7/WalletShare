package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepository;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepositoryImpl;

public class ParticularGroupFragment extends Fragment {


private TextView textView;

private GroupRepository groupRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

 groupRepository = GroupRepositoryImpl.getInstance();
        View view = inflater.inflate(R.layout.fragment_particular_group, container, false);
        textView = view.findViewById(R.id.textViewTestToPassgroupId);
        textView.setText(groupRepository.getCurrentGroup().getGroupName());
        return view;
    }
}