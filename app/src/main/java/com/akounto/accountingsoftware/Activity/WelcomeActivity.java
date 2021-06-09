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

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.adapter.SliderPagerAdapter;
import com.akounto.accountingsoftware.util.UiUtil;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SliderPagerAdapter myViewPagerAdapter;
    private com.google.android.material.tabs.TabLayout dotsLayout;
    private ImageView[] dots;
    private int[] layouts;
    private Button btnSignUp, btnSignIn;
    //private PrefManager prefManager;
    private Timer timer;
    final long DELAY_MS = 500;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 7000; // time in milliseconds between successive task executions.
    int current=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (com.google.android.material.tabs.TabLayout) findViewById(R.id.tab_layout);
        dotsLayout.setupWithViewPager(viewPager, true);
        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,Test.class));
            }
        });
        // layouts of welcome sliders
        layouts = new int[]{
                R.layout.welcome_slide1,
                R.layout.welcome_slide2,
                R.layout.welcome_slide3
        };

        // adding bottom dots
       // setUpViewPager();

        myViewPagerAdapter = new SliderPagerAdapter(layouts,getApplicationContext());
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

    }
/*
    public void setUpViewPager() {
        setupPagerIndidcatorDots();
        dots[0].setImageResource(R.drawable.tab_indicator_selected);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dots.length; i++) {
                    dots[i].setImageResource(R.drawable.tab_indicator_default);
                }
                dots[position].setImageResource(R.drawable.tab_indicator_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void setupPagerIndidcatorDots() {
        dots = new ImageView[layouts.length];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);
            dots[i].setLayoutParams(params);
            dots[i].setImageResource(R.drawable.tab_indicator_default);
            //ivArrayDotsPager[i].setAlpha(0.4f);
            dots[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setAlpha(1);
                }
            });
            dotsLayout.addView(dots[i]);
            dotsLayout.bringToFront();
        }
    }*/

    private void launchHomeScreen() {
        UiUtil.SetFirstLogin(getApplicationContext(),true);
        startActivity(new Intent(WelcomeActivity.this, SignInActivity.class));
        finish();
    }

}
