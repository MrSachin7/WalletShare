package com.sachin_himal.walletshare.entity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class ExpenditureLiveData extends LiveData<Expenditure> {

    DatabaseReference databaseReference;


    private final ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            Expenditure expenditure = snapshot.getValue(Expenditure.class);
            setValue(expenditure);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {


        };
    };


    public ExpenditureLiveData(DatabaseReference reference){
        databaseReference = reference;
    }

    @Override
    protected void onActive() {
        super.onActive();
        databaseReference.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        databaseReference.removeEventListener(listener);
    }
}
