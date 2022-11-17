package com.sachin_himal.walletshare.repository.groupSplit;

import com.sachin_himal.walletshare.entity.Group;

import java.util.List;

public interface GroupRepository {
    void  addNewGroup(Group group);
    List<Group> getAllGroup();



}
