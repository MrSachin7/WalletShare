package com.sachin_himal.walletshare.view.login;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.repository.login.LoginRepository;
import com.sachin_himal.walletshare.repository.login.LoginRepositoryImpl;

public class LoginViewModel extends ViewModel {


    private LoginRepository loginRepository;

    public LoginViewModel(){
        loginRepository = LoginRepositoryImpl.getInstance();
    }


    public void signUp(String email, String password) {
        loginRepository.addUser(email, password);
    }
}
