package com.sachin_himal.walletshare.repository.home;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepository;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepositoryImpl;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HomeRepositoryImpl implements HomeRepository{


    private static HomeRepository instance;
    private static Lock lock = new ReentrantLock();
    private DatabaseReference dbReference;
    private FirebaseDatabase firebaseDatabase;



    private HomeRepositoryImpl(){
    }



    public static HomeRepository getInstance() {
        if (instance ==null){
            synchronized (lock){
                if (instance ==null){
                    instance = new HomeRepositoryImpl();
                }
            }
        }
        return instance;
    }

}
