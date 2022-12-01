package com.geektech.calculator_jildiz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("shamal", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("shamal", "onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("shamal", "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("shamal", "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("shamal", "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("shamal", "onRestart");
    }

    private TextView textView;
    private Double first, second;
    private Boolean isOperationClick;
    private String operation;
    private Double result;
    private Button next;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("shamal", "onCreate");

        textView = findViewById(R.id.text_view);
        next=findViewById(R.id.btn_next);
next.setVisibility(View.INVISIBLE);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent perehod= new Intent(MainActivity.this,SecondActivity.class);
                startActivity(perehod);
                finish();
            }
        });
    }

    public void onNumberClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                //нажал на единицу
                if (textView.getText().toString().equals("0")) {
                    textView.setText("1");
                } else if (isOperationClick) {
                    textView.setText("1");
                } else {
                    textView.append("1");
                }
                break;
            case R.id.btn_two:
                //нажал на двойку
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("2");
                } else {
                    textView.append("2");
                }
                break;
            case R.id.btn_three:
                //нажал на три
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("3");
                } else {
                    textView.append("3");
                }
                isOperationClick = false;
                break;
            case R.id.btn_four:
                //нажал на 4
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("4");
                } else {
                    textView.append("4");
                }
                break;
            case R.id.btn_five:
                //нажал на 5
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("5");
                } else {
                    textView.append("5");
                }
                break;
            case R.id.btn_six:
                //нажал на 6
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("6");
                } else {
                    textView.append("6");
                }
                break;
            case R.id.btn_seven:
                //нажал на 7
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("7");
                } else {
                    textView.append("7");
                }
                break;
            case R.id.btn_eight:
                //нажал на 8
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("8");
                } else {
                    textView.append("8");
                }
                break;
            case R.id.btn_nine:
                //нажал на 9
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("9");
                } else {
                    textView.append("9");
                }
                break;
            case R.id.btn_zero:
                //нажал на 0
                if (textView.getText().toString().equals("0") || isOperationClick) {
                    textView.setText("0");
                } else {
                    textView.append("0");
                }
                break;
            case R.id.btn_dot:
                //нажал на 0
                if (!textView.getText().toString().contains(".")) {
                    textView.append(".");
                }
                break;
            case R.id.btn_clear:
                //нажал на очистить
                textView.setText("0");
                first = 0.0;
                second = 0.0;
                break;
        }

        isOperationClick = false;
    }

    public void onOperationClick(View view) {
        next.setVisibility(View.INVISIBLE);

        switch (view.getId()) {
            case R.id.btn_plus:
                operation = "+";
                //12
                first = Double.parseDouble(textView.getText().toString());
                break;
            case R.id.btn_minus:
                operation = "-";
                first = Double.parseDouble(textView.getText().toString());

                break;
            case R.id.btn_multiply:
                operation = "*";
                first = Double.parseDouble(textView.getText().toString());

                break;

            case R.id.btn_division:
                operation = "/";
                first = Double.parseDouble(textView.getText().toString());

                break;
            case R.id.btn_equal:
                next.setVisibility(View.VISIBLE);
                //21
                second = Double.parseDouble(textView.getText().toString());
                //33=12+21
                //Double result = Double.valueOf(0);
             switch (operation) {
                    case "+":
                        result = first + second;
                        textView.setText(new DecimalFormat("##.#######").format(result));
                        break;
                    case "-":
                        result = first - second;
                        textView.setText(new DecimalFormat("##.#######").format(result));
                        break;
                    case "*":
                        result = first * second;
                        textView.setText(new DecimalFormat("##.#######").format(result));
                        break;
                    case "/":
                        if (second == 0.0) {
                            textView.setText("На ноль делить нельзя!!!");
                        } else {
                            result = first / second;
                            textView.setText(new DecimalFormat("##.#######").format(result));
                        }
                        break;


                        //
                }
                break;
        }
        isOperationClick = true;

    }
}