package com.sachin_himal.walletshare.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.Balance;
import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepository;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepositoryImpl;

import java.util.List;

public class HomeViewModal extends ViewModel {

    public ExpenditureRepository repository;


    public HomeViewModal() {
        repository = ExpenditureRepositoryImpl.getInstance();
    }


    public void searchThreeLatestExpenses() {
        repository.searchThreeLatestExpenditure();
    }

    public void searchCurrentBalance() {
        repository.searchCurrentBalance();
    }

    public LiveData<List<Expenditure>> getThreeExpenditure(){
        return repository.getThreeLatestExpenditures();
    }


    public LiveData<Balance> getCurrentBalance(){
        return repository.getCurrentBalance();
    }


    public List<Expenditure> getExpendituresLastMonth() {
        return repository.getExpenditureLastMonth();

    }
}
