package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupMemberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupMemberFragment extends Fragment {

    private MemberAdapter memberAdapter;
    private RecyclerView recyclerView;
    private  GroupListViewModel groupListViewModel;
    private Group group;
    private List<String> userNames;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_group_member, container, false);
        groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);

        recyclerView = v.findViewById(R.id.recyclerViewsMember);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        memberAdapter = new MemberAdapter(getActivity());
        recyclerView.setAdapter(memberAdapter);

          group =   groupListViewModel.getCurrentGroup();

        groupListViewModel.getUserForCurrentGroup().observe(getViewLifecycleOwner(),this::memberForGroup);

        return v;

    }

    private void memberForGroup(List<GroupUser> groupUsers) {

     memberAdapter.setGroupUserList(groupUsers);
    }


}