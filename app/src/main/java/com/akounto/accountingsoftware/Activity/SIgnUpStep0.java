package com.akounto.accountingsoftware.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.CheckEmailData;
import com.akounto.accountingsoftware.Data.ErrorData;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Repository.LoginRepo;
import com.akounto.accountingsoftware.Services.Api;
import com.akounto.accountingsoftware.Services.ApiUtils;
import com.akounto.accountingsoftware.ViewModel.LoginViewModel;
import com.akounto.accountingsoftware.request.RegisterBusiness;
import com.akounto.accountingsoftware.request.User;
import com.akounto.accountingsoftware.util.LocalManager;
import com.akounto.accountingsoftware.util.UiUtil;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SIgnUpStep0 extends AppCompatActivity {

    private EditText emailET;
    private EditText passwordET;
    TextView tv_error, password_error;
    LinearLayout password_ll;
    RelativeLayout email_ll;
    ImageView mail_ckeck;
    CheckBox checkBox_tnc;
    Context mContext;
    private LoginViewModel model;
    private LifecycleOwner owner;
    boolean success = true;
    LinearLayout back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signupsetp0);
        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //getWindow().setFlags(1024, 1024);
            mContext = this;
            owner = this;
            //model = new ViewModelProviders().of(this).get(LoginViewModel.class);
            Bundle b = new Bundle();
            b.putString(Constant.CATEGORY, "sign_up");
            b.putString(Constant.ACTION, "sign_up0_screen_view");
            SplashScreenActivity.sendEvent("sign_up_screen_view", b);
            tv_error = findViewById(R.id.tv_error);
            password_error = findViewById(R.id.password_error);
            email_ll = findViewById(R.id.email_ll);
            password_ll = findViewById(R.id.password_ll);
            back = findViewById(R.id.back);
            this.emailET = findViewById(R.id.emailET);
            this.passwordET = findViewById(R.id.passwordET);

            mail_ckeck = findViewById(R.id.mail_ckeck);
            checkBox_tnc = findViewById(R.id.checkBox_tnc);
            emailET.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!UiUtil.isValidEmail(emailET.getText().toString())) {
                        mail_ckeck.setVisibility(View.VISIBLE);
                        Log.e("Checked ::", "true" + s);
                        Bundle b = new Bundle();
                        b.putString(Constant.CATEGORY, "sign_up");
                        b.putString(Constant.ACTION, "capture_email");
                        b.putString(Constant.EMAIL, emailET.getText().toString());
                        SplashScreenActivity.sendEvent("sign_up0_screen_capture_email", b);
                    } else {
                        mail_ckeck.setVisibility(View.GONE);
                        Log.e("Checked ::", "false" + s);
                    }
                }
            });
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SIgnUpStep0.this, WelcomeActivity.class));
                }
            });
            findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    reset();
                    Bundle b = new Bundle();
                    b.putString(Constant.CATEGORY, "sign_up");
                    b.putString(Constant.ACTION, "button_click");
                    b.putString(Constant.EMAIL, emailET.getText().toString());
                    SplashScreenActivity.sendEvent("sign_up0_button_click", b);

                    if (isValid()) {
                        checkEmail(mContext, emailET.getText().toString());
                    } else {
                        Bundle b1 = new Bundle();
                        b1.putString(Constant.CATEGORY, "sign_up");
                        b1.putString(Constant.ACTION, "email_verify_fail");
                        b1.putString(Constant.EMAIL, emailET.getText().toString());
                        SplashScreenActivity.sendEvent("verify_email_validtion_error", b1);

                    }
                }
            });
            findViewById(R.id.loginText).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    startActivity(new Intent(SIgnUpStep0.this, SignInActivity.class));
                }
            });

        findViewById(R.id.tnc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TnCActivity.class);
                intent.putExtra(Constant.LAUNCH_TYPE, "1");
                startActivity(intent);
            }
        });
        findViewById(R.id.pp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), TnCActivity.class);
                intent.putExtra(Constant.LAUNCH_TYPE, "2");
                startActivity(intent);
            }
        });
        } catch (Exception e) {
            LoginRepo.prinLogs(""+Log.getStackTraceString(e),5,"Sign Up virfy Email");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        success = true;
        emailET.setText("");
        passwordET.setText("");
    }

    public void checkEmail(Context mContext, String email) {

        UiUtil.showProgressDialogue(mContext, "", "Please wait..");
        Api api = ApiUtils.getAPIService();
        api.checkEmailExistRequest(Constant.X_SIGNATURE, email).enqueue(new Callback<CheckEmailData>() {
            @Override
            public void onResponse(Call<CheckEmailData> call, Response<CheckEmailData> response) {
                UiUtil.cancelProgressDialogue();
                try {
                    if (response.code() == 200) {
                        if (response.body().getTransactionStatus().isIsSuccess()) {
                            if (!response.body().isData()) {
                                if (success) {
                                    success = false;
                                    tv_error.setVisibility(View.GONE);
                                    Bundle b = new Bundle();
                                    b.putString(Constant.CATEGORY, "sign_up");
                                    b.putString(Constant.ACTION, "email_verify_success");
                                    b.putString(Constant.EMAIL, emailET.getText().toString());
                                    SplashScreenActivity.sendEvent("sign_up0_verify_email_success", b);

                                    RegisterBusiness registerBusiness = new RegisterBusiness();
                                    User user = new User();
                                    user.setEmail(emailET.getText().toString());
                                    user.setPassword(passwordET.getText().toString());
                                    user.setRole("Admin");
                                    registerBusiness.setUser(user);
                                    LocalManager.getInstance().setRegisterBusiness(registerBusiness);
                                    startActivity(new Intent(SIgnUpStep0.this, SignUpDetails.class));
                                }
                            } else {
                                Bundle b = new Bundle();
                                b.putString(Constant.CATEGORY, "sign_up");
                                b.putString(Constant.ACTION, "email_verify_fail");
                                b.putString(Constant.CAUSES, "email already exsit");
                                SplashScreenActivity.sendEvent("sign_up0_verify_email_fail", b);
                                tv_error.setVisibility(View.VISIBLE);
                                email_ll.setBackgroundResource(R.drawable.error);
                            }
                        } else {
                            Bundle b = new Bundle();
                            b.putString(Constant.CATEGORY, "sign_up");
                            b.putString(Constant.ACTION, "email_verify_fail");
                            b.putString(Constant.EMAIL, emailET.getText().toString());
                            SplashScreenActivity.sendEvent("sign_up0_verify_email_fail", b);
                            tv_error.setVisibility(View.VISIBLE);
                            email_ll.setBackgroundResource(R.drawable.error);
                            //Toast.makeText(SIgnUpStep0.this, response.body().getTransactionStatus().getError().getDescription(), Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Bundle b = new Bundle();
                        b.putString(Constant.CATEGORY, "sign_up");
                        b.putString(Constant.ACTION, "email_verify_fail");
                        b.putString(Constant.EMAIL, emailET.getText().toString());
                        SplashScreenActivity.sendEvent("sign_up0_verify_email_fail", b);
                        ErrorData error = new Gson().fromJson(response.errorBody().string(), ErrorData.class);
                        tv_error.setVisibility(View.VISIBLE);
                        tv_error.setText(error.getError_description());
                        email_ll.setBackgroundResource(R.drawable.error);
                    }
                } catch (Exception e) {
                    Log.d("TEG :: ", e.getLocalizedMessage());
                    Bundle b = new Bundle();
                    b.putString(Constant.CATEGORY, "sign_up");
                    b.putString(Constant.ACTION, "email_verify_fail");
                    b.putString(Constant.EMAIL, emailET.getText().toString());
                    SplashScreenActivity.sendEvent("sign_up0_verify_email_fail", b);
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText(e.getMessage());
                    email_ll.setBackgroundResource(R.drawable.error);
                    LoginRepo.prinLogs(""+Log.getStackTraceString(e),5,"Sign Up virfy Email");
                }
            }

            @Override
            public void onFailure(Call<CheckEmailData> call, Throwable t) {
                UiUtil.cancelProgressDialogue();
                Bundle b = new Bundle();
                b.putString(Constant.CATEGORY, "sign_up");
                b.putString(Constant.ACTION, "email_verify_fail");
                b.putString(Constant.EMAIL, emailET.getText().toString());
                SplashScreenActivity.sendEvent("sign_up0_verify_email_fail", b);
                tv_error.setVisibility(View.VISIBLE);
                tv_error.setText(t.getMessage());
                email_ll.setBackgroundResource(R.drawable.error);
            }
        });
    }

    private boolean isValid() {
        if (UiUtil.isValidEmail(this.emailET.getText().toString())) {
            //UiUtil.showToast(this, "Please enter valid email");
            emailET.requestFocus();
            tv_error.setText("Please enter valid email");
            tv_error.setVisibility(View.VISIBLE);
            email_ll.setBackgroundResource(R.drawable.error);
            return false;
        } else if (this.passwordET.getText().toString().length() == 0) {
            //UiUtil.showToast(this, "Please enter valid password");
            passwordET.requestFocus();
            password_error.setText("Please enter valid password");
            password_error.setVisibility(View.VISIBLE);
            password_ll.setBackgroundResource(R.drawable.error);
            return false;
        } else if (this.passwordET.getText().toString().length() < 6) {
            //UiUtil.showToast(this, "Password must be more the six characters");
            passwordET.requestFocus();
            password_error.setText("Password must be more the six characters");
            password_error.setVisibility(View.VISIBLE);
            password_ll.setBackgroundResource(R.drawable.error);
            return false;
        } else if (!checkBox_tnc.isChecked()) {
            UiUtil.showToast(this, "Please check Terms & Conditions");
            return false;
        } else {
            return true;
        }
    }

    private void reset(){
        email_ll.setBackgroundResource(R.drawable.new_light_blue);
        password_ll.setBackgroundResource(R.drawable.new_light_blue);

        tv_error.setVisibility(View.GONE);
        password_error.setVisibility(View.GONE);
    }
}