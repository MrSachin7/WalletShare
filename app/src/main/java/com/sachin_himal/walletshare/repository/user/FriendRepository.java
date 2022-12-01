package com.sachin_himal.walletshare.repository.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sachin_himal.walletshare.entity.CallBack;

import java.util.HashMap;

public interface FriendRepository {
    void initializeFriend(String uid);

    LiveData<HashMap<String,String>> getFriendList();

    void addNewFriend( CallBack callBack);



    String getSearchedFriendDetail();

    void findFriend(String email, CallBack callBack);


    LiveData<HashMap<String,String>> getAllReceivedRequests();

HashMap<String, String> getCurrentFriendList();
}
