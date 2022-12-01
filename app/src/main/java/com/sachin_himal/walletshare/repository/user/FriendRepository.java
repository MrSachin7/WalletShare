package com.sachin_himal.walletshare.repository.user;

import androidx.lifecycle.LiveData;

import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.User;

import java.util.HashMap;
import java.util.List;

public interface FriendRepository {
    void initializeFriend(String uid);

    LiveData<HashMap<String,String>> getFriendList();

    void addNewFriend( CallBack callBack);



    String getSearchedFriendDetail();

    void findFriend(String email, CallBack callBack);


    LiveData<HashMap<String,String>> getAllReceivedRequests();

HashMap<String, String> getCurrentFriendList();

    LiveData<String> getSuccessMessage();

    LiveData<String> getErrorMessage();

    void getFriendName();

    LiveData<List<User>> getAllReceivedFriendRequest();

    void searchForFriendRequest();

}
