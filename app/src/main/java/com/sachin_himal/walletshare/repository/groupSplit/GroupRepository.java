package com.sachin_himal.walletshare.repository.groupSplit;

import androidx.lifecycle.LiveData;

import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;

import java.util.List;

public interface GroupRepository {

    void initializeGroup(String uid);
    void  addNewGroup(String groupName, CallBack callBack);
   LiveData<List<Group>> getAllGroup();
    void searchAllGroup();

    void setCurrentGroup(Group group);
    Group getCurrentGroup();

   LiveData<List<GroupUser>> getUserDataForGroup();

    void addNewFriend(String fID);

    LiveData<List<User>> getCanBeAddedUser();

    LiveData<String> getSuccessMessage();
}
