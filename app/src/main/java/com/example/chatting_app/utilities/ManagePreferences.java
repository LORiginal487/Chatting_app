package com.example.chatting_app.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class ManagePreferences {
    private final SharedPreferences sharedPreferences;

    public ManagePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(Constants.Key_Preference_Name, Context.MODE_PRIVATE);
    }
    public void putBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    public Boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }
    public void putString(String key, String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }
    public void clearPref(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
