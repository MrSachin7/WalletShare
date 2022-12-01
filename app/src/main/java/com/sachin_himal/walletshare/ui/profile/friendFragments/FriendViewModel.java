package com.sachin_himal.walletshare.ui.profile.friendFragments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.repository.user.FriendRepository;
import com.sachin_himal.walletshare.repository.user.FriendRepositoryImpl;

import java.util.HashMap;

public class FriendViewModel extends ViewModel {
private MutableLiveData<Boolean> friendSearchedFinished;
FriendRepository friendRepository;

    public FriendViewModel() {
        this.friendRepository = FriendRepositoryImpl.getInstance();
        friendSearchedFinished = new MutableLiveData<>();
    }

    public void findFriendDetail(String email){
         friendRepository.findFriend(email,()-> friendSearchedFinished.setValue(true));
         getFriendSearchedFinished();
    }

    public LiveData<Boolean> getFriendSearchedFinished() {
        return friendSearchedFinished;
    }

    public String getSearchedFriendDetail() {
        return friendRepository.getSearchedFriendDetail();
    }
}
