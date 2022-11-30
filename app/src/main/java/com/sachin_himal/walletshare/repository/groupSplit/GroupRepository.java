package com.sachin_himal.walletshare.repository.groupSplit;

import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;

import java.util.List;

public interface GroupRepository {

    void initializeGroup(String uid);
    void  addNewGroup(String groupName, CallBack callBack);
   LiveData<List<Group>> getAllGroup();


    void setCurrentGroup(Group group);
    Group getCurrentGroup();

   LiveData<List<GroupUser>> getUserDataForGroup();

    void addNewFriend(String friendEmail);
    void addNewFriendWithRef();
}
