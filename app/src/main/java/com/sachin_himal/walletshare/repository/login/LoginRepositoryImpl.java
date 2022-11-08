package com.sachin_himal.walletshare.repository.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.entity.UserLiveData;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LoginRepositoryImpl implements LoginRepository {


    private final UserLiveData currentUser;
    private static LoginRepository instance;
    private static Lock lock = new ReentrantLock();
    MutableLiveData<String> error;

    private FirebaseAuth mUser;
    private FirebaseDatabase database;


    private LoginRepositoryImpl() {
        mUser = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = new UserLiveData();
        error = new MutableLiveData<>();

        // More later
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new LoginRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void login(String email, String password) {
         mUser.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
             if (!task.isSuccessful()){
                 error.setValue(task.getException().getMessage());
             }
         });

    }

    @Override
    public LiveData<String> getError() {
        return error;
    }


    @Override
    public void addUser(String email, String password) {

        Task<AuthResult> resultTask = mUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task) -> {

                    if (task.isSuccessful()) {
                        User user = new User(email, password);
                        database.getReference("Users").child(mUser.getCurrentUser().getUid()).setValue(user)
                                .addOnCompleteListener((task1 -> {
                                    if (task.isSuccessful()) {

                                    } else {
                                        // If user did not register successfully
                                    }
                                }));
                    }

                }
        );

    }

    @Override
    public LiveData<FirebaseUser> getCurrentUser() {
        return currentUser;
    }

    @Override
    public void signOut() {
        FirebaseAuth.getInstance().signOut();

    }



}
