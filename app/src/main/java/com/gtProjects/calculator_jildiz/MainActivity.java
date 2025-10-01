package com.gtProjects.calculator_jildiz;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String currentNumber = "";
    private String currentOperation = "";
    private double firstNumber = 0;
    private boolean isNewOperation = true;
    private boolean isError = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    private void initializeViews() {
        textView = findViewById(R.id.text_view);
        findViewById(R.id.btn_next).setVisibility(View.GONE);
    }

    public void onNumberClick(View view) {
        if (isError) {
            clearCalculator();
            return;
        }

        int viewId = view.getId();

        if (viewId == R.id.btn_zero) {
            appendNumber("0");
        } else if (viewId == R.id.btn_one) {
            appendNumber("1");
        } else if (viewId == R.id.btn_two) {
            appendNumber("2");
        } else if (viewId == R.id.btn_three) {
            appendNumber("3");
        } else if (viewId == R.id.btn_four) {
            appendNumber("4");
        } else if (viewId == R.id.btn_five) {
            appendNumber("5");
        } else if (viewId == R.id.btn_six) {
            appendNumber("6");
        } else if (viewId == R.id.btn_seven) {
            appendNumber("7");
        } else if (viewId == R.id.btn_eight) {
            appendNumber("8");
        } else if (viewId == R.id.btn_nine) {
            appendNumber("9");
        } else if (viewId == R.id.btn_dot) {
            addDecimal();
        }
    }

    public void onOperationClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.btn_clear) {
            clearCalculator();
        } else if (viewId == R.id.plus_minus) {
            if (!isError) togglePlusMinus();
        } else if (viewId == R.id.percent) {
            if (!isError) calculatePercent();
        } else if (viewId == R.id.btn_division) {
            if (!isError) setOperation("÷");
        } else if (viewId == R.id.btn_multiply) {
            if (!isError) setOperation("×");
        } else if (viewId == R.id.btn_plus) {
            if (!isError) setOperation("+");
        } else if (viewId == R.id.btn_minus) {
            if (!isError) setOperation("-");
        } else if (viewId == R.id.btn_equal) {
            if (!isError) calculateResult();
        }
    }

    private void appendNumber(String number) {
        if (isNewOperation) {
            currentNumber = number;
            isNewOperation = false;
        } else {
            if (currentNumber.equals("0")) {
                currentNumber = number;
            } else {
                currentNumber += number;
            }
        }
        updateDisplay();
    }

    private void addDecimal() {
        if (isNewOperation) {
            currentNumber = "0.";
            isNewOperation = false;
        } else if (!currentNumber.contains(".")) {
            currentNumber += ".";
        }
        updateDisplay();
    }

    private void setOperation(String operation) {
        if (!currentNumber.isEmpty() && !isError) {
            try {
                firstNumber = Double.parseDouble(currentNumber);
                currentOperation = operation;
                isNewOperation = true;
                // Можно показать операцию на дисплее если нужно
                // textView.setText(operation);
            } catch (NumberFormatException e) {
                showError();
            }
        }
    }

    private void calculateResult() {
        if (!currentOperation.isEmpty() && !currentNumber.isEmpty() && !isNewOperation && !isError) {
            try {
                double secondNumber = Double.parseDouble(currentNumber);
                double result = 0;

                switch (currentOperation) {
                    case "+":
                        result = firstNumber + secondNumber;
                        break;
                    case "-":
                        result = firstNumber - secondNumber;
                        break;
                    case "×":
                        result = firstNumber * secondNumber;
                        break;
                    case "÷":
                        if (secondNumber != 0) {
                            result = firstNumber / secondNumber;
                        } else {
                            showError();
                            return;
                        }
                        break;
                }

                currentNumber = formatResult(result);
                currentOperation = "";
                isNewOperation = true;
                updateDisplay();

            } catch (NumberFormatException e) {
                showError();
            }
        }
    }

    private void calculatePercent() {
        if (!currentNumber.isEmpty() && !isError) {
            try {
                double number = Double.parseDouble(currentNumber);
                double percent;

                if (currentOperation.isEmpty()) {
                    // Если нет операции - просто показываем процент от числа
                    percent = number / 100;
                } else {
                    // Если есть операция - вычисляем процент от первого числа
                    percent = (firstNumber * number) / 100;
                }

                currentNumber = formatResult(percent);
                updateDisplay();
                // Не устанавливаем isNewOperation в true, чтобы можно было продолжить вычисления

            } catch (NumberFormatException e) {
                showError();
            }
        }
    }

    private void togglePlusMinus() {
        if (!currentNumber.isEmpty() && !currentNumber.equals("0") && !isError) {
            try {
                if (currentNumber.startsWith("-")) {
                    currentNumber = currentNumber.substring(1);
                } else {
                    currentNumber = "-" + currentNumber;
                }
                updateDisplay();
            } catch (NumberFormatException e) {
                showError();
            }
        }
    }

    private void clearCalculator() {
        currentNumber = "";
        currentOperation = "";
        firstNumber = 0;
        isNewOperation = true;
        isError = false;
        textView.setText("0");
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
    }

    private void showError() {
        isError = true;
        textView.setText("Error");
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.holo_red_light));
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            String formatted = String.valueOf(result);
            if (formatted.contains("E")) {
                return formatted;
            }
            if (formatted.length() > 10) {
                return String.format("%.8f", result).replaceAll("0*$", "").replaceAll("\\.$", "");
            }
            return formatted.replaceAll("0*$", "").replaceAll("\\.$", "");
        }
    }

    private void updateDisplay() {
        if (currentNumber.isEmpty()) {
            textView.setText("0");
        } else {
            if (currentNumber.length() > 12) {
                if (currentNumber.contains(".")) {
                    textView.setText(currentNumber.substring(0, 12));
                } else {
                    try {
                        double num = Double.parseDouble(currentNumber);
                        textView.setText(String.format("%.6e", num));
                    } catch (NumberFormatException e) {
                        textView.setText(currentNumber.substring(0, 12));
                    }
                }
            } else {
                textView.setText(currentNumber);
            }
        }
    }
}

