package com.atmecs.digiwallet.Api;

import android.content.Context;

import com.android.volley.toolbox.JsonObjectRequest;
import com.atmecs.digiwallet.Fragments.LoginFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class DigiWalletAPIManager {

    public JSONObject jsonBodyObj;
    public VolleyAPIHandler volleyAPIHandler;
    public static LoginData loginData;
    public static String userDataString, userMessageString;

    public DigiWalletAPIManager(APIResponseInterface apiResponseInterface) {
        volleyAPIHandler = new VolleyAPIHandler(apiResponseInterface);
    }

    public void registerUser(Context context, RegistrationData registrationData) {

        jsonBodyObj = new JSONObject();

        try {
            jsonBodyObj.put("firstName", registrationData.getFirstName());
            jsonBodyObj.put("lastName", registrationData.getLastName());
            jsonBodyObj.put("email", registrationData.getEmailId());
            jsonBodyObj.put("phoneNumber", registrationData.getPhoneNumber());
            jsonBodyObj.put("password", registrationData.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String registrationRequestBody = jsonBodyObj.toString();
        System.out.println("UserData: " + registrationRequestBody);
        VolleyAPIHandler.makePostAPICall(context, registrationRequestBody, ConstantsAPI.REGISTER_URL);
    }

    public void loginUser(Context context, LoginData loginData) {

        jsonBodyObj = new JSONObject();

        try {
            jsonBodyObj.put("phoneNumber", loginData.getPhoneNumber());
            jsonBodyObj.put("password", loginData.getPassword());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginRequestBody = jsonBodyObj.toString();
        System.out.println("UserData: " + loginRequestBody);
        VolleyAPIHandler.makePostAPICall(context, loginRequestBody, ConstantsAPI.LOGIN_URL);
        //VolleyAPIHandler.makeGetAPICall(context,loginRequestBody,ConstantsAPI.WALLET_BALANCE_URL);
    }

    public void forgotPasswordAPICall(Context context, LoginData loginData) {

        jsonBodyObj = new JSONObject();

        try {
            jsonBodyObj.put("phoneNumber", loginData.getForgotPasswordPhoneNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String loginRequestBody = jsonBodyObj.toString();
        System.out.println("UserData: " + loginRequestBody);
        VolleyAPIHandler.makePostAPICall(context, loginRequestBody, ConstantsAPI.FORGOT_PASSWORD_URL);
    }

    public static String getUserId(JSONObject response) {
        loginData = new LoginData();

        try {
            loginData.setUserId(response.getString("userId"));
            userDataString = loginData.getUserId();
            System.out.println("User_ID is: " + userDataString);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return userDataString;
    }

    public String userBalance(JSONObject response) {
        loginData = new LoginData();

        try {
            loginData.setUserId(response.getString("balance"));
            userDataString = loginData.getUserId();
            System.out.println("Wallet Balance is: " + userDataString);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return userDataString;
    }

    public static String getResponseMessage(JSONObject response) {
        loginData = new LoginData();

        try {
            loginData.setMessage(response.getString("message"));
            userMessageString = loginData.getMessage();
            System.out.println("Message Is: " + userMessageString);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return userMessageString;
    }

    public void getWalletBalance(Context context, String userId) {

        VolleyAPIHandler.makeGetAPICall(context, ConstantsAPI.WALLET_BALANCE_URL, userId);

    }
}
