package com.sachin_himal.walletshare.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepository;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepositoryImpl;
import com.sachin_himal.walletshare.repository.login.UserRepository;
import com.sachin_himal.walletshare.repository.login.UserRepositoryImpl;

public class MainActivityViewModal extends ViewModel {

    private final UserRepository userRepository;
    private final ExpenditureRepository expenditureRepository;


    public MainActivityViewModal(){
        userRepository = UserRepositoryImpl.getInstance();
        expenditureRepository = ExpenditureRepositoryImpl.getInstance();
    }


    public LiveData<FirebaseUser> getCurrentUser(){
        return userRepository.getCurrentUser();
    }

    public void signOut() {
        userRepository.signOut();
    }


    public void init() {
        String userId = userRepository.getCurrentUser().getValue().getUid();

        expenditureRepository.init(userId);
    }
}
