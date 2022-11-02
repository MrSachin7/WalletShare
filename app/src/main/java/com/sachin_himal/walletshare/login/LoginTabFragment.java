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


    public LoginTabFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login_tab_fragment, container, false);
        initializeAllFields(view);

        return view;

    }




    private void initializeAllFields(View view) {
        email = view.findViewById(R.id.emailFieldLogin);
        password = view.findViewById(R.id.passwordFieldLogin);
        login = view.findViewById(R.id.loginBtn);
        forgetPassword = view.findViewById(R.id.forget_pass);
    }
}
