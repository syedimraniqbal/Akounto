package com.akounto.accountingsoftware.Activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.akounto.accountingsoftware.Activity.SplashScreenActivity;
import com.google.gson.Gson;
import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.Business;
import com.akounto.accountingsoftware.Data.LoginData;
import com.akounto.accountingsoftware.Data.UserDetails;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Activity.Dashboard.BussniessList;
import com.akounto.accountingsoftware.Activity.Dashboard.MoreFragment;
import com.akounto.accountingsoftware.network.CustomCallBack;
import com.akounto.accountingsoftware.network.RestClient;
import com.akounto.accountingsoftware.request.AddCompanyRequest;
import com.akounto.accountingsoftware.response.AddBusinessResponse;
import com.akounto.accountingsoftware.response.SignInResponse;
import com.akounto.accountingsoftware.util.AddFragments;
import com.akounto.accountingsoftware.util.JsonUtils;
import com.akounto.accountingsoftware.util.UiUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;

public class AddBusinessFragment extends Fragment {

    TextView businessCurrencyErrorTv;
    Spinner businessCurrencySpinner;
    TextView companyNameErrorTv;
    TextView countryErrorTv;
    List<String> countryListForSpinner = new ArrayList();
    Map<Integer, String> countryMap = new HashMap();
    Spinner countrySpinner;
    List<String> currencyListForSpinner = new ArrayList();
    Map<String, String> currencyMap = new HashMap();
    TextView dealsWithErrorTv;
    List<String> dealsWithList = new ArrayList();
    Spinner dealsWithSpinner;
    EditText etCompanyName;
    int selectedCountry = 0;
    String selectedCurrencyId = "USD";
    String selectedDealsWith;
    String selectedTypeOfBusiness;
    TextView typeOfBusinessErrorTv;
    Spinner typeOfBusinessSpinner;
    List<String> typesOfBusinessList = new ArrayList();
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.add_business_fragment, container, false);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragments.addFragmentToDrawerActivity(getContext(), null, MoreFragment.class);
            }
        });
        initUI();
        return this.view;
    }

    private void initUI() {
        this.etCompanyName = this.view.findViewById(R.id.et_companyName);
        this.typeOfBusinessSpinner = this.view.findViewById(R.id.businessTypeSpinner);
        this.dealsWithSpinner = this.view.findViewById(R.id.dealsWithSpinner);
        this.countrySpinner = this.view.findViewById(R.id.countrySpinner);
        this.businessCurrencySpinner = this.view.findViewById(R.id.businessCurrencySpinner);
        this.companyNameErrorTv = this.view.findViewById(R.id.companyNameErrorTv);
        this.typeOfBusinessErrorTv = this.view.findViewById(R.id.typeOfBusinessErrorTv);
        this.dealsWithErrorTv = this.view.findViewById(R.id.dealsWithErrorTv);
        this.businessCurrencyErrorTv = this.view.findViewById(R.id.businessCurrencyErrorTv);
        this.countryErrorTv = this.view.findViewById(R.id.countryErrorTv);
        this.typesOfBusinessList.add("LLC");
        this.typesOfBusinessList.add("Non Profit Organisation");
        this.typesOfBusinessList.add("Partnership or LLP");
        this.typesOfBusinessList.add("Proprietorship");
        this.typesOfBusinessList.add("Others");
        ArrayAdapter dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, typesOfBusinessList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeOfBusinessSpinner.setAdapter(dataAdapter);
        typeOfBusinessSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTypeOfBusiness = typesOfBusinessList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        this.dealsWithList.add("Book-keeping");
        this.dealsWithList.add("Construction");
        this.dealsWithList.add("Financial Services");
        this.dealsWithList.add("Health Services");
        this.dealsWithList.add("Information Technology Services");
        this.dealsWithList.add("Marketing Services");
        this.dealsWithList.add("Medical Services");
        this.dealsWithList.add("Non Profit Organisations");
        this.dealsWithList.add("Photography and creative services");
        this.dealsWithList.add("Real Estate");
        this.dealsWithList.add("Retailers and Resellers");
        this.dealsWithList.add("Others");
        ArrayAdapter dataAdapterdeals = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, dealsWithList);
        dataAdapterdeals.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dealsWithSpinner.setAdapter(dataAdapterdeals);
        dealsWithSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDealsWith = dealsWithList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        try {
            fetchCountries();
            fetchCurrencies();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.view.findViewById(R.id.saveButton).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AddBusinessFragment.this.lambda$initUI$2$AddBusinessFragment(view);
            }
        });
        this.view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AddBusinessFragment.this.lambda$initUI$3$AddBusinessFragment(view);
            }
        });
    }

    public void lambda$initUI$0$AddBusinessFragment(int i, String s, int selectedIndex, String selectedItem) {

    }

    public void lambda$initUI$1$AddBusinessFragment(int i, String s, int selectedIndex, String selectedItem) {

    }

    public void lambda$initUI$2$AddBusinessFragment(View v) {
        createNewBusiness();
    }

    public void lambda$initUI$3$AddBusinessFragment(View v) {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void fetchCountries() throws JSONException {
        String loadJSONFromAsset = JsonUtils.loadJSONFromAsset( "country.json",getContext());
        Objects.requireNonNull(loadJSONFromAsset);
        JSONArray jsonArray = new JSONArray(loadJSONFromAsset);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            this.countryMap.put((Integer) jsonObject.get("Id"), jsonObject.getString("Name"));
            this.countryListForSpinner.add(jsonObject.getString("Name"));
        }
        ////Collections.sort(countryListForSpinner);
        ArrayAdapter dataAdaptercountry = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, countryListForSpinner);
        dataAdaptercountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        String loadJSONFromAsset = JsonUtils.loadJSONFromAsset("currency.json",getContext());
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
        ArrayAdapter dataAdapterCurrency = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, currencyListForSpinner2);
        dataAdapterCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

    private void createNewBusiness() {
        String companyName = this.etCompanyName.getText().toString();
        if (companyName.isEmpty()) {
            this.companyNameErrorTv.setText("Please enter company name");
            this.companyNameErrorTv.setVisibility(View.VISIBLE);
            return;
        }
        this.companyNameErrorTv.setVisibility(View.GONE);
        if (this.selectedTypeOfBusiness.isEmpty()) {
            this.typeOfBusinessErrorTv.setVisibility(View.VISIBLE);
            return;
        }
        this.typeOfBusinessErrorTv.setVisibility(View.GONE);
        if (this.selectedDealsWith.isEmpty()) {
            this.dealsWithErrorTv.setVisibility(View.VISIBLE);
            return;
        }
        this.dealsWithErrorTv.setVisibility(View.GONE);
        if (this.selectedCountry == 0) {
            this.countryErrorTv.setVisibility(View.VISIBLE);
            return;
        }
        this.countryErrorTv.setVisibility(View.GONE);
        if (this.selectedCurrencyId.isEmpty()) {
            this.businessCurrencyErrorTv.setVisibility(View.VISIBLE);
            return;
        }
        this.businessCurrencyErrorTv.setVisibility(View.GONE);
        save(new AddCompanyRequest((this.dealsWithList.indexOf(this.selectedDealsWith) + 1) + "", (this.typesOfBusinessList.indexOf(this.selectedTypeOfBusiness) + 1) + "", this.selectedCurrencyId, companyName, this.selectedDealsWith, this.selectedCountry + "", this.selectedTypeOfBusiness));
    }

    public void save(AddCompanyRequest addCompanyRequest) {
        RestClient.getInstance(getContext()).addCompany(Constant.X_SIGNATURE,"Bearer " +UiUtil.getAcccessToken(getContext()),UiUtil.getComp_Id(getContext()),addCompanyRequest).enqueue(new CustomCallBack<AddBusinessResponse>(getContext(), "Adding business...") {
            public void onResponse(Call<AddBusinessResponse> call, Response<AddBusinessResponse> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    UiUtil.showToast(AddBusinessFragment.this.getContext(), "Business Added!");
                    Bundle b=new Bundle();
                    b.putString(Constant.CATEGORY,"profile");
                    b.putString(Constant.ACTION,"add_business");
                    SplashScreenActivity.mFirebaseAnalytics.logEvent("profile_add_business",b);
                    UserDetails userDetails = UiUtil.getUserDetail(getContext());
                    SignInResponse signInResponse=UiUtil.getUserDetails(getContext());
                    List<Business> list_buss=userDetails.getBusiness();
                    list_buss.add(response.body().getData());
                    userDetails.setBusiness(list_buss);
                    signInResponse.setUserDetails(new Gson().toJson(userDetails));
                    LoginData loginData=new Gson().fromJson(new Gson().toJson(signInResponse), LoginData.class);
                    loginData.setExpires(signInResponse.getExpires());
                    UiUtil.addUserDetails(getContext(), signInResponse);
                    AddFragments.addFragmentToDrawerActivity(getContext(), null, BussniessList.class);
                    return;
                }
                UiUtil.showToast(AddBusinessFragment.this.getContext(), "Some error occurred while adding.");
            }

            public void onFailure(Call<AddBusinessResponse> call, Throwable t) {
                super.onFailure(call, t);
                UiUtil.showToast(AddBusinessFragment.this.getContext(), "Error occurred while adding.");
            }
        });
    }
}
