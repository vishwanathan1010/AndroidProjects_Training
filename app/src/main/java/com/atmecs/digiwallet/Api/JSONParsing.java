/*
package com.atmecs.digiwallet.Api;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParsing {

    public static JSONObject jsonObject = VolleyAPIHandler.responseBody;


    public static ArrayList userData;

    public static ArrayList onUserId(String user_Data) {

        if (user_Data != null)
            try {
                userData = new ArrayList<>();

                JSONObject jsonObj = new JSONObject(user_Data);

                for (int index = 0; index < jsonObject.length(); index++) {
                    JSONObject object = jsonObject.getJSONObject(index);

                    LoginData loginData=new LoginData();

                    loginData.setUserId("userId: "+object.getString("userId"));

                    userData.add(loginData);
                }
            }
            catch (JSONException errorMessage) {
                Log.e("JsonParser Example", "unexpected JSON exception", errorMessage);
            }
        else {
            System.out.println("Couldn't get json from server.");
        }
        return userData;
    }
}
*/
