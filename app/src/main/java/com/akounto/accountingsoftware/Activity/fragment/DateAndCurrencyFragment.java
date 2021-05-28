package com.akounto.accountingsoftware.Activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.LoginData;
import com.akounto.accountingsoftware.Data.UserDetails;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Activity.Setting.SettingMenu;
import com.akounto.accountingsoftware.network.CustomCallBack;
import com.akounto.accountingsoftware.network.RestClient;
import com.akounto.accountingsoftware.response.SignInResponse;
import com.akounto.accountingsoftware.util.AddFragments;
import com.akounto.accountingsoftware.util.UiUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class DateAndCurrencyFragment extends Fragment {

    List<String> dates = new ArrayList();
    Spinner datesSpinner;
    int financialDayEnd = 31;
    int financialMonthEnd = 12;
    List<String> months = Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");
    Spinner monthsSpinner;
    TextView tipTv;
    View view;
    int d = 0;
    int m = 0;
    UserDetails userDetails;
    SignInResponse signInResponse;
    boolean start = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.dates_and_currency_fragment, container, false);
        try {
            String endfy = UiUtil.getUserDetail(getContext()).getActiveBusiness().getFinancialYearEnd();
            d = Integer.parseInt(endfy.split("-")[0]);
            m = Integer.parseInt(endfy.split("-")[1]) - 1;

            userDetails = UiUtil.getUserDetail(getContext());
            signInResponse = UiUtil.getUserDetails(getContext());


        } catch (Exception e) {

        }
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragments.addFragmentToDrawerActivity(getActivity(), null, SettingMenu.class);
            }
        });
        initUI();
        return this.view;
    }

    private void initUI() {
        this.tipTv = this.view.findViewById(R.id.tipTV);
        this.monthsSpinner = this.view.findViewById(R.id.monthsSpinner);
        this.datesSpinner = this.view.findViewById(R.id.datesSpinner);
        updateDateSpinner(this.financialMonthEnd - 1);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, this.months);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthsSpinner.setAdapter(dataAdapter);
        try {
            this.monthsSpinner.setSelection(m);
        } catch (Exception e) {
        }
        monthsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    financialMonthEnd = position + 1;
                    updateDateSpinner(position);
                } catch (Exception e) {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        this.view.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DateAndCurrencyFragment.this.lambda$initUI$1$DateAndCurrencyFragment(view);
            }
        });
        this.view.findViewById(R.id.infoIv).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                DateAndCurrencyFragment.this.lambda$initUI$2$DateAndCurrencyFragment(view);
            }
        });
    }

    public void lambda$initUI$0$DateAndCurrencyFragment(int i, String s, int selectedIndex, String selectedItem) {

    }

    public void lambda$initUI$1$DateAndCurrencyFragment(View v) {
        Map<String, Integer> map = new HashMap<>();
        map.put("FinancialMonthEnd", Integer.valueOf(this.financialMonthEnd));
        map.put("FinancialDayEnd", Integer.valueOf(this.financialDayEnd));
        submitRequest(map);
    }

    public void lambda$initUI$2$DateAndCurrencyFragment(View v) {
        if (this.tipTv.getVisibility() == View.VISIBLE) {
            this.tipTv.setVisibility(View.GONE);
        } else {
            this.tipTv.setVisibility(View.VISIBLE);
        }
    }

    private void updateDateSpinner(int financialMonthEnd2) {
        this.dates.clear();
        Spinner powerSpinnerView = this.datesSpinner;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(1, calendar.get(1));
            calendar.set(2, financialMonthEnd2);
            int days = calendar.getActualMaximum(5);
            this.financialDayEnd = days;
            for (int i = 1; i <= days; i++) {
                List<String> list = this.dates;
                list.add("" + i);
            }
            ArrayAdapter dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, this.dates);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            powerSpinnerView.setAdapter(dataAdapter);
            if (start) {
                powerSpinnerView.setSelection(d - 1);
            }
        } catch (Exception e) {
        }
        powerSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start = false;
                financialDayEnd = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        List<String> list2 = this.dates;
    }

    public void lambda$updateDateSpinner$3$DateAndCurrencyFragment(int i, String s, int selectedIndex, String selectedItem) {

    }

    private void submitRequest(Map<String, Integer> map) {
        RestClient.getInstance(getContext()).editCompanyFinancialYear(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(getContext()), UiUtil.getComp_Id(getContext()), map).enqueue(new CustomCallBack<ResponseBody>(getContext(), null) {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    UiUtil.showToast(DateAndCurrencyFragment.this.getContext(), "Saved");
                    userDetails.getActiveBusiness().setFinancialYearEnd(financialDayEnd + "-" + financialMonthEnd);
                    signInResponse.setUserDetails(new Gson().toJson(userDetails));
                    LoginData loginData = new Gson().fromJson(new Gson().toJson(signInResponse), LoginData.class);
                    loginData.setExpires(signInResponse.getExpires());
                    UiUtil.addUserDetails(getContext(), signInResponse);
                } else {
                    UiUtil.showToast(DateAndCurrencyFragment.this.getContext(), "Error while Saving");
                }
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                Log.d("error", t.toString());
            }
        });
    }
}
