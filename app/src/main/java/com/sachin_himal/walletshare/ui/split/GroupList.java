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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepository;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepositoryImpl;

import java.util.ArrayList;
import java.util.List;


public class GroupList extends Fragment {


    private RecyclerView recyclerView;
    private  CardAdapter cardAdapter;
    private ArrayList<Group> groupArrayList;
    FloatingActionButton fab;
    View view;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();




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
        getAllGroup();
        CreateDataForCards();

    }

    private void CreateDataForCards() {


      //  ArrayList<String> allGroupOfUser = getAllGroup();



        cardAdapter.notifyDataSetChanged();

    }

    private ArrayList<String> getAllGroup() {
        ArrayList<String> list = new ArrayList<>();
         FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
      String  currentUserID = currentUser.getUid();
        DatabaseReference getAllGroupName = firebaseDatabase.getReference().child("Users").child(currentUserID).child("GroupList").getRef();
        getAllGroupName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for ( DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    System.out.println(dataSnapshot.getValue().toString());
                    list.add(dataSnapshot.getValue().toString());
                    DatabaseReference groupListReference = firebaseDatabase.getReference().child("Groups").child(dataSnapshot.getValue().toString());
                    groupListReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                         //   groupArrayList.add((Group) snapshot.getValue());
                          //  cardAdapter.notifyDataSetChanged();
                            System.out.println("Trying to get groups");
                            String groupName = (String) snapshot.child("groupName").getValue();
                            int amount =  Integer.parseInt(snapshot.child("amount").getValue().toString()) ;


                            Group group = new Group(groupName,amount);
                            groupArrayList.add(group);
                            cardAdapter.notifyDataSetChanged();
                  }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return list;
    }
}