package com.mpoznyak.casemanager.view.activity;

import android.content.Intent;
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
import com.mpoznyak.casemanager.presenter.AddTypePresenter;
import com.mpoznyak.casemanager.presenter.AddTypePresenterImpl;
import com.mpoznyak.domain.model.Color;

import java.util.ArrayList;
import java.util.List;

public class WelcomeAddTypeActivity extends AppCompatActivity {

    private AddTypePresenter mWelcomePresenter;
    private ColorSpinnerArrayAdapter mAdapter;
    private ConstraintLayout mConstraintLayout;
    private List<Color> mColors;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_add_type);
        mWelcomePresenter = new AddTypePresenterImpl(this);
        ImageButton addTypeButton = findViewById(R.id.btn_add_type);
        mConstraintLayout = findViewById(R.id.typeadding_screen);
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

            }
        });

    }

    private void initColors() {
        mColors = new ArrayList<>();
        for (Color color : Color.values()) {
            mColors.add(color);
        }
    }
}
