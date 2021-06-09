package com.akounto.accountingsoftware.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.akounto.accountingsoftware.R;

public class Test  extends AppCompatActivity {

    LinearLayout next,aboutUs,aboutBusiness;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup1);
        next=findViewById(R.id.nxt_done);
        aboutUs=findViewById(R.id.about_us);
        aboutBusiness=findViewById(R.id.about_business);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutUs.setVisibility(View.GONE);
                aboutBusiness.setVisibility(View.VISIBLE);
            }
        });
    }
}
