package com.example.djiang.viewmeasurelayoutdrawtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView top;
    TextView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        top = (TextView) findViewById(R.id.top);
        bottom = (TextView) findViewById(R.id.bottom);

        
    }
}
