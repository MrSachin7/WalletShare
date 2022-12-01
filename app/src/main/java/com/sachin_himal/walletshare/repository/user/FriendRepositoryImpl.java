package com.sachin_himal.walletshare.repository.user;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;
import static com.sachin_himal.walletshare.repository.Database.USERS;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FriendRepositoryImpl implements FriendRepository{

    private static  FriendRepository friendRepositoryInstance;
    private static Lock friendLock = new ReentrantLock();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference currentUserDBReference;
    private DatabaseReference usersDBReference;

    private MutableLiveData<HashMap<String,String>> userFriends;
    private String currentUID;

    private String currentFriendKeyData;
    private String currentFriendName;
    private  MutableLiveData<List<String>> allFriendRequestSentKey;
    private MutableLiveData<List<String>> allCurrentFriendKey;
    private MutableLiveData<List<String>> allReceivedFriendRequestKey;

    private MutableLiveData<String> errorMessage;
    private MutableLiveData<String> successMessage;


    private MutableLiveData<HashMap<String,String>> allReceivesRequest;

    private  MutableLiveData<HashMap<String,String>> userSendRequest;





    public FriendRepositoryImpl() {
        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);

        userFriends = new MutableLiveData<>();
        userFriends.setValue(new HashMap<>());


        //All Friend  keys
        allFriendRequestSentKey = new MutableLiveData<>();
        allFriendRequestSentKey.postValue(new ArrayList<>());
        allCurrentFriendKey = new MutableLiveData<>();
        allCurrentFriendKey.postValue(new ArrayList<>());
        allReceivedFriendRequestKey = new MutableLiveData<>();
        allReceivedFriendRequestKey.setValue(new ArrayList<>());

        allReceivesRequest = new MutableLiveData<>();

        errorMessage = new MutableLiveData<>();
        successMessage = new MutableLiveData<>();
        allReceivesRequest.setValue(new HashMap<>());


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
        currentUID = uid;
        currentUserDBReference = firebaseDatabase.getReference().child(USERS).child(uid);
        usersDBReference = firebaseDatabase.getReference().child(USERS);

        getReceivedRequestedFriendKey();
        getSentFriendRequestKey();
        getCurrentFriendListKey();

    }




    private void getSentFriendRequestKey() {
        List<String> key = new ArrayList<>();
        currentUserDBReference.child("sentFriendRequest").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                snapshot.getChildren().forEach(dataSnapshot -> key.add(dataSnapshot.getValue().toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        allFriendRequestSentKey.setValue(key);
    }

    private void getCurrentFriendListKey() {
        List<String> key = new ArrayList<>();
        currentUserDBReference.child("friendList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(snapshot.getValue());
                snapshot.getChildren().forEach(dataSnapshot -> key.add(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        allCurrentFriendKey.setValue(key);
        getFriendsName();
    }



    private void getReceivedRequestedFriendKey() {
        List<String> key = new ArrayList<>();

     currentUserDBReference.child("receivedFriendRequests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getValue().toString();
                    snapshot.getChildren().forEach(dataSnapshot ->
                        key.add(dataSnapshot.getValue().toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        allReceivedFriendRequestKey.postValue(key);
    }


    @Override
    public LiveData<HashMap<String, String>> getFriendList() {
        return null;
    }

    @Override
    public void addNewFriend(CallBack callBack) {

        successMessage.setValue(null);
        errorMessage.setValue(null);
        if (currentFriendKeyData != null && !(currentFriendKeyData.isEmpty()) && !(allFriendRequestSentKey.getValue().contains(currentFriendKeyData)) && !(allCurrentFriendKey.getValue().contains(currentFriendKeyData)) && !(allReceivedFriendRequestKey.getValue().contains(currentFriendKeyData))) {

            currentUserDBReference.child("sentFriendRequest").push().setValue(currentFriendKeyData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        usersDBReference.child(currentFriendKeyData).child("receivedFriendRequests").push().setValue(currentUID);
                        successMessage.setValue("Friend request has been send");
                    }
                    else{
                        errorMessage.setValue("Something wen wrong");
                    }

                }
            });
        }else{
            errorMessage.setValue("Selected account is either already a friend or there is a pending request");
        }

    };



    //Getting friend key and name using friend Email :  --
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

                }

                currentFriendKeyData = FriendKey;
                currentFriendName = FriendName;

                callBack.callBack();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public LiveData<HashMap<String, String>> getAllReceivedRequests() {
            return allReceivesRequest;
    }

    @Override
    public HashMap<String, String> getCurrentFriendList() {
        System.out.println(userFriends.getValue().size() + "--------`´´´´´´´´´´´´´´");
        return userFriends.getValue();
    }

    @Override
    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    @Override
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void getFriendName() {
        getAllReceivedRequest();
    }

    void getFriendsName(){
        HashMap<String,String> names = new HashMap<>();
        List<String> key = allCurrentFriendKey.getValue();
       for (int i = 0; i <key.size(); i++) {
           System.out.println("getting friend ");
           DatabaseReference databaseReference =usersDBReference.child(key.get(i));
           String name =  databaseReference.child("firstName").toString() + " " + databaseReference.child("lastName").toString();
           System.out.println(name);
           names.put(key.get(i),name);
       }
       userFriends.setValue(names);
    }

    public void getAllReceivedRequest() {

        HashMap<String,String> allFriends = new HashMap<>();
        List<String> all = allReceivedFriendRequestKey.getValue();
        System.out.println(allReceivedFriendRequestKey.getValue().size());
        System.out.println("I am heree");
        for (int i = 0; i < all.size(); i++) {
            System.out.println("can i pass");
            DatabaseReference databaseReference =usersDBReference.child(all.get(i));
           String name =  databaseReference.child("firstName").toString() + " " + databaseReference.child("lastName").toString();
            System.out.println(name);
            allFriends.put(all.get(i),name);
        }
        allReceivesRequest.setValue(allFriends);

    }


    @Override
    public String getSearchedFriendDetail() {
        if (currentFriendKeyData == null || currentFriendKeyData.isEmpty()) {
            return "";
        } else
            return currentFriendName;
    }

    public void retrieveAllFriendRequests(){


      //  currentUserDBReference.child("receivedFriendRequests").
    }
    }

