package com.mpoznyak.casemanager.view;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCasesList, mTypesList;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    private MainPresenter mMainPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
