package com.sachin_himal.walletshare.repository.addExpenditure;

import com.sachin_himal.walletshare.repository.login.LoginRepository;
import com.sachin_himal.walletshare.repository.login.LoginRepositoryImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpenditureRepositoryImpl implements ExpenditureRepository   {

    private static ExpenditureRepository instance;
    private static Lock lock = new ReentrantLock();



    private ExpenditureRepositoryImpl(){

        // More later
    }

    public static ExpenditureRepository getInstance() {
        if (instance ==null){
            synchronized (lock){
                if (instance ==null){
                    instance = new ExpenditureRepositoryImpl();
                }
            }
        }
        return instance;
    }
}
