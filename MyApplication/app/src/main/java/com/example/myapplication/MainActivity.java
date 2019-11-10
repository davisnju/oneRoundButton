package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    Button button;
    private int clickNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button_go);
        button.setOnClickListener(this);
        button.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        callApi();
    }

    private void callApi() {
        clickNo++;
        waitReturn();
        switch (clickNo % 3) {
            case 1:
                returnTrue();
                break;
            case 2:
                returnFalse();
                break;
            default:
                returnUnknown();
                break;
        }
    }

    private void returnUnknown() {
        button.setBackgroundResource(R.drawable.btn_round_yellow);
        button.setText(R.string.rerun);
    }

    private void returnFalse() {
        button.setBackgroundResource(R.drawable.btn_round_red);
        button.setText(R.string.rerun);
    }

    private void returnTrue() {
        button.setBackgroundResource(R.drawable.btn_round_green);
        button.setText(R.string.rerun);
    }

    private void waitReturn() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP) {
            button.setText(R.string.processing);
            button.setBackgroundResource(R.drawable.btn_round_processing);
        }
        return false;
    }
}
