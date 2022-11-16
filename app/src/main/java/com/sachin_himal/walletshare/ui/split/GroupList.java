package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.User;

import java.util.ArrayList;


public class GroupList extends Fragment {


    private RecyclerView recyclerView;
    private  CardAdapter cardAdapter;
    private ArrayList<Group> groupArrayList;
    FloatingActionButton fab;
    View view;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view  =  inflater.inflate(R.layout.fragment_group_list, container, false);
        InitializeCardView(view);





        fab  = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                Fragment newGroupFragment = new AddNewGroup();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainerView, newGroupFragment ); // give your fragment container id in first parameter
                //  transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();}
        });
        return view;
    }

    private void InitializeCardView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewsCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        groupArrayList = new ArrayList<>();
        cardAdapter = new CardAdapter(getActivity(),groupArrayList);
        recyclerView.setAdapter(cardAdapter);




        //MOCKING Data
        CreateDataForCards();
    }

    private void CreateDataForCards() {
        User user = new User("as","asa");
        User user1 = new User("as","asa");
        User user2 = new User("as","asa");
        ArrayList<User> users = new ArrayList<User>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        Group group = new Group(" As",users);
        group.setAmount(500);
        groupArrayList.add(group);
        Group group1 = new Group(" As",users);
        groupArrayList.add(group);
        group1.setAmount(500);

        cardAdapter.notifyDataSetChanged();
        groupArrayList.add(group1);
    }
}