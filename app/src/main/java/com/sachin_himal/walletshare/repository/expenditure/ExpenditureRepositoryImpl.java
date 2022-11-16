package com.sachin_himal.walletshare.repository.expenditure;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sachin_himal.walletshare.entity.Balance;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.entity.ExpenditureLiveData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpenditureRepositoryImpl implements ExpenditureRepository {

    private static ExpenditureRepository instance;
    private static Lock lock = new ReentrantLock();
    private DatabaseReference dbReference;
    private FirebaseDatabase firebaseDatabase;
    private ExpenditureLiveData expenditure;

    private List<String> allCategories;
    private List<String> allPaymentTypes;

    private MutableLiveData<List<Expenditure>> lastThreeExpenses;
    private MutableLiveData<Balance> currentBalance;

    private final String EXPENSES = "Expenses";
    private final String BALANCE = "Balance";
    private final String DB_ADDRESS = "https://walletshare-92139-default-rtdb.firebaseio.com/";


    private ExpenditureRepositoryImpl() {
        allCategories = new ArrayList<>();
        allPaymentTypes = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance();
        lastThreeExpenses = new MutableLiveData<>();
        currentBalance = new MutableLiveData<>();

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
        dbReference = FirebaseDatabase.getInstance(DB_ADDRESS).getReference().child("users").child(userId);
        expenditure = new ExpenditureLiveData(dbReference);

    }

    @Override
    public void saveExpenditure(Expenditure expenditure, CallBack callBack) {


        Log.d("Expenditure", expenditure.getPayee());
        // Todo the db is not working ... ask Kasper
        dbReference.child(EXPENSES).push().setValue(expenditure).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateBalance(expenditure.getAmount());
                callBack.callBack();
            }
        });

    }

    private void updateBalance(double amount) {
        if (dbReference ==null) return;

        dbReference.child(BALANCE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {


                if (task.isSuccessful()) {
                    Balance balanceFromServer = task.getResult().getValue(Balance.class);
                    if (balanceFromServer == null) {
                        dbReference.child(BALANCE).setValue(new Balance(0.0));
                        updateBalance(amount);
                    } else {
                        balanceFromServer.setBalance(balanceFromServer.getBalance() + amount);
                        dbReference.child(BALANCE).setValue(balanceFromServer);
                        currentBalance.setValue(balanceFromServer);

                    }
                }

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

    @Override
    public void searchThreeLatestExpenditure() {

        if (dbReference ==null) return;

//        dbReference.child(EXPENSES).limitToLast(3).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()) {
//                    HashMap<String, List<Expenditure>> listFromServer =(HashMap<String, List<Expenditure>>) task.getResult().getValue();
//
//                    Object[] objects = listFromServer.values().toArray();
//                    List<Expenditure> temp = new ArrayList<>();
//                    for (Object item :
//                            objects) {
//                        if (item instanceof Expenditure) {
//                            temp.add((Expenditure) item);
//                        }
//
//                    }
//                    lastThreeExpenses.setValue(temp);
//
//                }
//            }
//        });

        List<Expenditure> temp = mockExpenditures();

        lastThreeExpenses.setValue(temp);


    }

    @Override
    public void searchCurrentBalance() {

        if (dbReference ==null) return;

        dbReference.child(BALANCE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    Balance value = task.getResult().getValue(Balance.class);
                    currentBalance.setValue(value);
                }
            }
        });
    }

    @NonNull
    private List<Expenditure> mockExpenditures() {
        List<Expenditure> temp = new ArrayList<>();
        temp.add(new Expenditure(110,"2022-11-05" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-09-21" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-09-21" ,"12:11:11","Food", "test", "test", "test", "test" ));
        return temp;
    }

    @Override
    public LiveData<List<Expenditure>> getThreeLatestExpenditures() {
        return lastThreeExpenses;
    }

    @Override
    public LiveData<Balance> getCurrentBalance() {
        return currentBalance;
    }
}
