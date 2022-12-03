package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupMemberFragment#} factory method to
 * create an instance of this fragment.
 */
public class GroupMemberFragment extends Fragment {

    List<String> userName;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> stringArrayAdapter;
    TextInputLayout textInputLayout;
    String memberTobBeAdded;
    private MemberAdapter memberAdapter;
    private RecyclerView recyclerView;
    private GroupListViewModel groupListViewModel;
    private Group group;
    private List<User> userList;
    private AppCompatButton appCompatButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        userList = new ArrayList<>();
        userName = new ArrayList<>();
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_group_member, container, false);
        groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);

        recyclerView = v.findViewById(R.id.recyclerViewsMember);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        memberAdapter = new MemberAdapter(getActivity());
        recyclerView.setAdapter(memberAdapter);

        group = groupListViewModel.getCurrentGroup();

        groupListViewModel.getUserForCurrentGroup().observe(getViewLifecycleOwner(), this::memberForGroup);
        appCompatButton = v.findViewById(R.id.saveMemberNameBtn);

        appCompatButton.setOnClickListener(this::saveNewFriend);

        groupListViewModel.getUserThatCanBeAdded().observe(getViewLifecycleOwner(), this::addMemberList);

        return v;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        autoCompleteTextView = view.findViewById(R.id.friendNameToAddInGroupValues);
        textInputLayout = view.findViewById(R.id.friendNameToAddInGroup);


        autoCompleteTextView.setShowSoftInputOnFocus(false);
        autoCompleteTextView.setCursorVisible(false);

        stringArrayAdapter = new ArrayAdapter<String>(this.getContext(), R.layout.spinner_dropdown_item, userName);
        autoCompleteTextView.setAdapter(stringArrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                memberTobBeAdded = userList.get(position).getUid();
            }
        });


    }

    private void addMemberList(List<User> users) {
        userList = users;
        userList.forEach(
                user -> {
                    userName.add(user.retrieveFullName());
                }
        );
    }

    private void saveNewFriend(View view) {
        // System.out.println(friendEmail.getText().toString().trim());
        // groupListViewModel.addNewFriend(friendEmail.getText().toString().trim());

        if (memberTobBeAdded != null) {
            groupListViewModel.addNewFriend(memberTobBeAdded);
            //  groupListViewModel.getSuccessMessage().observe(getViewLifecycleOwner(),this::sucessMessageObser);
        }
    }

    private void sucessMessageObser(String s) {
        if (!(s == null && s.isEmpty())) {
            FancyToast.makeText(getContext(), s, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
            groupListViewModel.getAllGroupForUser();
            groupListViewModel.getUserThatCanBeAdded();
            ((MainActivity) getActivity()).changeFragment(R.id.particularGroupFragment);

        }

    }

    private void memberForGroup(List<GroupUser> groupUsers) {
        System.out.println(groupUsers.size());
        memberAdapter.setGroupUserList(groupUsers);
    }


}