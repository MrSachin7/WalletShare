package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;


public class GroupListFragment extends Fragment {

    private RecyclerView recyclerView;
    private  CardAdapter cardAdapter;
    private ArrayList<Group> groupArrayList;
    private GroupListViewModel groupListViewModel;
    AppCompatButton saveGroupButton;
    AppCompatEditText editTextGroupName;














    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view  =  inflater.inflate(R.layout.fragment_group_list, container, false);
        InitializeCardView(view);
        groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);
        saveGroupButton.setOnClickListener(this::saveGroupPressed);
        groupListViewModel.groupisDone().observe(getViewLifecycleOwner(), this::groupDone);
        groupListViewModel.getAllGroupForUser().observe(getViewLifecycleOwner(),this::groupListObserver);
        return view;
    }

    private void groupListObserver(List<Group> groups) {
        cardAdapter.setGroupArrayList(groups);
    }


    private void groupDone(Boolean aBoolean) {
        if (aBoolean){
            FancyToast.makeText(getContext(),"New Group added successfully ", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS, true).show();
            editTextGroupName.setText("");
        }

        else{
            FancyToast.makeText(getContext(),"Failed to add group", FancyToast.LENGTH_SHORT,FancyToast.ERROR, true).show();
        }
    }

    private void saveGroupPressed(View view) {
        String groupName = editTextGroupName.getText().toString().trim();

        if (groupName.equals("")) {
            FancyToast.makeText(getContext(),"Amount must be entered", FancyToast.LENGTH_SHORT,FancyToast.WARNING, true).show();
        }else {
            groupListViewModel.addNewGroup(groupName);
        }}


    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewsCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupArrayList = new ArrayList<>();
        cardAdapter = new CardAdapter(getActivity(),groupArrayList);
        recyclerView.setAdapter(cardAdapter);
        editTextGroupName= view.findViewById(R.id.group_Name);
        saveGroupButton= view.findViewById(R.id.saveGroupNameBtn);

    }

    private void CreateDataForCards() {


      //  ArrayList<String> allGroupOfUser = getAllGroup();



        cardAdapter.notifyDataSetChanged();

    }

}