package com.psyex.application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class Timer extends AppCompatActivity {

    int condition=1;
    static public Class nextAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        final TextView timer = (TextView) findViewById(R.id.textView_timer_timer);
        condition = getIntent().getIntExtra("condition",2);
        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf((millisUntilFinished / 1000) + 1));
            }

            public void onFinish() {
                timer.setText("");
                Intent intent = new Intent(Timer.this, nextAct);
                intent.putExtra(getString(R.string.practice_extra),getIntent().getBooleanExtra(getString(R.string.practice_extra),true));
                intent.putExtra("condition",getIntent().getIntExtra("condition",1));
                intent.putExtra("trialNum",getIntent().getIntExtra("trialNum",0));
                finish();
                startActivity(intent);
//                if(condition==1) {
//
//                }
//                else{
//
//                }
//                Intent in1 = new Intent(timer.this, activites[order.get((currAct - 1) / 2)]);
//                in1.putExtra("isPractice",true);
//                in1.putExtra("trial",trialNum);
//                finish();
//                startActivity(in1);
            }
        }.start();

    }}
