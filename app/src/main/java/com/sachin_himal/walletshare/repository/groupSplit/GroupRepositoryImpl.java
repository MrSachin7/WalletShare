package com.sachin_himal.walletshare.repository.groupSplit;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;

import static com.sachin_himal.walletshare.repository.Database.GROUPS;
import static com.sachin_himal.walletshare.repository.Database.USERS;


import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.repository.user.FriendRepository;
import com.sachin_himal.walletshare.repository.user.FriendRepositoryImpl;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GroupRepositoryImpl implements GroupRepository {

    private static GroupRepository groupRepositoryInstance;
    private static Lock groupLock = new ReentrantLock();
    private DatabaseReference groupDBReference;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference memberDBReference;
    private DatabaseReference currentUserDBReference;

    private MutableLiveData<List<Group>> allGroupForUser;
    private MutableLiveData<String> error;

    private String currentUserID;

    private Group currentGroup;

    private DatabaseReference referenceForAddingFriend;

    MutableLiveData<List<GroupUser>> userDetail;
MutableLiveData<List<User>> canBeAddedToGroup;
FriendRepository friendRepository ;
MutableLiveData<String> successMessage;

    private GroupRepositoryImpl() {
        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);
        allGroupForUser = new MutableLiveData<>();
        allGroupForUser.postValue(new ArrayList<>());
        error = new MutableLiveData<>();

        userDetail = new MutableLiveData<>();
        userDetail.postValue(new ArrayList<>());
        canBeAddedToGroup = new MutableLiveData<>();
this.friendRepository = FriendRepositoryImpl.getInstance();
successMessage = new MutableLiveData<>();
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
        currentUserID = uid;
        groupDBReference = firebaseDatabase.getReference().child(GROUPS);
        memberDBReference = firebaseDatabase.getReference().child(USERS);



        currentUserDBReference = firebaseDatabase.getReference().child(USERS).child(currentUserID);
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
                newAddedReference.child("amount").child(currentUserID).push().setValue(0.0);
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
        friendRepository.searchForALlFriends();

    }

    public Group getCurrentGroup() {
        return currentGroup;
    }

    @Override
    public LiveData<List<GroupUser>> getUserDataForGroup() {
        return userDetail;
    }

    @Override
    public void addNewFriend(String fId) {

        successMessage.setValue("");
        groupDBReference.child(currentGroup.getGroupId()).child("usersId").push().setValue(fId).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                groupDBReference.child(currentGroup.getGroupId()).child("amount").child(fId).push().setValue(0.00);
                memberDBReference.child(fId).child("GroupList").push().setValue(currentGroup.getGroupId()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            successMessage.setValue("Friend Is Successfully Added");
                        }
                    }
                });
            }}

        });


    }




    public void addNewFriendWithRef() {

        System.out.println("in the refrence method");
        String snapshotKey = referenceForAddingFriend.getKey();
        groupDBReference.child(currentGroup.getGroupId()).child("usersId").push().setValue(snapshotKey);
        groupDBReference.child("GroupList").getRef().push().setValue(currentGroup.getGroupId());
    }


    public void searchAllGroup() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        executorService.execute(() -> {
            currentUserDBReference.child("GroupList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    List<Group> allGroup = new ArrayList<>();

                    for (DataSnapshot child : children) {
                        String groupId = child.getValue(String.class);
                        mainThreadHandler.post(() -> {
                            convertAndAdd(groupId, allGroup);
                        });
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void convertAndAdd(String groupId, List<Group> allGroup) {


Group group = new Group();

                groupDBReference.child(groupId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()){
                                    DataSnapshot dataSnapshot = task.getResult();
                                    group.setGroupId(dataSnapshot.getKey());
                                    group.setGroupName(dataSnapshot.child("groupName").getValue(String.class));
                                        task.getResult().getRef().child("amount").child(currentUserID).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                               snapshot.getChildren().forEach(dataSnapshot1 -> group.setAmount(dataSnapshot1.getValue(Double.class)) );
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                    //  group.setusersIdManual(dataSnapshot.child("usersId").getValue(List<User>.class));
                                   // System.out.println(dataSnapshot.child("amount").child(currentUserID).getValue(Double.class));
                                    group.setAmount(1.0);
                                    allGroup.add(group);

                            }

    }


    });
    allGroupForUser.setValue(allGroup);}


    //////
    ////
    //

    public void getUserDataForGroupQuery() {
        List<GroupUser> groupUserList = new ArrayList<>();
        List<String> uids = new ArrayList<>();
        System.out.println(currentGroup.getGroupId());

        if (currentGroup != null) {


            groupDBReference.child(currentGroup.getGroupId()).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    groupUserList.clear();
                    uids.clear();
                    for (DataSnapshot userssnapshot : snapshot.child("usersId").getChildren()) {

                        //user id:
                        System.out.println(userssnapshot.getValue());
                        GroupUser groupUser = new GroupUser();

                        // amount for this user
                        for (DataSnapshot amountSnapshot : snapshot.child("amount").child(userssnapshot.getValue().toString()).getChildren()) {


                            groupUser.setAmountDue(Double.valueOf(amountSnapshot.getValue().toString()));
                        }


                        memberDBReference.child(userssnapshot.getValue().toString()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DataSnapshot dataSnapshot = task.getResult();
                                    groupUser.setFirstName(dataSnapshot.child("firstName").getValue(String.class));
                                    groupUser.setLastName(dataSnapshot.child("lastName").getValue(String.class));
                                    groupUser.setuId(dataSnapshot.getKey());
                                    String a = dataSnapshot.getKey();

                                        groupUserList.add(groupUser);
                                        uids.add(a);
                                        userDetail.setValue(groupUserList);
                                        currentGroup.setusersIdManual(uids);
                                    checkForFriendthatCanbeAdded(userDetail.getValue());

                                }
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void checkForFriendthatCanbeAdded(List<GroupUser> groupUserList) {
        canBeAddedToGroup.setValue(new ArrayList<>());
        List<User> allCurrent=friendRepository.getAllFriendListData().getValue();
        List<User> canBeAdded = new ArrayList<>();
        for (int i = 0;i<allCurrent.size();i++){
           if (!currentGroup.getUsersId().contains(allCurrent.get(i).getUid())){
               canBeAdded.add(allCurrent.get(i));
           }
        }
        canBeAddedToGroup.setValue(canBeAdded);

    }

    public LiveData<List<User>> getCanBeAddedUser() {
        return canBeAddedToGroup;
    }

    public MutableLiveData<String> getSuccessMessage() {
        return successMessage;
    }
}
