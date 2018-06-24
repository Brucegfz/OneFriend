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
import android.widget.CheckBox;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Barry on 2/9/17.
 */

public class StressTest extends AppCompatActivity {
    Button done;
    CheckBox b1;
    CheckBox b2;
    CheckBox b3;
    CheckBox b4;
    CheckBox b5;
    CheckBox b6;
    CheckBox b7;
    CheckBox b8;
    CheckBox b9;
    CheckBox b10;
    CheckBox b11;
    CheckBox b12;
    Integer score;
    Button back;
    Button question;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stress_test);
        score = 0;
        done = (Button) findViewById(R.id.done);
        back = (Button) findViewById(R.id.back_button);
        question = (Button) findViewById(R.id.buttonquestion);
        b1 = (CheckBox) findViewById(R.id.button1);
        b2 = (CheckBox) findViewById(R.id.button2);
        b3 = (CheckBox) findViewById(R.id.button3);
        b4 = (CheckBox) findViewById(R.id.button4);
        b5 = (CheckBox) findViewById(R.id.button5);
        b6 = (CheckBox) findViewById(R.id.button6);
        b7 = (CheckBox) findViewById(R.id.button7);
        b8 = (CheckBox) findViewById(R.id.button8);
        b9 = (CheckBox) findViewById(R.id.button9);
        b10 = (CheckBox) findViewById(R.id.button10);
        b11 = (CheckBox) findViewById(R.id.button11);
        b12 = (CheckBox) findViewById(R.id.button12);

        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation1.setDuration(1000);


        AlphaAnimation animationview2 = new AlphaAnimation(0f, 1f);
        animationview2.setDuration(200);
        b1.setAnimation(animationview2);
        b2.setAnimation(animationview2);
        b3.setAnimation(animationview2);
        AlphaAnimation animationview3 = new AlphaAnimation(0f, 1f);
        animationview3.setDuration(500);
        b4.setAnimation(animationview3);
        b5.setAnimation(animationview3);
        b6.setAnimation(animationview3);
        AlphaAnimation animationview4 = new AlphaAnimation(0f, 1f);
        animationview4.setDuration(1000);
        b7.setAnimation(animationview4);
        b8.setAnimation(animationview4);
        b9.setAnimation(animationview4);
        AlphaAnimation animationview5 = new AlphaAnimation(0f, 1f);
        animationview5.setDuration(1200);
        b10.setAnimation(animationview5);
        b11.setAnimation(animationview5);
        b12.setAnimation(animationview5);

