package com.zephyrs.android.onefriend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageButton;
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
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrackPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrackPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackPage extends Fragment implements SeekBar.OnSeekBarChangeListener {

    Button reset;
    TextView sleep;
    TextView exercise;
    TextView meditation;
    TextView social;
    TextView water;
    TextView hobby;
    Button buttonquestion;
    Button done;
    Button h1;
    Button h2;
    Button h3;
    Button h4;
    Button h5;
    Button h6;
    Button back;
    ImageButton head;
    private SeekBar mSeekBar1, mSeekBar2,mSeekBar3,mSeekBar4,mSeekBar5,mSeekBar6;
    Integer score;
    Integer total;
    View homefragment;
    private DatabaseReference mDatabase;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TrackPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrackPage newInstance(String param1, String param2) {
        TrackPage fragment = new TrackPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homefragment = inflater.inflate(R.layout.fragment_home, container, false);
        head = (ImageButton) homefragment.findViewById(R.id.head);
        back = (Button) homefragment.findViewById(R.id.back_button);
        done =  (Button) homefragment.findViewById(R.id.done);
        buttonquestion = (Button)homefragment.findViewById(R.id.buttonquestion);
        sleep = (TextView) homefragment.findViewById(R.id.sleep_text);
        exercise = (TextView) homefragment.findViewById(R.id.exercise_text);
        meditation = (TextView) homefragment.findViewById(R.id.meditation_text);
        social = (TextView) homefragment.findViewById(R.id.social_text);
        water = (TextView) homefragment.findViewById(R.id.water_text);
        hobby = (TextView) homefragment.findViewById(R.id.hobby_text);
        mSeekBar1 = (SeekBar) homefragment.findViewById(R.id.sb_1);
        mSeekBar2 = (SeekBar) homefragment.findViewById(R.id.sb_2);
        mSeekBar3 = (SeekBar) homefragment.findViewById(R.id.sb_3);
        mSeekBar4 = (SeekBar) homefragment.findViewById(R.id.sb_4);
        mSeekBar5 = (SeekBar) homefragment.findViewById(R.id.sb_5);
        mSeekBar6 = (SeekBar) homefragment.findViewById(R.id.sb_6);
        mSeekBar1.setOnSeekBarChangeListener(this);
        mSeekBar2.setOnSeekBarChangeListener(this);
        mSeekBar3.setOnSeekBarChangeListener(this);
        mSeekBar4.setOnSeekBarChangeListener(this);
        mSeekBar5.setOnSeekBarChangeListener(this);
        mSeekBar6.setOnSeekBarChangeListener(this);
        h1 = (Button) homefragment.findViewById(R.id.hint1);
        h2 = (Button) homefragment.findViewById(R.id.hint2);
        h3 = (Button) homefragment.findViewById(R.id.hint3);
        h4 = (Button) homefragment.findViewById(R.id.hint4);
        h5 = (Button) homefragment.findViewById(R.id.hint5);
        h6 = (Button) homefragment.findViewById(R.id.hint6);
        reset = (Button) homefragment.findViewById(R.id.reselect);

        AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
        animationview.setDuration(500);
        homefragment.setAnimation(animationview);
        head.setAnimation(animationview);

        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation1.setDuration(400);
        mSeekBar1.setAnimation(scaleAnimation1);

        ScaleAnimation scaleAnimation2 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation2.setDuration(600);
        mSeekBar2.setAnimation(scaleAnimation2);

        ScaleAnimation scaleAnimation3 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation3.setDuration(800);
        mSeekBar3.setAnimation(scaleAnimation3);

        ScaleAnimation scaleAnimation4 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation4.setDuration(1000);
        mSeekBar4.setAnimation(scaleAnimation4);

        ScaleAnimation scaleAnimation5 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation5.setDuration(1200);
        mSeekBar5.setAnimation(scaleAnimation5);

        ScaleAnimation scaleAnimation6 = new ScaleAnimation(0f, 1, 1f, 1f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f);
        scaleAnimation6.setDuration(1400);
        mSeekBar6.setAnimation(scaleAnimation6);

        score =0;
        total = 0;
        h1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Sleep longer hours").setMessage("High stress levels can cause trouble sleeping, research suggests that at least 7-8 hours of sleep per night can reduce stress levels.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();
            }
        });
        h2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Exercise routinely").setMessage("Exercise ranging from aerobics to yoga is great way to stay fit and reduce stress, preferably try to spend 20 minutes or more on exercising.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();

            }
        });
        h3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Meditate frequently").setMessage("Medication can help to become more mindful. Research recommends at least 5 minutes each day can improve your mindfulness.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();
            }
        });
        h4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Socialize more").setMessage("Stress can often be depressing. Routinely taking 10 minutes off from your busy schedule will have significant impact on your daily stress levels.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();
            }
        });
        h5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Drink more water").setMessage("Drinking water will increase your fluids level which can help reduce immediate stress. Medical experts suggest at least 6 glasses of water per day.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();
            }
        });
        h6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Spend time on hobbies").setMessage("Hobbies will divert your mind which in turn will results in reducing the stress. Ranging from reading novels to playing board games. Spend approximately 10-15 minutes each day.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),ReportPage.class);
                testintent.putExtra("page", 2);
                testintent.putExtra("pagechange","bottom");
                startActivity(testintent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),StressLevel.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),StressLevel.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        buttonquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(getActivity()).setTitle("Instruction").setMessage("Please select one of your favorite habits below to start tracking your efforts to overcome stress.")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }}).show();
            }
        });


        SharedPreferences settings = this.getActivity().getSharedPreferences("score", 0);
        String getscore = settings.getString("stress_head","0");
        String ms1 = settings.getString("seekbar1","0");
        String ms2 = settings.getString("seekbar2","0");
        String ms3 = settings.getString("seekbar3","0");
        String ms4 = settings.getString("seekbar4","0");
        String ms5 = settings.getString("seekbar5","0");
        String ms6 = settings.getString("seekbar6","0");
        String reduce = settings.getString("stress_reduce","0");

        mSeekBar1.setProgress(Integer.valueOf(ms1));
        mSeekBar2.setProgress(Integer.valueOf(ms2));
        mSeekBar3.setProgress(Integer.valueOf(ms3));
        mSeekBar4.setProgress(Integer.valueOf(ms4));
        mSeekBar5.setProgress(Integer.valueOf(ms5));
        mSeekBar6.setProgress(Integer.valueOf(ms6));
        Integer pointss = Integer.valueOf(getscore);
        score = Integer.valueOf(reduce);
        if(pointss>12){
            pointss=12;
        }
        total=pointss;
        checkchangehead();
        score=0;
        return homefragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (mSeekBar1.getProgress() == 0){
//            sleep.setText("3 Hours or less");
            sleep.setText(mSeekBar1.getProgress()+" Hour");
            score = score + 0;
        } else if (mSeekBar1.getProgress() >= 0 && mSeekBar1.getProgress() <= 3){
//            sleep.setText("3 Hours or less");
            sleep.setText(mSeekBar1.getProgress()+" Hours");
                score = score + 0;
        } else if (mSeekBar1.getProgress() >3 && mSeekBar1.getProgress() <= 5) {
//            sleep.setText("4-5 Hours");
            sleep.setText(mSeekBar1.getProgress()+" Hours");
                score = score + 1;
            } else if (mSeekBar1.getProgress() > 5 && mSeekBar1.getProgress() <= 7) {
//            sleep.setText("6-7 Hours");
            sleep.setText(mSeekBar1.getProgress()+" Hours");
                score = score + 2;
            } else if (mSeekBar1.getProgress() > 7 && mSeekBar1.getProgress() <= 9) {
//            sleep.setText("8-9 Hours");
            sleep.setText(mSeekBar1.getProgress()+" Hours");
                score = score + 3;
            } else if (mSeekBar1.getProgress() >= 9 && mSeekBar1.getProgress() < 10) {
//            sleep.setText("10 Hours or more");
            sleep.setText(mSeekBar1.getProgress()+" Hours");
                score = score + 4;
            } else if (mSeekBar1.getProgress() == 10) {
//            sleep.setText("10 Hours or more");
            sleep.setText(mSeekBar1.getProgress()+" Hours or more");
            score = score + 4;
        }

        if (mSeekBar2.getProgress() == 0){
//            exercise.setText("0 Minutes");
            exercise.setText(mSeekBar2.getProgress()+" Minute");
            score = score + 0;
        } else if (mSeekBar2.getProgress() > 0 && mSeekBar2.getProgress() <= 10){
//            exercise.setText("0 Minutes");
            exercise.setText(mSeekBar2.getProgress()+" Minutes");
            score = score + 0;
        } else if (mSeekBar2.getProgress() >10 && mSeekBar2.getProgress() <= 20) {
            exercise.setText(mSeekBar2.getProgress()+" Minutes");
//            exercise.setText("20 Minutes");
                score = score + 1;
            } else if(mSeekBar2.getProgress() >20 && mSeekBar2.getProgress()<=30){
            exercise.setText(mSeekBar2.getProgress()+" Minutes");
//            exercise.setText("40 Minutes");
                score = score + 2;
            }else if(mSeekBar2.getProgress() >30 && mSeekBar2.getProgress()<=40){
            exercise.setText(mSeekBar2.getProgress()+" Minutes");
//            exercise.setText("60 Minutes");
                score = score + 3;
            }else if(mSeekBar2.getProgress() >40 && mSeekBar2.getProgress()<60){
            exercise.setText(mSeekBar2.getProgress()+" Minutes");
//            exercise.setText("80 Minutes or more");
                score = score + 4;
            }else if(mSeekBar2.getProgress()==60){
            exercise.setText(mSeekBar2.getProgress()+" Minutes or more");
//            exercise.setText("80 Minutes or more");
            score = score + 4;
        }


        if (mSeekBar3.getProgress() == 0){
            meditation.setText("0 Minute");
            score = score + 0;
        } else if (mSeekBar3.getProgress() <= 2){
            meditation.setText(mSeekBar3.getProgress()+" Minutes");
            score = score + 0;
            } else if (mSeekBar3.getProgress() >2 && mSeekBar3.getProgress() <= 5) {
//            meditation.setText("1-5 Minutes");
            meditation.setText(mSeekBar3.getProgress()+" Minutes");
                score = score + 1;
            } else if(mSeekBar3.getProgress() >5 && mSeekBar3.getProgress()<=8){
//            meditation.setText("6-10 Minutes");
            meditation.setText(mSeekBar3.getProgress()+" Minutes");
                score = score + 2;
            }else if(mSeekBar3.getProgress() >8 && mSeekBar3.getProgress()<=11){
//            meditation.setText("11-15 Minutes");
            meditation.setText(mSeekBar3.getProgress()+" Minutes");
                score = score + 3;
            }else if(mSeekBar3.getProgress() >11 && mSeekBar3.getProgress()<15){
//            meditation.setText("16 Minutes or more");
            meditation.setText(mSeekBar3.getProgress()+" Minutes");
                score = score + 4;
            }else if(mSeekBar3.getProgress()==15){
//            meditation.setText("16 Minutes or more");
            meditation.setText(mSeekBar3.getProgress()+" Minutes or more");
            score = score + 4;
        }

        if (mSeekBar4.getProgress() == 0){
//            social.setText("0 Minutes");
            social.setText(mSeekBar4.getProgress()+" Minute");
            score = score + 0;
            } else if (mSeekBar4.getProgress() < 8){
//            social.setText("0 Minutes");
            social.setText(mSeekBar4.getProgress()+" Minutes");
            score = score + 0;
            } else if (mSeekBar4.getProgress() >=8 && mSeekBar4.getProgress() <= 28) {
//            social.setText("15 Minutes");
            social.setText(mSeekBar4.getProgress()+" Minutes");
                score = score + 1;
            } else if(mSeekBar4.getProgress() >28 && mSeekBar4.getProgress()<=46){
//            social.setText("30 Minutes");
            social.setText(mSeekBar4.getProgress()+" Minutes");
                score = score + 2;
            }else if(mSeekBar4.getProgress() >46 && mSeekBar4.getProgress()<=64){
//            social.setText("60 Minutes");
            social.setText(mSeekBar4.getProgress()+" Minutes");
                score = score + 3;
            }else if(mSeekBar4.getProgress() >64 && mSeekBar4.getProgress()<90){
//            social.setText("90 Minutes or more");
            social.setText(mSeekBar4.getProgress()+" Minutes");
                score = score + 4;
            }else if(mSeekBar4.getProgress()==90){
//            social.setText("90 Minutes or more");
            social.setText(mSeekBar4.getProgress()+" Minutes or more");
            score = score + 4;
        }

        if (mSeekBar5.getProgress() == 0){
            water.setText(mSeekBar5.getProgress()+" Glass");
            score = score + 0;
        } else if (mSeekBar5.getProgress() <= 2){
            water.setText(mSeekBar5.getProgress()+" Glasses");
            score = score + 0;
        } else if (mSeekBar5.getProgress() >2 && mSeekBar5.getProgress() <= 4) {
//            water.setText("1-3 Glasses");
            water.setText(mSeekBar5.getProgress()+" Glasses");
                score = score + 1;
            } else if(mSeekBar5.getProgress() >4 && mSeekBar5.getProgress()<=6){
//            water.setText("4-6 Glasses");
            water.setText(mSeekBar5.getProgress()+" Glasses");
                score = score + 2;
            }else if(mSeekBar5.getProgress() >6 && mSeekBar5.getProgress()<=8){
//            water.setText("7-9 Glasses");
            water.setText(mSeekBar5.getProgress()+" Glasses");
                score = score + 3;
            }else if(mSeekBar5.getProgress() >8 && mSeekBar5.getProgress()<10){
//            water.setText("10 Glasses or more");
            water.setText(mSeekBar5.getProgress()+" Glasses");
                score = score + 4;
            }else if(mSeekBar5.getProgress()==10){
//            water.setText("10 Glasses or more");
            water.setText(mSeekBar5.getProgress()+" Glasses or more");
            score = score + 4;
        }

        if (mSeekBar6.getProgress() == 0){
            hobby.setText(mSeekBar6.getProgress()+" Minute");
//            hobby.setText("0 Minutes");
            score = score + 0;
        } else if (mSeekBar6.getProgress() < 15){
            hobby.setText(mSeekBar6.getProgress()+" Minutes");
//            hobby.setText("0 Minutes");
            score = score + 0;
        } else if (mSeekBar6.getProgress() >=15 && mSeekBar6.getProgress() <= 25) {
            hobby.setText(mSeekBar6.getProgress()+" Minutes");
//            hobby.setText("15 Minutes");
                score = score + 1;
            } else if(mSeekBar6.getProgress() >25 && mSeekBar6.getProgress()<=40){
            hobby.setText(mSeekBar6.getProgress()+" Minutes");
//            hobby.setText("30 Minutes");
                score = score + 2;
            }else if(mSeekBar6.getProgress() >40 && mSeekBar6.getProgress()<=55){
            hobby.setText(mSeekBar6.getProgress()+" Minutes");
//            hobby.setText("45 Minutes");
                score = score + 3;
            }else if(mSeekBar6.getProgress() >55 && mSeekBar6.getProgress()<70){
            hobby.setText(mSeekBar6.getProgress()+" Minutes");
//            hobby.setText("60 Minutes or more");
                score = score + 4;
            }else if(mSeekBar6.getProgress()==70){
            hobby.setText(mSeekBar6.getProgress()+" Minutes or more");
//            hobby.setText("60 Minutes or more");
            score = score + 4;
        }
        checkchangehead();
        score =0;
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void checkchangehead(){
        Integer difference;
        difference = total - score;
        if(difference<0){
            difference=0;
        }
        SharedPreferences settings = getActivity().getSharedPreferences("score", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("seekbar1", String.valueOf(mSeekBar1.getProgress()));
        editor.putString("seekbar2", String.valueOf(mSeekBar2.getProgress()));
        editor.putString("seekbar3", String.valueOf(mSeekBar3.getProgress()));
        editor.putString("seekbar4", String.valueOf(mSeekBar4.getProgress()));
        editor.putString("seekbar5", String.valueOf(mSeekBar5.getProgress()));
        editor.putString("seekbar6", String.valueOf(mSeekBar6.getProgress()));
        editor.putString("stress_head", String.valueOf(total));
        editor.putString("stress_reduce", String.valueOf(score));
        editor.commit();

        String sleep1;
        String exercise1;
        String meditation1;
        String social1;
        String water1;
        String hobby1;


        if(mSeekBar1.getProgress() ==0){
            sleep1 = mSeekBar1.getProgress()+" Hour";
        } else if(mSeekBar1.getProgress()>0 && mSeekBar1.getProgress()<10){
            sleep1 = mSeekBar1.getProgress()+" Hours";
        } else {
            sleep1 = mSeekBar1.getProgress()+" Hours or more";
        }

        if(mSeekBar2.getProgress() ==0){
            exercise1 = mSeekBar2.getProgress()+" Minute";
        } else if(mSeekBar2.getProgress()>0 && mSeekBar2.getProgress()<60){
            exercise1 = mSeekBar2.getProgress()+" Minutes";
        } else{
            exercise1 = mSeekBar2.getProgress()+" Minutes or more";
        }

        if(mSeekBar3.getProgress() ==0){
            meditation1 = mSeekBar3.getProgress()+" Minute";
        } else if(mSeekBar3.getProgress()>0 && mSeekBar3.getProgress()<15){
            meditation1 = mSeekBar3.getProgress()+" Minutes";
        } else{
            meditation1 = mSeekBar3.getProgress()+" Minutes or more";
        }

        if(mSeekBar4.getProgress() ==0){
            social1 = mSeekBar4.getProgress()+" Minute";
        } else if(mSeekBar4.getProgress()>0 && mSeekBar4.getProgress()<90){
            social1 = mSeekBar4.getProgress()+" Minutes";
        } else{
            social1 = mSeekBar4.getProgress()+" Minutes or more";
        }
        if(mSeekBar5.getProgress() ==0){
            water1 = mSeekBar5.getProgress()+" Glasse";
        } else if(mSeekBar5.getProgress()>0 && mSeekBar5.getProgress()<10){
            water1 = mSeekBar5.getProgress()+" Glasses";
        } else {
            water1 = mSeekBar5.getProgress()+" Glasses or more";
        }
        if(mSeekBar6.getProgress() ==0){
            hobby1 = mSeekBar6.getProgress()+" Minute";
        } else if(mSeekBar6.getProgress()>0 && mSeekBar6.getProgress() <70){
            hobby1 = mSeekBar6.getProgress()+" Minutes";
        } else {
            hobby1 = mSeekBar6.getProgress()+" Minutes or more";
        }

        Date date = new Date();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Sleep").setValue(sleep1);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Exercise").setValue(exercise1);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Meditation").setValue(meditation1);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Social").setValue(social1);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Water").setValue(water1);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Hobby").setValue(hobby1);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Finally").setValue(difference);
        mDatabase.child("Profile").child(currentUser.getUid()).child("Report").child(ConvertDate(date)).child("Today").setValue(total);



        if(difference<=0){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h7));
        } else if(difference>0 && difference<=2){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h6));
        } else if(difference>2 && difference<=4){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h5));
        } else if(difference>4 && difference<=6){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h4));
        } else if(difference>6 && difference<=8){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h3));
        } else if(difference>8 && difference<=10){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h2));
        } else if(difference>10 && difference<=12){
            head.setImageDrawable(getResources().getDrawable(R.drawable.h1));
        }
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

    public String checksleep(Integer sleep) {
        if (sleep == 0) {
            return "3 Hours or less";
        } else if (sleep > 0 && sleep <= 15) {
            return "4-5 Hours";
        } else if (sleep > 15 && sleep <= 30) {
            return "6-7 Hours";
        } else if (sleep > 30 && sleep <= 45) {
            return "8-9 Hours";
        } else if (sleep > 45 && sleep <= 60) {
            return "10 Hours or more";
        } else {
            return "3 Hours or less";
        }
    }

    public String checkexercise(Integer exercise) {
        if (exercise == 0) {
            return "0 Minutes";
        } else if (exercise > 0 && exercise <= 15) {
            return "20 Minutes";
        } else if (exercise > 15 && exercise <= 30) {
            return "40 Minutes";
        } else if (exercise > 30 && exercise <= 45) {
            return "60 Minutes";
        } else if (exercise > 45 && exercise <= 60) {
            return "80 Minutes or more";
        } else {
            return "0 Minutes";
        }
    }

    public String checkmeditation(Integer meditation) {
        if (meditation == 0) {
            return "0 Minutes";
        } else if (meditation > 0 && meditation <= 15) {
            return "1-5 Minutes";
        } else if (meditation > 15 && meditation <= 30) {
            return "6-10 Minutes";
        } else if (meditation > 30 && meditation <= 45) {
            return "11-15 Minutes";
        } else if (meditation > 45 && meditation <= 60) {
            return "16 Minutes or more";
        } else {
            return "0 Minutes";
        }
    }

    public String checksocial(Integer social) {
        if (social == 0) {
            return "0 Minutes";
        } else if (social > 0 && social <= 15) {
            return "15 Minutes";
        } else if (social > 15 && social <= 30) {
            return "30 Minutes";
        } else if (social > 30 && social <= 45) {
            return "60 Minutes";
        } else if (social > 45 && social <= 60) {
            return "90 Minutes or more";
        } else {
            return "0 Minutes";
        }
    }

    public String checkwater(Integer water) {
        if (water == 0) {
            return "0 Minutes";
        } else if (water > 0 && water <= 15) {
            return "1-3 Glasses";
        } else if (water > 15 && water <= 30) {
            return "4-6 Glasses";
        } else if (water > 30 && water <= 45) {
            return "7-9 Glasses";
        } else if (water > 45 && water <= 60) {
            return "10 Glasses or more";
        } else {
            return "0 Minutes";
        }
    }

    public String checkhobby(Integer hobby) {
        if (hobby == 0) {
            return "0 Minutes";
        } else if (hobby > 0 && hobby <= 15) {
            return "15 Minutes";
        } else if (hobby > 15 && hobby <= 30) {
            return "30 Minutes";
        } else if (hobby > 30 && hobby <= 45) {
            return "45 Minutes";
        } else if (hobby > 45 && hobby <= 60) {
            return "60 Minutes or more";
        } else {
            return "0 Minutes";
        }
    }

}
