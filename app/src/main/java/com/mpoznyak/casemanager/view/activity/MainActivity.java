package com.mpoznyak.casemanager.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.CaseAdapter;
import com.mpoznyak.casemanager.adapter.TypeAdapter;
import com.mpoznyak.casemanager.presenter.MainPresenter;
import com.mpoznyak.casemanager.presenter.MainPresenterImpl;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private RecyclerView mCasesRecyclerView, mTypesRecyclerView;
    private CaseAdapter mCaseAdapter;
    private TypeAdapter mTypeAdapter;
    private TextView mNameToolbarTv;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBar actionBar;
    private Button mAddTypeButton;
    private FloatingActionButton mNewCaseBtn;
    private MainPresenter mMainPresenter;
    private String currentType;
    private List<Case> mCases;
    private List<Type> mTypes;
    int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    //TODO move to presenter rewrite it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresenter = new MainPresenterImpl(this);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_CALENDAR)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        mAddTypeButton = findViewById(R.id.main_add_type);
        mAddTypeButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewTypeActivity.class);
            startActivity(intent);
        });

        mCasesRecyclerView = findViewById(R.id.main_cases_recyclerview);
        mTypesRecyclerView = findViewById(R.id.main_types_recyclerview);
        mTypes = mMainPresenter.loadTypes();
        mTypeAdapter = new TypeAdapter(mTypes, new TypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = mTypesRecyclerView.getChildLayoutPosition(view);
                Type type = mTypes.get(position);
                type.setLastOpened(true);
                mMainPresenter.updateType(type);
                List<Case> list = mMainPresenter.loadCasesBySelectedType(type.getName());
                mCases.clear();
                mCases.addAll(list);
                mCaseAdapter.notifyDataSetChanged();
                currentType = type.getName();
                mNameToolbarTv.setText(currentType);
                mDrawerLayout.closeDrawers();


            }
        });
        mTypesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCases = mMainPresenter.loadCasesByLastOpenedType();
        mCaseAdapter = new CaseAdapter(mCases,
                new CaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view) {
                        int position = mTypesRecyclerView.getChildLayoutPosition(view);
                        Case aCase = mCases.get(position);
                        int caseId = aCase.getId();
                        Intent intent = new Intent(getApplicationContext(), CaseActivity.class);
                        intent.putExtra("caseId", caseId);
                        startActivity(intent);


                    }
                });
        mCasesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCasesRecyclerView.setAdapter(mCaseAdapter);
        mTypesRecyclerView.setAdapter(mTypeAdapter);

        mNameToolbarTv = findViewById(R.id.mainNameToolbarTv);
        mToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24px);

        mDrawerLayout = findViewById(R.id.main_drawer_layout);

        mNewCaseBtn = findViewById(R.id.fabNewCase);
        mNewCaseBtn.setOnClickListener(v -> {
            Intent newCaseIntent = new Intent(this, NewCaseActivity.class);
            newCaseIntent.putExtra("type", currentType);
            startActivity(newCaseIntent);
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        mTypeAdapter.notifyDataSetChanged();
        mCaseAdapter.notifyDataSetChanged();
        currentType = mMainPresenter.loadNameForLastOpenedType();
        mNameToolbarTv.setText(currentType);
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
