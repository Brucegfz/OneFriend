package com.zephyrs.android.onefriend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Barry on 4/9/17.
 */

public class ReportPage extends AppCompatActivity implements OnChartValueSelectedListener, View.OnClickListener{
    Button back;
    private BarChart mBarChart;
    private CombinedChart mChart;
    Integer ms1;
    Integer ms2;
    Integer ms3;
    Integer ms4;
    Integer ms5;
    Integer ms6;
    float reduce;
    float getscore;
    TextView sleep;
    TextView exercise;
    TextView meditation;
    TextView social;
    TextView water;
    TextView hobby;
    Bundle testb = new Bundle();
    Integer num;
    View testview;
    ImageButton yesterday;
    ImageButton today;
    float gettoday;
    float getyesterday;
    ImageButton current;
    Button previous;
    Button email;
    Button changeactivity;
    String test;
    Button next;
    String Mon;
    String Tue;
    String Wed;
    String Thu;
    String Fri;
    String Sat;
    String Sun;

    Button download;
    CombinedChart sleepchart;
    CombinedChart exercisechart;
    CombinedChart meditationchart;
    CombinedChart waterchart;
    CombinedChart socialchart;
    CombinedChart hobbychart;
    CombinedChart totalchart;
    Integer checktimes =  0;
    int heheee = 0;
    int keko = 1;
    TextView dateofreport;
    //    DatabaseReference myRef2;
//    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    Integer i = 0;
    float hehe;
    Long yy;
    Integer week;
    String currentday;
    String specificday;
    TextView textview1;
    ArrayList<Report> allreport = new ArrayList<>();
    ArrayList<String> alldata = new ArrayList<>();
    String pp;

    Adapter madapter;
    ArrayList<String> alllevel = new ArrayList<>();
    float stress; //use for check previous stress level
    String stressday; //use for check previous stress level day
    Number number;
    Intent ii = new Intent();
    protected String[] mMonths = new String[] {" ","Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"};


    //pdf
    TextView sleeppdf;
    TextView exercisepdf;
    TextView meditationpdf;
    TextView socialpdf;
    TextView waterpdf;
    TextView hobbypdf;
    ImageButton yesterdaypdf;
    ImageButton todaypdf;
    ImageButton currentpdf;
    TextView currentdatepdf;
    TextView physicalsymptoms;
    TextView emotionalsymptoms;
    TextView cognitivesymptoms;
    TextView behaviourssymptoms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report_page);
        back = (Button) findViewById(R.id.back_button);
//        mBarChart = (BarChart) findViewById(R.id.chart1);
        mChart = (CombinedChart) findViewById(R.id.chart12);
        sleep = (TextView) findViewById(R.id.sleep_record);
        meditation = (TextView) findViewById(R.id.meditation_record);
        social = (TextView) findViewById(R.id.social_record);
        water = (TextView) findViewById(R.id.water_record);
        exercise = (TextView) findViewById(R.id.exercise_record);
        hobby = (TextView) findViewById(R.id.hobby_record);
        yesterday = (ImageButton) findViewById(R.id.head1);
        today = (ImageButton) findViewById(R.id.head2);
        current = (ImageButton) findViewById(R.id.head3);
        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        dateofreport = (TextView) findViewById(R.id.dateofreport);
        textview1 = (TextView) findViewById(R.id.textView4);
        changeactivity = (Button) findViewById(R.id.changeactivity);
        download = (Button) findViewById(R.id.download_button);
        sleepchart = (CombinedChart) findViewById(R.id.chart_sleep);
        exercisechart = (CombinedChart) findViewById(R.id.chart_exercise);
        meditationchart = (CombinedChart) findViewById(R.id.chart_meditaion);
        socialchart = (CombinedChart) findViewById(R.id.chart_social);
        waterchart = (CombinedChart) findViewById(R.id.chart_water);
        hobbychart = (CombinedChart) findViewById(R.id.chart_hobby);
        totalchart = (CombinedChart) findViewById(R.id.chart_total);

//pdf
        sleeppdf = (TextView) findViewById(R.id.sleep_record_pdf);
        exercisepdf = (TextView) findViewById(R.id.exercise_record_pdf);
        meditationpdf = (TextView) findViewById(R.id.meditation_record_pdf);
        socialpdf = (TextView) findViewById(R.id.social_record_pdf);
        waterpdf = (TextView) findViewById(R.id.water_record_pdf);
        hobbypdf = (TextView) findViewById(R.id.hobby_record_pdf);
        yesterdaypdf = (ImageButton) findViewById(R.id.headyesterday_pdf);
        todaypdf = (ImageButton) findViewById(R.id.headtoday_pdf);
        currentpdf = (ImageButton) findViewById(R.id.headcurrent_pdf);
        currentdatepdf = (TextView) findViewById(R.id.currentdate_pdf);
        physicalsymptoms = (TextView) findViewById(R.id.physicalresult_pdf);
        emotionalsymptoms = (TextView) findViewById(R.id.emotionalresult_pdf);
        cognitivesymptoms = (TextView) findViewById(R.id.cognitiveresult_pdf);
        behaviourssymptoms = (TextView) findViewById(R.id.behavioursresult_pdf);



