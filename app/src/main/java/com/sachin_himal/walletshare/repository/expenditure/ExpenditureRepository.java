package com.sachin_himal.walletshare.repository.expenditure;

import androidx.lifecycle.LiveData;

import com.sachin_himal.walletshare.entity.Balance;
import com.sachin_himal.walletshare.entity.CallBack;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.entity.ExpenditureLiveData;

import java.util.List;

public interface ExpenditureRepository {
    List<String> getAllCategories();

    void init(String userId);

    void saveExpenditure(Expenditure expenditure, CallBack callable);

    List<String> getAllPaymentCategories();

    void searchThreeLatestExpenditure();
    void searchExpenditureLastMonth();

    LiveData<List<Expenditure>> getLastMonthExpenseObserver();

    void searchCurrentBalance();

   List<Expenditure> getExpenditureLastWeek();
    List<Expenditure> getExpenditureLastMonth();
    List<Expenditure> getExpenditureLastThreeMonths();
    List<Expenditure> getExpenditureLastSixMonths();
    List<Expenditure> getExpenditureLastOneYear();



    LiveData<List<Expenditure>> getThreeLatestExpenditures();
    LiveData<List<Expenditure>> getAllExpenditures();


    LiveData<Balance> getCurrentBalance();
    LiveData<String> getError();

    void searchAllExpenditures();


}
