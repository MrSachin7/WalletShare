package com.sachin_himal.walletshare.repository.groupSplit;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;

import static com.sachin_himal.walletshare.repository.Database.GROUPS;
import static com.sachin_himal.walletshare.repository.Database.USERS;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

    private Group currentGroup;

private DatabaseReference referenceForAddingFriend;

    MutableLiveData<List<GroupUser>> userDetail;


    private GroupRepositoryImpl() {
        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);

        allGroupForUser = new MutableLiveData<>();
        allGroupForUser.postValue(new ArrayList<>());
        error = new MutableLiveData<>();

        userDetail = new MutableLiveData<>();
        userDetail.postValue(new ArrayList<>());

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
                newAddedReference.child("usersId").push().setValue(currentUserID);
                newAddedReference.child("amount").child(currentUserID).push().setValue(0);
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

    @Override
    public void setCurrentGroup(Group group) {
        currentGroup = group;

       getUserDataForGroupQuery();

    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    @Override
    public LiveData<List<GroupUser>> getUserDataForGroup() {
        return userDetail;
    }

    @Override
    public void addNewFriend(String friendEmail) {

        String key ="";

        System.out.println(friendEmail);


    memberDBReference.orderByChild("email").equalTo(friendEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                  dataSnapshot.getKey();


                        DatabaseReference     referenceForAddingFriend = dataSnapshot.getRef();

                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


     /**
        memberDBReference.orderByChild("email").equalTo(friendEmail).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot snapshot) {

         snapshot.getChildren().forEach(dataSnapshot -> {

             String snapshotKey = dataSnapshot.getKey();
             groupDBReference.child(currentGroup.getGroupId()).child("usersId").push().setValue(snapshotKey);
             dataSnapshot.child("GroupList").getRef().push().setValue(currentGroup.getGroupId());


              //   groupDBReference.child(currentGroup.getGroupId()).child("usersId").push().setValue();

         }   );










         //snapshot.getRef().child("GroupList").push().setValue(currentGroup.getGroupId());
         }

         @Override
         public void onCancelled(@NonNull DatabaseError error) {

         }
     });
      */

    }


    public void addNewFriendWithRef(){

        System.out.println("in the refrence method");
        String snapshotKey = referenceForAddingFriend.getKey();
        groupDBReference.child(currentGroup.getGroupId()).child("usersId").push().setValue(snapshotKey);
        groupDBReference.child("GroupList").getRef().push().setValue(currentGroup.getGroupId());
    }


    private void searchAllGroup() {

        List<Group> list = new ArrayList<>();
        DatabaseReference getAllGroupName = memberDBReference.child(currentUserID).child("GroupList").getRef();

        getAllGroupName.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    System.out.println(dataSnapshot.getValue().toString());
                    list.clear();


                    DatabaseReference groupListReference = groupDBReference.child(dataSnapshot.getValue().toString());
                    groupListReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            System.out.println("Trying to get groups" + snapshot.getKey());
                            String groupName = (String) snapshot.child("groupName").getValue();

                            final Double[] aDouble = {0.0};
                            if (snapshot.child("amount").child(currentUserID).getValue() != null) {

                                snapshot.child("amount").child(currentUserID).getChildren().forEach(dataSnapshot1 -> aDouble[0] = Double.valueOf(dataSnapshot1.getValue().toString()));

                            }


                            Group group = new Group(groupName, aDouble[0]);

                            group.setGroupId(snapshot.getKey());
                            group.getUsersId().toString();
                            System.out.println(group.getGroupName() + "   " + group.getAmount());
                            group.setGroupId(snapshot.getKey());
                            list.add(group);

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

 allGroupForUser.setValue(list);

    }


    //////
    ////
    //

    public void getUserDataForGroupQuery() {
        List<GroupUser> groupUserList = new ArrayList<>();
        System.out.println(currentGroup.getGroupId());

        if (currentGroup != null) {

            System.out.println("current group is not null");
            groupDBReference.child(currentGroup.getGroupId()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    groupUserList.clear();
                    System.out.println("printing user id");
                    System.out.println(snapshot.getValue());
                    for (DataSnapshot userssnapshot : snapshot.child("usersId").getChildren()) {

                        //user id:
                        System.out.println(userssnapshot.getValue());
                        GroupUser groupUser = new GroupUser();
                        groupUser.setuId(userssnapshot.getValue().toString());
                        // amount for this user
                        for (DataSnapshot amountSnapshot : snapshot.child("amount").child(userssnapshot.getValue().toString()).getChildren()) {
                            System.out.print(amountSnapshot.getValue());
                            System.out.println("printed");
                            groupUser.setAmountDue(Double.valueOf(amountSnapshot.getValue().toString()));
                        }

                        //getting username for that user
                        memberDBReference.child(userssnapshot.getValue().toString()).child("email").addValueEventListener(new ValueEventListener(
                        ) {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                System.out.println(snapshot.getValue());
                                groupUser.setEmail(snapshot.getValue().toString());
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        groupUserList.add(groupUser);


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            userDetail.setValue(groupUserList);
        }

    }





}
