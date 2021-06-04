package com.akounto.accountingsoftware.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.LoginData;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.ViewModel.ProfileViewModel;
import com.akounto.accountingsoftware.util.JsonUtils;
import com.akounto.accountingsoftware.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

public class ExternalSignUp extends AppCompatActivity implements View.OnClickListener {

    private ProfileViewModel model;
    private TextView submit, pre;
    private EditText b_name, m_number;
    private String name, email, id_token;
    private Spinner typeBusinessSpinner;
    private Spinner dealsWithBusinessSpinner;
    private Context mContext;
    private LoginData loginData;
    private String businessType = "", dealsWith = "", business_name;
    private int businessTypeId = 0, dealsWithId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_extlogin);
        try {
            mContext = this;
            typeBusinessSpinner = findViewById(R.id.typeBusinessSpinner);
            dealsWithBusinessSpinner = findViewById(R.id.dealsWithBusinessSpinner);
            m_number = findViewById(R.id.ed_mobile);
            submit = findViewById(R.id.continueButton);
            pre = findViewById(R.id.previousButton);
            pre.setVisibility(View.GONE);
            b_name = findViewById(R.id.business_name);
            submit.setOnClickListener(this);
            name = getIntent().getStringExtra(Constant.FIRST_NAME);
            email = getIntent().getStringExtra(Constant.EMAIL);
            id_token = getIntent().getStringExtra(Constant.ID_TOKEN);

            settypeBusinessSpinner();

            model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProfileViewModel.class);
        } catch (Exception e) {
        }
    }

    private void settypeBusinessSpinner() {
        this.typeBusinessSpinner = findViewById(R.id.typeBusinessSpinner);
        final List<String> finalCategories = new ArrayList<>();
        finalCategories.add("LLC");
        finalCategories.add("Non Profit Organization");
        finalCategories.add("Partnership or LLP");
        finalCategories.add("Proprietorship");
        finalCategories.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, finalCategories);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.typeBusinessSpinner.setAdapter(dataAdapter);
        this.typeBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                ExternalSignUp.this.businessTypeId = pos;
                ExternalSignUp.this.businessType = finalCategories.get(pos);
                Log.e("pospospos", finalCategories.get(pos));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.dealsWithBusinessSpinner = findViewById(R.id.dealsWithBusinessSpinner);
        final List<String> finalCategories2 = new ArrayList<>();
        finalCategories2.add("Book-keeping");
        finalCategories2.add("Others");
        finalCategories2.add("Construction");
        finalCategories2.add("Financial Services");
        finalCategories2.add("Health Services");
        finalCategories2.add("Information Technology Services");
        finalCategories2.add("Marketing Services");
        finalCategories2.add("Medical Services");
        finalCategories2.add("Non Profit Organizations");
        finalCategories2.add("Photography and creative services");
        finalCategories2.add("Real Estate");
        finalCategories2.add("Retailers and Resellers");
        finalCategories2.add("Others");
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, R.layout.spinner_item, finalCategories2);
        dataAdapter.setDropDownViewResource(R.layout.spinner_item);
        this.dealsWithBusinessSpinner.setAdapter(dataAdapter2);
        this.dealsWithBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                ExternalSignUp.this.dealsWithId = pos;
                ExternalSignUp.this.dealsWith = finalCategories2.get(pos);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        String bn = b_name.getText().toString();
        if (!bn.equalsIgnoreCase("")) {
            if (m_number.getText().toString().length()>=10) {
                model.extReg(mContext, JsonUtils.getExtRegRequst(m_number.getText().toString(),name, email, bn, id_token, String.valueOf(ExternalSignUp.this.dealsWithId + 1), ExternalSignUp.this.dealsWith, String.valueOf(ExternalSignUp.this.businessTypeId + 1), ExternalSignUp.this.businessType)).observe(this, userDetails -> {
                    if (userDetails.getStatus() == 0) {
                        Bundle b=new Bundle();
                        b.putString(Constant.CATEGORY,"sign_up");
                        b.putString(Constant.ACTION,"social");
                        b.putString(Constant.EMAIL,email);
                        SplashScreenActivity.mFirebaseAnalytics.logEvent("sign_up_social",b);
                        UiUtil.addLoginToSharedPref(ExternalSignUp.this, true);
                        UiUtil.addUserDetails(ExternalSignUp.this, userDetails);
                        Intent mainIntent = new Intent(ExternalSignUp.this, SignUpStep3.class);
                        ExternalSignUp.this.startActivity(mainIntent);
                    } else {
                        Toast.makeText(ExternalSignUp.this, userDetails.getStatusMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(ExternalSignUp.this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(ExternalSignUp.this, "Please enter valid business name", Toast.LENGTH_SHORT).show();
        }
    }
}
