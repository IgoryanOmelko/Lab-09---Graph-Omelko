package com.example.lab08_omelko;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ParamsActivity extends AppCompatActivity {
    EditText etPointsNumber;
    EditText etXMin;
    EditText etXMax;
    Intent i;
    int N;
    float Max, Min;
    Context t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        etPointsNumber = findViewById(R.id.etPointNumber);
        etXMin = findViewById(R.id.etXMin);
        etXMax = findViewById(R.id.etXMax);
        i = getIntent();
        N = i.getIntExtra("N", 100);
        Min = i.getFloatExtra("Min", 0.0f);
        Max = i.getFloatExtra("Max", (float) Math.PI * 4.0f);
        etPointsNumber.setText(String.valueOf(N));
        etXMin.setText(String.valueOf(Min));
        etXMax.setText(String.valueOf(Max));
        t = this;
    }

    public void onCancelClick(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void onSetButtonClick(View v) {
        //Toast.makeText(this,"Parameter is wrong",Toast.LENGTH_SHORT).show();
        try {
            N = Integer.parseInt(etPointsNumber.getText().toString());
            Min = Float.parseFloat(etXMin.getText().toString());
            Max = Float.parseFloat(etXMax.getText().toString());
        } catch (NumberFormatException e) {
            DialogWindow("Parameter is wrong");
            //Toast.makeText(ParamsActivity.this,"Parameter is wrong",Toast.LENGTH_SHORT).show();
            return;
        }
        if (N > 100000 || Max < 0 || Min < 0 || N < 0) {
            DialogWindow("Parameter is wrong");
            //Toast.makeText(t,"Parameter is wrong",Toast.LENGTH_SHORT).show();
            return;
        } else {
            i = new Intent();
            i.putExtra("N", N);
            i.putExtra("Min", Min);
            i.putExtra("Max", Max);
            setResult(RESULT_OK, i);
            finish();
        }
    }

    public void DialogWindow(String mes) { //created by Igor Omelko
        new AlertDialog.Builder(this)
                .setTitle("Attention")
                .setMessage(mes).setNegativeButton(android.R.string.no, null)
                .create()
                .show();
    }
}