package com.zephyrs.android.onefriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Barry on 2/9/17.
 */

public class StressLevel extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private SeekBar levelbar;
    TextView buttontext;
    Button image;
    Button buttonhelp;
    Button backbutton;
    Button done;
    TextView stresslevel;
    private DatabaseReference mDatabase;
    LinearLayout stresslevelpage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stress_level);
        stresslevelpage = (LinearLayout) findViewById(R.id.stress_level);
        image = (Button) findViewById(R.id.button_text);
        levelbar = (SeekBar) findViewById(R.id.sb_level);
        buttontext = (TextView) findViewById(R.id.stresslevel2);
        buttonhelp = (Button) findViewById(R.id.need_help_button);
        backbutton = (Button) findViewById(R.id.back_button);
        done = (Button) findViewById(R.id.done);
        stresslevel = (TextView) findViewById(R.id.stresslevel);
        levelbar.setOnSeekBarChangeListener(this);

        AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
        animationview.setDuration(1000);
        stresslevelpage.setAnimation(animationview);

        SharedPreferences settings = getBaseContext().getSharedPreferences("score", 0);
        String getscore = settings.getString("stress_score", "0");
        Integer point = Integer.valueOf(getscore);
        levelbar.setProgress(point);

        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation1.setDuration(1000);
        levelbar.setAnimation(scaleAnimation1);

        AlphaAnimation animationview2 = new AlphaAnimation(0f, 1f);
        animationview2.setDuration(1000);
        buttontext.setAnimation(animationview2);
        stresslevel.setAnimation(animationview2);

        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.5f, 1, 0.5f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(800);
        image.setAnimation(scaleAnimation2);

        buttonhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getBaseContext(), StressTest.class);
                testintent.putExtra("pagechange","StressLevel");
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences settings = getBaseContext().getSharedPreferences("score", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("stress_head", String.valueOf(levelbar.getProgress()));
                editor.putString("stress_today", String.valueOf(levelbar.getProgress()));
                editor.putString("seekbar1", String.valueOf(0));
                editor.putString("seekbar2", String.valueOf(0));
                editor.putString("seekbar3", String.valueOf(0));
                editor.putString("seekbar4", String.valueOf(0));
                editor.putString("seekbar5", String.valueOf(0));
                editor.putString("seekbar6", String.valueOf(0));
                editor.putString("stress_score", String.valueOf(0));
                editor.putString("stress_reduce", String.valueOf(0));
                editor.commit();

                String sleep = 0+"";
                String exercise = 0+"";
                String meditation = 0+"";
                String social = 0+"";
                String water = 0+"";
                String hobby = 0+"";

                Date date = new Date();
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Sleep").setValue(sleep);
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Exercise").setValue(exercise);
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Meditation").setValue(meditation);
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Social").setValue(social);
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Water").setValue(water);
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Hobby").setValue(hobby);
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Today").setValue(levelbar.getProgress());
                mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Finally").setValue(levelbar.getProgress());

                Intent testintent = new Intent(getBaseContext(), BottomBar.class);
                testintent.putExtra("page", 2);
                testintent.putExtra("pagechange", "StressLevel");
                testintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.equals(levelbar)) {
            if (progress <= 3) {
                stresslevel.setText("Low stress");
                buttontext.setText("Stress that is normal for average person. May include headaches and restlessness.");
                AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
                animationview.setDuration(500);
                buttontext.setAnimation(animationview);
                stresslevel.setAnimation(animationview);
            }
            if (progress > 3 && progress <= 6) {
                stresslevel.setText("Mild stress");
                buttontext.setText("Stress level is more than usual. May include nausea, fatigue, anxiety and depression.");
                AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
                animationview.setDuration(500);
                buttontext.setAnimation(animationview);
                stresslevel.setAnimation(animationview);
            }
            if (progress > 6 && progress <= 9) {
                stresslevel.setText("Medium stress");
                buttontext.setText("Stress levels are significantly above average. May include severe muscle aches, body ache and lack of concentration. ");
                AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
                animationview.setDuration(500);
                buttontext.setAnimation(animationview);
                stresslevel.setAnimation(animationview);
            }
            if (progress > 9) {
                stresslevel.setText("High stress");
                buttontext.setText("Stress levels are severe and at a critical stage. May include trouble sleeping, desire for social isolation and involuntary twitching.");
                AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
                animationview.setDuration(500);
                buttontext.setAnimation(animationview);
                stresslevel.setAnimation(animationview);
            }



        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public String ConvertDate(Date date) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(date);
        String result = s;
        try {
            date = df.parse(result);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
