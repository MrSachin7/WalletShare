package com.sachin_himal.walletshare.ui.expenditure;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.entity.Expenditure;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepository;
import com.sachin_himal.walletshare.repository.expenditure.ExpenditureRepositoryImpl;

import java.util.List;

public class ExpenditureViewModal extends ViewModel {

    private ExpenditureRepository repository;
    private MutableLiveData<Boolean> isDone;


    public ExpenditureViewModal() {
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

    public LiveData<Expenditure> getExpenditure(){

        return repository.getExpenditure();
    }

    public LiveData<Boolean> isDone(){
        return isDone;
    }





}
