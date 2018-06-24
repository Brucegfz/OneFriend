package com.zephyrs.android.onefriend;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingPage extends Fragment{
    Button logout;
    Button introduction;
    View setting;
    TextView user;
    TextView email;
    Button feekback;
    Button tour;
    Button about;
    Button disclaimer;
    Switch aSwitch;
    ImageView userphoto;
    Button setreminder;
    Button report;
    RelativeLayout reminderbutton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 9001;

    //Google
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private TextView mDetailTextView;
    private ProgressDialog mProgressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> kk = new ArrayList<>();
    String photolink;
    DatabaseReference myRef2;
    Bitmap bitmap;
    FirebaseUser currentUser;
    private OnFragmentInteractionListener mListener;

    public SettingPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingPage.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingPage newInstance(String param1, String param2) {
        SettingPage fragment = new SettingPage();
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
        setting = inflater.inflate(R.layout.fragment_share, container, false);
        introduction = (Button) setting.findViewById(R.id.tour);
        logout = (Button) setting.findViewById(R.id.log_out);
        user = (TextView) setting.findViewById(R.id.user);
        email = (TextView) setting.findViewById(R.id.email);
        feekback = (Button) setting.findViewById(R.id.feekback);
        tour = (Button) setting.findViewById(R.id.tour);
        disclaimer = (Button) setting.findViewById(R.id.disclaimer);
        about = (Button) setting.findViewById(R.id.about);
        aSwitch = (Switch)setting.findViewById(R.id.switch1);
        setreminder = (Button)setting.findViewById(R.id.setNotification);
        reminderbutton = (RelativeLayout) setting.findViewById(R.id.notificationappear);
        userphoto = (ImageView) setting.findViewById(R.id.userphoto);
        report = (Button)setting.findViewById(R.id.report);
        AlphaAnimation animationview = new AlphaAnimation(0f, 1f);
        animationview.setDuration(500);
        setting.setAnimation(animationview);


        SharedPreferences ppstress = getActivity().getSharedPreferences("previousstress", 0);
        String turn = ppstress.getString("turn","off");
        if(turn.equals("on")){
            aSwitch.setChecked(true);
            setreminder.setVisibility(View.VISIBLE);
            reminderbutton.setVisibility(View.VISIBLE);
        } else {
            aSwitch.setChecked(false);
            setreminder.setVisibility(View.GONE);
            reminderbutton.setVisibility(View.GONE);
//            getActivity().findViewById(R.id.setNotification).setVisibility(View.GONE);
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    SharedPreferences settings = getActivity().getSharedPreferences("previousstress", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("turn", "on");
                    editor.commit();
                    SharedPreferences ppstress = getActivity().getSharedPreferences("previousstress", 0);
                    int hour = Integer.valueOf(ppstress.getString("hour","0"));
                    int min = Integer.valueOf(ppstress.getString("min","0"));
                    setnotification(hour,min);
                    aSwitch.setChecked(true);
                    getActivity().findViewById(R.id.setNotification).setVisibility(View.VISIBLE);
                    reminderbutton.setVisibility(View.VISIBLE);
                } else {
                    SharedPreferences settings = getActivity().getSharedPreferences("previousstress", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("turn", "off");
                    editor.commit();
                    getActivity().findViewById(R.id.setNotification).setVisibility(View.GONE);
                    reminderbutton.setVisibility(View.GONE);

                    Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
                    notificationIntent.addCategory("android.intent.category.DEFAULT");
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent();
                    intent.setAction("testalarm0");
                    alarmManager.cancel(PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));
                    aSwitch.setChecked(false);
                }

            }
        });

        setreminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),NotificationPage.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });


        tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),IntroductionPage.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),lvWithButtonExt.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        feekback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),Feekback.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });

        disclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),Disclaimer.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });


        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent testintent = new Intent(getActivity(),AboutPage.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
            }
        });


        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef2 = database.getReference();

        FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
        myRef1.child("Profile").child(user3.getUid()).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String user1 = dataSnapshot.getValue(String.class);
                user.setText(user1);
                kk.add(user1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        myRef2.child("Profile").child(currentUser.getUid()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String email1 = dataSnapshot.getValue(String.class);
                email.setText(email1);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


        myRef2.child("Profile").child(currentUser.getUid()).child("photo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                photolink = dataSnapshot.getValue(String.class);
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        bitmap = getBitmapfromUrl(photolink);
                        return "good";
                    }
                    @Override
                    protected void onPostExecute(String courses) {
                        userphoto.setImageBitmap(bitmap);
                    }
                }.execute();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        kk.size();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Firebase sign out

                mAuth.signOut();
                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                deleteAccessToken();
                AccessToken.setCurrentAccessToken(null);
                Intent testintent = new Intent(getActivity(),BottomBar.class);
                startActivity(testintent);
                getActivity().overridePendingTransition(R.anim.fade, R.anim.hold);
                    // Google sign out
            }
        });


        // Inflate the layout for this fragment
        return setting;
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


    public Bitmap getBitmapfromUrl(String imageUrl)
    {
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    public void setnotification(int hour,int min){

        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");
        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, min);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
//        Intent intent = new Intent();
//        intent.setAction("testalarm0");
//        PendingIntent pendingIntent=PendingIntent.getBroadcast(getBaseContext(),0, intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,broadcast);
    }

    private void deleteAccessToken() {
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    //User logged out
                    LoginManager.getInstance().logOut();
                }
            }
        };
    }
}
