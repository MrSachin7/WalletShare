package com.sachin_himal.walletshare.repository.addExpenditure;



import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.entity.ExpenditureLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpenditureRepositoryImpl implements ExpenditureRepository   {

    private static ExpenditureRepository instance;
    private static Lock lock = new ReentrantLock();
    private DatabaseReference dbReference;
    private FirebaseDatabase firebaseDatabase;
    private ExpenditureLiveData expenditure;

    private List<String> allCategories;
    private List<String> allPaymentTypes;



    private ExpenditureRepositoryImpl(){
        allCategories = new ArrayList<>();
        allPaymentTypes = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();

        mockCategories();
        mockPaymentTypes();

        // More later
    }

    private void mockPaymentTypes() {

        // Todo if i can mock these instead of using db
        allPaymentTypes.add("Cash");
        allPaymentTypes.add("Debit Card");
        allPaymentTypes.add("Credit Card");
        allPaymentTypes.add("Bank Transfer");
        allPaymentTypes.add("Voucher");
        allPaymentTypes.add("Mobile Payment");
        allPaymentTypes.add("Web Payment");
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

    @Override
    public void init(String userId) {
        dbReference = FirebaseDatabase.getInstance("https://walletshare-92139-default-rtdb.firebaseio.com/").getReference().child("users").child(userId);
        expenditure = new ExpenditureLiveData(dbReference);



    }

    @Override
    public void saveExpenditure(Expenditure expenditure, CallBack callBack) {

        // Todo the db is not working ... ask Kasper
        dbReference.setValue(expenditure).addOnCompleteListener(task -> {
            if (!task.isSuccessful()){
                Log.d("Test", " It comes to save expense from repo");
                Log.d("Exception", task.getException().getMessage());
            }
        });
    }

    @Override
    public ExpenditureLiveData getExpenditure() {
        return expenditure;
    }

    @Override
    public List<String> getAllPaymentCategories() {
        return allPaymentTypes;
    }
}
