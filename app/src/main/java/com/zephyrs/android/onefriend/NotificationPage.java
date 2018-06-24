package com.zephyrs.android.onefriend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Locale;

import static com.zephyrs.android.onefriend.R.id.timepicker;

/**
 * Created by Barry on 20/9/17.
 */

public class NotificationPage extends AppCompatActivity {
    Button back;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button send;
    EditText subject1;
    EditText comment;
    TextView t1;
    TextView t2;
    TextView t3;
    TimePicker timePicker;
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_page);
        t1 = (TextView) findViewById(R.id.textView1);
        back = (Button) findViewById(R.id.back_button);
        timePicker = (TimePicker)findViewById(timepicker);
        set = (Button) findViewById(R.id.setalarm);
        set_timepicker_text_colour(timePicker);
        SharedPreferences ppstress = getBaseContext().getSharedPreferences("previousstress", 0);
        int hour = Integer.valueOf(ppstress.getString("hour","0"));
        int min = Integer.valueOf(ppstress.getString("min","0"));
        timePicker.setHour(hour);
        timePicker.setMinute(min);


        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int hour=timePicker.getHour();
                int min=timePicker.getMinute();
                setnotification(hour,min);
                SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("hour", String.valueOf(hour));
                editor.putString("min", String.valueOf(min));
                editor.putString("turn", "on");
                editor.commit();
                onBackPressed();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade, R.anim.hold);
//                Intent testintent = new Intent(getBaseContext(),BottomBar.class);
//                startActivity(testintent);
            }
        });




    }
    private void set_timepicker_text_colour(TimePicker time_picker){
        Resources system = Resources.getSystem();
        int hour_numberpicker_id = system.getIdentifier("hour", "id", "android");
        int minute_numberpicker_id = system.getIdentifier("minute", "id", "android");
        int ampm_numberpicker_id = system.getIdentifier("amPm", "id", "android");

        NumberPicker hour_numberpicker = (NumberPicker) time_picker.findViewById(hour_numberpicker_id);
        NumberPicker minute_numberpicker = (NumberPicker) time_picker.findViewById(minute_numberpicker_id);
        NumberPicker ampm_numberpicker = (NumberPicker) time_picker.findViewById(ampm_numberpicker_id);

        set_numberpicker_text_colour(hour_numberpicker);
        set_numberpicker_text_colour(minute_numberpicker);
        set_numberpicker_text_colour(ampm_numberpicker);
    }
    private void set_numberpicker_text_colour(NumberPicker number_picker){
        final int count = number_picker.getChildCount();
        final int color = getResources().getColor(R.color.buttonColor);

        for(int i = 0; i < count; i++){
            View child = number_picker.getChildAt(i);

            try{
                Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
                wheelpaint_field.setAccessible(true);

                ((Paint)wheelpaint_field.get(number_picker)).setColor(color);
                ((EditText)child).setTextColor(color);
                number_picker.invalidate();
            }
            catch(NoSuchFieldException e){
                Log.w("setColor", e);
            }
            catch(IllegalAccessException e){
                Log.w("setColor", e);
            }
            catch(IllegalArgumentException e){
                Log.w("setColor", e);
            }
        }
    }
    public void setnotification(int hour,int min){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

