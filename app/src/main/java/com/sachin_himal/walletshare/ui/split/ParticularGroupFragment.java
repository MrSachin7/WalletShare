package com.sachin_himal.walletshare.ui.split;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.Group;
import com.sachin_himal.walletshare.entity.GroupUser;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepository;
import com.sachin_himal.walletshare.repository.groupSplit.GroupRepositoryImpl;

import java.util.List;

public class ParticularGroupFragment extends Fragment {


private AppCompatTextView textView;
private AppCompatButton memberButton,saveExpensesBtn;
AppCompatEditText totalExpensesEditText;

private GroupListViewModel groupListViewModel;
private Group group;
private RecyclerView recyclerView;
    private List<User> userList;
    List<String> userName;
    private AddingExpensesToGroupAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        groupListViewModel = new ViewModelProvider(this).get(GroupListViewModel.class);

        View view = inflater.inflate(R.layout.fragment_particular_group, container, false);
       textView = view.findViewById(R.id.particularGroupName);

        recyclerView = view.findViewById(R.id.groupMemberExpenseAdderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AddingExpensesToGroupAdapter();
        recyclerView.setAdapter(adapter);
        totalExpensesEditText = view.findViewById(R.id.expense_amount_field_for_group);

        group =  setCurrentGroup();
     textView.setText(group.getGroupName());



     memberButton = view.findViewById(R.id.memberList);
        groupListViewModel.getUserForCurrentGroup().observe(getViewLifecycleOwner(),this::memberForGroup);


     memberButton.setOnClickListener(v -> {
         Navigation.findNavController(v).navigate(R.id.groupMemberFragment);
     });
        saveExpensesBtn = view.findViewById(R.id.saveBtnGroupExpense);

        saveExpensesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (adapter.getExpenseFinalTotal()==Double.parseDouble(totalExpensesEditText.getText().toString().trim())){
                    Double a = Double.parseDouble(totalExpensesEditText.getText().toString().trim());
                    System.out.println("DOnne " + adapter.expenseFinalTotal);
                    groupListViewModel.addNewExpensesToGroup(a,adapter.getUpdatedList());
                }else{
                    System.out.println("NOT EQUAL");
                }
            }
        });






     return  view;
    }

    private void memberForGroup(List<GroupUser> groupUsers) {
        adapter.setAllFriendList(groupUsers);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private Group setCurrentGroup() {
      return   groupListViewModel.getCurrentGroup();
    }


}