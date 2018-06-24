package com.zephyrs.android.onefriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Barry on 20/9/17.
 */

public class AboutUs extends AppCompatActivity {
    Button back;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        t1 = (TextView)findViewById(R.id.textView1);
        back = (Button)findViewById(R.id.back_button);

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
