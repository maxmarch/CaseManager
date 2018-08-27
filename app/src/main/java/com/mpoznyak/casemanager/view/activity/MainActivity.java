package com.mpoznyak.casemanager.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CasesAdapter;
import com.mpoznyak.casemanager.adapter.TypesAdapter;
import com.mpoznyak.casemanager.presenter.MainPresenter;
import com.mpoznyak.casemanager.presenter.MainPresenterImpl;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCasesList, mTypesList;
    private CasesAdapter mCasesAdapter;
    private TypesAdapter mTypesAdapter;
    private TextView mNameToolbarTv;
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

        } else {
            setContentView(R.layout.activity_main);

            mAddTypeButton = findViewById(R.id.main_add_type);
            mAddTypeButton.setOnClickListener(v -> {
                Intent intent = new Intent(this, MainAddTypeActivity.class);
                startActivity(intent);
            });

            mCasesList = findViewById(R.id.main_cases_recyclerview);
            mTypesList = findViewById(R.id.main_types_recyclerview);
            mTypesAdapter = new TypesAdapter(this, mMainPresenter.loadTypes());
            mTypesList.setLayoutManager(new LinearLayoutManager(this));
            mCasesAdapter = new CasesAdapter(this, mMainPresenter.loadCasesByLastOpenedType());
            mCasesList.setLayoutManager(new LinearLayoutManager(this));
            mCasesList.setAdapter(mCasesAdapter);
            mTypesList.setAdapter(mTypesAdapter);

            mNameToolbarTv = findViewById(R.id.mainNameToolbarTv);
            mToolbar = findViewById(R.id.main_toolbar);
            setSupportActionBar(mToolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

            mDrawerLayout = findViewById(R.id.main_drawer_layout);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTypesAdapter.notifyDataSetChanged();
        mNameToolbarTv.setText(mMainPresenter.loadNameForLastOpenedType());
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
