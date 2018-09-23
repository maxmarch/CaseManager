package com.mpoznyak.document_assistant.view.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.adapter.ColorSpinnerArrayAdapter;
import com.mpoznyak.document_assistant.presenter.NewTypePresenter;
import com.mpoznyak.domain.model.Color;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        mWelcomePresenter = new NewTypePresenter(this);
        ImageButton addTypeButton = findViewById(R.id.btn_add_type);
        mConstraintLayout = findViewById(R.id.layoutNewTypeActivity);
        mConstraintLayout.setOnTouchListener((v, w) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            return true;
        });
        final EditText typeNameInput = findViewById(R.id.et_welcome_add_type);
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
                                getString(R.string.set_group_name), Toast.LENGTH_LONG).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.this_name_exists),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        mBackButton = findViewById(R.id.buttonBackNewTypeActivity);
        mBackButton.setOnClickListener(v -> this.onBackPressed());

    }

    private void initColors() {
        mColors = new ArrayList<>();
        Collections.addAll(mColors, Color.values());
    }
}
