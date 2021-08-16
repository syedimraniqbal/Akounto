package com.akounto.accountingsoftware.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.CountryData;
import com.akounto.accountingsoftware.Data.UserDetails;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Repository.LoginRepo;
import com.akounto.accountingsoftware.ViewModel.ProfileViewModel;
import com.akounto.accountingsoftware.request.RegisterBusiness;
import com.akounto.accountingsoftware.util.AppSingle;
import com.akounto.accountingsoftware.util.JsonUtils;
import com.akounto.accountingsoftware.util.LocalManager;
import com.akounto.accountingsoftware.util.UiConstants;
import com.akounto.accountingsoftware.util.UiUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SocialSignUp2 extends AppCompatActivity {

    private String fist = "", last = "", phone = "", businessType = "", dealsWith = "", business_name, code = "+1", email = "";
    private int businessTypeId = 0, dealsWithId = 0;
    TextView b_name_error, deals_with_business_error, type_business_error;
    EditText b_name;
    ImageView c_care;
    private ProfileViewModel model;
    RegisterBusiness registerBusiness;
    int selectedCountry = 0;
    String countryCode = "US", id_token = "";
    String selectedCurrencyId = "USD";
    List<String> countryListForSpinner = new ArrayList();
    List<String> countryListFoCurrency = new ArrayList();
    List<String> currencyListForSpinner = new ArrayList();
    Map<String, String> currencyMap = new HashMap();
    Map<Integer, String> countryMap = new HashMap();
    Map<Integer, String> dealsMap = new HashMap();
    List<String> dealsinForSpinner = new ArrayList();
    Map<Integer, String> typeBusnisMap = new HashMap();
    List<String> typeBusniSpinner = new ArrayList();
    private Spinner typeBusinessSpinner, countrySpinner, businessCurrencySpinner;
    private Spinner dealsWithBusinessSpinner;
    LinearLayout dealsWithBusiness_error, typeBusiness_error;
    Context mContext;
    LifecycleOwner owner;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signupsetp2);
        mContext = this;
        owner = this;
        c_care = findViewById(R.id.cusmoter_care);
        b_name = findViewById(R.id.business_name);
        countrySpinner = findViewById(R.id.countrySpinner);
        businessCurrencySpinner = findViewById(R.id.businessCurrencySpinner);
        b_name_error = findViewById(R.id.business_name_error);
        deals_with_business_error = findViewById(R.id.deals_with_business_error);
        type_business_error = findViewById(R.id.type_business_error);
        dealsWithBusiness_error = findViewById(R.id.dealsWithBusiness_error);
        typeBusiness_error = findViewById(R.id.typeBusiness_error);
        try {
            this.registerBusiness = LocalManager.getInstance().getRegisterBusiness();
            fist = getIntent().getStringExtra(UiConstants.FIRST_NAME);
            last = getIntent().getStringExtra(UiConstants.LAST_NAME);
            phone = getIntent().getStringExtra(UiConstants.PHONE_NUMBER);
            code = getIntent().getStringExtra(UiConstants.PHONE_CODE);
            email = getIntent().getStringExtra(Constant.EMAIL);
            id_token = getIntent().getStringExtra(Constant.ID_TOKEN);
            fetchCountries();
            fetchCurrencies();
            settypeBusinessSpinner();
            model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ProfileViewModel.class);
            c_care.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_DIAL);
                        callIntent.setData(Uri.parse("tel:+18332568686"));
                        startActivity(callIntent);
                    } catch (Exception e) {
                    }
                }
            });
            findViewById(R.id.continueButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle b = new Bundle();
                    b.putString(Constant.CATEGORY, "sign_up");
                    b.putString(Constant.ACTION, "onClick");
                    b.putString(Constant.EMAIL, email);
                    SplashScreenActivity.sendEvent("social_signup_onClick", b);
                    String bn = b_name.getText().toString();
                    reset();
                    if (isValid()) {
                        model.extReg(mContext, JsonUtils.getExtRegRequst(phone, fist, last, email, b_name.getText().toString().trim(), id_token, "" + dealsWithId, dealsWith, "" + businessTypeId, businessType, "" + selectedCountry, "" + selectedCurrencyId, code)).observe(owner, userDetails -> {
                            try {
                                if (userDetails.getStatus() == 0) {
                                    Bundle b11 = new Bundle();
                                    b11.putString(Constant.CATEGORY, "sign_up");
                                    b11.putString(Constant.ACTION, "social");
                                    b11.putString(Constant.EMAIL, email);
                                    SplashScreenActivity.sendEvent("sign_up_social", b11);
                                    UiUtil.addLoginToSharedPref(SocialSignUp2.this, true);
                                    UiUtil.addUserDetails(SocialSignUp2.this, userDetails);
                                    AppSingle.getInstance().setComp_name(new Gson().fromJson(userDetails.getData().getUserDetails(), UserDetails.class).getActiveBusiness().getName());
                                    AppSingle.getInstance().setEmail(email);
                                    Intent mainIntent = new Intent(SocialSignUp2.this, SocialSignUp3.class);
                                    SocialSignUp2.this.startActivity(mainIntent);
                                } else {
                                    Toast.makeText(SocialSignUp2.this, userDetails.getStatusMessage(), Toast.LENGTH_SHORT).show();
                                    Bundle b11 = new Bundle();
                                    b11.putString(Constant.CATEGORY, "sign_up");
                                    b11.putString(Constant.ACTION, "social");
                                    b11.putString(Constant.EMAIL, email);
                                    SplashScreenActivity.sendEvent("sign_up_social_fail", b11);
                                    UiUtil.showToast(mContext,userDetails.getStatusMessage());
                                }
                            } catch (Exception e) {
                                LoginRepo.prinLogs("" + Log.getStackTraceString(e), 5, "Sign up Socel");
                                Bundle b11 = new Bundle();
                                b11.putString(Constant.CATEGORY, "sign_up");
                                b11.putString(Constant.ACTION, "social");
                                b11.putString(Constant.EMAIL, email);
                                SplashScreenActivity.sendEvent("sign_up_social_fail", b11);
                                UiUtil.showToast(mContext,"There is issue with the data");
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private boolean isValid() {
        boolean response = true;
        String focusfield = "";
        if (b_name.getText().toString().trim().length() == 0) {
            response = false;
            b_name_error.setVisibility(View.VISIBLE);
            b_name.setBackgroundResource(R.drawable.error);
            if (focusfield.equalsIgnoreCase("")) {
                b_name.requestFocus();
                focusfield = "1";
            }
        } else if (b_name.getText().toString().trim().length() < 2) {
            b_name_error.setVisibility(View.VISIBLE);
            b_name_error.setText("Please enter business name min 2 character");
            b_name.setBackgroundResource(R.drawable.error);
            response = false;
            if (focusfield.equalsIgnoreCase("")) {
                b_name.requestFocus();
                focusfield = "1";
            }
        }
        if (dealsWithId == 0) {
            dealsWithBusiness_error.setBackgroundResource(R.drawable.error);
            deals_with_business_error.setVisibility(View.VISIBLE);
            response = false;
            if (focusfield.equalsIgnoreCase("")) {
                dealsWithBusiness_error.requestFocus();
                focusfield = "1";
            }
        }

        if (businessTypeId == 0) {
            typeBusiness_error.setBackgroundResource(R.drawable.error);
            type_business_error.setVisibility(View.VISIBLE);
            if (focusfield.equalsIgnoreCase("")) {
                typeBusiness_error.requestFocus();
                focusfield = "1";
            }
            response = false;
        }

        return response;
    }

    private void reset() {
        try {
            b_name.setBackgroundResource(R.drawable.sign_in_input);
            dealsWithBusiness_error.setBackgroundResource(R.drawable.sign_in_input);
            typeBusiness_error.setBackgroundResource(R.drawable.sign_in_input);
            b_name_error.setVisibility(View.GONE);
            deals_with_business_error.setVisibility(View.GONE);
            type_business_error.setVisibility(View.GONE);
        } catch (Exception e) {
            LoginRepo.prinLogs("" + Log.getStackTraceString(e), 5, "Sign up");
        }
    }

    private void settypeBusinessSpinner() throws JSONException {
        this.typeBusinessSpinner = findViewById(R.id.typeBusinessSpinner);
        String loadJSONFromAsset = JsonUtils.loadJSONFromAsset("type_of_business.json", getApplicationContext());
        Objects.requireNonNull(loadJSONFromAsset);
        JSONArray jsonArray = new JSONArray(loadJSONFromAsset);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.typeBusnisMap.put((Integer) jsonObject.get("Id"), jsonObject.getString("Name"));
            this.typeBusniSpinner.add(jsonObject.getString("Name"));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.dropdown_text, typeBusniSpinner);
        dataAdapter.setDropDownViewResource(R.layout.dropdown_text);
        this.typeBusinessSpinner.setAdapter(dataAdapter);
        this.typeBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                businessTypeId = pos;
                businessType = typeBusniSpinner.get(pos);
                Log.e("selected", typeBusniSpinner.get(pos));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.dealsWithBusinessSpinner = findViewById(R.id.dealsWithBusinessSpinner);

        String loadJSONFromAsset1 = JsonUtils.loadJSONFromAsset("deals_in.json", getApplicationContext());
        Objects.requireNonNull(loadJSONFromAsset1);
        JSONArray jsonArray1 = new JSONArray(loadJSONFromAsset1);
        for (int i = 0; i < jsonArray1.length(); i++) {
            JSONObject jsonObject = jsonArray1.getJSONObject(i);
            this.dealsMap.put((Integer) jsonObject.get("Id"), jsonObject.getString("Name"));
            this.dealsinForSpinner.add(jsonObject.getString("Name"));
        }
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, R.layout.dropdown_text, dealsinForSpinner);
        dataAdapter2.setDropDownViewResource(R.layout.dropdown_text);
        this.dealsWithBusinessSpinner.setAdapter(dataAdapter2);
        this.dealsWithBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                dealsWithId = pos;
                dealsWith = dealsinForSpinner.get(pos);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fetchCountries() throws JSONException {
        String loadJSONFromAsset = JsonUtils.loadJSONFromAsset("country.json", getApplicationContext());
        Objects.requireNonNull(loadJSONFromAsset);
        JSONArray jsonArray = new JSONArray(loadJSONFromAsset);
        CountryData data;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.countryMap.put((Integer) jsonObject.get("Id"), jsonObject.getString("Name"));
            this.countryListForSpinner.add(jsonObject.getString("Name"));
            this.countryListFoCurrency.add(jsonObject.getString("Currency"));
        }
        ArrayAdapter<String> dataAdaptercountry = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_text, countryListForSpinner);
        dataAdaptercountry.setDropDownViewResource(R.layout.dropdown_text);
        countrySpinner.setAdapter(dataAdaptercountry);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCountry = getCountryId(countryListForSpinner.get(i));
                Log.e("Country ::", countryListFoCurrency.get(i));
                businessCurrencySpinner.setSelection(searchIndexCur(countryListFoCurrency.get(i)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private int getCountryId(String selectedCountry2) {
        for (Map.Entry<Integer, String> map : this.countryMap.entrySet()) {
            if (map.getValue().equals(selectedCountry2)) {
                return map.getKey().intValue();
            }
        }
        return 0;
    }

    private void fetchCurrencies() throws JSONException {
        String loadJSONFromAsset = JsonUtils.loadJSONFromAsset("currency.json", getApplicationContext());
        Objects.requireNonNull(loadJSONFromAsset);
        JSONArray jsonArray = new JSONArray(loadJSONFromAsset);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String currency = jsonObject.getString("Id") + " (" + jsonObject.getString("Symbol") + ")- " + jsonObject.getString("Name");
            this.currencyMap.put((String) jsonObject.get("Id"), currency);
            this.currencyListForSpinner.add(currency);
        }
        setCurrencySpinner(this.currencyListForSpinner);
    }

    private void setCurrencySpinner(List<String> currencyListForSpinner2) {
        ArrayAdapter<String> dataAdapterCurrency = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_text, currencyListForSpinner2);
        dataAdapterCurrency.setDropDownViewResource(R.layout.dropdown_text);
        businessCurrencySpinner.setAdapter(dataAdapterCurrency);
        businessCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCurrencyId = currencyListForSpinner2.get(i).substring(0, 3);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private int searchIndexCur(String cur_id) {
        int i = 0;
        for (int c = 0; c < currencyListForSpinner.size(); c++) {
            if (currencyListForSpinner.get(c).contains(cur_id)) {
                i = c;
            }
        }
        return i;
    }
}
