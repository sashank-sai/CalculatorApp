package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonc, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        resultTv = findViewById(R.id.button_tv);
        solutionTv = findViewById(R.id.button_sol);
        assignId(button0, R.id.buttton_0);
        assignId(button1, R.id.buttton_1);
        assignId(buttonc, R.id.buttton_c);
        assignId(button2, R.id.buttton_2);
        assignId(button3, R.id.buttton_3);
        assignId(button4, R.id.buttton_4);
        assignId(button5, R.id.buttton_5);
        assignId(button6, R.id.buttton_6);
        assignId(button7, R.id.buttton_7);
        assignId(button8, R.id.buttton_8);
        assignId(button9, R.id.buttton_9);
        assignId(buttonAC, R.id.buttton_ac);
        assignId(buttonDot, R.id.buttton_dot);
        assignId(buttonDivide, R.id.buttton_div);
        assignId(buttonMultiply, R.id.buttton_mul);
        assignId(buttonPlus, R.id.buttton_plus);
        assignId(buttonMinus, R.id.buttton_minus);
        assignId(buttonEquals, R.id.buttton_equal);
        assignId(buttonBrackOpen, R.id.buttton_bra1);
        assignId(buttonBrackClose, R.id.buttton_bra2);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCal = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        if (buttonText.equals("C")) {
            if (!dataToCal.isEmpty()) {
                dataToCal = dataToCal.substring(0, dataToCal.length() - 1);
            }
        } else {
            if (dataToCal.equals("0") && !buttonText.equals(".")) {
                dataToCal = buttonText; // Replace "0" with the new number
            } else {
                dataToCal = dataToCal + buttonText;
            }
        }

        solutionTv.setText(dataToCal);

        if (!dataToCal.isEmpty()) {
            String finalResult = getResult(dataToCal);
            if (!finalResult.equals("Error")) {
                resultTv.setText(finalResult);
            } else {
                resultTv.setText("0");
            }
        } else {
            resultTv.setText("0");
        }
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Error";
        }
    }


}
