package com.atmecs.digiwallet.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.atmecs.digiwallet.BuildConfig;
import com.atmecs.digiwallet.Fragments.CreateWalletFragment;
import com.atmecs.digiwallet.Fragments.LoginFragment;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Preferences;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    //private Preferences preferences;

    boolean session;
    public LoginFragment loginFragment = new LoginFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        preferences = new Preferences(getApplicationContext());
        loadLoginFragment();
    }

    /**
     * to load the LoginFragment from MainActivity.
     */
    public void loadLoginFragment() {
        Fragment fragment = null;
        fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, fragment);
        fragmentTransaction.commit();
    }
}
