package com.zephyrs.android.onefriend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Barry on 16/8/17.
 */

public class BottomBar extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    Integer fragment;
    private GoogleApiClient mGoogleApiClient;
    private TextView mStatusTextView;
    private SignInButton signInButton;
    private TextView mDetailTextView;
    private ProgressDialog mProgressDialog;
    private DatabaseReference mDatabase;
    int finalIndex1 = 0;
    ArrayList<Report> allreport = new ArrayList<>();
    CallbackManager mCallbackManager;
    Bundle testb = new Bundle();
    ArrayList<String> dd = new ArrayList<>();
    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN = 1;
    String jk;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    transaction.replace(R.id.content,new HomePage()).commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction.replace(R.id.content,new TrackPage()).commit();
                    return true;
                case R.id.navigation_activity:
                    transaction.replace(R.id.content,new ActivityPage()).commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction.replace(R.id.content,new SettingPage()).commit();
                    return true;
//                    Toast.makeText(getBaseContext(),"Develop Later...",Toast.LENGTH_SHORT).show();
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_bar);

        FacebookSdk.sdkInitialize(this.getApplicationContext());
        AppEventsLogger.activateApp(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        Intent changepage = getIntent();
        fragment = changepage.getIntExtra("page",1);
        if(fragment==1){
            transaction.replace(R.id.content,new HomePage()).commit();
        }
        if(fragment==2) {
            transaction.replace(R.id.content,new TrackPage  ()).commit();
            Menu menu = navigation.getMenu();
                MenuItem item = menu.getItem(1);
                item.setChecked(true);
        }


        findViewById(R.id.sign_in_button).setOnClickListener(this);
//        findViewById(R.id.sign_out_button).setOnClickListener(this);

        String dd = getString(R.string.default_web_client_id);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // [START initialize_auth]
        // [END initialize_auth]

        //FaceBook

        mAuth = FirebaseAuth.getInstance();
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
                Toast.makeText(BottomBar.this, "cancel",
                        Toast.LENGTH_SHORT).show();
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });
    }


    public static void disableMenuShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
//            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("TAG", "firebaseAuthWithGoogle:" + acct.getId());
        // [START_EXCLUDE silent]
        showProgressDialog();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(BottomBar.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    // [START signin]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signin]

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();
        LoginManager.getInstance().logOut();
        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });


    }

    private void revokeAccess() {
        // Firebase sign out
        mAuth.signOut();

        // Google revoke access
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            setvalue();
            setdata();
            findViewById(R.id.navigation).setVisibility(View.VISIBLE);
            findViewById(R.id.content).setVisibility(View.VISIBLE);
            findViewById(R.id.login_button).setVisibility(View.GONE);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
            findViewById(R.id.login_disappear).setVisibility(View.GONE);
        } else {
            findViewById(R.id.navigation).setVisibility(View.GONE);
            findViewById(R.id.content).setVisibility(View.GONE);
            findViewById(R.id.login_button).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
            findViewById(R.id.login_disappear).setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("TAG", "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        } 
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);

        showProgressDialog();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            findViewById(R.id.navigation).setVisibility(View.VISIBLE);
                            findViewById(R.id.content).setVisibility(View.VISIBLE);
                            findViewById(R.id.login_button).setVisibility(View.GONE);
                            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
//                        findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
                            findViewById(R.id.login_disappear).setVisibility(View.GONE);
                            setvalue();
                            setdata();
//                            Intent homeIntent = new Intent(FirebaseTest.this,Test.class);
//                            startActivity(homeIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(BottomBar.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();

                        // ...
                    }
                });
    }


    public void setvalue(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Date date = new Date();
        mDatabase.child("Profile").child(currentUser.getUid()).child("email").setValue(currentUser.getEmail());
        mDatabase.child("Profile").child(currentUser.getUid()).child("name").setValue(currentUser.getDisplayName());
        mDatabase.child("Profile").child(currentUser.getUid()).child("photo").setValue(currentUser.getPhotoUrl().toString());
        mDatabase.child("Profile").child(currentUser.getUid()).child("uid").setValue(currentUser.getUid());
//        mDatabase.child("Profile").child(currentUser.getUid()).child(ConvertDate(date)+"Today").setValue("0");
//        mDatabase.child("Profile").child(currentUser.getUid()).child(ConvertDate(date)+"Finally").setValue("0");
    }

    public String ConvertDate(Date date){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = df.format(date);
        String result = s;
        try {
            date=df.parse(result);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return s;
    }


    public void setdata() {

        Date datespecific = new Date();
        String specificday = ConvertDate(datespecific);
        Integer num = getNumber(getWeek(datespecific));
                SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("1", "-2");
                editor.putString("2", "-2");
                editor.putString("3", "-2");
                editor.putString("4", "-2");
                editor.putString("5", "-2");
                editor.putString("6", "-2");
                editor.putString("7", "-2");
                editor.putString("specifictoday","0");
                String json = "";
                for(int index=1;index<8;index++){
                    editor.putString("SerializableObject"+index, json);
                }
                editor.commit();





        final ArrayList<String> datee = new ArrayList();
                final Integer week = num;

                String monday = getSpecifiedDayBefore(specificday, num-1);
        for (int index = 0; index < num-1; index++) {
            datee.add(monday);
            monday = getSpecifiedDayAfter(monday);
        }


        for (int index = 0; index < num-1; index++) {
            FirebaseUser user3 = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference myRef1 = FirebaseDatabase.getInstance().getReference();
            final int finalIndex = index;
            myRef1 = myRef1.child("Profile").child(user3.getUid()).child("Report").child(datee.get(index));
            myRef1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        Report data = dataSnapshot.getValue(Report.class);
                        float n = Float.valueOf(data.Today.toString());
                        n = checkstresslevel(n);
                        SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        Gson gson = new Gson();
                        String json = gson.toJson(data);
                        int total = finalIndex+1;
                        editor.putString("SerializableObject"+total, json);
                        editor.putString(String.valueOf(finalIndex+1), String.valueOf(n));
                        editor.commit();
                    } catch (Exception e) {
                        Report a = new Report("0 Minutes",0l,"0 Minutes","0 Minutes","3 Hours or less","0 Minutes",0l,"0 Minutes");
                        allreport.add(a);
                        SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(String.valueOf(finalIndex+1), "-2");
                        editor.commit();
                    }
                }


                @Override
                public void onCancelled(DatabaseError error) {
                    SharedPreferences settings = getBaseContext().getSharedPreferences("previousstress", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(String.valueOf(finalIndex+1), "-2");
                    editor.commit();
                    // Failed to read value
                    Log.w("TAG", "Failed to read value.", error.toException());
                }
            });

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

    public float checkstresslevel(float difference){
        float level = 0;
        if(difference<=0){
            level = 0;
        } else if(difference>0 && difference<=2){
            level = 55;
        } else if(difference>2 && difference<=4){
            level = 60;
        } else if(difference>4 && difference<=6){
            level = 65;
        } else if(difference>6 && difference<=8){
            level = 70;
        } else if(difference>8 && difference<=10){
            level = 75;
        } else if(difference>10 && difference<=12){
            level = 80;
        }
        return level;
    }

    @Override
    public void onBackPressed() {
        finishActivity(0);
        finishActivity(1);
        finishActivity(2);
        finishActivity(3);
        finish();
    }
}
