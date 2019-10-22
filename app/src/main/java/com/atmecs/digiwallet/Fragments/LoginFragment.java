package com.atmecs.digiwallet.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.atmecs.digiwallet.Activities.HomeActivity;
import com.atmecs.digiwallet.Api.APIResponseInterface;
import com.atmecs.digiwallet.Api.VolleyAPIHandler;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Preferences;
import com.atmecs.digiwallet.Utilities.Utils;
import com.atmecs.digiwallet.Api.DigiWalletAPIManager;
import com.atmecs.digiwallet.Api.LoginData;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends BaseFragment {
    public EditText loginPhoneNumberEditText, loginPasswordEditText;
    public AwesomeValidation awesomeValidation;
    public Button createWalletButton;
    public TextView forgotPasswordTextView;
    public Button openWalletButton;
    public static LoginData loginData = new LoginData();
    public DigiWalletAPIManager apiManager;
    private Preferences preferences;
    public DigiWalletAPIManager digiWalletAPIManager;
    public Activity activity;
    public String userId;
    public Utils utils;

    public LoginFragment() {

        apiManager = new DigiWalletAPIManager(this);
    }

    @Override
    public View fragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        utils = new Utils();
        preferences = new Preferences(getContext());

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        TextView forgotPassword = rootView.findViewById(R.id.link_forgot_password);
        String forgotPasswordLinkText = Utils.getStringValue(R.string.forgot_password_link_text, getContext());
        forgotPassword.setText(Html.fromHtml("<u>" + forgotPasswordLinkText + "</u>"));
        forgotPassword.setClickable(true);
        forgotPassword.setMovementMethod(LinkMovementMethod.getInstance());

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        loginPhoneNumberEditText = rootView.findViewById(R.id.phone_number);
        loginPasswordEditText = rootView.findViewById(R.id.password);

        openWalletButton = rootView.findViewById(R.id.btn_open_wallet);

        if (preferences.readLoginStatus()) {

            loadHomeActivity();

        }

        createWalletButton = rootView.findViewById(R.id.btn_create_wallet);
        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCreateWalletFragment();
            }
        });

        forgotPasswordTextView = rootView.findViewById(R.id.link_forgot_password);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadForgotPasswordFragment();
            }
        });

        return rootView;
    }


    /**
     * validation of phone number & password.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        awesomeValidation.addValidation(getActivity(), R.id.phone_number, "^[0-9]{10}", R.string.login_phonenumber_validation_message);
        awesomeValidation.addValidation(getActivity(), R.id.password, "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})", R.string.login_password_validation_message);
        openWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    loginData.setPhoneNumber(loginPhoneNumberEditText.getText().toString());
                    loginData.setPassword(loginPasswordEditText.getText().toString());

                    apiManager.loginUser(getContext(), loginData);

                    System.out.println("UserId inside login: " + userId);

                    utils.progressBar(getContext(), Utils.getStringValue(R.string.progress_bar_open_wallet, getContext()));
                }
            }
        });
    }

    /**
     * to load the CreateWallet Fragment from Login Fragment.
     */
    public void loadCreateWalletFragment() {
        CreateWalletFragment createWalletFragment = new CreateWalletFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, createWalletFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * to load the ForgotPassword Fragment from Login Fragment.
     */
    public void loadForgotPasswordFragment() {
        ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_content_frame, forgotPasswordFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /**
     * to load the HomeActivity from Login Fragment.
     */
    public void loadHomeActivity() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        utils.progressBar(getContext(), Utils.getStringValue(R.string.progress_bar_open_wallet, getContext()));
        //Toast.makeText(getContext(), "Login Success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponseInterface(JSONObject responseData) {

        String userId = apiManager.getUserId(responseData);
        String userResponseMessage = apiManager.getResponseMessage(responseData);

        if (userId != "null") {
            System.out.println("Login fragment userId: "+userId);
            preferences.writeLoginStatus(userId, true);
            loadHomeActivity();
            loginPhoneNumberEditText.getText().clear();
            loginPasswordEditText.getText().clear();
            Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();

        } else if (userResponseMessage.equals("wrong password")) {
            utils.getCheckAlertDialog(getContext(), Utils.getStringValue(R.string.ok_button, getContext()),
                    Utils.getStringValue(R.string.incorrect_password_title, getContext()));

        } else if (userResponseMessage.equals("user not found")) {
            utils.getCheckAlertDialog(getContext(), Utils.getStringValue(R.string.ok_button, getContext()),
                    Utils.getStringValue(R.string.registered_phonenumber_title, getContext()));
        }
    }
}