//This code is for keep the syntom you select

        SharedPreferences ppstress = getBaseContext().getSharedPreferences("previousstress", 0);
        String specifictoday = ppstress.getString("specifictoday","");
        Date checkdate = new Date();
        String checktoday = ConvertDate(checkdate);
        if(checktoday.equals(specifictoday)) {
            Gson gson = new Gson();
            String physicaljson = ppstress.getString("physical","");
            String emotionaljson = ppstress.getString("emotional","");
            String cognitivejson = ppstress.getString("cognitive","");
            String behaviourjson = ppstress.getString("behaviours","");
            ArrayList<String> physicalsystom = gson.fromJson(physicaljson, new TypeToken<ArrayList<String>>(){}.getType());
            ArrayList<String> emotionsystom = gson.fromJson(emotionaljson, new TypeToken<ArrayList<String>>(){}.getType());
            ArrayList<String> cognitivesystom = gson.fromJson(cognitivejson, new TypeToken<ArrayList<String>>(){}.getType());
            ArrayList<String> behavioursystom = gson.fromJson(behaviourjson, new TypeToken<ArrayList<String>>(){}.getType());

            for (int index = 0; index < physicalsystom.size(); index++) {
                if (physicalsystom.get(index).equals(b1.getText())) {
                    b1.setChecked(true);
                }
                if (physicalsystom.get(index).equals(b2.getText())) {
                    b2.setChecked(true);
                }
                if (physicalsystom.get(index).equals(b3.getText())) {
                    b3.setChecked(true);
                }
            }

            for (int index = 0; index < emotionsystom.size(); index++) {
                if (emotionsystom.get(index).equals(b4.getText())) {
                    b4.setChecked(true);
                }
                if (emotionsystom.get(index).equals(b5.getText())) {
                    b5.setChecked(true);
                }
                if (emotionsystom.get(index).equals(b6.getText())) {
                    b6.setChecked(true);
                }
            }

            for (int index = 0; index < cognitivesystom.size(); index++) {
                if (cognitivesystom.get(index).equals(b7.getText())) {
                    b7.setChecked(true);
                }
                if (cognitivesystom.get(index).equals(b8.getText())) {
                    b8.setChecked(true);
                }
                if (cognitivesystom.get(index).equals(b9.getText())) {
                    b9.setChecked(true);
                }
            }

            for (int index = 0; index < behavioursystom.size(); index++) {
                if (behavioursystom.get(index).equals(b10.getText())) {
                    b10.setChecked(true);
                }
                if (behavioursystom.get(index).equals(b11.getText())) {
                    b11.setChecked(true);
                }
                if (behavioursystom.get(index).equals(b12.getText())) {
                    b12.setChecked(true);
                }
            }
        }

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> physical = new ArrayList<String>();
                ArrayList<String> emtional = new ArrayList<String>();
                ArrayList<String> cognitive = new ArrayList<String>();
                ArrayList<String> behaviours = new ArrayList<String>();

                score = 0;
                if(b1.isChecked()){
                    score = score + 1;
                    physical.add(b1.getText().toString());
                }
                if(b2.isChecked()){
                    score = score + 1;
                    physical.add(b2.getText().toString());
                }
                if(b3.isChecked()){
                    score = score + 2;
                    physical.add(b3.getText().toString());
                }
                if(b4.isChecked()){
                    score = score + 1;
                    emtional.add(b4.getText().toString());
                }
                if(b5.isChecked()){
                    score = score + 1;
                    emtional.add(b5.getText().toString());
                }
                if(b6.isChecked()){
                    score = score + 2;
                    emtional.add(b6.getText().toString());
                }
                if(b7.isChecked()){
                    score = score + 1;
                    cognitive.add(b7.getText().toString());
                }
                if(b8.isChecked()){
                    score = score + 1;
                    cognitive.add(b8.getText().toString());
                }
                if(b9.isChecked()){
                    score = score + 2;
                    cognitive.add(b9.getText().toString());
                }
                if(b10.isChecked()){
                    score = score + 1;
                    behaviours.add(b10.getText().toString());
                }
                if(b11.isChecked()){
                    score = score + 1;
                    behaviours.add(b11.getText().toString());
                }
                if(b12.isChecked()){
                    score = score + 2;
                    behaviours.add(b12.getText().toString());
                }

                SharedPreferences settings = getBaseContext().getSharedPreferences("score", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("stress_score", score.toString());
                editor.putString("stress_today", score.toString());
                editor.commit();

                SharedPreferences setting1 = getBaseContext().getSharedPreferences("previousstress", 0);
                SharedPreferences.Editor editor1 = setting1.edit();
                Date datespecific = new Date();
                String specificday = ConvertDate(datespecific);
                Gson gson = new Gson();
                String phy = gson.toJson(physical);
                String emo = gson.toJson(emtional);
                String cog = gson.toJson(cognitive);
                String beh = gson.toJson(behaviours);
                editor1.putString("physical",phy);
                editor1.putString("emotional",emo);
                editor1.putString("cognitive",cog);
                editor1.putString("behaviours",beh);
                editor1.putString("specifictoday",specificday);
                editor1.commit();

                Intent testintent = new Intent(StressTest.this,StressLevel.class);
                testintent.putExtra("pagechange","StressTest");
                testintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(StressTest.this,FactPage.class);
                testintent.putExtra("pagechange","StressTest");
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        //Refresh your stuff here
    }

    @Override
    public void onBackPressed() {
        finish();
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
}
