package com.mpoznyak.document_assistant.view.activity;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.adapter.ColorSpinnerArrayAdapter;
import com.mpoznyak.document_assistant.presenter.NewTypePresenter;
import com.mpoznyak.domain.model.Color;
import com.mpoznyak.domain.model.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditTypeActivity extends AppCompatActivity {

    private NewTypePresenter mWelcomePresenter;
    private ColorSpinnerArrayAdapter mAdapter;
    private ImageButton mBackButton;
    private List<Color> mColors;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_type);
        mWelcomePresenter = new NewTypePresenter(this);
        ImageButton editTypeButton = findViewById(R.id.btnAcceptEditType);

        final EditText typeNameInput = findViewById(R.id.etEditType);
        final Spinner colorSpinner = findViewById(R.id.colorSpinnerEditType);
        initColors();
        mAdapter = new ColorSpinnerArrayAdapter(this, R.layout.item_color_spinner, mColors);
        colorSpinner.setAdapter(mAdapter);
        editTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = typeNameInput.getText().toString();
                    Color color = (Color) colorSpinner.getSelectedItem();
                    if (!(name.equals("") || name.equals(" ") || name == null)) {
                        int typeId = getIntent().getIntExtra("typeId", 0);
                        Type type = new Type();
                        type.setId(typeId);
                        type.setColor(color.getHexColorCode());
                        type.setName(name);
                        type.setLastOpened(true);
                        mWelcomePresenter.updateType(type);
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.define_group_name)
                                , Toast.LENGTH_LONG).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.this_name_exists),
                            Toast.LENGTH_LONG).show();
                    Log.d(EditTypeActivity.class.getSimpleName(), e.getMessage());
                }

            }
        });

        mBackButton = findViewById(R.id.backArrowEditType);
        mBackButton.setOnClickListener(v -> this.onBackPressed());

    }

    private void initColors() {
        mColors = new ArrayList<>();
        Collections.addAll(mColors, Color.values());
    }
}
