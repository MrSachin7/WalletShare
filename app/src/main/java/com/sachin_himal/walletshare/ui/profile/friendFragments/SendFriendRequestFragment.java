package com.sachin_himal.walletshare.ui.profile.friendFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

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


        sendFriendRequest.setVisibility(View.INVISIBLE);
        sendFriendRequest.setOnClickListener(this::addFriend);


    }

    private void errorObserver(String s) {
                if (s==null || s.isEmpty()) return;

        FancyToast.makeText(getContext(), s,FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
    }

    private void succeessObserver(String s) {
        if (s==null || s.isEmpty()) return;
        FancyToast.makeText(getContext(), s,FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        ((MainActivity)getActivity()).changeFragment(R.id.friendFragment);
    }

    private void addFriend(View view) {
        friendViewModel.addFriend();
        friendViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), this::succeessObserver);
        friendViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::errorObserver);
    }


    private void searchFriend(View view) {
        String friendEmail = emailId.getText().toString().trim();
        friendViewModel.findFriendDetail(friendEmail);
    }

    private void updateFriendName(Boolean aBoolean) {
        if (aBoolean){
            friendName.setText(friendViewModel.getSearchedFriendDetail());
            if (!friendName.getText().toString().trim().isEmpty()){
                sendFriendRequest.setVisibility(View.VISIBLE);
            }else {
                sendFriendRequest.setVisibility(View.INVISIBLE);
            }

        }
    }




}