package com.sachin_himal.walletshare.repository.friends;

import static com.sachin_himal.walletshare.repository.Database.ALLFRIENDLIST;
import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;
import static com.sachin_himal.walletshare.repository.Database.RECEIVEDFRIENDREQUEST;
import static com.sachin_himal.walletshare.repository.Database.SENTFRIENDREQUEST;
import static com.sachin_himal.walletshare.repository.Database.USERS;

import android.net.Uri;
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.User;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FriendRepositoryImpl implements FriendRepository {

    private static FriendRepository friendRepositoryInstance;
    private static Lock friendLock = new ReentrantLock();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference currentUserDBReference;
    private DatabaseReference usersDBReference;

    private String currentUID;


    private String currentFriendName;
    private MutableLiveData<List<String>> allFriendRequestSentKey;
    private MutableLiveData<List<String>> allCurrentFriendKey;
    private MutableLiveData<List<String>> allReceivedFriendRequestKey;
    private MutableLiveData<Uri> currentFriendProfileImage;

    private MutableLiveData<String> errorMessage;
    private MutableLiveData<String> successMessage;

    private MutableLiveData<List<User>> allFriendRequests;
    private MutableLiveData<List<User>> allCurrentFriend;
    private MutableLiveData<User> searchedUser;

    HashMap<String , Uri> allFriendProfileImage = new HashMap<>();


    public FriendRepositoryImpl() {
        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);


        //All Friend  keys
        allFriendRequestSentKey = new MutableLiveData<>();
        allFriendRequestSentKey.postValue(new ArrayList<>());
        allCurrentFriendKey = new MutableLiveData<>();
        allCurrentFriendKey.postValue(new ArrayList<>());
        allReceivedFriendRequestKey = new MutableLiveData<>();
        allReceivedFriendRequestKey.setValue(new ArrayList<>());
        currentFriendProfileImage = new MutableLiveData<>();

        searchedUser = new MutableLiveData<>();


        allFriendRequests = new MutableLiveData<>();
        allCurrentFriend = new MutableLiveData<>();

        errorMessage = new MutableLiveData<>();
        successMessage = new MutableLiveData<>();



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
        initializeFriend();

    }

    public void initializeFriend() {

      //  clearAllData();

        searchForALlFriends();
        getReceivedRequestedFriendKey();
        getSentFriendRequestKey();
        getCurrentFriendListKey();

    }

    private void clearAllData() {

        allFriendRequestSentKey.postValue(null);
        allCurrentFriendKey.postValue(null);
        allReceivedFriendRequestKey.postValue(null);
        allFriendRequests.postValue(null);
        allCurrentFriend.postValue(null);

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

                snapshot.getChildren().forEach(dataSnapshot -> key.add(dataSnapshot.getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        allCurrentFriendKey.setValue(key);

    }


    private void getReceivedRequestedFriendKey() {
        List<String> key = new ArrayList<>();

        currentUserDBReference.child("receivedFriendRequests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> key.add(dataSnapshot.getValue().toString()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
        allReceivedFriendRequestKey.postValue(key);
    }


    @Override
    public void addNewFriend(CallBack callBack) {
        successMessage.setValue(null);
        errorMessage.setValue(null);
        String currentFriendKeyData = searchedUser.getValue().getUid();
        if (currentFriendKeyData != null && !(currentFriendKeyData.isEmpty()) && !(allFriendRequestSentKey.getValue().contains(currentFriendKeyData)) && !(allCurrentFriendKey.getValue().contains(currentFriendKeyData)) && !(allReceivedFriendRequestKey.getValue().contains(currentFriendKeyData))) {

            currentUserDBReference.child("sentFriendRequest").push().setValue(currentFriendKeyData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        usersDBReference.child(currentFriendKeyData).child("receivedFriendRequests").push().setValue(currentUID);
                        successMessage.setValue("Friend request has been send");
                    } else {
                        errorMessage.setValue("Something wen wrong");
                    }
                }
            });
        } else {
            errorMessage.setValue("Selected account is either already a friend or there is a pending request");
        }
    }


    //Getting friend key and name using friend Email :  --
    @Override
    public void findFriend(String email, CallBack callBack) {
        firebaseDatabase.getReference().child(USERS).orderByChild("email").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                String uId = "", firstName = "", lastName = "";
                Iterable<DataSnapshot> snapshotIterable = snapshot.getChildren();
                for (DataSnapshot dataSnapshot : snapshotIterable) {
                    uId = dataSnapshot.getKey();
                    firstName = dataSnapshot.child("firstName").getValue().toString();
                    lastName = dataSnapshot.child("lastName").getValue().toString();

                }
                if (uId.isEmpty()) {
                    errorMessage.setValue("No user found");
                    return;
                }
                User user = new User();
                user.setUid(uId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                searchedUser.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
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
    public LiveData<List<User>> getAllReceivedFriendRequest() {
        return allFriendRequests;
    }


    @Override
    public void searchForFriendRequest() {
        retrieveAllFriendRequests();
    }

    @Override
    public void rejectFriendRequest(String uid) {
        errorMessage.setValue(null);


        currentUserDBReference.child(RECEIVEDFRIENDREQUEST).orderByValue().equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue();
                    allFriendRequests.getValue().removeIf(user -> user.getUid().equals(uid));
                    allFriendRequests.setValue(allFriendRequests.getValue());
                    successMessage.setValue("Friend request has been rejected");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        usersDBReference.child(uid).child(SENTFRIENDREQUEST).orderByValue().equalTo(currentUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public LiveData<Uri> getProfileImage() {
        return currentFriendProfileImage;
    }

    @Override
    public void searchProfileImage(String uid) {

        if (allFriendProfileImage.get(uid)!=null) {
            currentFriendProfileImage.setValue(allFriendProfileImage.get(uid));
            return;
        }


        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile_images").child(uid);
        try {
            File localFile = File.createTempFile(uid, "jpg");
            storageReference.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        if (localFile.getName().contains(uid)) {
                            allFriendProfileImage.put(uid, Uri.fromFile(localFile));
                            currentFriendProfileImage.setValue(Uri.fromFile(localFile));
                        }
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    @Override
    public void resetProfileImage() {
        currentFriendProfileImage.postValue(null);
    }

    @Override
    public LiveData<User> getSearchedFriend() {
        return searchedUser;
    }

    @Override
    public void acceptFriendRequest(String uid) {

        errorMessage.setValue(null);
        currentUserDBReference.child(ALLFRIENDLIST).push().setValue(uid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                successMessage.setValue(null);
                if (task.isSuccessful()) {

                    usersDBReference.child(uid).child(SENTFRIENDREQUEST).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                            dataSnapshots.forEach(dataSnapshot -> {
                                        if (dataSnapshot.getValue().toString().equals(currentUID)) {
                                            dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        usersDBReference.child(uid).child(ALLFRIENDLIST).push().setValue(currentUID);
                                                        allFriendRequests.getValue().removeIf(user -> user.getUid().equals(uid));
                                                        allFriendRequests.setValue(allFriendRequests.getValue());
                                                        successMessage.setValue("Friend request accepted");
                                                    }

                                                }
                                            });
                                        }
                                    }
                            );
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        currentUserDBReference.child(RECEIVEDFRIENDREQUEST).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> dataSnapshots = snapshot.getChildren();
                dataSnapshots.forEach(dataSnapshot -> {
                            if (dataSnapshot.getValue().toString().equals(uid)) {
                                dataSnapshot.getRef().removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        }
                                    }
                                });
                            }
                        }
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        retrieveAllFriendRequests();
    }





        public void retrieveAllFriendRequests () {

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

            executorService.execute(() -> {
                currentUserDBReference.child("receivedFriendRequests").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        List<User> allFriendRequestsFromServer = new ArrayList<>();

                        for (DataSnapshot child : children) {
                            String uId = child.getValue(String.class);
                            mainThreadHandler.post(() -> {
                                convertAndAdd(uId, allFriendRequestsFromServer, 1);
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            });

        }


        @Override
        public void searchForALlFriends () {

            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

            executorService.execute(() -> {
                currentUserDBReference.child(ALLFRIENDLIST).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        List<User> userArrayList = new ArrayList<>();

                        for (DataSnapshot child : children) {
                            String uId = child.getValue(String.class);
                            mainThreadHandler.post(() -> {
                                convertAndAdd(uId, userArrayList, 2);
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            });
        }



    @Override
    public LiveData<List<User>> getAllFriendListData() {
        return allCurrentFriend;
    }


    private void convertAndAdd (String uId, List < User > listOfCurrentFriend,int type){
            successMessage.setValue(null);
            errorMessage.setValue(null);
            //Set int type as 1 If you want request , 2 if you want all friend list
            User user = new User();
            usersDBReference.child(uId).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        DataSnapshot dataSnapshot = task.getResult();
                        user.setFirstName(dataSnapshot.child("firstName").getValue(String.class));
                        user.setLastName(dataSnapshot.child("lastName").getValue(String.class));
                        user.setUid(dataSnapshot.getKey());

                        if (type == 1) {
                            listOfCurrentFriend.add(user);
                            allFriendRequests.setValue(listOfCurrentFriend);

                        } else if (type == 2) {
                            listOfCurrentFriend.add(user);
                            allCurrentFriend.setValue(listOfCurrentFriend);

                        }
                    }
                }
            });

        }

    }

