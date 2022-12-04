package com.sachin_himal.walletshare.ui.friendFragments;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.repository.friends.FriendRepository;
import com.sachin_himal.walletshare.repository.friends.FriendRepositoryImpl;

import java.util.List;

public class FriendViewModel extends ViewModel {
private MutableLiveData<Boolean> friendSearchedFinished;
FriendRepository friendRepository;
    private MutableLiveData<Boolean> friendAddedFinished;
    public FriendViewModel() {
        this.friendRepository = FriendRepositoryImpl.getInstance();
        friendSearchedFinished = new MutableLiveData<>();
        friendAddedFinished= new MutableLiveData<>();
    }

    public void findFriendDetail(String email){
         friendRepository.findFriend(email,()-> friendSearchedFinished.setValue(true));
       //  getFriendSearchedFinished();
    }

    public LiveData<Boolean> getFriendSearchedFinished() {
        return friendSearchedFinished;
    }



    public void addFriend() {
        friendRepository.addNewFriend(() -> friendAddedFinished.setValue(true));
    }






    public LiveData<String> getSuccessMessage() {
        return friendRepository.getSuccessMessage();
    }

    public LiveData<String> getErrorMessage() {
        return friendRepository.getErrorMessage();
    }



    public LiveData<List<User>> getAllReceivedFriendRequest() {
        return friendRepository.getAllReceivedFriendRequest();
    }



    public void searchForFriendRequest() {

        friendRepository.searchForFriendRequest();
    }

    public void initializeFriendKey() {
        friendRepository.initializeFriend();
    }

    public void acceptFriendRequest(String uid) {
        friendRepository.acceptFriendRequest(uid);
    }

    public void searchForALlFriends() {
        friendRepository.searchForALlFriends();
    }

    public LiveData<List<User>> getALlFriendList() {
        return friendRepository.getAllFriendListData();
    }

    public void rejectFriendRequests(String uid) {
        friendRepository.rejectFriendRequest(uid);
    }

    public LiveData<Uri> getProfileImage() {
        return friendRepository.getProfileImage();
    }

    public void searchProfileImage(String uid) {
        friendRepository.searchProfileImage(uid);
    }


    public void resetProfileImage() {
        friendRepository.resetProfileImage();
    }

    public LiveData<User> getSearchedFriend() {
        return friendRepository.getSearchedFriend();
    }
}
