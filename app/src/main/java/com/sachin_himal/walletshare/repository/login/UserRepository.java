package com.sachin_himal.walletshare.repository.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.sachin_himal.walletshare.entity.User;

public interface UserRepository {

    void addUser(String email, String password, String firstName, String lastName);

    LiveData<FirebaseUser> getCurrentUser();

    void signOut();

    void login(String email, String password);

    LiveData<String> getLoginError();
    LiveData<String> getSignUpError();

    void signInWithGoogle(AuthCredential credential);

    void loginWithFacebook();

    LiveData<User> getLoggedInUser();

    void searchForCurrentUser();

    void updateProfile(String firstNameText, String lastNameText);

    LiveData<String> getSuccessMessage();

    LiveData<String> getErrorMessage();

}