//package com.gtProjects.calculator_jildiz;
//
//import com.gtProjects.calculator_jildiz.R;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//
//import java.text.DecimalFormat;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        Log.d("panda", "onStart");
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d("panda", "onResume");
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d("panda", "onStop");
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        Log.d("panda", "onPause");
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Log.d("panda", "onDestroy");
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Log.d("panda", "onRestart");
//    }
//
//    private TextView textView;
//    private Double first, second, number;
//    private Boolean isOperationClick;
//    private String operation;
//    private Double result;
//    private Button next;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Log.d("panda", "onCreate");
//
//        textView = findViewById(R.id.text_view);
//        next = findViewById(R.id.btn_next);
//
//        next.setVisibility(View.INVISIBLE);// кнопка не видима
//
//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent perehod = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(perehod);
//                finish();
//            }
//        });
//    }
//
//    public void onNumberClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_one:
//                //нажал на единицу
//                if (textView.getText().toString().equals("0")) {
//                    textView.setText("1");
//                } else if (isOperationClick) {
//                    textView.setText("1");
//                } else {
//                    textView.append("1");
//                }
//                break;
//            case R.id.btn_two:
//                //нажал на двойку
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("2");
//                } else {
//                    textView.append("2");
//                }
//                break;
//            case R.id.btn_three:
//                //нажал на три
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("3");
//                } else {
//                    textView.append("3");
//                }
//                isOperationClick = false;
//                break;
//            case R.id.btn_four:
//                //нажал на 4
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("4");
//                } else {
//                    textView.append("4");
//                }
//                break;
//            case R.id.btn_five:
//                //нажал на 5
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("5");
//                } else {
//                    textView.append("5");
//                }
//                break;
//            case R.id.btn_six:
//                //нажал на 6
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("6");
//                } else {
//                    textView.append("6");
//                }
//                break;
//            case R.id.btn_seven:
//                //нажал на 7
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("7");
//                } else {
//                    textView.append("7");
//                }
//                break;
//            case R.id.btn_eight:
//                //нажал на 8
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("8");
//                } else {
//                    textView.append("8");
//                }
//                break;
//            case R.id.btn_nine:
//                //нажал на 9
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("9");
//                } else {
//                    textView.append("9");
//                }
//                break;
//            case R.id.btn_zero:
//                //нажал на 0
//                if (textView.getText().toString().equals("0") || isOperationClick) {
//                    textView.setText("0");
//                } else {
//                    textView.append("0");
//                }
//                break;
//            case R.id.btn_dot:
//                //нажал на 0
//                if (!textView.getText().toString().contains(".")) {
//                    textView.append(".");
//                }
//                break;
//            case R.id.btn_clear:
//                //нажал на очистить
//                textView.setText("0");
//                first = 0.0;
//                second = 0.0;
//                break;
//        }
//
//        isOperationClick = false;
//    }
//
//    public void onOperationClick(View view) {
//        next.setVisibility(View.INVISIBLE);
//
//        switch (view.getId()) {
//            case R.id.plus_minus:
//                operation = "+/-";
//                first = Double.parseDouble(textView.getText().toString());
//                result = first*(-1);
//                textView.setText(new DecimalFormat("##.#######").format(result));
//                break;
//            case R.id.percent:
//                operation = "%";
//                first = Double.parseDouble(textView.getText().toString());
////                result = first/100;
////                textView.setText(new DecimalFormat("##.#######").format(result));
//                //number = Double.parseDouble(textView.getText().toString());
//                break;
//            case R.id.btn_plus:
//                operation = "+";
//                //12
//                first = Double.parseDouble(textView.getText().toString());
//                break;
//            case R.id.btn_minus:
//                operation = "-";
//                first = Double.parseDouble(textView.getText().toString());
//
//                break;
//            case R.id.btn_multiply:
//                operation = "*";
//                first = Double.parseDouble(textView.getText().toString());
//
//                break;
//
//            case R.id.btn_division:
//                operation = "/";
//                first = Double.parseDouble(textView.getText().toString());
//
//                break;
//            case R.id.btn_equal:
//                next.setVisibility(View.VISIBLE);
//                //21
//                second = Double.parseDouble(textView.getText().toString());
//                //33=12+21
//                //Double result = Double.valueOf(0);
//                //Double number = Double.valueOf(0);
//                Double persent = Double.valueOf(0);
//
//                switch (operation) {
////                    case "+/-":
////                        result = first*(-1);
////                        textView.setText(new DecimalFormat("##.#######").format(result));
////                        break;
//                    case "%":
//                        persent = (first*second)/100;
//                        textView.setText(new DecimalFormat("##.#######").format(persent));
//                        break;
//                    case "+":
//                        result = first + second;
//                        textView.setText(new DecimalFormat("##.#######").format(result));
//                        break;
//                    case "-":
//                        result = first - second;
//                        textView.setText(new DecimalFormat("##.#######").format(result));
//                        break;
//                    case "*":
//                        result = first * second;
//                        textView.setText(new DecimalFormat("##.#######").format(result));
//                        break;
//                    case "/":
//                        if (second == 0.0) {
//                            textView.setText("На ноль делить нельзя!!!");
//                        } else {
//                            result = first / second;
//                            textView.setText(new DecimalFormat("##.#######").format(result));
//                        }
//                        break;
//
//
//                    //
//                }
//                break;
//        }
//        isOperationClick = true;
//
//    }
//}