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
import android.widget.Toast;

import com.mpoznyak.casemanager.R;
import com.mpoznyak.casemanager.presenter.NewCasePresenter;
import com.mpoznyak.casemanager.presenter.NewCasePresenterImpl;
import com.mpoznyak.domain.model.Color;

import java.util.List;

public class NewCaseActivity extends AppCompatActivity {

    private ConstraintLayout mConstraintLayout;
    private NewCasePresenter mNewCasePresenter;
    private ImageButton mBackButton;
    private List<Color> mColors;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_case);
        mNewCasePresenter = new NewCasePresenterImpl(this);
        ImageButton addTypeButton = findViewById(R.id.btnAddCase);
        mConstraintLayout = findViewById(R.id.newCaseScreen);
        mConstraintLayout.setOnTouchListener((v, w) -> {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            return true;
        });
        EditText caseNameInput = findViewById(R.id.etNewCase);
        addTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = caseNameInput.getText().toString();
                    String type = getIntent().getExtras().getString("type");
                    mNewCasePresenter.saveCase(name, type);
                    if (!(name.equals("") || name.equals(" ") || name == null)) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "You should define a name of your case", Toast.LENGTH_LONG).show();
                        Log.d(this.getClass().getSimpleName(), "CLICK");
                    }
                } catch (SQLiteConstraintException e) {
                    Toast.makeText(getApplicationContext(), "You've already defined the case with this name!",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        mBackButton = findViewById(R.id.backArrowNewCase);
        mBackButton.setOnClickListener(v -> this.onBackPressed());

    }

}
