package com.sachin_himal.walletshare.ui.split;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepository;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepositoryImpl;

import java.util.List;

public class GroupListViewModel extends ViewModel {

    private GroupRepository groupRepository;
    private MutableLiveData<Boolean> done;

    public GroupListViewModel(){
        groupRepository = GroupRepositoryImpl.getInstance();
        done = new MutableLiveData<>();
    }

    public void addNewGroup(String groupName){
        groupRepository.addNewGroup(groupName, ()->{
            done.setValue(true);
        });
    }

    public LiveData<Boolean> groupisDone(){
        return done;
    }

    public LiveData<List<Group>> getAllGroupForUser(){
        return groupRepository.getAllGroup();
    }


    public void setCurrentGroup(Group group) {
        groupRepository.setCurrentGroup(group);
    }

    public Group getCurrentGroup() {
        return groupRepository.getCurrentGroup();
    }

    public LiveData<List<GroupUser>> getUserForCurrentGroup() {
        return  groupRepository.getUserDataForGroup();
    }
}
