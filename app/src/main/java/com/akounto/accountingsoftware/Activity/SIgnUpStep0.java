package com.akounto.accountingsoftware.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.request.RegisterBusiness;
import com.akounto.accountingsoftware.request.User;
import com.akounto.accountingsoftware.util.LocalManager;
import com.akounto.accountingsoftware.util.UiUtil;

public class SIgnUpStep0 extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    private EditText rePasswordET;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signupsetp0);
        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getWindow().setFlags(1024, 1024);
            this.emailET = findViewById(R.id.emailET);
            this.passwordET = findViewById(R.id.passwordET);
            this.rePasswordET = findViewById(R.id.rePasswordET);

            findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    if (isValid()) {
                        Bundle b=new Bundle();
                        b.putString(Constant.CATEGORY,"sign_up");
                        b.putString(Constant.ACTION,"email_verify");
                        b.putString(Constant.EMAIL,emailET.getText().toString());
                        SplashScreenActivity.mFirebaseAnalytics.logEvent("sign_up_verify_email",b);

                        RegisterBusiness registerBusiness = new RegisterBusiness();
                        User user = new User();
                        user.setEmail(emailET.getText().toString());
                        user.setPassword(passwordET.getText().toString());
                        user.setRole("Admin");
                        registerBusiness.setUser(user);
                        LocalManager.getInstance().setRegisterBusiness(registerBusiness);
                        startActivity(new Intent(SIgnUpStep0.this, SignUpStep1.class));
                    }
                }
            });
            findViewById(R.id.loginText).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    startActivity(new Intent(SIgnUpStep0.this, SignInActivity.class));
                }
            });
        } catch (Exception e) {
        }
    }

    private boolean isValid() {
        if (UiUtil.isValidEmail(this.emailET.getText().toString())) {
            UiUtil.showToast(this, "Please enter valid email");
            return false;
        } else if (this.passwordET.getText().toString().length() == 0) {
            UiUtil.showToast(this, "Please enter valid password");
            return false;
        } else if (this.passwordET.getText().toString().length() < 6) {
            UiUtil.showToast(this, "Password must be more the six characters");
            return false;
        } else if (this.passwordET.getText().toString().length() != this.rePasswordET.getText().toString().length()) {
            UiUtil.showToast(this, "Password does not match");
            return false;
        } else {
            return true;
        }
    }
}