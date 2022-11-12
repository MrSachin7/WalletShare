package com.sachin_himal.walletshare.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;

public class SignUpTabFragment extends Fragment {

    TextInputLayout emailField, passwordField;
    AppCompatButton signUp;
    FloatingActionButton facebook, google, twitter;
    ProgressBar progressBar;
    private UserViewModal viewModel;

    public SignUpTabFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_tab_fragment, container, false);
        initializeAllFields(view);

        viewModel = new ViewModelProvider(this).get(UserViewModal.class);

        signUp.setOnClickListener(this::signUpPressed);

        viewModel.getSignUpError().observe(getViewLifecycleOwner(), this::errorOnLogin);




        return view;



    }

    private void errorOnLogin(String s) {
        progressBar.setVisibility(View.INVISIBLE);
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }


    private void signUpPressed(View view) {


        boolean isAppropriateEmail = validateEmail();
        boolean isAppropriatePassword= validatePassword();

        if (!(isAppropriateEmail && isAppropriatePassword)){
            return;
        }

        String email = emailField.getEditText().getText().toString().trim();
        String password = passwordField.getEditText().getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);


        viewModel.signUp(email, password);

//        progressBar.setVisibility(View.INVISIBLE);


    }

    private boolean validatePassword() {
        String password = passwordField.getEditText().getText().toString().trim();

        if (password.isEmpty()){
            passwordField.setError("Password cannot be empty");
            return false;
        }
        else if (password.length()<8){
            passwordField.setError("Password must contain more than 8 chars");
            return false;
        }
        else if (password.length()>12){
            passwordField.setError("Password must contain less than 12 characters");
            return false;
        }
        else{
            passwordField.setError(null);
            passwordField.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        String email = emailField.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()){
            emailField.setError("Email cannot be empty");
            return false;
        }
        else if (!email.matches(checkEmail)){
            emailField.setError("Invalid email!");
            return false;

        }
        else{
            emailField.setError(null);
            emailField.setErrorEnabled(false);
            return true;
        }


    }

    private void initializeAllFields(View view) {


        facebook = view.findViewById(R.id.facebook_btn);
        google = view.findViewById(R.id.google_btn);
        twitter = view.findViewById(R.id.twitter_btn);
        emailField = view.findViewById(R.id.emailFieldCreate);
        passwordField = view.findViewById(R.id.passwordFieldCreate);
        signUp = view.findViewById(R.id.sign_up_btn);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);



    }
}
