package com.sachin_himal.walletshare.repository.groupSplit;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;

import static com.sachin_himal.walletshare.repository.Database.GROUPS;
import static com.sachin_himal.walletshare.repository.Database.USERS;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Group;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GroupRepositoryImpl implements GroupRepository {

    private static GroupRepository groupRepositoryInstance;
    private static Lock groupLock = new ReentrantLock();
    private DatabaseReference groupDBReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference memberDBReference;

    private MutableLiveData<List<Group>> allGroupForUser;
    private MutableLiveData<String> error;

    private String currentUserID;





    private GroupRepositoryImpl() {

        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);

        allGroupForUser = new MutableLiveData<>();

        allGroupForUser.postValue(new ArrayList<>());
        error = new MutableLiveData<>();

    }

    public static GroupRepository getInstance() {
        if (groupRepositoryInstance == null) {
            synchronized (groupLock) {
                if (groupRepositoryInstance == null) {
                    groupRepositoryInstance = new GroupRepositoryImpl();
                }
            }
        }
        return groupRepositoryInstance;
    }

    @Override
    public void initializeGroup(String uid) {

        groupDBReference = firebaseDatabase.getReference().child(GROUPS);
        memberDBReference = firebaseDatabase.getReference().child(USERS);
        currentUserID = uid;
        searchAllGroup();

    }


    @Override
    public void addNewGroup(String groupName, CallBack callBack) {
        Group group = new Group(groupName);
        DatabaseReference newAddedReference = groupDBReference.push();
        String keyFromRecentGroup = newAddedReference.getKey();
        newAddedReference.setValue(group).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                memberDBReference.child(currentUserID).child("GroupList").push().setValue(keyFromRecentGroup);
                newAddedReference.child("UsersId").push().setValue(currentUserID);
                System.out.println("Sucesfully added the group " + keyFromRecentGroup);
                callBack.callBack();

                /**
                 *
                 * TODO: Need to update card Adapter when it is update
                 *
                 */

            } else System.out.println("Could not add the group ");
            ;
        });

    }


    @Override
    public LiveData<List<Group>> getAllGroup() {
        return allGroupForUser;
    }

    private void searchAllGroup() {



        DatabaseReference getAllGroupName = memberDBReference.child(currentUserID).child("GroupList").getRef();
        getAllGroupName.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
           List<Group> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    System.out.println(dataSnapshot.getValue().toString());

                    DatabaseReference groupListReference = groupDBReference.child(dataSnapshot.getValue().toString());
                    groupListReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //   groupArrayList.add((Group) snapshot.getValue());
                            //  cardAdapter.notifyDataSetChanged();
                            System.out.println("Trying to get groups" + snapshot.getKey());
                            String groupName = (String) snapshot.child("groupName").getValue();
                            int amount = Integer.parseInt(snapshot.child("amount").getValue().toString());
                            Group group = new Group(groupName, amount);
                            group.setGroupId(snapshot.getKey());
                        list.add(group);

                        }



                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                allGroupForUser.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



    }


}
