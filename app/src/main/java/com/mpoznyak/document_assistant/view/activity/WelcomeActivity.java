package com.mpoznyak.document_assistant.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Button;

import com.mpoznyak.document_assistant.R;

public class WelcomeActivity extends FragmentActivity {

    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mStartButton = findViewById(R.id.welcome_start_button);
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("app_pref", MODE_PRIVATE);
        SharedPreferences.Editor mPrefEditor = preferences.edit();
        mPrefEditor.putBoolean("welcome_shown", true).apply();
        final Intent intent = new Intent(this, WelcomeNewTypeActivity.class);
        mStartButton.setOnClickListener(v -> {
            finish();
            startActivity(intent);
        });
    }
}
