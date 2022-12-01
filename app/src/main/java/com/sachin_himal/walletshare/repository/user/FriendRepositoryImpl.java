package com.sachin_himal.walletshare.repository.user;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;
import static com.sachin_himal.walletshare.repository.Database.USERS;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.entity.CallBack;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FriendRepositoryImpl implements FriendRepository{

    private static  FriendRepository friendRepositoryInstance;
    private static Lock friendLock = new ReentrantLock();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference userDBReference;

    private MutableLiveData<HashMap<String,String>> userFriends;
    private String currentUID;

    private String currentFriendKeyData;
    private String currentFriendName;



    public FriendRepositoryImpl() {
        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);

        userFriends = new MutableLiveData<>();
        userFriends.postValue(new HashMap<>());
    }

    public static FriendRepository getInstance() {
        if (friendRepositoryInstance == null) {
            synchronized (friendLock) {
                if (friendRepositoryInstance == null) {
                    friendRepositoryInstance = new FriendRepositoryImpl();
                }
            }
        }
        return friendRepositoryInstance;
    }


    @Override
    public void initializeFriend(String uid) {
        userDBReference = firebaseDatabase.getReference().child(USERS).child(uid).child("friendList");
        currentUID = uid;



    }

    @Override
    public LiveData<HashMap<String, String>> getFriendList() {
        return null;
    }

    @Override
    public void addNewFriend(String friendEmail, CallBack callBack) {
      //  DatabaseReference databaseReference = userDBReference.push().setValue(friendEmail);
    }

    @Override
    public void findFriend(String email,CallBack callBack) {


        firebaseDatabase.getReference().child(USERS).orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<String, String> searchedFriendDetail = new HashMap<>();
                String FriendKey = "",FriendName="";
                Iterable<DataSnapshot> snapshotIterable = snapshot.getChildren();
                for (DataSnapshot dataSnapshot : snapshotIterable){
                    FriendKey = dataSnapshot.getKey();

                    FriendName= dataSnapshot.child("firstName").getValue()+" "+dataSnapshot.child("lastName").getValue();
                    searchedFriendDetail.put(FriendKey,FriendName);
                    callBack.callBack();
                }

                currentFriendKeyData = FriendKey;
                currentFriendName = FriendName;



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public String getSearchedFriendDetail() {
        if (currentFriendKeyData == null || currentFriendKeyData.isEmpty()) {
            return "";
        } else
            return currentFriendName;
    }
    }

