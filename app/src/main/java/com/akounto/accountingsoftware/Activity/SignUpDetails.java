package com.akounto.accountingsoftware.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Services.Api;
import com.akounto.accountingsoftware.Services.ApiUtils;
import com.akounto.accountingsoftware.request.RegisterBusiness;
import com.akounto.accountingsoftware.request.User;
import com.akounto.accountingsoftware.response.SignUp.SignUpResponse;
import com.akounto.accountingsoftware.util.JsonUtils;
import com.akounto.accountingsoftware.util.LocalManager;
import com.akounto.accountingsoftware.util.UiUtil;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpDetails extends AppCompatActivity {

    private EditText f_n, l_n, p_n, b_name;
    TextView f_n_error, l_n_error, p_n_error, b_name_error;
    private LinearLayout next, aboutUs, aboutBusiness, nxt_back, back,f_n_ll, l_n_ll, p_n_ll, b_name_ll;
    private String fist = "", last = "", phone = "", businessType = "", dealsWith = "", business_name;
    private int businessTypeId = 0, dealsWithId = 0;
    RegisterBusiness registerBusiness;
    Spinner businessCurrencySpinner;
    Spinner countrySpinner;
    int selectedCountry = 0;
    String selectedCurrencyId = "USD";
    List<String> countryListForSpinner = new ArrayList();
    Map<Integer, String> countryMap = new HashMap();
    List<String> currencyListForSpinner = new ArrayList();
    Map<String, String> currencyMap = new HashMap();
    private Spinner typeBusinessSpinner;
    private Spinner dealsWithBusinessSpinner;
    Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_signup1);
        try {
            mContext = this;
            registerBusiness = LocalManager.getInstance().getRegisterBusiness();
            //
            Bundle b = new Bundle();
            b.putString(Constant.CATEGORY, "sign_up");
            b.putString(Constant.ACTION, "screen_view");
            b.putString(Constant.COMPANY, business_name);
            SplashScreenActivity.sendEvent("sign_up2_screen_view", b);
            next = findViewById(R.id.nxt_done);
            nxt_back = findViewById(R.id.nxt_back);
            aboutUs = findViewById(R.id.about_us);
            back = findViewById(R.id.back);
            aboutBusiness = findViewById(R.id.about_business);
            this.countrySpinner = findViewById(R.id.countrySpinner);
            this.businessCurrencySpinner = findViewById(R.id.businessCurrencySpinner);
            f_n = findViewById(R.id.first_name);
            l_n = findViewById(R.id.last_name);
            p_n = findViewById(R.id.edt_phone_number);
            b_name = findViewById(R.id.business_name);

            f_n_error = findViewById(R.id.first_name_error);
            l_n_error = findViewById(R.id.last_name_error);
            p_n_error = findViewById(R.id.edt_phone_number_error);
            b_name_error = findViewById(R.id.business_name_error);

            f_n_ll = findViewById(R.id.first_name_ll);
            l_n_ll = findViewById(R.id.last_name_ll);
            p_n_ll = findViewById(R.id.phone_number_ll);
            b_name_ll = findViewById(R.id.business_name_ll);

            /*next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isValidAbout()) {
                        aboutUs.setVisibility(View.GONE);
                        aboutBusiness.setVisibility(View.VISIBLE);


                    }
                }
            });
            nxt_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    aboutUs.setVisibility(View.VISIBLE);
                    aboutBusiness.setVisibility(View.GONE);

                }
            });
           */
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(SignUpDetails.this, SIgnUpStep0.class));
                }
            });
            settypeBusinessSpinner();
            try {
                fetchCountries();
                fetchCurrencies();
            } catch (Exception e) {
                e.printStackTrace();
                Bundle ab = new Bundle();
                b.putString(Constant.CATEGORY, "sign_up");
                b.putString(Constant.ACTION, "Exception");
                b.putString(Constant.COMPANY, business_name);
                SplashScreenActivity.sendEvent("sign_up_Exception", b);
            }

            findViewById(R.id.continueButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reset();
                    User user;
                    fist = f_n.getText().toString();
                    last = l_n.getText().toString();
                    phone = p_n.getText().toString();
                    try {
                        user = registerBusiness.getUser();
                        user.setFirstName(fist);
                        user.setLastName(last);
                    } catch (Exception e) {
                        user = new User();
                        user.setFirstName("NA");
                        user.setLastName("NA");
                    }
                    registerBusiness.setUser(user);
                    registerBusiness.setBusinessName(b_name.getText().toString());
                    registerBusiness.setBusinessEntity(businessType);
                    registerBusiness.setBusinessEntityId(businessTypeId + 1);
                    registerBusiness.setIndustryTypeId(dealsWithId + 1);
                    registerBusiness.setIndustryTypeName(dealsWith);
                    registerBusiness.setPhone(phone);
                    registerBusiness.setCountry(selectedCountry);
                    registerBusiness.setBusinessCurrency(selectedCurrencyId);
                    business_name = b_name.getText().toString();
                    Bundle b = new Bundle();
                    b.putString(Constant.CATEGORY, "sign_up");
                    b.putString(Constant.ACTION, "business_info_click");
                    b.putString(Constant.COMPANY, business_name);
                    SplashScreenActivity.sendEvent("sign_up_business_info_click", b);
                    if (isValidAbout() && isValidBusiness()) {
                        UiUtil.showProgressDialogue(mContext, "", "Please wait...");
                        JsonObject requet = UiUtil.getRegRequst(registerBusiness);
                        Api api = ApiUtils.getAPIService();
                        api.register(Constant.X_SIGNATURE, requet).enqueue(new Callback<SignUpResponse>() {
                            @Override
                            public void onResponse(Call<SignUpResponse> call, Response<SignUpResponse> response) {
                                UiUtil.cancelProgressDialogue();

                                SignUpResponse responsed = response.body();
                                try {
                                    if (responsed.getTransactionStatus().getIsSuccess()) {
                                        UiUtil.showToast(mContext, "Register successfully");
                                        UiUtil.addLoginToSharedPref(mContext, true);
                                        UiUtil.addUserDetails(mContext, responsed);
                                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Bundle b = new Bundle();
                                        b.putString(Constant.CATEGORY, "sign_up");
                                        b.putString(Constant.ACTION, "business_info_success");
                                        b.putString(Constant.COMPANY, business_name);
                                        SplashScreenActivity.sendEvent("sign_up_register_success", b);
                                    } else {
                                        Bundle b = new Bundle();
                                        b.putString(Constant.CATEGORY, "sign_up");
                                        b.putString(Constant.ACTION, "business_info_fail");
                                        b.putString(Constant.COMPANY, business_name);
                                        SplashScreenActivity.sendEvent("sign_up_register_fail", b);
                                        if (!responsed.getTransactionStatus().getIsSuccess()) {
                                            UiUtil.showToast(mContext, ((LinkedTreeMap) (response.body().getTransactionStatus().getError())).get("Description").toString());
                                        } else {
                                            UiUtil.showToast(mContext, "Not able to send register request");
                                        }
                                    }
                                } catch (Exception e) {
                                    Bundle b = new Bundle();
                                    b.putString(Constant.CATEGORY, "sign_up");
                                    b.putString(Constant.ACTION, "business_info_fail");
                                    b.putString(Constant.COMPANY, business_name);
                                    SplashScreenActivity.sendEvent("sign_up_register_fail", b);
                                    UiUtil.showToast(mContext, "Something went wrong");
                                }
                            }

                            @Override
                            public void onFailure(Call<SignUpResponse> call, Throwable t) {
                                UiUtil.cancelProgressDialogue();
                                UiUtil.showToast(mContext, "Not able to send register request");
                                Bundle b = new Bundle();
                                b.putString(Constant.CATEGORY, "sign_up");
                                b.putString(Constant.ACTION, "business_info_fail");
                                SplashScreenActivity.sendEvent("sign_up_register_fail", b);
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            Bundle b = new Bundle();
            b.putString(Constant.CATEGORY, "sign_up");
            b.putString(Constant.ACTION, "Exception");
            b.putString(Constant.CAUSES, e.getMessage());
            SplashScreenActivity.sendEvent("sign_up_Exception", b);
        }
    }

    private void settypeBusinessSpinner() {
        this.typeBusinessSpinner = findViewById(R.id.typeBusinessSpinner);
        final List<String> finalCategories = new ArrayList<>();
        finalCategories.add("INC or Corporation");
        finalCategories.add("Limited Liability Company (LLC)");
        finalCategories.add("Partnership or LLP");
        finalCategories.add("Sole Proprietorship");
        finalCategories.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, R.layout.dropdown_text, finalCategories);
        dataAdapter.setDropDownViewResource(R.layout.dropdown_text);
        this.typeBusinessSpinner.setAdapter(dataAdapter);
        this.typeBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                businessTypeId = pos;
                businessType = finalCategories.get(pos);
                Log.e("selected", finalCategories.get(pos));
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        this.dealsWithBusinessSpinner = findViewById(R.id.dealsWithBusinessSpinner);
        final List<String> finalCategories2 = new ArrayList<>();
        finalCategories2.add("Accounting Services");
        finalCategories2.add("Consultants, Doctors, Lawyers and similar");
        finalCategories2.add("Information Technology Services");
        finalCategories2.add("Manufacturing");
        finalCategories2.add("Professional, Scientific and Technical Services");
        finalCategories2.add("Restaurants, Bars and Similar");
        finalCategories2.add("Retail and Similar");
        finalCategories2.add("Other Financial Services");
        finalCategories2.add("Tour and Travels / Hospitality");
        finalCategories2.add("Wholesale Trade");
        finalCategories2.add("Logistics and Transportation");
        finalCategories2.add("Other Services");
        finalCategories2.add("Other");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(this, R.layout.dropdown_text, finalCategories2);
        dataAdapter.setDropDownViewResource(R.layout.dropdown_text);
        this.dealsWithBusinessSpinner.setAdapter(dataAdapter2);
        this.dealsWithBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long arg3) {
                dealsWithId = pos;
                dealsWith = finalCategories2.get(pos);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fetchCountries() throws JSONException {
        String loadJSONFromAsset = JsonUtils.loadJSONFromAsset("country.json", getApplicationContext());
        Objects.requireNonNull(loadJSONFromAsset);
        JSONArray jsonArray = new JSONArray(loadJSONFromAsset);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.countryMap.put((Integer) jsonObject.get("Id"), jsonObject.getString("Name"));
            this.countryListForSpinner.add(jsonObject.getString("Name"));
        }
        ////Collections.sort(countryListForSpinner);
        ArrayAdapter<String> dataAdaptercountry = new ArrayAdapter<>(getApplicationContext(), R.layout.dropdown_text, countryListForSpinner);
        dataAdaptercountry.setDropDownViewResource(R.layout.dropdown_text);
        countrySpinner.setAdapter(dataAdaptercountry);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCountry = getCountryId(countryListForSpinner.get(i));
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

    private boolean isValidAbout() {
        if (f_n.getText().toString().length() == 0) {
            //UiUtil.showToast(this, "Please enter first name");
            f_n_error.setVisibility(View.VISIBLE);
            f_n_ll.setBackgroundResource(R.drawable.error);
            f_n.requestFocus();
            return false;
        } else if (f_n.getText().toString().length() < 2) {
            //UiUtil.showToast(this, "Please enter first name min two character");
            f_n_error.setVisibility(View.VISIBLE);
            f_n_error.setText("Please enter first name min two character");
            f_n_ll.setBackgroundResource(R.drawable.error);
            f_n.requestFocus();
            return false;
        } else if (l_n.getText().toString().length() == 0) {
            //UiUtil.showToast(this, "Please enter last name");
            l_n_error.setVisibility(View.VISIBLE);
            l_n_ll.setBackgroundResource(R.drawable.error);
            l_n.requestFocus();
            return false;
        } else if (l_n.getText().toString().length() < 2) {
            //UiUtil.showToast(this, "Please enter last name min two character");
            l_n_error.setVisibility(View.VISIBLE);
            l_n_error.setText("Please enter last name min two character");
            l_n_ll.setBackgroundResource(R.drawable.error);
            l_n.requestFocus();
            return false;
        } else if (p_n.getText().toString().length() == 0) {
            //UiUtil.showToast(this, "Please enter valid phone number");
            p_n_error.setVisibility(View.VISIBLE);
            p_n_ll.setBackgroundResource(R.drawable.error);
            p_n.requestFocus();
            return false;
        } else if (p_n.getText().toString().length() < 10) {
            //UiUtil.showToast(this, "Please enter valid phone number");
            p_n_error.setVisibility(View.VISIBLE);
            p_n_ll.setBackgroundResource(R.drawable.error);
            p_n.requestFocus();
            return false;
        } else {
            reset();
            Bundle b = new Bundle();
            b.putString(Constant.CATEGORY, "sign_up");
            b.putString(Constant.ACTION, "about_us");
            b.putString(Constant.COMPANY, business_name);
            SplashScreenActivity.sendEvent("sign_up_about_us", b);
            return true;
        }
    }

    private boolean isValidBusiness() {
        if (b_name.getText().toString().length() == 0) {
            //UiUtil.showToast(this, "Please enter business name");
            b_name_error.setVisibility(View.VISIBLE);
            b_name_ll.setBackgroundResource(R.drawable.error);
            b_name.requestFocus();
            return false;
        } else if (b_name.getText().toString().length() < 2) {
            //UiUtil.showToast(this, "Please enter business name min two character");
            b_name_error.setVisibility(View.VISIBLE);
            b_name_error.setText("Please enter business name min two character");
            b_name_ll.setBackgroundResource(R.drawable.error);
            b_name.requestFocus();
            return false;
        } else {
            //reset();
            return true;
        }
    }

    private void reset() {
        try {
            f_n_ll.setBackgroundResource(R.drawable.new_light_blue);
            l_n_ll.setBackgroundResource(R.drawable.new_light_blue);
            p_n_ll.setBackgroundResource(R.drawable.new_light_blue);
            b_name_ll.setBackgroundResource(R.drawable.new_light_blue);

            f_n_error.setVisibility(View.GONE);
            l_n_error.setVisibility(View.GONE);
            p_n_error.setVisibility(View.GONE);
            b_name_error.setVisibility(View.GONE);
        } catch (Exception e) {
        }
    }
}
