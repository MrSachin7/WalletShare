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
        return view;
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

    /**
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

*/
}