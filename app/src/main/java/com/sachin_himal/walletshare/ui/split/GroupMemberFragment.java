package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    
    private AppCompatEditText friendEmail;
    private AppCompatButton appCompatButton;

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
        appCompatButton = v.findViewById(R.id.saveMemberNameBtn);
        friendEmail = v.findViewById(R.id.friendEmail);
        
        appCompatButton.setOnClickListener(this::saveNewFriend);
        
        return v;

    }

    private void saveNewFriend(View view) {
        System.out.println(friendEmail.getText().toString().trim());
        groupListViewModel.addNewFriend(friendEmail.getText().toString().trim());


    }

    private void memberForGroup(List<GroupUser> groupUsers) {
        System.out.println(groupUsers.size());
     memberAdapter.setGroupUserList(groupUsers);
    }


}