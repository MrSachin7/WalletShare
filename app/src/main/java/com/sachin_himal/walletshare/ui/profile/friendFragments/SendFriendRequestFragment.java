package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.CallBack;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SendFriendRequestFragment#} factory method to
 * create an instance of this fragment.
 */
public class SendFriendRequestFragment extends Fragment {


    AppCompatButton sendFriendRequest,searchForFriend ;
    AppCompatEditText emailId;
FriendViewModel friendViewModel;
AppCompatTextView friendName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_friend_request, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailId = view.findViewById(R.id.editTextFriendEmail);
        sendFriendRequest = view.findViewById(R.id.sendFriendRequestBtn);
        searchForFriend = view.findViewById(R.id.searchForEmail);
        friendName = view.findViewById(R.id.friendNameToShow);
        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        searchForFriend.setOnClickListener(this::searchFriend);
        friendViewModel.getFriendSearchedFinished().observe(getViewLifecycleOwner(),this::updateFriendName);


    }


    private void searchFriend(View view) {
        friendViewModel.findFriendDetail("himal28924@gmail.com");
    }

    private void updateFriendName(Boolean aBoolean) {
        if (aBoolean){
            friendName.setText(friendViewModel.getSearchedFriendDetail());
        }
    }




}