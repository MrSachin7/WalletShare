package com.sachin_himal.walletshare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.sachin_himal.walletshare.view.Fragments.HomeFragment;
import com.sachin_himal.walletshare.view.Fragments.NewTranscationFragment;
import com.sachin_himal.walletshare.view.Fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private MeowBottomNavigation meowBottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing menu
        meoItems();

        //Show first menu
        showFirstFragment();

        //Action for click on Nav bar
        meowMenuSelection();

    }








    // Initializing Meow Menu bar
    private void meoItems() {
        meowBottomNavigation =(MeowBottomNavigation) findViewById(R.id.meowBottomNavigation);
        meowBottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.home_button));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.add_circle));
        meowBottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.profile_vector));

    }



    //Observing for mouse click
    private void meowMenuSelection() {
        meowBottomNavigation.setOnClickMenuListener(model -> {
            // YOUR CODES

            switch (model.getId()){
                case 1: replace(new HomeFragment());
                    break;
                case 2: replace(new NewTranscationFragment());
                    break;
                case 3: replace(new ProfileFragment());
                break;


            }
            return null;
        });

    }

    // to show the first fragment while logged in
    private void showFirstFragment() {
        replace(new HomeFragment());
        meowBottomNavigation.show(1,false);

    }

    //To change the fragment in main activity fragment container
    private void replace(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}