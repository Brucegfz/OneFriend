package com.zephyrs.android.onefriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Barry on 19/9/17.
 */

public class Feekback extends AppCompatActivity {
    Button back;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button send;
    EditText subject1;
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feekback);
        back = (Button)findViewById(R.id.back_button);
        send = (Button) findViewById(R.id.send);
        subject1 = (EditText) findViewById(R.id.subject);
        comment = (EditText) findViewById(R.id.comment);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] receiver = new String[] {"596907093@qq.com"};
                String subject = subject1.getText().toString();
                String content = comment.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SEND);


                intent.putExtra(Intent.EXTRA_EMAIL, receiver);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, content);
                intent.setType("message/rfc822");
                startActivity(Intent.createChooser(intent, "Please choose software to send"));
            }
        });

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
