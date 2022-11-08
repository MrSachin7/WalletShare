package com.sachin_himal.walletshare.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sachin_himal.walletshare.MainActivity;
import com.sachin_himal.walletshare.R;

public class LoginTabFragment extends Fragment
{

    TextInputLayout emailField, passwordField;
    AppCompatButton login;
    TextView forgetPassword;
    FloatingActionButton facebook, google, twitter;

    ProgressBar progressBar;

    private LoginViewModel viewModel;

    public LoginTabFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);
        initializeAllFields(view);

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        login.setOnClickListener(this::loginBtnPressed);
        viewModel.getError().observe(getViewLifecycleOwner(), this::errorOnLogin);

        return view;

    }

    private void errorOnLogin(String s) {
        progressBar.setVisibility(View.INVISIBLE);

        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void loginBtnPressed(View view) {
        boolean isValidEmail = validateEmail();
        boolean isValidPassword = validatePassword();
        boolean isEverythingValid = isValidEmail && isValidPassword;
        String email = emailField.getEditText().getText().toString().trim();
        String password = passwordField.getEditText().getText().toString().trim();


        if (isEverythingValid){
            progressBar.setVisibility(View.VISIBLE);
            viewModel.login(email, password);

        }


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

        emailField = view.findViewById(R.id.emailFieldLogin);
        passwordField = view.findViewById(R.id.passwordFieldLogin);
        login = view.findViewById(R.id.loginBtn);
        forgetPassword = view.findViewById(R.id.forget_pass);
        facebook = view.findViewById(R.id.facebook_btn);
        google = view.findViewById(R.id.google_btn);
        twitter = view.findViewById(R.id.twitter_btn);
        progressBar = view.findViewById(R.id.progress_bar_login);

        progressBar.setVisibility(View.INVISIBLE);

    }
}
