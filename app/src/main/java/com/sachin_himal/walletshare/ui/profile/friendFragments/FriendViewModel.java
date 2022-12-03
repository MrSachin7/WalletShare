package com.sachin_himal.walletshare.ui.profile.friendFragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.repository.friends.FriendRepository;
import com.sachin_himal.walletshare.repository.friends.FriendRepositoryImpl;

import java.util.HashMap;
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

    public String getSearchedFriendDetail() {
        return friendRepository.getSearchedFriendDetail();
    }

    public void addFriend() {
        friendRepository.addNewFriend(() -> friendAddedFinished.setValue(true));
    }

    public LiveData<HashMap<String,String>> getAllReceivedRequests(){
        return friendRepository.getAllReceivedRequests();
    }


    public HashMap<String, String> getALlFriends() {
        return friendRepository.getCurrentFriendList();
    }

    public LiveData<String> getSuccessMessage() {
        return friendRepository.getSuccessMessage();
    }

    public LiveData<String> getErrorMessage() {
        return friendRepository.getErrorMessage();
    }

    public void getFriendName() {
        friendRepository.getFriendName();
    }

    public LiveData<List<User>> getAllReceievedFriendRequest() {
        return friendRepository.getAllReceivedFriendRequest();


    }

    public void searchForFriendRequest() {

        friendRepository.searchForFriendRequest();
    }
}
