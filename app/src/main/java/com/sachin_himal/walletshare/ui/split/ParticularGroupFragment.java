package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepository;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepositoryImpl;

public class ParticularGroupFragment extends Fragment {


private AppCompatTextView textView;
private AppCompatButton memberButton;

private GroupListViewModel groupListViewModel;
private GroupRepository groupRepository;
private Group group;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);

        View view = inflater.inflate(R.layout.fragment_particular_group, container, false);
       textView = view.findViewById(R.id.particularGroupName);

      group =  setCurrentGroup();
     textView.setText(group.getGroupName());



     memberButton = view.findViewById(R.id.memberList);


     memberButton.setOnClickListener(v -> {
         Navigation.findNavController(v).navigate(R.id.groupMemberFragment);

     });


     return  view;
    }

    private Group setCurrentGroup() {
      return   groupListViewModel.getCurrentGroup();
    }


}