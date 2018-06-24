package com.zephyrs.android.onefriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;

/**
 * Created by Barry on 11/9/17.
 */

public class FactCauseofstress extends AppCompatActivity {
    Button back;
    ScrollView scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fact_causeofstress);
        scroll = (ScrollView) findViewById(R.id.scroll);

        back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
