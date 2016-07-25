package com.example.haariskhan.outsiderecs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;

public class WhatDay extends AppCompatActivity {

    Button friday;
    Button saturday;
    Button sunday;

    boolean fri;
    boolean sat;
    boolean sun;

    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_what_day);

        Intent intent = getIntent();
        token = intent.getStringExtra("Token");

        friday = (Button) findViewById(R.id.Friday);
        saturday = (Button) findViewById(R.id.Saturday);
        sunday = (Button) findViewById(R.id.Sunday);

        fri = false;
        sat = false;
        sun = false;

        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fri) {
                    friday.setText(R.string.selected);
                } else {
                    friday.setText(R.string.unselected);
                }
                fri = !fri;
            }
        });

        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sat) {
                    saturday.setText(R.string.selected);
                } else {
                    saturday.setText(R.string.unselected);
                }
                sat = !sat;
            }
        });

        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sun) {
                    sunday.setText(R.string.selected);
                } else {
                    sunday.setText(R.string.unselected);
                }
                sun = !sun;
            }
        });
    }

    public void nextActivity(View view) {
        Intent intent = new Intent("com.example.haariskhan.outsiderecs.ListSchedule");

        intent.putExtra("Friday", fri);
        intent.putExtra("Saturday", sat);
        intent.putExtra("Sunday", sun);
        intent.putExtra("Token", token);

        startActivity(intent);
    }
}
