package com.mpoznyak.casemanager.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import com.mpoznyak.casemanager.adapter.CasesAdapter;
import com.mpoznyak.casemanager.adapter.TypesAdapter;
import com.mpoznyak.casemanager.presenter.MainPresenter;
import com.mpoznyak.casemanager.presenter.MainPresenterImpl;
import com.mpoznyak.domain.model.Case;
import com.mpoznyak.domain.model.Type;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mCasesRecyclerView, mTypesRecyclerView;
    private CasesAdapter mCasesAdapter;
    private TypesAdapter mTypesAdapter;
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
                Intent intent = new Intent(this, NewTypeActivity.class);
                startActivity(intent);
            });

            mCasesRecyclerView = findViewById(R.id.main_cases_recyclerview);
            mTypesRecyclerView = findViewById(R.id.main_types_recyclerview);
            mTypes = mMainPresenter.loadTypes();
            mTypesAdapter = new TypesAdapter(mTypes, new TypesAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view) {
                    int position = mTypesRecyclerView.getChildLayoutPosition(view);
                    Type type = mTypes.get(position);
                    type.setLastOpened(true);
                    mMainPresenter.updateType(type);
                    List<Case> list = mMainPresenter.loadCasesBySelectedType(type.getName());
                    mCases.clear();
                    mCases.addAll(list);
                    mCasesAdapter.notifyDataSetChanged();
                    currentType = type.getName();
                    mNameToolbarTv.setText(currentType);
                    mDrawerLayout.closeDrawers();


                }
            });
            mTypesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mCases = mMainPresenter.loadCasesByLastOpenedType();
            mCasesAdapter = new CasesAdapter(mCases,
                    new CasesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view) {
                            int position = mTypesRecyclerView.getChildLayoutPosition(view);
                            Case aCase = mCases.get(position);

                            Intent intent = new Intent(getApplicationContext(), CaseActivity.class);
                            startActivity(intent);


                        }
                    });
            mCasesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mCasesRecyclerView.setAdapter(mCasesAdapter);
            mTypesRecyclerView.setAdapter(mTypesAdapter);

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
    }

    @Override
    public void onResume() {
        super.onResume();
        mTypesAdapter.notifyDataSetChanged();
        mCasesAdapter.notifyDataSetChanged();
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
