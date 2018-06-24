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
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import android.widget.ViewFlipper;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by Barry on 1/9/17.
 */

public class IntroductionPage extends AppCompatActivity implements GestureDetector.OnGestureListener {
    ViewFlipper viewFlipper;
    Button button1;
    Button button2;
    Button button3;
    Button skip1;
    Button skip2;
    Button skip3;
    TimePicker timepicker;
    View item04View;
    // 定义手势检测器实例
    GestureDetector detector;
    //定义一个动画数组，用于为ViewFlipper指定切换动画效果
    Animation[] animations = new Animation[4];
    //定义手势动作两点之间的最小距离
    final int FLIP_DISTANCE = 50;
    Button next;
    Button  previous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        timepicker = (TimePicker) findViewById(R.id.timepicker2);
        button1 = (Button) findViewById(R.id.next1);
        button2 = (Button) findViewById(R.id.next2);
        button3 = (Button) findViewById(R.id.next3);
        skip1 = (Button) findViewById(R.id.skip1);
        skip2 = (Button) findViewById(R.id.skip2);
        skip3= (Button) findViewById(R.id.skip3);
        set_timepicker_text_colour(timepicker);
        detector = new GestureDetector(this);

        animations[0] = AnimationUtils.loadAnimation(this
                , R.anim.left_in);
        animations[1] = AnimationUtils.loadAnimation(this
                , R.anim.left_out);
        animations[2] = AnimationUtils.loadAnimation(this
                , R.anim.right_in);
        animations[3] = AnimationUtils.loadAnimation(this
                , R.anim.right_out);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(animations[0]);
                viewFlipper.setOutAnimation(animations[1]);
                viewFlipper.showNext();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(animations[0]);
                viewFlipper.setOutAnimation(animations[1]);
                viewFlipper.showNext();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(animations[0]);
                viewFlipper.setOutAnimation(animations[1]);
                int hour = 12;
                int min = 12;


    hour=timepicker.getHour();
    min=timepicker.getMinute();
    setnotification(hour,min);


                SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("hour", String.valueOf(hour));
                editor.putString("min", String.valueOf(min));
                editor.putString("turn", "on");
                editor.commit();

                Intent testintent = new Intent(IntroductionPage.this,BottomBar.class);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        skip1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(animations[0]);
                viewFlipper.setOutAnimation(animations[1]);
                Intent testintent = new Intent(IntroductionPage.this,BottomBar.class);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        skip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(animations[0]);
                viewFlipper.setOutAnimation(animations[1]);
                Intent testintent = new Intent(IntroductionPage.this,BottomBar.class);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
        skip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewFlipper.setInAnimation(animations[0]);
                viewFlipper.setOutAnimation(animations[1]);
                Intent testintent = new Intent(IntroductionPage.this,BottomBar.class);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });
    }

    // 定义添加ImageView的工具方法
    private View addImageView(int resId)
    {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(resId);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }


    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY)
    {
/*
* 如果第一个触点事件的X座标大于第二个触点事件的X座标超过FLIP_DISTANCE
* 也就是手势从右向左滑。
*/
        if (event1.getX() - event2.getX() > FLIP_DISTANCE)
        {
// 为flipper设置切换的的动画效果
            viewFlipper.setInAnimation(animations[0]);
            viewFlipper.setOutAnimation(animations[1]);
            if(viewFlipper.getDisplayedChild()==2){
                viewFlipper.stopFlipping();
            } else{
                viewFlipper.showNext();
            }
            return true;
        }
/*
* 如果第二个触点事件的X座标大于第一个触点事件的X座标超过FLIP_DISTANCE
* 也就是手势从右向左滑。
*/
        else if (event2.getX() - event1.getX() > FLIP_DISTANCE)
        {
// 为flipper设置切换的的动画效果
            viewFlipper.setInAnimation(animations[0]);
            viewFlipper.setOutAnimation(animations[1]);
if(viewFlipper.getDisplayedChild()==0){
    viewFlipper.stopFlipping();
} else{
    viewFlipper.showPrevious();

}

            return true;
        }
        return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
//将该Activity上的触碰事件交给GestureDetector处理
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent arg0)
    {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent event)
    {
    }
    @Override
    public boolean onScroll(MotionEvent event1, MotionEvent event2,
                            float arg2, float arg3)
    {
        return false;
    }


    @Override
    public void onShowPress(MotionEvent event)
    {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent event)
    {
        return false;
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
        Intent intent = new Intent();
        intent.setAction("testalarm0");
//        PendingIntent pendingIntent=PendingIntent.getBroadcast(getBaseContext(),0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
    }


    public  String convert(String ampm){
        if(ampm.contains("am")){
            ampm=ampm.substring(0,ampm.length()-2);
        }else if(ampm.contains("pm")){
            ampm=ampm.substring(0,ampm.length()-2);
            int hour=Integer.parseInt(ampm.split(":")[0]);
            if(hour<12){
                ampm=(hour+12)+":"+ampm.split(":")[1];
            }
        }
        return ampm;
    }
}
