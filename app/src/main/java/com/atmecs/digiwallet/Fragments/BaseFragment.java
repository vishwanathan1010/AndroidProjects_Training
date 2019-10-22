package com.atmecs.digiwallet.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.atmecs.digiwallet.Api.APIResponseInterface;
import com.atmecs.digiwallet.Api.DigiWalletAPIManager;

import org.json.JSONObject;

public abstract class BaseFragment extends Fragment implements APIResponseInterface {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = fragmentView(inflater, container, savedInstanceState);
        return view;
    }

    public abstract View fragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    @Override
    public void onResponseInterface(JSONObject responseData) {

        DigiWalletAPIManager.getUserId(responseData);
        DigiWalletAPIManager.getResponseMessage(responseData);
    }
}

