package com.atmecs.digiwallet.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.atmecs.digiwallet.Fragments.HomeFragment;
import com.atmecs.digiwallet.Fragments.LoginFragment;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Constants;
import com.atmecs.digiwallet.Utilities.Preferences;
import com.atmecs.digiwallet.Utilities.Utils;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public Toolbar toolbar;
    private Fragment fragment;
    String versionNameString;
    TextView versionNameTextView;
    public static AppCompatActivity appCompatActivity;
    public Utils utils = new Utils();
    private Preferences preferences;
    public EditText loginPhoneNumberEditText, loginPasswordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

/**
 * to get Version_Name from BuildConfig file.
 */
        versionNameString = Constants.VERSION_NAME;
        try {

            TextView version = findViewById(R.id.nav_version);
            version.setText("Version:" + versionNameString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        NavigationView navigationViewVersion = findViewById(R.id.navigation_view);
        if (navigationViewVersion != null) {

            versionNameTextView = findViewById(R.id.nav_version);
            versionNameTextView.setText("Version: " + versionNameString);
        }

        toolbar = findViewById(R.id.toolbar_home_activity);
        toolbar.setTitle(R.string.home_activity_title);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                closeKeyBoard();
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        /**
         *  to change the profile photo.
         */
        View header = navigationView.getHeaderView(0);

        ImageView imageView = header.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, Utils.getStringValue(R.string.toast_profile_photo_click, HomeActivity.this), Toast.LENGTH_SHORT).show();
            }
        });
        loadHomeFragment();
    }

    protected void closeKeyBoard() throws NullPointerException {

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            IBinder windowToken = currentFocus.getWindowToken();

            int hideType = InputMethodManager.HIDE_NOT_ALWAYS;

            inputManager.hideSoftInputFromWindow(windowToken, hideType);
        }
    }

    public void loadHomeFragment() {
        fragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.home_content_frame, fragment);
        fragmentTransaction.commit();
    }

    /**
     * to quit the entire application when back button press.
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)
            getQuitApplication();
        return false;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void getQuitApplication() {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        finishAffinity();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setMessage(Utils.getStringValue(R.string.alert_dialog_title_exit_text, HomeActivity.this))
                .setPositiveButton(Utils.getStringValue(R.string.positive_button_text, HomeActivity.this), dialogClickListener)
                .setNegativeButton(Utils.getStringValue(R.string.negative_button_text, HomeActivity.this), dialogClickListener).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_my_logout:
                logout();
                return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        utils.getAlertDialog(HomeActivity.this,
                Utils.getStringValue(R.string.positive_button_text, HomeActivity.this),
                Utils.getStringValue(R.string.negative_button_text, HomeActivity.this),
                Utils.getStringValue(R.string.alert_dialog_title_logout_text, HomeActivity.this));
    }
}
