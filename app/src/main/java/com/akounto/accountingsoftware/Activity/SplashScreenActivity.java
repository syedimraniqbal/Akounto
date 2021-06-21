package com.akounto.accountingsoftware.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Repository.LoginRepo;
import com.akounto.accountingsoftware.util.UiUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imageView;
    private Context mContext;
    public static FirebaseAnalytics mFirebaseAnalytics;
    private int i = 0;
    int a[] = {1, 2};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = this;
       /* try{
            int i= a[5];
        }catch (Exception e){
            LoginRepo loginRepo =new LoginRepo();
            loginRepo.prinLogs(""+Log.getStackTraceString(e),5,"SplashScreenActivity");
        }*/
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        try {
            this.imageView = findViewById(R.id.app_logo);
            this.imageView.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce));
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    try {
                        if (i == 0) {
                            i++;
                            //if (!UiUtil.isLoggedin(SplashScreenActivity.this)) {
                            if (UiUtil.isFirstLogin(getApplicationContext())) {
                                Log.e("Count %s", String.valueOf(i));
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
                            } else {
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, WelcomeActivity.class));
                            }/*} else {
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, DashboardActivity.class));
                            }*/
                            SplashScreenActivity.this.finish();
                        }
                    } catch (Exception e) {
                    }
                }
            }, 2000);
        } catch (Exception e) {
        }
    }

    public static void sendEvent(String event_name, Bundle b) {
        try {
            if (Constant.ANALATICS_ON)
                SplashScreenActivity.mFirebaseAnalytics.logEvent(event_name, b);
        } catch (Exception e) {
        }
    }

}
