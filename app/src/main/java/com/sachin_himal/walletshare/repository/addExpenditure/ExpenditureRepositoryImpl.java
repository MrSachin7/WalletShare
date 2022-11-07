package com.sachin_himal.walletshare.repository.addExpenditure;

import com.sachin_himal.walletshare.repository.login.LoginRepository;
import com.sachin_himal.walletshare.repository.login.LoginRepositoryImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpenditureRepositoryImpl implements ExpenditureRepository   {

    private static ExpenditureRepository instance;
    private static Lock lock = new ReentrantLock();

    private List<String> allCategories;



    private ExpenditureRepositoryImpl(){
        allCategories = new ArrayList<>();

        mockCategories();

        // More later
    }

    private void mockCategories() {
        allCategories.add("Food & Drinks");
        allCategories.add("Transport");
        allCategories.add("Shopping");
        allCategories.add("Life & Entertainment");
        allCategories.add("Investments");
        allCategories.add("Wage");
        allCategories.add("Salary");
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

    @Override
    public List<String> getAllCategories() {

        return allCategories;

    }
}
