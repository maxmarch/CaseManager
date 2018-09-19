package com.mpoznyak.casemanager;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by Max Poznyak on 19.09.2018.
 */
public class ApplicationApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (!preferences.getBoolean("welcome_shown", false)) {
            editor.putBoolean("welcome_shown", true).apply();
        } else {
            editor.putBoolean("welcome_shown", false).apply();
        }

    }
}
