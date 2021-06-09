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
import com.akounto.accountingsoftware.util.UiUtil;
import com.google.firebase.analytics.FirebaseAnalytics;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView imageView;
    Context mContext;
    public static FirebaseAnalytics mFirebaseAnalytics;
    int i = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mContext = this;
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
                            if(UiUtil.isFirstLogin(getApplicationContext())) {
                                Log.e("Count %s", String.valueOf(i));
                                SplashScreenActivity.this.startActivity(new Intent(SplashScreenActivity.this, SignInActivity.class));
                            }else{
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
/*

    public void loadSearch(Context mContext) {
        RestClient.getInstance(getApplicationContext()).searchDashboard(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(mContext), UiUtil.getComp_Id(mContext)).enqueue(new CustomCallBack<SearchDashboardData>(mContext, null) {
            @Override
            public void onResponse(Call<SearchDashboardData> call, Response<SearchDashboardData> response) {
                super.onResponse(call, response);
                ud = new SearchDashboardData();
                ud.setStatus(response.code());
                try {
                    if (response.code() == 200) {
                        if (response.body().getTransactionStatus().getIsSuccess()) {
                            ud.setStatusMessage("Success");
                            ud = response.body();
                            if (ud != null) {
                                HomeDashboardFragment.filter = SearchDashboardData.setFilter(ud.getData());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                                Calendar c = Calendar.getInstance();
                                fromDate = simpleDateFormat.format(c.getTime());
                                getHomeDashBoardData(new GetDashboardRequest(fromDate, ud.getData().get(0).getStart(), ud.getData().get(0).getEnd(), UiUtil.getAccountingType(mContext), 0), false);
                            }
                        } else {
                            ud.setStatus(444);
                            Toast.makeText(mContext, ((LinkedTreeMap) (response.body().getTransactionStatus().getError())).get("Description").toString(), Toast.LENGTH_SHORT).show();
                            ud.setStatusMessage(((LinkedTreeMap) (response.body().getTransactionStatus().getError())).get("Description").toString());
                        }
                    } else {
                        ErrorData error = new Gson().fromJson(response.errorBody().string(), ErrorData.class);

                    }
                } catch (Exception e) {
                    Log.d("TEG :: ", e.getLocalizedMessage());
                    ud.setStatus(444);
                    ud.setStatusMessage("Something went wrong");
                }
            }

            @Override
            public void onFailure(Call<SearchDashboardData> call, Throwable t) {
                super.onFailure(call, t);
                Log.d("TEG :: ", t.getLocalizedMessage());
                SearchDashboardData ud = new SearchDashboardData();
                ud.setStatus(444);
                ud.setStatusMessage("Something went wrong");
                Toast.makeText(mContext, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getHomeDashBoardData(GetDashboardRequest getDashboardRequest, boolean refreshWithDates) {

        RestClient.getInstance(mContext).getDashboard(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(mContext), UiUtil.getComp_Id(mContext), getDashboardRequest).enqueue(new CustomCallBack<DashboardData>(mContext, null) {
            public void onResponse(Call<DashboardData> call, Response<DashboardData> response) {
                super.onResponse(call, response);
                try {
                    dashboardResponse = ActivitiesDashboardFragment.dashboardResponse = response.body();
                } catch (Exception e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            public void onFailure(Call<DashboardData> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }
*/

}
