package com.example.fcmtester;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedprefManager {

    private static Context mContext;
    private static SharedprefManager newInstance;
    private static final String SHARED_PREF_NAME = "fcmSharedPref";
    private static final String KEY_ACCESS_TOKEN = "token";

    private SharedprefManager(Context context){
        mContext = context;
    }

    public static synchronized SharedprefManager getInstance(Context mContext){
        if(newInstance == null){
            newInstance = new SharedprefManager(mContext);
        }
        return newInstance;
    }


    public boolean storeToken(String token){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ACCESS_TOKEN, token);
        editor.apply();
        return true;
    }

    public String getToken(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, null);
    }
}
