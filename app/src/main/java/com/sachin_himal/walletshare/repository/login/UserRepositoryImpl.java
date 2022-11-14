package com.sachin_himal.walletshare.repository.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.entity.UserLiveData;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserRepositoryImpl implements UserRepository {


    private final UserLiveData currentUser;
    private static UserRepository instance;
    private static Lock lock = new ReentrantLock();
    MutableLiveData<String> loginError, signUpError;

    private FirebaseAuth mUser;
    private FirebaseDatabase database;


    private UserRepositoryImpl() {
        mUser = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        currentUser = new UserLiveData();
        loginError = new MutableLiveData<>();
        signUpError = new MutableLiveData<>();

        // More later
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new UserRepositoryImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void login(String email, String password) {
         mUser.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
             if (!task.isSuccessful()){
                 loginError.setValue(task.getException().getMessage());
             }
         });

    }

    @Override
    public LiveData<String> getLoginError() {
        return loginError;
    }

    @Override
    public LiveData<String> getSignUpError() {
        return signUpError;
    }

    @Override
    public void signInWithGoogle(AuthCredential credential) {
        mUser.signInWithCredential(credential)
                .addOnCompleteListener(this::doStuffWithTask);

    }

    @Override
    public void loginWithFacebook() {


    }

    private void doStuffWithTask(Task<AuthResult> task) {
            if (task.isSuccessful()) {

                User user = new User();
                database.getReference("Users").child(mUser.getCurrentUser().getUid()).setValue(user)
                        .addOnCompleteListener((task1 -> {
                            if (task.isSuccessful()) {
                            } else {
                                // If user did not register successfully
                            }
                        }));
            }

            else if (task.isCanceled()){
                signUpError.setValue("Sign in cancelled");
            }
            else{
                signUpError.setValue(task.getException().getMessage());

            }

        }





    @Override
    public void addUser(String email, String password) {

        Task<AuthResult> resultTask = mUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this::doStuffWithTask);

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
