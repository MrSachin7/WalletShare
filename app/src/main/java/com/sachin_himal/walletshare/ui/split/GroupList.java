package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepositoryImpl;

import java.util.ArrayList;
import java.util.List;


public class GroupList extends Fragment {


    private RecyclerView recyclerView;
    private  CardAdapter cardAdapter;
    private ArrayList<Group> groupArrayList;
    FloatingActionButton fab;
    View view;
    GroupRepositoryImpl groupRepository;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
groupRepository = new GroupRepositoryImpl();

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








        //

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://walletshare-92139-default-rtdb.firebaseio.com");
        DatabaseReference groupDatabaseRef = firebaseDatabase.getReference().child("Groups");
        groupDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Group group = new Group();

                    String name = (String) dataSnapshot.child("groupName").getValue();
                    group.setGroupName(name);
                    group.setAmount(0);
                    List<User> users = new ArrayList<>();
                    System.out.println( dataSnapshot.child("users").getValue());
                    group.setUsers((List<User>)  dataSnapshot.child("users").getValue());
                    System.out.println(group.getUsers().toString());
                    groupArrayList.add(group);
                    System.out.println("sucesfully added ");
                    // group.setAmount((int) dataSnapshot.child("amount").getValue());
                        cardAdapter.notifyDataSetChanged();


                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        //

        //MOCKING Data
     //groupArrayList = groupRepository.getAllGroup();
     //   System.out.println(groupArrayList.get(0).getGroupName());
    });

        Group a = new Group("as");
        User user = new User("!!!");
        a.addUser(user);
        groupArrayList.add(a);
        System.out.println(groupArrayList.size());
        cardAdapter = new CardAdapter(getActivity(),groupArrayList);
        recyclerView.setAdapter(cardAdapter);

}
}
