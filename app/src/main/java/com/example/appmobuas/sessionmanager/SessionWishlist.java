package com.example.appmobuas.sessionmanager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionWishlist {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String PREF_NAME = "WishlistPref";
    private static final String KEY_WISHLIST = "wishlist";

    public SessionWishlist(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveWishlist(String wishlist) {
        editor.putString(KEY_WISHLIST, wishlist);
        editor.apply();
    }

    public String getWishlist() {
        return sharedPreferences.getString(KEY_WISHLIST, "");
    }

    public void clearWishlist() {
        editor.remove(KEY_WISHLIST);
        editor.apply();
    }
}
