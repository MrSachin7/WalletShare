package com.sachin_himal.walletshare.repository.login;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginRepositoryImpl implements LoginRepository {

    private static LoginRepository instance;
    private static Lock lock = new ReentrantLock();



    private LoginRepositoryImpl(){

        // More later
    }

    public static LoginRepository getInstance() {
        if (instance ==null){
            synchronized (lock){
                if (instance ==null){
                    instance = new LoginRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void addUser(String email, String password) {

    }
}
