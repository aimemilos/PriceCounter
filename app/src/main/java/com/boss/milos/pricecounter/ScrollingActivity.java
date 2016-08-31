package com.boss.milos.pricecounter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class ScrollingActivity extends AppCompatActivity {


    private ArrayList<Element> elements = new ArrayList<Element>();
    private ViewBuilder viewBuilder = new ViewBuilder(this, elements);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        viewBuilder.init();
    }








}