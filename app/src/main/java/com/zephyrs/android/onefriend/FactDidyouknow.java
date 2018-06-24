package com.zephyrs.android.onefriend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * Created by Barry on 11/9/17.
 */

public class FactDidyouknow extends AppCompatActivity {
    protected String[] mMonths = new String[] {
            " ", "Stress", "Depression","Substance"
    };
    Integer score;
    private View vbarchart;
    float Frequency1;
    float Frequency2;
    float Frequency3;
    float Frequency4;
    float Frequency5;
    ScrollView scroll;
    ViewGroup appear;
    Button readmore;
    ScrollView scrollview;
    BarChart barChart;
    BarData theData;
    ArrayList<String> unitname = new ArrayList<>();
    RadioButton male;
    RadioButton female;
    RadioButton person;
    RadioButton age16;
    RadioButton age25;
    Button back;
    TextView bartext;
    LinearLayout root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fact_didyouknow);

        appear = (ViewGroup) findViewById(R.id.appear);
        barChart = (BarChart) findViewById(R.id.barchart);
//        readmore = (Button) findViewById(R.id.read_more);
        back = (Button) findViewById(R.id.back_button);
        male = (RadioButton) findViewById(R.id.Male);
        female = (RadioButton) findViewById(R.id.Female);
        person = (RadioButton) findViewById(R.id.Person);
        age16 = (RadioButton) findViewById(R.id.age16);
        age25 = (RadioButton) findViewById(R.id.age25);
        bartext = (TextView) findViewById(R.id.barcharttext);
        Frequency1 = 0;
        Frequency2 = 0;
        Frequency3 = 0;

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        age16.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        male.setOnCheckedChangeListener(cb);
        female.setOnCheckedChangeListener(cb);
        person.setOnCheckedChangeListener(cb);
        age16.setOnCheckedChangeListener(cb);
        age25.setOnCheckedChangeListener(cb);
        male.setChecked(true);
        age16.setChecked(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private CompoundButton.OnCheckedChangeListener cb = new CompoundButton.OnCheckedChangeListener() { //实例化一个cb
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(male.isChecked()&&age16.isChecked()){
                Frequency1=120.3f;
                Frequency2=56.3f;
                Frequency3=201.0f;
                bartext.setText("Males From 16 to 24 Years Old");
            }
            if(male.isChecked()&&age25.isChecked()){
                Frequency1=162.8f;
                Frequency2=99.0f;
                Frequency3=159.9f;
                bartext.setText("Males From 25 to 34 Years Old");
            }
            if(female.isChecked()&&age16.isChecked()){
                Frequency1=270.9f;
                Frequency2=105.0f;
                Frequency3=122.5f;
                bartext.setText("Females From 16 to 24 Years Old");
            }
            if(female.isChecked()&&age25.isChecked()){
                Frequency1=297.0f;
                Frequency2=121.9f;
                Frequency3=46.5f;
                bartext.setText("Females From 24 to 25 Years Old");
            }
            if(person.isChecked()&&age16.isChecked()){
                Frequency1=391.3f;
                Frequency2=161.4f;
                Frequency3=323.5f;
                bartext.setText("Persons From 16 to 24 Years Old");
            }
            if(person.isChecked()&&age25.isChecked()){
                Frequency1=459.7f;
                Frequency2=220.9f;
                Frequency3=206.4f;
                bartext.setText("Persons From 24 to 25 Years Old");
            }

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
            entries.add(new BarEntry(1, Frequency1));
            entries.add(new BarEntry(2, Frequency2));
            entries.add(new BarEntry(3, Frequency3));
            BarDataSet set1 = new BarDataSet(entries, "Bar");

            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(Color.parseColor("#76D7C4"));
            colors.add(Color.parseColor("#ffcc65"));
            colors.add(Color.parseColor("#f57f76"));
            set1.setColors(colors);
            set1.setValueTextColor(Color.rgb(60, 220, 78));
            set1.setValueTextSize(10f);
            set1.setDrawValues(false);
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
//            float barWidth = 1f; // x2 dataset

            BarData d = new BarData(set1);
            d.setDrawValues(false);
            barChart.setData(d);
            barChart.getAxisRight().setEnabled(false);
            barChart.getAxisLeft().setDrawGridLines(false);
            barChart.getXAxis().setDrawGridLines(false);
            barChart.getXAxis().isDrawLabelsEnabled();
            barChart.setTouchEnabled(true);
            barChart.setDragEnabled(false);
            barChart.animateXY(400, 800);
            barChart.getDescription().setText("Population (Thousands)");
            barChart.getDescription().setXOffset(240f);
            barChart.getDescription().setYOffset(322f);
            Legend l = barChart.getLegend();
            l.setEnabled(false);
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return mMonths[(int) value % mMonths.length];
                }
            });
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            barChart.invalidate();
//            d.setBarWidth(barWidth);


        }
    };




}
