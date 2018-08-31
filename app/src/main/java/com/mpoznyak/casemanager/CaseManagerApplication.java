package com.mpoznyak.casemanager;

import android.app.Application;
import android.content.Intent;

import com.mpoznyak.casemanager.presenter.MainPresenter;
import com.mpoznyak.casemanager.presenter.MainPresenterImpl;
import com.mpoznyak.casemanager.view.activity.MainActivity;
import com.mpoznyak.casemanager.view.activity.WelcomeActivity;

public class CaseManagerApplication extends Application {

    MainPresenter mMainPresenter;

    @Override
    public void onCreate() {
        super.onCreate();
        mMainPresenter = new MainPresenterImpl(this);
        if (mMainPresenter.typeDataisEmpty()) {
            Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
            welcomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(welcomeIntent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
