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

    ExpenditureLiveData getExpenditure();

    List<String> getAllPaymentCategories();

    void searchThreeLatestExpenditure();

    void searchCurrentBalance();



    LiveData<List<Expenditure>> getThreeLatestExpenditures();


    LiveData<Balance> getCurrentBalance();


}
