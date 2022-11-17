package com.sachin_himal.walletshare.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.sachin_himal.walletshare.R;

import java.util.Arrays;

public class LoginTabFragment extends Fragment {

    TextInputLayout emailField, passwordField;
    AppCompatButton login;
    TextView forgetPassword;
    FloatingActionButton facebook, google, twitter;

    ProgressBar progressBar;

    private UserViewModal viewModel;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions gso;
    private CallbackManager callbackManager;


    public LoginTabFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);
        initializeAllFields(view);


        viewModel = new ViewModelProvider(this).get(UserViewModal.class);
        login.setOnClickListener(this::loginBtnPressed);
        viewModel.getLoginError().observe(getViewLifecycleOwner(), this::errorOnLogin);


        createGoogleRequest();
        google.setOnClickListener(this::loginViaGoogle);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(),"Logged in with facebook", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Login cancelled", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onError(@NonNull FacebookException e) {

                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        facebook.setOnClickListener(this::loginWithFacebook);

        return view;

    }

    private void loginWithFacebook(View view) {
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends") );

        //viewModel.loginWithFacebook();
    }


    private void createGoogleRequest() {

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)).requestEmail()
                .build();
                googleSignInClient = GoogleSignIn.getClient(getContext(), gso);


    }


    private void loginViaGoogle(View view) {
        progressBar.setVisibility(View.VISIBLE);
        Intent intent = new Intent(googleSignInClient.getSignInIntent());
        startActivityForResult(intent, 1234);

    }


    // TODO replace with activity result launcher...
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==1234){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                viewModel.signInWithGoogle(credential);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }
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

        emailField = view.findViewById(R.id.emailFieldSignUp);
        passwordField = view.findViewById(R.id.passwordFieldCreate);
        login = view.findViewById(R.id.loginBtn);
        forgetPassword = view.findViewById(R.id.forget_pass);
        facebook = view.findViewById(R.id.facebook_btn);
        google = view.findViewById(R.id.google_btn);
        twitter = view.findViewById(R.id.twitter_btn);
        progressBar = view.findViewById(R.id.progress_bar_login);

        progressBar.setVisibility(View.INVISIBLE);

    }
}
