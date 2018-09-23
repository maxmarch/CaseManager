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
import android.widget.Toast;

import com.mpoznyak.document_assistant.R;
import com.mpoznyak.document_assistant.presenter.NewCasePresenter;
import com.mpoznyak.domain.model.Color;

import java.util.List;
import java.util.Objects;

public class NewCaseActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;
    private NewCasePresenter mNewCasePresenter;
    private ImageButton mBackButton;
    private List<Color> mColors;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);
        mNewCasePresenter = new NewCasePresenter(this);
        ImageButton addTypeButton = findViewById(R.id.btnAddCase);
        mConstraintLayout = findViewById(R.id.newCaseScreen);
        mConstraintLayout.setOnTouchListener((v, w) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            Objects.requireNonNull(imm).hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
            return true;
        });
        EditText caseNameInput = findViewById(R.id.etNewCase);
        addTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = caseNameInput.getText().toString();
                    String type = Objects.requireNonNull(getIntent().getExtras()).getString("type");
                    mNewCasePresenter.saveCase(name, type);
                    if (!(name.equals("") || name.equals(" ") || name == null)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getString(R.string.should_define_name_case), Toast.LENGTH_LONG).show();
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.case_with_name_exists),
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        mBackButton = findViewById(R.id.backArrowNewCase);
        mBackButton.setOnClickListener(v -> this.onBackPressed());

    }

}
