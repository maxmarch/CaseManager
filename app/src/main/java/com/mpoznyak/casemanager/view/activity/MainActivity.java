package com.mpoznyak.casemanager.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.presenters.MainPresenter;
import com.mpoznyak.casemanager.presenters.MainPresenterImpl;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCasesList, mTypesList;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    private Button mAddTypeButton;
    private MainPresenter mMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = new MainPresenterImpl(this);
        if (mMainPresenter.typeDataisEmpty()) {
            Intent welcomeIntent = new Intent(this, WelcomeActivity.class);
            finish();
            startActivity(welcomeIntent);

        }
        setContentView(R.layout.activity_main);
        mAddTypeButton = findViewById(R.id.main_add_type);
        mAddTypeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TypeAddingActivity.class);
            startActivity(intent);
        });
        mCasesList = findViewById(R.id.main_cases_recyclerview);
        mTypesList = findViewById(R.id.main_types_recyclerview);
        mToolbar = findViewById(R.id.main_toolbar);
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}