package com.atmecs.digiwallet.Fragments;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.atmecs.digiwallet.Api.DigiWalletAPIManager;
import com.atmecs.digiwallet.R;
import com.atmecs.digiwallet.Utilities.DecimalDigitsInputFilter;
import com.atmecs.digiwallet.Utilities.Preferences;
import com.atmecs.digiwallet.Utilities.Utils;

import org.json.JSONObject;

public class HomeFragment extends BaseFragment {
    public View rootView;
    public EditText userAmountEditText;
    public DigiWalletAPIManager apiManager;
    public Preferences preferences;
    public String userID;
public Utils utils;
    public HomeFragment() {

        apiManager = new DigiWalletAPIManager(this);
    }

    @Override
    public View fragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences = new Preferences(getContext());
        userID =  preferences.userSessionDetails();
        apiManager.getWalletBalance(getContext(),userID);
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        userAmountEditText = rootView.findViewById(R.id.user_amount);
        userAmountEditText.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(11, 2)});
        return rootView;
    }

    @Override
    public void onResponseInterface(JSONObject responseData) {
        utils = new Utils();
        utils.progressBar(getContext(),"Fetching wallet balance");
        String balance=apiManager.userBalance(responseData);
        System.out.println("Balance is: "+balance);
        TextView walletBalance=rootView.findViewById(R.id.wallet_balance_value);
        walletBalance.setText("\u20B9 "+balance);



    }



}

