package com.atmecs.digiwallet.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import com.atmecs.digiwallet.Api.DigiWalletAPIManager;
import com.atmecs.digiwallet.Api.LoginData;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Utils;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

public class ForgotPasswordFragment extends BaseFragment {
    public EditText phoneNumberEditText, forgotPasswordEmailIdEditText;
    public AwesomeValidation awesomeValidation;
    public Button sendPasswordButton;
    public Toolbar toolbar;
    public static View rootView;
    public static String emailAddressString;
    public static LoginData loginData = new LoginData();
    public DigiWalletAPIManager apiManager;
    public Utils utils;

    public ForgotPasswordFragment() {

        apiManager = new DigiWalletAPIManager(this);
    }

    @Override
    public View fragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        utils = new Utils();
        rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        toolbar = rootView.findViewById(R.id.forgot_toolbar);
        toolbar.setTitle(Utils.getStringValue(R.string.forgot_password_title, getContext()));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        phoneNumberEditText = rootView.findViewById(R.id.forgot_password_phone_number);

        sendPasswordButton = rootView.findViewById(R.id.button_send_password);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        awesomeValidation.addValidation(getActivity(), R.id.forgot_password_phone_number, "^[0-9]{10}", R.string.forgot_password__email_validation_message);
        sendPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    forgotPasswordEmailIdEditText = rootView.findViewById(R.id.forgot_password_phone_number);
                    phoneNumberEditText = rootView.findViewById(R.id.forgot_password_phone_number);
                    loginData.setForgotPasswordPhoneNumber(phoneNumberEditText.getText().toString());
                    utils.progressBar(getContext(), Utils.getStringValue(R.string.progress_bar_sending_password, getContext()));
                    apiManager.forgotPasswordAPICall(getContext(), loginData);
                    sendPasswordButton.setEnabled(false);
                    //showConfirmationMessage();
                }
            }
        });
    }

    @Override
    public void onResponseInterface(JSONObject responseData) {

        String userResponseMessage = apiManager.getResponseMessage(responseData);

        if (userResponseMessage.equals("Success")) {

            utils.getForgotPasswordDialog(getContext(), getActivity().getSupportFragmentManager(), new LoginFragment(),
                    R.id.main_content_frame, "OK", "Successfully sent a password to your registered email address");
        } else if (userResponseMessage.equals("user not found")) {
            utils.getCheckAlertDialog(getContext(), Utils.getStringValue(R.string.ok_button, getContext()),
                    Utils.getStringValue(R.string.user_not_found, getContext()));
            sendPasswordButton.setEnabled(true);
        }
    }
}