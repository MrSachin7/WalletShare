package com.sachin_himal.walletshare.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sachin_himal.walletshare.R;

public class LoginTabFragment extends Fragment
{

    EditText email, password;
    Button login;
    TextView forgetPassword;
    float alpha;

    public LoginTabFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);
        initializeAllFields(view);
//        showAnimation();

        return view;

    }


    private void showAnimation() {
        email.setTranslationY(800);
        password.setTranslationY(800);
        forgetPassword.setTranslationY(800);
        login.setTranslationY(800);

        email.setAlpha(alpha);
        password.setAlpha(alpha);
        forgetPassword.setAlpha(alpha);
        login.setAlpha(alpha);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        forgetPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

    }

    private void initializeAllFields(View view) {
        email = view.findViewById(R.id.emailField);
        password = view.findViewById(R.id.password_Field);
        login = view.findViewById(R.id.loginBtn);
        forgetPassword = view.findViewById(R.id.forget_pass);
    }
}