//        email = (Button) findViewById(R.id.email_button);
        SharedPreferences ppstress = getBaseContext().getSharedPreferences("previousstress", 0);
        Mon = ppstress.getString("1","-2");
        Tue = ppstress.getString("2","-2");
        Wed = ppstress.getString("3","-2");
        Thu = ppstress.getString("4","-2");
        Fri = ppstress.getString("5","-2");
        Sat = ppstress.getString("6","-2");
        Sun = ppstress.getString("7","-2");


        Gson gson = new Gson();
        String physicaljson = ppstress.getString("physical","");
        String emotionaljson = ppstress.getString("emotional","");
        String cognitivejson = ppstress.getString("cognitive","");
        String behaviourjson = ppstress.getString("behaviours","");
        String specifictoday = ppstress.getString("specifictoday","");
        ArrayList<String> physicalsystom = gson.fromJson(physicaljson, new TypeToken<ArrayList<String>>(){}.getType());
        ArrayList<String> emotionsystom = gson.fromJson(emotionaljson, new TypeToken<ArrayList<String>>(){}.getType());
        ArrayList<String> cognitivesystom = gson.fromJson(cognitivejson, new TypeToken<ArrayList<String>>(){}.getType());
        ArrayList<String> behavioursystom = gson.fromJson(behaviourjson, new TypeToken<ArrayList<String>>(){}.getType());

        String physicalresult="";
        String emotionalresult="";
        String cognitiveresult="";
        String behaviourresult="";


        Date checkdate = new Date();
        String checktoday = ConvertDate(checkdate);
        if(checktoday.equals(specifictoday)){
            for(int index=0;index<physicalsystom.size();index++){
                if(index == physicalsystom.size()-1){
                    physicalresult = physicalresult + physicalsystom.get(index);
                } else {
                    physicalresult = physicalresult + physicalsystom.get(index)+"、";
                }
            }
            physicalsymptoms.setText(physicalresult);

            for(int index=0;index<emotionsystom.size();index++){
                if(index == emotionsystom.size()-1){
                    emotionalresult = emotionalresult + emotionsystom.get(index);
                } else {
                    emotionalresult = emotionalresult + emotionsystom.get(index)+"、";
                }
            }
            emotionalsymptoms.setText(emotionalresult);

            for(int index=0;index<cognitivesystom.size();index++){
                if(index == cognitivesystom.size()-1){
                    cognitiveresult = cognitiveresult + cognitivesystom.get(index);
                } else {
                    cognitiveresult = cognitiveresult + cognitivesystom.get(index)+"、";
                }
            }
            cognitivesymptoms.setText(cognitiveresult);

            for(int index=0;index<behavioursystom.size();index++){
                if(index == behavioursystom.size()-1){
                    behaviourresult = behaviourresult + behavioursystom.get(index);
                } else {
                    behaviourresult = behaviourresult + behavioursystom.get(index)+"、";
                }
            }
            behaviourssymptoms.setText(behaviourresult);
        }



//        testview = (View) findViewById(R.id.hoho);

        for(int index=1;index<8;index++){
            String json = ppstress.getString("SerializableObject"+index, "");
            if(json != ""){
                allreport.add(gson.fromJson(json, Report.class));
            }else{
                allreport.add(new Report("0 Minute",0l,"0 Minute","0 Minute","0 Hour","0 Minute",0l,"0 Minute"));
            }
        }
//        allreport = gson.fromJson(json, new TypeToken<List<Report>>(){}.getType());
        allreport.size();

