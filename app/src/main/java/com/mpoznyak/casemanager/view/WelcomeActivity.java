package com.mpoznyak.casemanager.view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.mpoznyak.casemanager.R;

public class WelcomeActivity extends FragmentActivity {

    private AppCompatButton mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mStartButton = (AppCompatButton) findViewById(R.id.welcome_start_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
