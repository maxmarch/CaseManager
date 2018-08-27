package com.mpoznyak.casemanager.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;

import com.mpoznyak.casemanager.R;

public class WelcomeActivity extends FragmentActivity {

    private Button mStartButton;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mStartButton = findViewById(R.id.welcome_start_button);
        final Intent intent = new Intent(this, WelcomeAddTypeActivity.class);
        mStartButton.setOnClickListener(v -> {
            finish();
            startActivity(intent);
        });
    }
}
