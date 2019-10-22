package com.atmecs.digiwallet.Utilities;


import android.content.Context;
import android.content.SharedPreferences;

import com.atmecs.digiwallet.Api.DigiWalletAPIManager;
import com.atmecs.digiwallet.R;

public class Preferences {

    private SharedPreferences sharedPreferences;
    private Context context;
    public static final String KEY_USER_ID = "userId";
    public static final String LOGGED_IN = "LoggedIn";
    public SharedPreferences.Editor editor;
    public static String userDetails;

    public Preferences(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.login_preferences), Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void writeLoginStatus(String userIdString, boolean flag) {

        editor = sharedPreferences.edit();
        editor.putBoolean(LOGGED_IN, flag);
        editor.putString(KEY_USER_ID, userIdString);
        //System.out.println("UserData in Shared preferences: "+userDataString);
        editor.commit();
    }

    public boolean readLoginStatus() {

        boolean status = sharedPreferences.getBoolean(LOGGED_IN, false);
        return status;
    }

    public void OnLogout() {
        editor.clear();
        editor.commit();
    }

    public String userSessionDetails() {

        userDetails = sharedPreferences.getString(KEY_USER_ID, null);
        System.out.println("UserData in Shared preferences userSessionDetails method: "+userDetails);
        return userDetails;
    }
}
