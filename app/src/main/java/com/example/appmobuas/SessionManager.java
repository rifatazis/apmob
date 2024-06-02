package com.example.appmobuas;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.appmobuas.model.login.LoginData;

import java.util.HashMap;

public class SessionManager {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "islogging";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String BIO = "bio";

    public SessionManager(Context context){
        this.context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void loginSession(LoginData user) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getUserId());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(NAME, user.getName());
        editor.putString(BIO, user.getBio());
        editor.commit();
    }

    public void updateUser(String username, String name, String bio) {
        editor.putString(USERNAME, username);
        editor.putString(NAME, name);
        editor.putString(BIO, bio);
        editor.commit();
    }

    public HashMap<String,String> getUser(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID, null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(BIO, sharedPreferences.getString(BIO, null));
        return user;
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

    public boolean isLogIng(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}
