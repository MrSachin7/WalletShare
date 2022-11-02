package com.sachin_himal.walletshare.repository.login;

import androidx.lifecycle.MutableLiveData;

import com.sachin_himal.walletshare.entity.User;

public interface LoginRepository {



    void addUser(String email, String password);

}
