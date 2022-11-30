package com.sachin_himal.walletshare.repository.expenditure;


import static com.sachin_himal.walletshare.repository.Database.BALANCE;
import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;
import static com.sachin_himal.walletshare.repository.Database.EXPENSES;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sachin_himal.walletshare.entity.Balance;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.entity.ExpenditureLiveData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExpenditureRepositoryImpl implements ExpenditureRepository {

    private static ExpenditureRepository instance;
    private static Lock lock = new ReentrantLock();
    private DatabaseReference dbReference;
    private FirebaseDatabase firebaseDatabase;

    private List<String> allCategories;
    private List<String> allPaymentTypes;

    private MutableLiveData<List<Expenditure>> lastThreeExpenses;
    private MutableLiveData<List<Expenditure>> allExpenditures;
    private MutableLiveData<List<Expenditure>> expendituresLastMonth;


    private MutableLiveData<Balance> currentBalance;
    private MutableLiveData<String> error;


    private ExpenditureRepositoryImpl() {
        allCategories = new ArrayList<>();
        allPaymentTypes = new ArrayList<>();

        firebaseDatabase = FirebaseDatabase.getInstance(DB_ADDRESS);
        lastThreeExpenses = new MutableLiveData<>();
        allExpenditures = new MutableLiveData<>();
        allExpenditures.postValue(new ArrayList<>());
        expendituresLastMonth = new MutableLiveData<>();
        currentBalance = new MutableLiveData<>();
        error = new MutableLiveData<>();

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
        allCategories.add("Rent");
        allCategories.add("Transport");
        allCategories.add("Shopping");
        allCategories.add("Life & Entertainment");
        allCategories.add("Investments");
        allCategories.add("Wage");
        allCategories.add("Salary");
        allCategories.add("SU");
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
        dbReference = firebaseDatabase.getReference().child(EXPENSES).child(userId);
        searchAllExpenditures();
        searchCurrentBalance();


    }

    @Override
    public void saveExpenditure(Expenditure expenditure, CallBack callBack) {
        dbReference.child(EXPENSES).push().setValue(expenditure).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateBalance(expenditure.getAmount());
                callBack.callBack();
            }
        });
    }



    // tyo ta friends ko page bata garne ho
    // get all friends and put them on a deopdown list instead of ty

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
    public List<String> getAllPaymentCategories() {
        return allPaymentTypes;
    }

    @Override
    public void searchThreeLatestExpenditure() {
        List<Expenditure> value = allExpenditures.getValue();
        if (value == null) return;
        List<Expenditure> temp = new ArrayList<>();
        for (int i = 0; i < 3 && i < value.size(); i++) {
            if (value.get(i) == null) break;
            temp.add(value.get(i));

        }
        lastThreeExpenses.setValue(temp);

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


    }

    @Override
    public void searchExpenditureLastMonth() {

        List<Expenditure> temp = new ArrayList<>();
        temp.addAll(allExpenditures.getValue());
        temp.removeIf(expenditure -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
            LocalDateTime monthAgo = now.minusMonths(1);
            return timeOfExpenditure.isBefore(monthAgo);
//
        });
        expendituresLastMonth.setValue(temp);

    }

    @Override
    public LiveData<List<Expenditure>> getLastMonthExpenseObserver() {
        return expendituresLastMonth;
    }

    @Override
    public void searchCurrentBalance() {

        //        if (dbReference ==null) return;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        executorService.execute(() -> {
            dbReference.child(BALANCE).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        Balance value = task.getResult().getValue(Balance.class);
                        mainThreadHandler.post(() -> {
                            currentBalance.setValue(value);
                        });
                    }
                }
            });
        });

    }


    @Override
    public List<Expenditure> getExpenditureLastWeek() {

        List<Expenditure> temp = new ArrayList<>();

        temp.addAll(allExpenditures.getValue());
        temp.removeIf(expenditure -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
            LocalDateTime monthAgo = now.minusWeeks(1);
            return timeOfExpenditure.isBefore(monthAgo);
        });
        return temp;

    }

    @Override
    public List<Expenditure> getExpenditureLastMonth() {
        List<Expenditure> temp = new ArrayList<>();

        temp.addAll(allExpenditures.getValue());
        temp.removeIf(expenditure -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
            LocalDateTime monthAgo = now.minusMonths(1);
            return timeOfExpenditure.isBefore(monthAgo);
        });
        return temp;
    }

    @Override
    public List<Expenditure> getExpenditureLastThreeMonths() {
        List<Expenditure> temp = new ArrayList<>();
        temp.addAll(allExpenditures.getValue());
        temp.removeIf(expenditure -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
            LocalDateTime monthAgo = now.minusMonths(3);
            return timeOfExpenditure.isBefore(monthAgo);
        });
        return temp;
    }

    @Override
    public List<Expenditure> getExpenditureLastSixMonths() {
        List<Expenditure> temp = new ArrayList<>();
        temp.addAll(allExpenditures.getValue());
        temp.removeIf(expenditure -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
            LocalDateTime monthAgo = now.minusMonths(6);
            return timeOfExpenditure.isBefore(monthAgo);
        });
        return temp;
    }

    @Override
    public List<Expenditure> getExpenditureLastOneYear() {
        List<Expenditure> temp = new ArrayList<>();
        temp.addAll(allExpenditures.getValue());
        temp.removeIf(expenditure -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime timeOfExpenditure = expenditure.retrieveAsLocalDateTime();
            LocalDateTime monthAgo = now.minusYears(1);
            return timeOfExpenditure.isBefore(monthAgo);
        });
        return temp;
    }

    @NonNull
    private List<Expenditure> mockAllExpenditures() {
        List<Expenditure> temp = new ArrayList<>();
        temp.add(new Expenditure(110,"2022-11-04" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-04" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-04" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-05" ,"12:11:11",allCategories.get(1), "Income", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-06" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-07" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-08" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-08" ,"12:11:11",allCategories.get(1), "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-08" ,"12:11:11","Food", "Income", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-09" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-09" ,"12:11:11","Food", "Income", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-10" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-10" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-10" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-10" ,"12:11:11","Food", "Income", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-10" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-10" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-11" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-12" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-14" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-14" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-14" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-15" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-15" ,"12:11:11","Income", "Income", "test", "test", "Income" ));
        temp.add(new Expenditure(110,"2022-11-15" ,"12:11:11","Food", "test", "test", "test", "test" ));


        temp.add(new Expenditure(110,"2022-11-16" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-16" ,"12:11:11","Food", "test", "test", "test", "test" ));
        temp.add(new Expenditure(110,"2022-11-15" ,"12:11:11","Food", "test", "test", "test", "test" ));
        return temp;
    }


    @Override
    public LiveData<List<Expenditure>> getThreeLatestExpenditures() {
        return lastThreeExpenses;
    }

    @Override
    public LiveData<List<Expenditure>> getAllExpenditures() {
        return allExpenditures;
    }

    @Override
    public LiveData<Balance> getCurrentBalance() {
        return currentBalance;
    }

    @Override
    public void searchAllExpenditures() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

        executorService.execute(() -> {
            dbReference.child(EXPENSES).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Iterable<DataSnapshot> children = snapshot.getChildren();
                    List<Expenditure> expenditures = new ArrayList<>();
                    for (DataSnapshot snapshot1 : children) {

                        Expenditure value = snapshot1.getValue(Expenditure.class);
                        expenditures.add(value);
                    }
                    Collections.sort(expenditures);
                    mainThreadHandler.post(() -> {
                        allExpenditures.setValue(expenditures);
                        searchThreeLatestExpenditure();
                        searchExpenditureLastMonth();
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });
    }


    @Override
    public LiveData<String> getError() {
        return error;
    }
}
