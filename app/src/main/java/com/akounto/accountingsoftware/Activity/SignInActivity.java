package com.akounto.accountingsoftware.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.internal.LinkedTreeMap;
import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.CheckEmailData;
import com.akounto.accountingsoftware.Data.ErrorData;
import com.akounto.accountingsoftware.Data.LoginData;
import com.akounto.accountingsoftware.Data.UserDetails;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Services.Api;
import com.akounto.accountingsoftware.Services.ApiUtils;
import com.akounto.accountingsoftware.ViewModel.LoginViewModel;
import com.akounto.accountingsoftware.util.UiUtil;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.xwray.passwordview.PasswordView;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private EditText emailET;
    private PasswordView passwordET;
    private TextView signup;
    private TextView forgot_password;
    private Context mContext;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient = null;
    private static final int RC_SIGN_IN = 1;
    private String name, email;
    private String idToken;
    private LoginViewModel model;
    private LifecycleOwner owner;
    boolean external_sign_up = true;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.layout_signin);
            getWindow().setFlags(1024, 1024);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

            mContext = this;
            owner = this;
            Dexter.withActivity(this).withPermissions("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE").withListener(new MultiplePermissionsListener() {
                public void onPermissionsChecked(MultiplePermissionsReport report) {
                }

                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken token) {
                }
            }).check();
            this.emailET = findViewById(R.id.emailET);
            this.passwordET = findViewById(R.id.passwordET);
            signup = findViewById(R.id.text_signup);
            forgot_password = findViewById(R.id.txt_forgot_password);
            firebaseAuth = com.google.firebase.auth.FirebaseAuth.getInstance();
            findViewById(R.id.signUpButton).setOnClickListener(new View.OnClickListener() {
                public final void onClick(View view) {
                    login();
                }
            });

            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignInActivity.this.startActivity(new Intent(SignInActivity.this, SIgnUpStep0.class));
                }
            });

            forgot_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SignInActivity.this.startActivity(new Intent(SignInActivity.this, ForgotPassword.class));
                }
            });
            //model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(LoginViewModel.class);
            model = new ViewModelProviders().of(this).get(LoginViewModel.class);
            authStateListener = firebaseAuth -> {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            };
            GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.web_client_id2))//you can also use R.string.default_web_client_id
                    .requestEmail()
                    .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                    .requestServerAuthCode(getString(R.string.web_client_id2))
                    .requestEmail()
                    .build();
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            signInButton = findViewById(R.id.sign_in_button);
            setGooglePlusButtonText(signInButton);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //UiUtil.showToast(mContext,"signInButton");
                    UiUtil.showProgressDialogue(mContext, "", "Loding..");
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    SignInActivity.this.startActivityForResult(intent, RC_SIGN_IN);
                }
            });
        } catch (Exception e) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {

        } catch (Exception e) {
        }
    }

    public void login() {
        try {
            String user = this.emailET.getText().toString();
            String pass = this.passwordET.getText().toString();
            if (!user.equalsIgnoreCase("")) {
                if (!pass.equalsIgnoreCase("")) {
                    loadLogin(mContext, user, pass);
                } else {
                    Toast.makeText(mContext, "Please enter valid password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, "Please enter valid email id", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void loadLogin(Context mContext, String username, String pass) {
        UiUtil.showProgressDialogue(mContext, "", "Please wait..");

        Api api = ApiUtils.getAPIService();
        api.loginRequest(Constant.X_SIGNATURE, username, pass, Constant.GRANT_TYPE).enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, retrofit2.Response<LoginData> response) {
                UiUtil.cancelProgressDialogue();
                LoginData loginData = response.body();
                try {
                    if (response.code() == 200) {
                        if (response.body().getTransactionStatus() == null) {
                            UiUtil.addLoginToSharedPref(SignInActivity.this, true);
                            UiUtil.addUserDetails(SignInActivity.this, loginData);
                            Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                            SignInActivity.this.startActivity(intent);
                            SignInActivity.this.finish();
                        } else {
                            Toast.makeText(mContext, ((LinkedTreeMap) (response.body().getTransactionStatus().getError())).get("Description").toString(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ErrorData error = new Gson().fromJson(response.errorBody().string(), ErrorData.class);
                        Toast.makeText(mContext, error.getError_description(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* access modifiers changed from: private */
    public void showErrorDialogue() {
        new SweetAlertDialog(this, 1).setTitleText("Oops...").setContentText("Fail to signin.Please try again with proper id and password").setConfirmText("OK").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            public void onClick(SweetAlertDialog sDialog) {
                sDialog.dismissWithAnimation();
            }
        }).show();
    }

    protected void setGooglePlusButtonText(SignInButton signInButton) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);
            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText("Sign in with Google");
                return;
            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                acct.getServerAuthCode();
                //Log.d("Server Auth :: ",acct.getServerAuthCode());
                handleSignInResult(result);
            }
        } catch (Exception e) {
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        try {
            UiUtil.cancelProgressDialogue();
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                idToken = account.getIdToken();
                name = account.getDisplayName();
                email = account.getEmail();

                try {
                    //Toast.makeText(this, result.getSignInAccount().getServerAuthCode(), Toast.LENGTH_SHORT).show();
                    Log.w("Token :: ", result.getSignInAccount().getServerAuthCode());
                } catch (Exception e) {
                    Log.w("Token Error :: ", e.getLocalizedMessage());
                }
                AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                firebaseAuthWithGoogle(credential, account);
            } else {
                // Google Sign In failed, update UI appropriately
                Log.e("TAG", "Login Unsuccessful. " + result.getStatus().getStatusMessage());
                Toast.makeText(this, "Login Unsuccessful" + result.getStatus().getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential, GoogleSignInAccount account) {

        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, task -> {
            Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
            if (task.isSuccessful()) {
                model.checkEmail(mContext, task.getResult().getUser().getEmail()).observe(SignInActivity.this, new Observer<CheckEmailData>() {
                    @Override
                    public void onChanged(CheckEmailData checkEmailData) {
                       /* if (external_sign_up) {
                            external_sign_up = false;*/
                            try {
                                if (checkEmailData.getStatus() == 0) {
                                    if (!checkEmailData.isData()) {
                                        Intent mainIntent = new Intent(SignInActivity.this, ExternalSignUp.class);
                                        mainIntent.putExtra(Constant.FIRST_NAME, task.getResult().getUser().getDisplayName());
                                        mainIntent.putExtra(Constant.EMAIL, task.getResult().getUser().getEmail());
                                        mainIntent.putExtra(Constant.ID_TOKEN, account.getIdToken());
                                        SignInActivity.this.startActivity(mainIntent);
                                    } else {
                                        model.extLogin(mContext, "Google", account.getIdToken()).observe(owner, userDetails -> {
                                            UserDetails ud;
                                            if (userDetails.getStatus() == 0) {
                                                try {
                                                    UiUtil.addLoginToSharedPref(SignInActivity.this, true);
                                                    UiUtil.addUserDetails(SignInActivity.this, userDetails);
                                                    Intent mainIntent = new Intent(SignInActivity.this, DashboardActivity.class);
                                                    SignInActivity.this.startActivity(mainIntent);
                                                } catch (Exception e) {
                                                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            } else {
                                                Toast.makeText(SignInActivity.this, userDetails.getStatusMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    Toast.makeText(SignInActivity.this, checkEmailData.getStatusMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                            }
                       /* }*/
                    }
                });
            } else {
                Log.w("TAG", "signInWithCredential" + task.getException().getMessage());
                task.getException().printStackTrace();
                Toast.makeText(SignInActivity.this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (authStateListener != null) {
                firebaseAuth.removeAuthStateListener(authStateListener);
            }
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if (authStateListener != null) {
                FirebaseAuth.getInstance().signOut();
            }
            firebaseAuth.addAuthStateListener(authStateListener);
        } catch (Exception e) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (authStateListener != null) {
                firebaseAuth.removeAuthStateListener(authStateListener);
            }
        } catch (Exception e) {
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        finishAffinity();
        Intent i=new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }
}
