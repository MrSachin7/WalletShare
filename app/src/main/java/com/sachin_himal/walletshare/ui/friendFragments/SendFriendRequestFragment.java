package com.sachin_himal.walletshare.ui.friendFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import de.hdodenhof.circleimageview.CircleImageView;


public class SendFriendRequestFragment extends Fragment {


    AppCompatButton sendFriendRequest, searchForFriend;
    AppCompatEditText emailId;
    CircleImageView profileImage;
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
        profileImage = view.findViewById(R.id.profile_image);
        sendFriendRequest.setVisibility(View.INVISIBLE);
        profileImage.setVisibility(View.INVISIBLE);
        friendName.setVisibility(View.INVISIBLE);
        friendViewModel = new ViewModelProvider(this).get(FriendViewModel.class);
        searchForFriend.setOnClickListener(this::searchFriend);

        friendViewModel.getSearchedFriend().observe(getViewLifecycleOwner(), this:: updateUI);

        friendViewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::errorMessageObserver);




        sendFriendRequest.setVisibility(View.INVISIBLE);
        sendFriendRequest.setOnClickListener(this::addFriend);


    }

    private void errorMessageObserver(String s) {
        if(s != null){
            FancyToast.makeText(getActivity(),s,FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
            emailId.getText().clear();
            sendFriendRequest.setVisibility(View.INVISIBLE);
            profileImage.setVisibility(View.INVISIBLE);
            friendName.setVisibility(View.INVISIBLE);
        }
    }

    private void updateUI(User user) {
        sendFriendRequest.setVisibility(View.VISIBLE);


        String uId = user.getUid();
        String name = user.retrieveFullName();
        friendName.setText(name);
        friendViewModel.searchProfileImage(uId);
        friendViewModel.getProfileImage().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                profileImage.setImageURI(uri);
                friendViewModel.resetProfileImage();

            }
        });

    }



    private void successObserver(String s) {
        if (s == null || s.isEmpty()) return;
        FancyToast.makeText(getContext(), s, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        sendFriendRequest.setVisibility(View.INVISIBLE);
        profileImage.setVisibility(View.INVISIBLE);
        friendName.setVisibility(View.INVISIBLE);
        ((MainActivity) getActivity()).changeFragment(R.id.friendFragment);
    }

    private void addFriend(View view) {
        friendViewModel.addFriend();
        friendViewModel.getSuccessMessage().observe(getViewLifecycleOwner(), this::successObserver);
    }


    private void searchFriend(View view) {
        String friendEmail = emailId.getText().toString().trim();
        if (friendEmail.isEmpty()) {
            FancyToast.makeText(getContext(), "Please enter email", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return;
        }
        friendViewModel.findFriendDetail(friendEmail);
        sendFriendRequest.setVisibility(View.VISIBLE);

        profileImage.setVisibility(View.VISIBLE);
        friendName.setVisibility(View.VISIBLE);

    }


}