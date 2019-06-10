package com.example.android.popcake;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.popcake.model.Step;

public class ActivityStepDetails extends AppCompatActivity {
    Step currentStep;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        Intent i = getIntent();
        currentStep = i.getParcelableExtra(Const.PACKAGE_STEP);

        TextView mStepsDescription = findViewById(R.id.step_details_instruction);
        mStepsDescription.setText(currentStep.getDescription());

    }
}
