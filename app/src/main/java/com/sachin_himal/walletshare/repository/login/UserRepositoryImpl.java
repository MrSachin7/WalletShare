package com.sachin_himal.walletshare.repository.login;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;
import static com.sachin_himal.walletshare.repository.Database.USERS;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
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
    private DatabaseReference databaseReference;


    private UserRepositoryImpl() {
        mUser = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(DB_ADDRESS);
        databaseReference = database.getReference().child(USERS);
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
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){

                        FirebaseUser user = getCurrentUser().getValue();
                        String displayName = user.getDisplayName();
                        String[] split = displayName.split("\\s+");

                        String firstName = split[0];
                        String lastName = split[1];
                        String email = user.getEmail();

                        User userToAdd = new User(email, firstName, lastName);
                        addUserToDatabase(userToAdd);
                    }
                });

    }

    @Override
    public void loginWithFacebook() {


    }

    @Override
    public void addUser(String email, String password, String firstName, String lastName) {
        mUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()){
                User user = new User(email, password, firstName, lastName);
                addUserToDatabase(user);
            }
        });

    }

    private void addUserToDatabase(User user) {
        databaseReference.child(getCurrentUser().getValue().getUid()).push().setValue(user);
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
