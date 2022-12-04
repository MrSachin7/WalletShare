package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class GroupMemberFragment extends Fragment {

    List<String> userNameList;
    AutoCompleteTextView friendListDropDown;
    ArrayAdapter<String> stringArrayAdapter;
    TextInputLayout textInputLayout;
    String memberTobBeAdded;
    private MemberAdapter memberAdapter;
    private RecyclerView recyclerView;
    private GroupListViewModel groupListViewModel;
    private Group group;
    private List<User> userList;
    private AppCompatButton addNewGrpMember;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userList = new ArrayList<>();
        userNameList = new ArrayList<>();
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_group_member, container, false);
        groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);

        recyclerView = v.findViewById(R.id.recyclerViewsMember);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        memberAdapter = new MemberAdapter();
        recyclerView.setAdapter(memberAdapter);

        group = groupListViewModel.getCurrentGroup();

        addNewGrpMember = v.findViewById(R.id.saveMemberNameBtn);

        addNewGrpMember.setOnClickListener(this::saveNewFriend);

        groupListViewModel.getUserThatCanBeAdded().observe(getViewLifecycleOwner(), this::addMemberList);


        friendListDropDown = v.findViewById(R.id.friendNameToAddInGroupValues);
        textInputLayout = v.findViewById(R.id.friendNameToAddInGroup);


        friendListDropDown.setShowSoftInputOnFocus(false);
        friendListDropDown.setCursorVisible(false);

        stringArrayAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_dropdown_item, userNameList);
        friendListDropDown.setAdapter(stringArrayAdapter);
        friendListDropDown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memberTobBeAdded = userList.get(position).getUid();

            }
        });
        return v;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupListViewModel.getUserForCurrentGroup().observe(getViewLifecycleOwner(), this::memberForGroup);


    }

    private void addMemberList(List<User> users) {
        userList = users;
        userNameList.clear();
        userList.forEach(
                user -> {
                    userNameList.add(user.retrieveFullName());
                }
        );
    }

    private void saveNewFriend(View view) {


        if (memberTobBeAdded != null) {
            groupListViewModel.addNewFriend(memberTobBeAdded);
            ((MainActivity)getActivity()).changeFragment(R.id.particularGroupFragment);

        }
    }


    private void memberForGroup(List<GroupUser> groupUsers) {
        memberAdapter.setGroupUserList(groupUsers);
    }


}