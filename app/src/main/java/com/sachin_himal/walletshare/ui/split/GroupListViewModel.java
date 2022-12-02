package com.sachin_himal.walletshare.ui.split;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;
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

    public GroupRepository getGroupRepository() {
        return groupRepository;
    }

    public void addNewFriend(String fId) {
        groupRepository.addNewFriend(fId);

    }

    public void searchAllGroup() {
        groupRepository.searchAllGroup();
    }

    public LiveData<List<User>> getUserThatCanBeAdded() {
        return groupRepository.getCanBeAddedUser();
    }


    public LiveData<String> getSuccessMessage(){
        return groupRepository.getSuccessMessage();
    }
}
