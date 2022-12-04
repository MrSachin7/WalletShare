package com.sachin_himal.walletshare.ui.edit_profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sachin_himal.walletshare.R;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.ui.MainActivity;
import com.sachin_himal.walletshare.ui.login.UserViewModel;
import com.shashank.sony.fancytoastlib.FancyToast;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {


    TextView editProfileEmail;
    TextInputLayout firstName, lastName;
    AppCompatButton updateProfileButton;
    CircleImageView profileImage;

    ProgressBar progressBar;

    UserViewModel viewModel;

    ActivityResultLauncher<Intent> activityResultLauncherForGallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode() == getActivity().RESULT_OK){
                    Intent data = result.getData();
                    viewModel.updateProfileImage(data.getData());

//                    profileImage.setImageURI(data.getData());
                }

            }
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        initializeAllFields(view);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.getLoggedInUser().observe(getViewLifecycleOwner(), this::updateUI);
        updateProfileButton.setOnClickListener(this::updateProfile);
        profileImage.setOnClickListener(this::changeProfileImage);

        viewModel.getProfileImage().observe(getViewLifecycleOwner(), uri -> {
            if (uri != null) {
                profileImage.setImageURI(uri);
            }

        });

        return view;
    }

    private void changeProfileImage(View view) {

        Intent iGallery = new Intent(Intent.ACTION_PICK);
        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activityResultLauncherForGallery.launch(iGallery);


    }


    private void showErrorMessage(String s) {
        if (s == null || s.isEmpty()) return;

        FancyToast.makeText(getContext(), s, FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
    }

    private void showSuccessMessage(String s) {
        if (s == null || s.isEmpty()) return;
        progressBar.setVisibility(View.VISIBLE);
        FancyToast.makeText(getContext(), s, FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
        ((MainActivity) getActivity()).changeFragment(R.id.profileFragment);

    }

    private void updateProfile(View view) {
        boolean areNamesValid = validateName();
        if (!areNamesValid) {
            FancyToast.makeText(getContext(), "Please enter valid names", FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        String firstNameText = firstName.getEditText().getText().toString().trim();
        String lastNameText = lastName.getEditText().getText().toString().trim();

        viewModel.updateProfile(firstNameText, lastNameText);
        viewModel.getSuccessMessage().observe(getViewLifecycleOwner(), this::showSuccessMessage);
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), this::showErrorMessage);
    }


    private boolean validateName() {
        String firstNameText = firstName.getEditText().getText().toString().trim();
        String lastNameText = lastName.getEditText().getText().toString().trim();

        if (firstNameText.isEmpty()) {
            firstName.setError("Firstname cannot be empty");
            return false;
        }

        if (lastNameText.isEmpty()) {
            lastName.setError("Lastname cannot be empty");
            return false;
        }

        firstName.setError(null);
        lastName.setErrorEnabled(false);
        return true;

    }

    private void updateUI(User user) {
        editProfileEmail.setText(user.getEmail());
        firstName.getEditText().setText(user.getFirstName());
        lastName.getEditText().setText(user.getLastName());

    }

    private void initializeAllFields(View view) {
        editProfileEmail = view.findViewById(R.id.edit_profile_email);
        firstName = view.findViewById(R.id.firstNameUpdate);
        lastName = view.findViewById(R.id.lastNameUpdate);
        updateProfileButton = view.findViewById(R.id.update_profile_button);
        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        profileImage = view.findViewById(R.id.profile_image);
    }
}
