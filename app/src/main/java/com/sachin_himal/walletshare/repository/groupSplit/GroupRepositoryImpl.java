package com.sachin_himal.walletshare.repository.groupSplit;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.sachin_himal.walletshare.ui.split.AddNewGroup;
import com.sachin_himal.walletshare.ui.split.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class GroupRepositoryImpl implements GroupRepository{
    CardAdapter cardAdapter;
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://walletshare-92139-default-rtdb.firebaseio.com");
    DatabaseReference groupDatabaseRef = firebaseDatabase.getReference().child("Groups");
    private ArrayList<Group> groups ;


    public GroupRepositoryImpl() {
        this.groups = new ArrayList<>();
    }

    @Override
    public void addNewGroup(Group group) {

    }

    @Override
    public ArrayList<Group> getAllGroup() {


        groupDatabaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Group group = new Group();
/**
                    String name = (String) dataSnapshot.child("groupName").getValue();
                    group.setGroupName(name);
                    group.setAmount(0);
                    List<User> users = new ArrayList<>();
                    System.out.println( dataSnapshot.child("users").getValue());
                    group.setUsers((List<User>)  dataSnapshot.child("users").getValue());
                    System.out.println(group.getUsers().toString());
                    groups.add(group);
                    System.out.println("sucesfully added ");
                   // group.setAmount((int) dataSnapshot.child("amount").getValue());

**/

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        if (groups != null){
            System.out.println("FUCKKKKK");
            return  groups;
        }
        else{
            System.out.println("EMPtyyyyyy");
           return null;
        }

    }
}
