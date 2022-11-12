package com.sachin_himal.walletshare.view.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.sachin_himal.walletshare.repository.login.UserRepository;
import com.sachin_himal.walletshare.repository.login.UserRepositoryImpl;

public class UserViewModal extends ViewModel {


    private UserRepository repository;


    public UserViewModal(){
        repository = UserRepositoryImpl.getInstance();
    }


    public void signUp(String email, String password) {
        repository.addUser(email, password);
    }

    public LiveData<FirebaseUser> getCurrentUser(){
        return repository.getCurrentUser();
    }

    public void signOut() {

        repository.signOut();
    }

    public void login(String email, String password) {
        repository.login(email, password);
    }

    public LiveData<String> getLoginError(){
        return repository.getLoginError();
    }

    public LiveData<String> getSignUpError(){
        return repository.getSignUpError();
    }

    public void signInWithGoogle(AuthCredential credential) {
        repository.signInWithGoogle(credential);
    }

    public void loginWithFacebook() {

        repository.loginWithFacebook();
    }

}
