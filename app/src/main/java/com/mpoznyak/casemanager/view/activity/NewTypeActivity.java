package com.mpoznyak.casemanager.view.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.adapter.ColorSpinnerArrayAdapter;
import com.mpoznyak.casemanager.presenter.NewTypePresenter;
import com.mpoznyak.casemanager.presenter.NewTypePresenterImpl;
import com.mpoznyak.domain.model.Color;

import java.util.ArrayList;
import java.util.List;

public class NewTypeActivity extends AppCompatActivity {

    private NewTypePresenter mWelcomePresenter;
    private ColorSpinnerArrayAdapter mAdapter;
    private ConstraintLayout mConstraintLayout;
    private ImageButton mBackButton;
    private List<Color> mColors;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_type);
        mWelcomePresenter = new NewTypePresenterImpl(this);
        ImageButton addTypeButton = findViewById(R.id.btn_add_type);
        mConstraintLayout = findViewById(R.id.layoutNewTypeActivity);
        mConstraintLayout.setOnTouchListener((v, w) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
        });
        final EditText typeNameInput = findViewById(R.id.et_add_type);
        final Spinner colorSpinner = findViewById(R.id.color_spinner);
        initColors();
        mAdapter = new ColorSpinnerArrayAdapter(this, R.layout.item_color_spinner, mColors);
        colorSpinner.setAdapter(mAdapter);
        addTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = typeNameInput.getText().toString();
                    Color color = (Color) colorSpinner.getSelectedItem();
                    if (!(name.equals("") || name.equals(" ") || name == null)) {
                        mWelcomePresenter.saveType(name, color.getHexColorCode());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "You should define a name of your case's type", Toast.LENGTH_LONG).show();
                        Log.d(this.getClass().getSimpleName(), "CLICK");
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getApplicationContext(), "You've already defined the type with this name!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        mBackButton = findViewById(R.id.buttonBackNewTypeActivity);
        mBackButton.setOnClickListener(v -> this.onBackPressed());

    }

    private void initColors() {
        mColors = new ArrayList<>();
        for (Color color : Color.values()) {
            mColors.add(color);
        }
    }
}
