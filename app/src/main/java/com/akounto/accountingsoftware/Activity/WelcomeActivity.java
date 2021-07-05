package com.akounto.accountingsoftware.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.adapter.SliderPagerAdapter;
import com.akounto.accountingsoftware.util.UiUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SliderPagerAdapter myViewPagerAdapter;
    private com.google.android.material.tabs.TabLayout dotsLayout;
    private ImageView[] dots;
    private int[] layouts;
    private TextView btnSignUp, btnSignIn;
    //private PrefManager prefManager;
    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 7000; // time in milliseconds between successive task executions.
    int current = 0;
    Bundle b=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);
        try {
            Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken token) {
                }
            }).check();
            b = new Bundle();
            b.putString(Constant.CATEGORY, "welcome");
            b.putString(Constant.ACTION, "welcome_screen");
            SplashScreenActivity.sendEvent("welcome_screenview", b);
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            dotsLayout = (com.google.android.material.tabs.TabLayout) findViewById(R.id.tab_layout);
            dotsLayout.setupWithViewPager(viewPager, true);
            btnSignIn = (TextView) findViewById(R.id.btn_signin);
            btnSignUp = (TextView) findViewById(R.id.btn_signup);
            btnSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b = new Bundle();
                    b.putString(Constant.CATEGORY, "welcome");
                    b.putString(Constant.ACTION, "click_sign_in");
                    SplashScreenActivity.sendEvent("welcome_click_sign_in",b);
                    launchHomeScreen();
                }
            });
            btnSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    b = new Bundle();
                    b.putString(Constant.CATEGORY, "welcome");
                    b.putString(Constant.ACTION, "click_sign_up");
                    SplashScreenActivity.sendEvent("welcome_click_sign_up", b);
                    startActivity(new Intent(WelcomeActivity.this, SIgnUpStep0.class));
                }
            });
            // layouts of welcome sliders
            layouts = new int[]{
                    R.layout.welcome_slide1,
                    R.layout.welcome_slide2,
                    R.layout.welcome_slide3
            };

            myViewPagerAdapter = new SliderPagerAdapter(layouts, getApplicationContext());
            viewPager.setAdapter(myViewPagerAdapter);

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (current == layouts.length) {
                        current = 0;
                    }
                    viewPager.setCurrentItem(current++, true);
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer.schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, DELAY_MS, PERIOD_MS);
        } catch (Exception e) {
        }
    }

    private void launchHomeScreen() {
        UiUtil.SetFirstLogin(getApplicationContext(), true);
        startActivity(new Intent(WelcomeActivity.this, SignInActivity.class));
        finish();
    }

}
