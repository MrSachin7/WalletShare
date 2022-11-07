package com.sachin_himal.walletshare.view.addExpenditure;

import androidx.lifecycle.ViewModel;

import com.sachin_himal.walletshare.repository.addExpenditure.ExpenditureRepository;
import com.sachin_himal.walletshare.repository.addExpenditure.ExpenditureRepositoryImpl;

import java.util.List;

public class ExpenditureViewModal extends ViewModel {

    private ExpenditureRepository repository;

    public ExpenditureViewModal() {
        repository = ExpenditureRepositoryImpl.getInstance();
    }


    public List<String> getAllCategories() {

        return repository.getAllCategories();
    }
}
