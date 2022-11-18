package com.sachin_himal.walletshare.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.R;
import com.shashank.sony.fancytoastlib.FancyToast;

public class SignUpTabFragment extends Fragment {

    TextInputLayout emailField, passwordField, firstNameField, lastNameField;
    AppCompatButton signUp;
    ProgressBar progressBar;
    private UserViewModel viewModel;

    public SignUpTabFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_tab_fragment, container, false);
        initializeAllFields(view);

        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        signUp.setOnClickListener(this::signUpPressed);

        viewModel.getSignUpError().observe(getViewLifecycleOwner(), this::errorOnLogin);




        return view;



    }

    private void errorOnLogin(String s) {
        progressBar.setVisibility(View.INVISIBLE);
        FancyToast.makeText(getContext(),s, FancyToast.LENGTH_SHORT,FancyToast.ERROR, true).show();
    }


    private void signUpPressed(View view) {


        boolean isAppropriateEmail = validateEmail();
        boolean isAppropriatePassword= validatePassword();
        boolean isAppropriateName= validateName();

        if (!(isAppropriateEmail && isAppropriatePassword && isAppropriateName)){
            return;
        }

        String email = emailField.getEditText().getText().toString().trim();
        String password = passwordField.getEditText().getText().toString().trim();
        String firstName = firstNameField.getEditText().getText().toString().trim();
        String lastName = lastNameField.getEditText().getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);


        viewModel.signUp(email, password, firstName, lastName);

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

    private boolean validateName() {
        String firstName = firstNameField.getEditText().getText().toString().trim();
        String lastName = lastNameField.getEditText().getText().toString().trim();

        if (firstName.isEmpty()){
            firstNameField.setError("Password cannot be empty");
            return false;
        }

        if (lastName.isEmpty()){
            lastNameField.setError("Password cannot be empty");
            return false;
        }

        firstNameField.setError(null);
        lastNameField.setErrorEnabled(false);
        return true;

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



        emailField = view.findViewById(R.id.emailFieldSignUp);
        firstNameField = view.findViewById(R.id.firstNameFieldSignUp);
        lastNameField = view.findViewById(R.id.lastNameFieldSignUp);
        passwordField = view.findViewById(R.id.passwordFieldLogin);
        signUp = view.findViewById(R.id.signUpBtn);
        progressBar = view.findViewById(R.id.progressBarSignUp);
        progressBar.setVisibility(View.INVISIBLE);



    }
}
