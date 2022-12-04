package com.sachin_himal.walletshare.repository.login;

import static com.sachin_himal.walletshare.repository.Database.DB_ADDRESS;
import static com.sachin_himal.walletshare.repository.Database.USERS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sachin_himal.walletshare.entity.User;
import com.sachin_himal.walletshare.entity.UserLiveData;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserRepositoryImpl implements UserRepository {


    private final UserLiveData currentUser;
    private static UserRepository instance;
    private static Lock lock = new ReentrantLock();

    private MutableLiveData<String> loginError, signUpError, successMessage, errorMessage;
    private MutableLiveData<User> loggedInUser;
    private MutableLiveData<Uri> profileImage;
    private FirebaseAuth mUser;
    private StorageReference mStorageRef;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    private UserRepositoryImpl() {
        mUser = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance(DB_ADDRESS);
        databaseReference = database.getReference().child(USERS);
        currentUser = new UserLiveData();
        loggedInUser = new MutableLiveData<>();
        profileImage = new MutableLiveData<>();

        mStorageRef = FirebaseStorage.getInstance().getReference();

        loginError = new MutableLiveData<>();
        signUpError = new MutableLiveData<>();
        successMessage = new MutableLiveData<>();
        errorMessage = new MutableLiveData<>();

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
                        if (task.getResult().getAdditionalUserInfo().isNewUser()){
                            FirebaseUser user = getCurrentUser().getValue();
                            String displayName = user.getDisplayName();
                            String[] split = displayName.split("\\s+");

                            String firstName = split[0];
                            String lastName = split[1];
                            String email = user.getEmail();

                            User userToAdd = new User(email, firstName, lastName);
                            addUserToDatabase(userToAdd);
                        }

                    }
                });
    }

    @Override
    public void loginWithFacebook() {


    }

    @Override
    public LiveData<User> getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public void searchForCurrentUser() {
        String uid = getCurrentUser().getValue().getUid();
        databaseReference.child(uid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User user = task.getResult().getValue(User.class);
                loggedInUser.setValue(user);
            }
        });
    }

    @Override
    public void updateProfile(String firstNameText, String lastNameText) {

        successMessage.setValue(null
        );
        errorMessage.setValue(null);
        FirebaseUser user = getCurrentUser().getValue();
        String uid = user.getUid();


        databaseReference.child(uid).child("firstName").setValue(firstNameText).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                databaseReference.child(uid).child("lastName").setValue(lastNameText).addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        successMessage.setValue("Profile updated successfully");
                    } else {
                        errorMessage.setValue(task1.getException().getMessage());
                    }
                });
            } else {
                errorMessage.setValue(task.getException().getMessage());
            }
        });


    }

    @Override
    public LiveData<String> getSuccessMessage() {
        return successMessage;
    }

    @Override
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void updateProfileImage(Uri uri) {
        FirebaseUser user = getCurrentUser().getValue();
        String uid = user.getUid();
        StorageReference storageReference = mStorageRef.child("profile_images").child(uid);
        storageReference.putFile(uri).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        databaseReference.child(uid).child("profileImage").setValue(task1.getResult().toString()).addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                successMessage.setValue("Profile image updated successfully");
                            } else {
                                errorMessage.setValue(task2.getException().getMessage());
                            }
                        });
                    } else {
                        errorMessage.setValue(task1.getException().getMessage());
                    }
                });
            } else {
                errorMessage.setValue(task.getException().getMessage());
            }
        });
//        databaseReference.child(uid).child("profileImage").setValue(uri.toString()).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                successMessage.setValue("Profile image updated successfully");
//            } else {
//                errorMessage.setValue(task.getException().getMessage());
//            }
//        });

    }

    @Override
    public LiveData<Uri> getProfileImage() {
        return profileImage;
    }

    @Override
    public void searchForProfileImage() {
        FirebaseUser user = getCurrentUser().getValue();
        String uid = user.getUid();
        StorageReference storageReference = mStorageRef.child("profile_images").child(uid);
        try {
            File localFile = File.createTempFile("images", "jpg");
            storageReference.getFile(localFile).addOnCompleteListener(new OnCompleteListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<FileDownloadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Uri uri = Uri.fromFile(localFile);
                        profileImage.setValue(uri);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void addUser(String email, String password, String firstName, String lastName) {
        mUser.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                User user = new User(email, password, firstName, lastName);
                addUserToDatabase(user);
            } else {
                signUpError.setValue(task.getException().getMessage());
            }
        });

    }

    private void addUserToDatabase(User user) {
        databaseReference.child(getCurrentUser().getValue().getUid()).setValue(user);
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
