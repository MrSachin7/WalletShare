package com.sachin_himal.walletshare.repository.user;

import androidx.lifecycle.LiveData;

import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.User;

import java.util.HashMap;
import java.util.List;

public interface FriendRepository {
    void initializeFriend(String uid);
    void initializeFriend();

    void addNewFriend( CallBack callBack);

    String getSearchedFriendDetail();

    void findFriend(String email, CallBack callBack);


    LiveData<String> getSuccessMessage();

    LiveData<String> getErrorMessage();

    LiveData<List<User>> getAllReceivedFriendRequest();

    void searchForFriendRequest();

    void acceptFriendRequest(String uid);
}
