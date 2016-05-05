package com.example.djiang.chitubutton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    XButton button;
    Button toggle;
    boolean enabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (XButton) findViewById(R.id.xbutton);
        toggle = (Button) findViewById(R.id.switcher);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enabled)
                    button.disable();
                else
                    button.enable();

                enabled = !enabled;
            }
        });

    }
}