//        pp = ppstress.getString("pp","-2");
//        String oo = pp;


        Date datespecific = new Date();

        specificday = ConvertDate(datespecific);
        dateofreport.setText(specificday);

        currentday = specificday;
        currentdatepdf.setText(specificday);
        final String checknext = getSpecifiedDayAfter(specificday);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testintent = new Intent(getBaseContext(), BottomBar.class);
                startActivity(testintent);
                overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getday = getSpecifiedDayAfter(currentday);
                if(!checknext.equals(getday)){
                    dateofreport.setText(getday);
                    getReport(getday);
                    currentday = getday;
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getday = getSpecifiedDayBefore(currentday);
                dateofreport.setText(getday);
                getReport(getday);
                currentday = getday;
            }
        });


        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
                boolean boolean_save;
                Resources mResources = getResources();

                View content = findViewById(R.id.pdfchart);
                SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                SharedPreferences.Editor editor = settings.edit();
                ArrayList<String> allreport = new ArrayList<String>();
                allreport.add(specificday);
                Gson gson = new Gson();
                String allpdf = gson.toJson(allreport);
                editor.putString("allpdf", allpdf);
                editor.commit();

                PdfDocument document = new PdfDocument();

                for(int i=0;i<5;i++) {
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(displayMetrics.widthPixels, displayMetrics.heightPixels, 9).create();
                    PdfDocument.Page page = document.startPage(pageInfo);
                    if(i==0){
                        content = findViewById(R.id.page1);
                    } else if(i==1) {
                        content = findViewById(R.id.page2);
                    } else if(i==2) {
                        content = findViewById(R.id.page3);
                    } else if(i==3) {
                        content = findViewById(R.id.page4);
                    } else if(i==4) {
                        content = findViewById(R.id.page5);
                    }
                    Canvas canvas = page.getCanvas();
                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawPaint(paint);
                    paint.setColor(Color.BLUE);
                    content.draw(canvas);
                    document.finishPage(page);
                }


                File root = getFilesDir();
                final File file = new File(root,specificday+".pdf");
                if(file.exists()){
                    file.delete();
                }

                // write the document content
                String targetPdf = "/sdcard/test.pdf";


                try {
                    document.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // close the document
                document.close();

                try
                {
                    //Open the PDF
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Uri photoURI = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".my.package.name.provider", file);
                    intent.setDataAndType(photoURI, "application/pdf");
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                }
                catch(Exception e)
                {

                }
            }
        });


        changeactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = "yy min";
                if(checktimes > 6){
                    checktimes = 0;
                }
                checktimes = checktimes + 1;
                createcombinechart(mChart,checktimes);
            }
        });

        SharedPreferences settings = this.getSharedPreferences("score", 0);
        getscore = Integer.valueOf(settings.getString("stress_head","0"));
        gettoday = Integer.valueOf(settings.getString("stress_today","0"));
        ms1 = Integer.valueOf(settings.getString("seekbar1","0"));
        ms2 = Integer.valueOf(settings.getString("seekbar2","0"));
        ms3 = Integer.valueOf(settings.getString("seekbar3","0"));
        ms4 = Integer.valueOf(settings.getString("seekbar4","0"));
        ms5 = Integer.valueOf(settings.getString("seekbar5","0"));
        ms6 = Integer.valueOf(settings.getString("seekbar6","0"));
        reduce = Integer.valueOf(settings.getString("stress_reduce","0"));
        float difference = getscore - reduce;
        checkheadtoday(gettoday);
        checkheadcurrent(difference);
        checkheadyesterday();
        checkdetail();
        stressday = specificday; //current day
        num = getNumber(getWeek(datespecific));
        week = num;
        stress = checkstresslevel(gettoday);

        stressday = specificday; //current day
        num = getNumber(getWeek(datespecific));
        week = num;
        stress = checkstresslevel(gettoday);
        String s = "yy min";
        String a=s.substring(0,s.indexOf(" "));

        generatecombinechart(mChart,0,0,0,0,0,0,0,0,"","#00000000");
        createcombinechart(sleepchart,1);
        createcombinechart(exercisechart,2);
        createcombinechart(meditationchart,3);
        createcombinechart(socialchart,4);
        createcombinechart(waterchart,5);
        createcombinechart(hobbychart,6);
        createcombinechart(totalchart,7);
    }



    private void createPdf2(){
        boolean boolean_save;
        Resources mResources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.id.head1);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);



        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);

        document.finishPage(page);


        // write the document content
        String targetPdf = "/sdcard/test.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            boolean_save=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }



    private void createPdf(){
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(100, 100, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        paint.setColor(Color.RED);

        canvas.drawCircle(50, 50, 30, paint);

        // finish the page
        document.finishPage(page);

        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(500, 500, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(200, 200, 100, paint);
        document.finishPage(page);

        // write the document content
//        String targetPdf = "data/data/" + getBaseContext().getPackageName()+"/files"+ "/uu.pdf";
        String targetPdf = getFilesDir()+"/uu.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }


    public void createpdfFile (String fileName, PdfDocument document){
        FileOutputStream os = null;
        try {
            //Create folder in which you want to save Pdf documents
            File folder = new File(Environment.getRootDirectory(), "PdfFolder");
            if(!folder.exists()){
                folder.mkdirs();
            }
            //Create pdf file
            File mPdfFile = new File(folder,fileName);
            //Make sure that there isn't a file with the same name
            if(!mPdfFile.exists()) {
                mPdfFile.createNewFile();
            }
            os =  new FileOutputStream(mPdfFile);
            document.writeTo(os);

        } catch (IOException e) {

            e.printStackTrace();
        }finally{
            document.close();
            try{
                if(os!=null)os.close();
            }catch (Exception e){

            }
        }
    }

    public void generatecombinechart(final CombinedChart cChart, final float mon, final float tue, final float wed, final float thu, final float fri, final float sat, final float sun, final float week, final String activity, final String barcolor){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return "create successfully";
            }

            @Override
            protected void onPostExecute(String courses) {
                cChart.setDragEnabled(false);
                cChart.setTouchEnabled(false);
                cChart.getDescription().setText(" ");
                cChart.setHighlightFullBarEnabled(true);
                cChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                        CombinedChart.DrawOrder.BAR,CombinedChart.DrawOrder.LINE
                });
                cChart.getXAxis().setDrawLabels(true);
                Legend l = cChart.getLegend();
                l.setWordWrapEnabled(true);
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                YAxis rightAxis = cChart.getAxisRight();
                rightAxis.setDrawGridLines(true);
                rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
                YAxis leftAxis = cChart.getAxisLeft();
                leftAxis.setDrawGridLines(true);
                leftAxis.setAxisMinimum(0f);
                cChart.setDrawGridBackground(false);
                XAxis xAxis = cChart.getXAxis();
                xAxis.setAxisMinimum(0f);
                xAxis.setGranularity(1f);
                xAxis.setDrawGridLines(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return mMonths[(int) value % mMonths.length];
                    }
                });

                CombinedData data = new CombinedData();
                data.setData(getsingleBarData(mon,tue,wed,thu,fri,sat,sun,week,activity,barcolor));
                data.setData(generateLineData());
                xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                cChart.setData(data);
                cChart.getAxisRight().setEnabled(false);
                cChart.getAxisLeft().setEnabled(false);
                cChart.animateXY(500, 500);
                cChart.invalidate();
            }
        }.execute();
    }

    private LineData generateLineData() {

        LineData d = new LineData();

        final ArrayList<Entry> entries = new ArrayList<Entry>();
        final Bundle extras = new Bundle();
        Date datespecific = new Date();
        stressday = specificday; //current day
        num = getNumber(getWeek(datespecific));
        week = num;
        stress = checkstresslevel(gettoday);

        if(!Mon.equals("-2")){
            entries.add(new Entry(1, Float.valueOf(Mon)));
        }
        if(!Tue.equals("-2")){
            entries.add(new Entry(2, Float.valueOf(Tue)));
        }
        if(!Wed.equals("-2")){
            entries.add(new Entry(3, Float.valueOf(Wed)));
        }
        if(!Thu.equals("-2")){
            entries.add(new Entry(4, Float.valueOf(Thu)));
        }
        if(!Fri.equals("-2")){
            entries.add(new Entry(5, Float.valueOf(Fri)));
        }
        if(!Sat.equals("-2")){
            entries.add(new Entry(6, Float.valueOf(Sat)));
        }
        if(!Sun.equals("-2")){
            entries.add(new Entry(7, Float.valueOf(Sun)));
        }

        entries.add(new Entry(num, stress));

        LineDataSet set = new LineDataSet(entries, "Stress level(low to high)");
        set.setColors(Color.parseColor("#5DADE2"));
        set.setLineWidth(2.5f);
        set.setCircleColor(Color.parseColor("#76D7C4"));
        set.setCircleRadius(5f);
        set.setFillColor(Color.parseColor("#a5d079"));
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setValueTextSize(10f);
        set.setDrawValues(false);
        set.setValueTextColor(Color.parseColor("#f57f76"));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);
        return d;
    }


    private BarData getBarData() {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(1, new float[]{
                Float.valueOf(allreport.get(0).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(0).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(0).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(0).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                Float.valueOf(allreport.get(0).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                Float.valueOf(allreport.get(0).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(2, new float[]{
                Float.valueOf(allreport.get(1).Sleep.substring(0, allreport.get(1).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(1).Exercise.substring(0, allreport.get(1).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(1).Meditation.substring(0, allreport.get(1).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(1).Social.substring(0, allreport.get(1).Social.indexOf(" "))),
                Float.valueOf(allreport.get(1).Water.substring(0, allreport.get(1).Water.indexOf(" "))),
                Float.valueOf(allreport.get(1).Hobby.substring(0, allreport.get(1).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(3, new float[]{
                Float.valueOf(allreport.get(2).Sleep.substring(0, allreport.get(2).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(2).Exercise.substring(0, allreport.get(2).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(2).Meditation.substring(0, allreport.get(2).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(2).Social.substring(0, allreport.get(2).Social.indexOf(" "))),
                Float.valueOf(allreport.get(2).Water.substring(0, allreport.get(2).Water.indexOf(" "))),
                Float.valueOf(allreport.get(2).Hobby.substring(0, allreport.get(2).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(4, new float[]{
                Float.valueOf(allreport.get(3).Sleep.substring(0, allreport.get(3).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(3).Exercise.substring(0, allreport.get(3).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(3).Meditation.substring(0, allreport.get(3).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(3).Social.substring(0, allreport.get(3).Social.indexOf(" "))),
                Float.valueOf(allreport.get(3).Water.substring(0, allreport.get(3).Water.indexOf(" "))),
                Float.valueOf(allreport.get(3).Hobby.substring(0, allreport.get(3).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(5, new float[]{
                Float.valueOf(allreport.get(4).Sleep.substring(0, allreport.get(4).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(4).Exercise.substring(0, allreport.get(4).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(4).Meditation.substring(0, allreport.get(4).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(4).Social.substring(0, allreport.get(4).Social.indexOf(" "))),
                Float.valueOf(allreport.get(4).Water.substring(0, allreport.get(4).Water.indexOf(" "))),
                Float.valueOf(allreport.get(4).Hobby.substring(0, allreport.get(4).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(6, new float[]{
                Float.valueOf(allreport.get(5).Sleep.substring(0, allreport.get(5).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(5).Exercise.substring(0, allreport.get(5).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(5).Meditation.substring(0, allreport.get(5).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(5).Social.substring(0, allreport.get(5).Social.indexOf(" "))),
                Float.valueOf(allreport.get(5).Water.substring(0, allreport.get(5).Water.indexOf(" "))),
                Float.valueOf(allreport.get(5).Hobby.substring(0, allreport.get(5).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(7, new float[]{
                Float.valueOf(allreport.get(6).Sleep.substring(0, allreport.get(6).Sleep.indexOf(" "))),
                Float.valueOf(allreport.get(6).Exercise.substring(0, allreport.get(6).Exercise.indexOf(" "))),
                Float.valueOf(allreport.get(6).Meditation.substring(0, allreport.get(6).Meditation.indexOf(" "))),
                Float.valueOf(allreport.get(6).Social.substring(0, allreport.get(6).Social.indexOf(" "))),
                Float.valueOf(allreport.get(6).Water.substring(0, allreport.get(6).Water.indexOf(" "))),
                Float.valueOf(allreport.get(6).Hobby.substring(0, allreport.get(6).Hobby.indexOf(" ")))}));
        yVals1.add(new BarEntry(num, new float[]{ms1, ms2, ms3,ms4,ms5,ms6}));
        BarDataSet set1 = new BarDataSet(yVals1, " ");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor("#76D7C4"));
        colors.add(Color.parseColor("#ffcc65"));
        colors.add(Color.parseColor("#f57f76"));
        colors.add(Color.parseColor("#cde3f2"));
        colors.add(Color.parseColor("#a5d079"));
        colors.add(Color.parseColor("#5C0CE9"));
        set1.setColors(colors);
        set1.setStackLabels(new String[]{"sleep", "exercise", "meditation","socializing","water","hobbies"});
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);

        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if(value ==0){
                    DecimalFormat df = new DecimalFormat("###");
                    return " ";
                } else{
                    DecimalFormat df = new DecimalFormat("###");
                    return ""+df.format(value);
                }
            }
        });

        data.setDrawValues(false);
        data.setValueTextColor(Color.BLACK);
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        float barWidth = 0.8f; // x2 dataset
        data.setBarWidth(barWidth);
        return data;
    }



    private BarData getsingleBarData(float mon,float tue,float wed,float thu,float fri,float sat,float sun,float week,String activity,String chartcolor) {
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(1, mon));
        yVals1.add(new BarEntry(2, tue));
        yVals1.add(new BarEntry(3, wed));
        yVals1.add(new BarEntry(4, thu));
        yVals1.add(new BarEntry(5, fri));
        yVals1.add(new BarEntry(6, sat));
        yVals1.add(new BarEntry(7, sun));


        yVals1.remove(num-1);
        yVals1.add(new BarEntry(num, week));
        BarDataSet set1;
        set1 = new BarDataSet(yVals1, activity);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.parseColor(chartcolor));
        set1.setColors(colors);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setDrawValues(true);
        data.setValueTextColor(Color.BLACK);

        data.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                if(value ==0){
                    DecimalFormat df = new DecimalFormat("###");
                    return " ";
                } else{
                    DecimalFormat df = new DecimalFormat("###");
                    return ""+df.format(value);
                }
            }
        });

        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        float barWidth = 0.8f;
        data.setBarWidth(barWidth);
        return data;
    }

    public float checkstresslevel(float difference){
        float level = 0;
        if(difference<=0){
            level = 0;
        } else if(difference>0 && difference<=2){
            level = 20;
        } else if(difference>2 && difference<=4){
            level = 25;
        } else if(difference>4 && difference<=6){
            level = 30;
        } else if(difference>6 && difference<=8){
            level = 35;
        } else if(difference>8 && difference<=10){
            level = 40;
        } else if(difference>10 && difference<=12){
            level = 45;
        }
        return level;
    }

    public void generatecombinecharttotal(final CombinedChart totalchart){
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... params) {
                return "create successfully";
            }

            @Override
            protected void onPostExecute(String courses) {
                totalchart.setDragEnabled(false);
                totalchart.setTouchEnabled(false);
                totalchart.getDescription().setText(" ");
                totalchart.setHighlightFullBarEnabled(true);
                totalchart.setDrawOrder(new CombinedChart.DrawOrder[]{
                        CombinedChart.DrawOrder.BAR,CombinedChart.DrawOrder.LINE
                });
                totalchart.getXAxis().setDrawLabels(true);
                Legend l = totalchart.getLegend();
                l.setWordWrapEnabled(true);
                l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
                l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
                l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                YAxis rightAxis = totalchart.getAxisRight();
                rightAxis.setDrawGridLines(true);
                rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
                YAxis leftAxis = totalchart.getAxisLeft();
                leftAxis.setDrawGridLines(false);
                leftAxis.setAxisMinimum(0f);
                totalchart.setDrawGridBackground(false);
                XAxis xAxis = totalchart.getXAxis();
                xAxis.setAxisMinimum(0f);
                xAxis.setGranularity(1f);
                xAxis.setDrawGridLines(true);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return mMonths[(int) value % mMonths.length];
                    }
                });
                CombinedData data = new CombinedData();
                data.setData(getBarData());
                data.setData(generateLineData());
                xAxis.setAxisMaximum(data.getXMax() + 0.25f);
                totalchart.setData(data);
                totalchart.getAxisRight().setEnabled(false);
                totalchart.getAxisLeft().setEnabled(false);
                totalchart.animateXY(1200, 1200);
                totalchart.invalidate();
            }
        }.execute();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onClick(View v) {

    }


    public void checkdetail() {

        if (ms1 == 0) {
            sleeppdf.setText("Sleep:\n"+ms1+" Hour");
            sleep.setText("Sleep:\n"+ms1+" Hour");
        } else if (ms1 < 10 && ms1 > 0) {
            sleeppdf.setText("Sleep:\n"+ms1+" Hours");
            sleep.setText("Sleep:\n"+ms1+" Hours");
        } else if (ms1 >= 10 ) {
            sleeppdf.setText("Sleep:\n"+ms1+" Hours or more");
            sleep.setText("Sleep:\n"+ms1+" Hours or more");
        }

        if (ms2 == 0) {
            exercisepdf.setText("Exercise:\n"+ms2+" Minute");
            exercise.setText("Exercise:\n"+ms2+" Minute");
        } else if (ms2 < 60 && ms2 > 0) {
            exercisepdf.setText("Exercise:\n"+ms2+" Minutes");
            exercise.setText("Exercise:\n"+ms2+" Minutes");
        } else if (ms2 >= 60 ) {
            exercisepdf.setText("Exercise:\n"+ms2+" Minutes or more");
            exercise.setText("Exercise:\n"+ms2+" Minutes or more");
        }

        if (ms3 == 0) {
            meditationpdf.setText("Exercise:\n"+ms3+" Minute");
            meditation.setText("Exercise:\n"+ms3+" Minute");
        } else if (ms3 < 15 && ms3 > 0) {
            meditationpdf.setText("Exercise:\n"+ms3+" Minutes");
            meditation.setText("Exercise:\n"+ms3+" Minutes");
        } else if (ms3 >= 15 ) {
            meditationpdf.setText("Exercise:\n"+ms3+" Minutes or more");
            meditation.setText("Exercise:\n"+ms3+" Minutes or more");
        }

        if (ms4 == 0) {
            socialpdf.setText("Social:\n"+ms4+" Minute");
            social.setText("Social:\n"+ms4+" Minute");
        } else if (ms4 < 90 && ms4 > 0) {
            socialpdf.setText("Social:\n"+ms4+" Minutes");
            social.setText("Social:\n"+ms4+" Minutes");
        } else if (ms4 >= 90 ) {
            socialpdf.setText("Social:\n"+ms4+" Minutes or more");
            social.setText("Social:\n"+ms4+" Minutes or more");
        }

        if (ms5 == 0) {
            waterpdf.setText("Water:\n"+ms5+" Glass");
            water.setText("Water:\n"+ms5+" Glass");
        } else if (ms5 < 10 && ms5 > 0) {
            waterpdf.setText("Water:\n"+ms5+" Glasses");
            water.setText("Water:\n"+ms5+" Glasses");
        } else if (ms5 >= 10 ) {
            waterpdf.setText("Water:\n"+ms5+" Glasses or more");
            water.setText("Water:\n"+ms5+" Glasses or more");
        }

        if (ms6 == 0) {
            hobbypdf.setText("Hobby:\n"+ms6+" Minute");
            hobby.setText("Hobby:\n"+ms6+" Minute");
        } else if (ms6 < 70 && ms6 > 0) {
            hobbypdf.setText("Hobby:\n"+ms6+" Minutes");
            hobby.setText("Hobby:\n"+ms6+" Minutes");
        } else if (ms6 >= 70 ) {
            hobbypdf.setText("Hobby:\n"+ms6+" Minutes or more");
            hobby.setText("Hobby:\n"+ms6+" Minutes or more");
        }
    }


    public void checkheadcurrent(float difference){
        if(difference<=0){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h7));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h7));
        } else if(difference>0 && difference<=2){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h6));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h6));
        } else if(difference>2 && difference<=4){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h5));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h5));
        } else if(difference>4 && difference<=6){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h4));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h4));
        } else if(difference>6 && difference<=8){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h3));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h3));
        } else if(difference>8 && difference<=10){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h2));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h2));
        } else if(difference>10 && difference<=12){
            currentpdf.setImageDrawable(getResources().getDrawable(R.drawable.h1));
            current.setImageDrawable(getResources().getDrawable(R.drawable.h1));
        }
    }

    public void checkheadtoday(float difference){
        if(difference<=0){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h7));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h7));
        } else if(difference>0 && difference<=2){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h6));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h6));
        } else if(difference>2 && difference<=4){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h5));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h5));
        } else if(difference>4 && difference<=6){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h4));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h4));
        } else if(difference>6 && difference<=8){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h3));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h3));
        } else if(difference>8 && difference<=10){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h2));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h2));
        } else if(difference>10 && difference<=12){
            todaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h1));
            today.setImageDrawable(getResources().getDrawable(R.drawable.h1));
        }
    }

    public void checkheadyesterday(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        myRef2 = database.getReference();
        String yesterday = getSpecifiedDayBefore(specificday);
        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(yesterday).child("Today").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long data = dataSnapshot.getValue(Long.class);
                try{
                    float head = Float.valueOf(data.toString());
                    changeyesterdayhead(head);
                }catch(Exception e){
                    changeyesterdayhead(0);
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });




    }


    public void changeyesterdayhead(float difference){
        if(difference<=0){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h7));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h7));
        } else if(difference>0 && difference<=2){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h6));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h6));
        } else if(difference>2 && difference<=4){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h5));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h5));
        } else if(difference>4 && difference<=6){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h4));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h4));
        } else if(difference>6 && difference<=8){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h3));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h3));
        } else if(difference>8 && difference<=10){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h2));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h2));
        } else if(difference>10 && difference<=12){
            yesterdaypdf.setImageDrawable(getResources().getDrawable(R.drawable.h1));
            yesterday.setImageDrawable(getResources().getDrawable(R.drawable.h1));
        }
    }

    public static String getWeek(Date date){
        String[] weeks = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
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


    public static String getSpecifiedDayBefore(String specifiedDay,int n){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-n);

        String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayBefore(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day-1);

        String dayBefore=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayBefore;
    }

    public static String getSpecifiedDayAfter(String specifiedDay,int n){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+n);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }

    public static String getSpecifiedDayAfter(String specifiedDay){
        Calendar c = Calendar.getInstance();
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day=c.get(Calendar.DATE);
        c.set(Calendar.DATE,day+1);

        String dayAfter=new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return dayAfter;
    }


    public void getReport(String getday){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        myRef2 = database.getReference();
        String yesterday = getSpecifiedDayBefore(getday);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef2 = FirebaseDatabase.getInstance().getReference();

        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(yesterday).child("Today").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long data = dataSnapshot.getValue(Long.class);
                try{
                    float head = Float.valueOf(data.toString());
                    changeyesterdayhead(head);
                }catch(Exception e){
                    changeyesterdayhead(0);
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Today").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Long data = dataSnapshot.getValue(Long.class);
                try{
                    float head = Float.valueOf(data.toString());
                    checkheadtoday(head);
                }catch(Exception e){
                    checkheadtoday(0);
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Finally").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                Long data = dataSnapshot.getValue(Long.class);
                try{
                    float difference = Float.valueOf(data.toString());
                    checkheadcurrent(difference);
                }catch(Exception e){
                    checkheadcurrent(0);
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Sleep").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                String data = dataSnapshot.getValue(String.class);
                try{
                    sleep.setText("Sleep:"+"\n"+data.toString());
                }catch(Exception e){
                    sleep.setText("Sleep:"+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Exercise").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                String data = dataSnapshot.getValue(String.class);
                try{
                    exercise.setText("Exercise:"+"\n"+data.toString());
                }catch(Exception e){
                    exercise.setText("Exercise:"+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Meditation").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                String data = dataSnapshot.getValue(String.class);
                try{
                    meditation.setText("Meditation:"+"\n"+data.toString());
                }catch(Exception e){
                    meditation.setText("Meditation:"+"\n");
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Social").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                String data = dataSnapshot.getValue(String.class);
                try{
                    social.setText("Social:"+"\n"+data.toString());
                }catch(Exception e){
                    social.setText("Social:"+"\n");
                }

            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Water").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                String data = dataSnapshot.getValue(String.class);
                try{
                    water.setText("Water:"+"\n"+data.toString());
                }catch(Exception e){
                    water.setText("Water:"+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        myRef2.child("Profile").child(currentUser.getUid()).child("Report").child(getday).child("Hobby").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<String> d = new ArrayList<String>();
                String data = dataSnapshot.getValue(String.class);
                try{
                    hobby.setText("Hobby:"+"\n"+data.toString());
                }catch(Exception e){
                    hobby.setText("Hobby:"+"\n");
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
//        String dd = testb.getString("0","pp");
//        generatecombinechart();

    }

    public Integer getNumber(String week){
        Integer number = 0;
        if(week=="Mon"){
            number =1;
        }
        if(week=="Tue"){
            number =2;
        }
        if(week=="Wed"){
            number =3;
        }
        if(week=="Thu"){
            number =4;
        }
        if(week=="Fri"){
            number =5;
        }
        if(week=="Sat"){
            number =6;
        }
        if(week=="Sun"){
            number =7;
        }
        return number;
    }


    public float checksleep(String sleep){
        float point = 0;
        if(sleep == "3 Hours or less"){
            point = 1;
        }
        if(sleep == "4-5 Hours"){
            point = 2;
        }
        if(sleep == "6-7 Hours"){
            point = 3;
        }
        if(sleep == "8-9 Hours"){
            point = 4;
        }
        if(sleep == "10 Hours or more"){
            point = 5;
        }
        return point;
    }
    public float checkexercise(String sleep){
        float point = 0;
        if(sleep == "0 Minutes"){
            point = 1;
        }
        if(sleep == "4-5 Hours"){
            point = 2;
        }
        if(sleep == "6-7 Hours"){
            point = 3;
        }
        if(sleep == "8-9 Hours"){
            point = 4;
        }
        if(sleep == "10 Hours or more"){
            point = 5;
        }
        return point;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void createcombinechart(CombinedChart choosechart, Integer checktime ){
        if(checktime == 1) {
            if(choosechart == mChart){
                changeactivity.setText("Sleep");
            }
            generatecombinechart(
                    choosechart,
                    Float.valueOf(allreport.get(0).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(allreport.get(1).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(allreport.get(2).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(allreport.get(3).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(allreport.get(4).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(allreport.get(5).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(allreport.get(6).Sleep.substring(0, allreport.get(0).Sleep.indexOf(" "))),
                    Float.valueOf(ms1),"Sleep (Hours)",
                    "#ffcc65"

            );
        }
        if(checktime == 2) {
            if(choosechart == mChart){
                changeactivity.setText("Exercise");
            }
            generatecombinechart(
                    choosechart,
                    Float.valueOf(allreport.get(0).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(allreport.get(1).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(allreport.get(2).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(allreport.get(3).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(allreport.get(4).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(allreport.get(5).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(allreport.get(6).Exercise.substring(0, allreport.get(0).Exercise.indexOf(" "))),
                    Float.valueOf(ms2),"Exercise (Minutes)",
                    "#f57f76"
            );
        }
        if(checktime == 3) {
            if(choosechart == mChart){
                changeactivity.setText("Meditation");
            }
            generatecombinechart(
                    choosechart,
                    Float.valueOf(allreport.get(0).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(allreport.get(1).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(allreport.get(2).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(allreport.get(3).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(allreport.get(4).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(allreport.get(5).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(allreport.get(6).Meditation.substring(0, allreport.get(0).Meditation.indexOf(" "))),
                    Float.valueOf(ms3),"Meditation (Minutes)",
                    "#2DE3AC"
            );
        }
        if(checktime == 4) {
            if(choosechart == mChart){
                changeactivity.setText("Social");
            }
            generatecombinechart(
                    choosechart,
                    Float.valueOf(allreport.get(0).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(allreport.get(1).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(allreport.get(2).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(allreport.get(3).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(allreport.get(4).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(allreport.get(5).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(allreport.get(6).Social.substring(0, allreport.get(0).Social.indexOf(" "))),
                    Float.valueOf(ms4),"Social (Minutes)",
                    "#8B2DE3"
            );
        }
        if(checktime == 5) {
            if(choosechart == mChart){
                changeactivity.setText("Water");
            }
            generatecombinechart(
                    choosechart,
                    Float.valueOf(allreport.get(0).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(allreport.get(1).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(allreport.get(2).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(allreport.get(3).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(allreport.get(4).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(allreport.get(5).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(allreport.get(6).Water.substring(0, allreport.get(0).Water.indexOf(" "))),
                    Float.valueOf(ms5),"Water (Glasses)",
                    "#EB4160"
            );
        }
        if(checktime == 6) {
            if(choosechart == mChart){
                changeactivity.setText("Hobby");
            }
            generatecombinechart(
                    choosechart,
                    Float.valueOf(allreport.get(0).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(allreport.get(1).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(allreport.get(2).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(allreport.get(3).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(allreport.get(4).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(allreport.get(5).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(allreport.get(6).Hobby.substring(0, allreport.get(0).Hobby.indexOf(" "))),
                    Float.valueOf(ms6), "Hobbies (Minutes)",
                    "#F9ED2C"
            );
        }
        if(checktime == 7) {
            if(choosechart == mChart){
                changeactivity.setText("Total");
            }
            generatecombinecharttotal(choosechart);
        }
    }
}
