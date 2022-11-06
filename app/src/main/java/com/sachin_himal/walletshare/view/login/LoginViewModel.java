package com.sachin_himal.walletshare.view.login;

import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.repository.login.LoginRepository;
import com.sachin_himal.walletshare.repository.login.LoginRepositoryImpl;

public class LoginViewModel extends ViewModel {


    private LoginRepository repository;

    public LoginViewModel(){
        repository = LoginRepositoryImpl.getInstance();
    }


    public void signUp(String email, String password) {
        repository.addUser(email, password);
    }
}
