package com.example.lab08_omelko;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import math.interp;

public class MainActivity extends AppCompatActivity {
    int N, code;
    float Max, Min;//params
    boolean[] func;//function, what need to display
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = new Intent(this, ParamsActivity.class);
        N = 100;
        Max = (float) Math.PI * 4.0f;
        Min = 0.0f;
        code = 1;
        toDrawTheGraph(N, Min, Max, code);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itmParams: {
                //TODO set parameters in 2 activity
                i.putExtra("N", N);
                i.putExtra("Min", Min);
                i.putExtra("Max", Max);
                startActivityForResult(i, 111);
                return true;
            }
            case R.id.itmFunc: {
                //TODO set function on dialog window
                DialogWindow();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//created by Igor Omelko
        if (data != null && requestCode == 111) {
            N = data.getIntExtra("N", 100);
            Min = data.getFloatExtra("Min", 0.0f);
            Max = data.getFloatExtra("Max", (float) Math.PI * 4.0f);

        }
        toDrawTheGraph(N, Min, Max, code);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void toDrawTheGraph(int n, float min, float max, int code) {
        MySurface s = findViewById(R.id.mySurface);
        s.n = n;//set number of sample point
        s.x = new float[s.n];//allocate samples
        s.y = new float[s.n];
        for (int i = 0; i < s.n; i++) {
            //while i ranges from 0 to s.n -1 ==> x ranges from 0 to 4*pi
            s.x[i] = interp.map(i, 0, s.n - 1, min, max);
            // y ==> cos(x)
            switch (code) {
                case 1: {
                    s.y[i] = (float) Math.sin(s.x[i]);
                    break;
                }
                case 2: {
                    s.y[i] = (float) Math.cos(s.x[i]);
                    break;
                }
                case 3: {
                    s.y[i] = (float) Math.tan(s.x[i]);
                    break;
                }
                case 4: {
                    s.y[i] = (float) Math.sinh(s.x[i]);
                    break;
                }
                case 5: {
                    s.y[i] = (float) Math.cosh(s.x[i]);
                    break;
                }
            }

        }
        s.update();//compute min, max values
        s.invalidate(); // invoke ondraw()
    }

    public void DialogWindow() { //created by Igor Omelko
        LayoutInflater myLayout = LayoutInflater.from(this);
        View dialogView = myLayout.inflate(R.layout.dialogwindow, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dlg = builder.create();
        RadioGroup rb = dialogView.findViewById(R.id.rg);
        RadioButton Sin = dialogView.findViewById(R.id.rbSin);
        RadioButton Cos = dialogView.findViewById(R.id.rbCos);
        RadioButton Tan = dialogView.findViewById(R.id.rbTan);
        RadioButton SinH = dialogView.findViewById(R.id.rbSinH);
        RadioButton CosH = dialogView.findViewById(R.id.rbCosH);
        dlg.show();
        rb.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton RB = dialogView.findViewById(checkedId);
                int index = rb.indexOfChild(RB);
                switch (index) {
                    case 0:
                        code = 1;
                        toDrawTheGraph(N, Min, Max, code);
                        break;
                    case 1:
                        code = 2;
                        toDrawTheGraph(N, Min, Max, code);
                        break;
                    case 2:
                        code = 3;
                        toDrawTheGraph(N, Min, Max, code);
                        break;
                    case 3:
                        code = 4;
                        toDrawTheGraph(N, Min, Max, code);
                        break;
                    case 4:
                        code = 5;
                        toDrawTheGraph(N, Min, Max, code);
                        break;
                }
                dlg.cancel();
            }
        });
    }
}