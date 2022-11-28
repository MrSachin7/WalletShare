package com.sachin_himal.walletshare.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.login.UserViewModel;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileFragment extends Fragment {

    AppCompatButton
            logOutButton;
    TextView profileName, profileEmail;

    UserViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeAllFields(view);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        logOutButton.setOnClickListener(v -> logOut());

        viewModel.searchForCurrentUser();
        viewModel.getLoggedInUser().observe(getViewLifecycleOwner(),this::updateUI);

        return view;
    }

    private void updateUI(User user) {
        if (user != null){
            profileName.setText(user.getFirstName() + " " + user.getLastName());
            profileEmail.setText(user.getEmail());
        }
    }


    private void logOut() {
        viewModel.signOut();
    }

    private void initializeAllFields(View view) {
        logOutButton = view.findViewById(R.id.log_out);
        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);
    }
}