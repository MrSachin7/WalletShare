package com.sachin_himal.walletshare.ui.expenditure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepository;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepositoryImpl;

import java.util.List;

public class ExpenditureViewModel extends ViewModel {

    private ExpenditureRepository repository;
    private MutableLiveData<Boolean> isDone;


    public ExpenditureViewModel() {
        repository = ExpenditureRepositoryImpl.getInstance();
        isDone = new MutableLiveData<>();
    }


    public List<String> getAllCategories() {

        return repository.getAllCategories();
    }

    public List<String> getAllPaymentCategories() {

        return repository.getAllPaymentCategories();
    }

    public void addExpenditure(Expenditure expenditure) {
        repository.saveExpenditure(expenditure ,()->{
            isDone.setValue(true);
        } );
    }



    public LiveData<Boolean> isDone(){
        return isDone;
    }


    public void searchAllExpenditures() {
        repository.searchAllExpenditures();
    }



    public LiveData<List<Expenditure>> getAllExpenditures() {
        return repository.getAllExpenditures();
    }


    public LiveData<String> getError() {
        return repository.getError();
    }

    public List<Expenditure> getExpenditureLastWeek() {
        return repository.getExpenditureLastWeek();
    }

    public List<Expenditure> getExpenditureLastMonth() {
        return repository.getExpenditureLastMonth();
    }

    public List<Expenditure> getExpenditureLastThreeMonths(){
        return repository.getExpenditureLastThreeMonths();
    }

    public List<Expenditure> getExpenditureLastSixMonths(){
        return repository.getExpenditureLastSixMonths();
    }

    public List<Expenditure> getExpenditureLastOneYear(){
        return repository.getExpenditureLastOneYear();
    }
}
