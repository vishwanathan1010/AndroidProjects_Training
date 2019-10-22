package com.atmecs.digiwallet.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.Constants;
import com.atmecs.digiwallet.Utilities.Preferences;
import com.atmecs.digiwallet.Utilities.Utils;
import com.atmecs.digiwallet.Api.DigiWalletAPIManager;
import com.atmecs.digiwallet.Api.RegistrationData;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

import org.json.JSONObject;

public class CreateWalletFragment extends BaseFragment {
    public static EditText firstNameEditText, lastNameEditText, emailIdEditText, phoneNumberEditText, passwordEditText, confirmPasswordEditText;
    public CheckBox checkBox;
    public AwesomeValidation awesomeValidation;
    public Toolbar toolbar;
    public Button createWalletButton;
    public static String firstNameString, lastNameString, emailIdString, phoneNumberString, passwordString, confirmPasswordString;
    public static RegistrationData registrationData = new RegistrationData();
    public DigiWalletAPIManager apiManager;
    public Utils utils;

    public CreateWalletFragment() {

        apiManager = new DigiWalletAPIManager(this);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//    }

    @Override
    public View fragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_createwallet, container, false);
        TextView textView = rootView.findViewById(R.id.text_checkbox);
        String agreementCheckBoxString = Constants.AGREEMENT_CHECK_BOX_STRING;

        utils = new Utils();

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(agreementCheckBoxString);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        ClickableSpan termsClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Toast.makeText(getContext(), Constants.TERMS_AND_CONDITIONS_CLICK, Toast.LENGTH_LONG).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(true);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                textPaint.setColor(Color.BLACK);
                textPaint.bgColor = (Color.WHITE);
            }
        };

        ClickableSpan policyClickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), Utils.getStringValue(R.string.toast_privacy_policy_click, getContext()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(true);
                textPaint.setTypeface(Typeface.DEFAULT_BOLD);
                textPaint.setColor(Color.BLACK);
                textPaint.bgColor = (Color.WHITE);
            }
        };
        spannableStringBuilder.setSpan(
                termsClickableSpan, // Span to add
                agreementCheckBoxString.indexOf(Constants.TERMS_AND_CONDITIONS),
                agreementCheckBoxString.indexOf(Constants.TERMS_AND_CONDITIONS) + String.valueOf(Constants.TERMS_AND_CONDITIONS).length(), // End of the span (exclusive)
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE // Do not extend the span when text add later
        );
        spannableStringBuilder.setSpan(
                policyClickableSpan,
                agreementCheckBoxString.indexOf(Utils.getStringValue(R.string.privacy_policy, getContext())),
                agreementCheckBoxString.indexOf((Utils.getStringValue(R.string.privacy_policy, getContext())))
                        + String.valueOf((Utils.getStringValue(R.string.privacy_policy, getContext()))).length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        toolbar = rootView.findViewById(R.id.create_wallet_toolbar);
        toolbar.setTitle(Utils.getStringValue(R.string.create_wallet_title, getContext()));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorBlack));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        firstNameEditText = rootView.findViewById(R.id.text_first_name);
        lastNameEditText = rootView.findViewById(R.id.text_last_name);
        emailIdEditText = rootView.findViewById(R.id.text_email);
        phoneNumberEditText = rootView.findViewById(R.id.text_phone_number);
        passwordEditText = rootView.findViewById(R.id.createwallet_password);
        confirmPasswordEditText = rootView.findViewById(R.id.createwallet_confirm_password);
        checkBox = rootView.findViewById(R.id.button_checkbox);

        createWalletButton = rootView.findViewById(R.id.button_create_wallet);

        return rootView;
    }

    /**
     * validation for all fields in registration page.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        awesomeValidation.addValidation(getActivity(), R.id.text_first_name, "^([A-Za-z ]+)", R.string.firstname_validation_message);
        awesomeValidation.addValidation(getActivity(), R.id.text_last_name, "^^([A-Za-z ]+)", R.string.lastname_validation_message);
        awesomeValidation.addValidation(getActivity(), R.id.text_email, Patterns.EMAIL_ADDRESS, R.string.registration_email_validation_message);
        awesomeValidation.addValidation(getActivity(), R.id.text_phone_number, "^[0-9]{10}", R.string.phonenumber_validation_message);
        awesomeValidation.addValidation(getActivity(), R.id.createwallet_password, "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&!*]).{6,30})", R.string.password_validation_message);
        awesomeValidation.addValidation(getActivity(), R.id.createwallet_confirm_password, "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%&!*]).{6,30})", R.string.confirm_password_validation_message);

        createWalletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    passwordString = passwordEditText.getText().toString();
                    confirmPasswordString = confirmPasswordEditText.getText().toString();

                    if (!passwordString.equals(confirmPasswordString)) {
                        confirmPasswordEditText.setError(Utils.getStringValue(R.string.confirm_password_mismatch_message, getContext()));
                        return;
                    }
                    if (checkBox.isChecked()) {

                        utils.progressBar(getContext(), "Creating Wallet");

                        registrationData.setFirstName(firstNameEditText.getText().toString());
                        registrationData.setLastName(lastNameEditText.getText().toString());
                        registrationData.setEmailId(emailIdEditText.getText().toString());
                        registrationData.setPhoneNumber(phoneNumberEditText.getText().toString());
                        registrationData.setPassword(passwordEditText.getText().toString());

                        apiManager.registerUser(getContext(), registrationData);


                    } else {
                        Toast.makeText(getContext(), Constants.CHECK_BOX_ACCEPTANCE_MESSAGE, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onResponseInterface(JSONObject responseData) {

        String userResponseMessage = apiManager.getResponseMessage(responseData);

        if (userResponseMessage != "null") {


            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
            // set title
            alertDialogBuilder.setTitle(Utils.getStringValue(R.string.alert_dialog_registration_title, getContext()));
            // set dialog message
            alertDialogBuilder
                    .setMessage(Utils.getStringValue(R.string.alert_dialog_registration_message, getContext()))
                    .setPositiveButton(Utils.getStringValue(R.string.alert_dialog_registration_login_button, getContext()), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.popBackStackImmediate();
                        }
                    });
            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();

        } else {

        }
    }
}