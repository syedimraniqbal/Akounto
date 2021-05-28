package com.akounto.accountingsoftware.Activity.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akounto.accountingsoftware.Activity.AddChatOfAccountActivity;
import com.akounto.accountingsoftware.Activity.Dashboard.MoreFragment;
import com.akounto.accountingsoftware.Activity.TransactionCategoryClick;
import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.adapter.AddWithdrawalCategoryAdapter;
import com.akounto.accountingsoftware.model.AddWithDrawalCategory;
import com.akounto.accountingsoftware.model.AddWithdrawalCategorySpinnerItem;
import com.akounto.accountingsoftware.network.CustomCallBack;
import com.akounto.accountingsoftware.network.RestClient;
import com.akounto.accountingsoftware.request.AddTransactionRequest;
import com.akounto.accountingsoftware.request.BankSubTransactionsItem;
import com.akounto.accountingsoftware.response.CustomeResponse;
import com.akounto.accountingsoftware.response.Transaction;
import com.akounto.accountingsoftware.response.accounting.BankResponse;
import com.akounto.accountingsoftware.response.accounting.BanksItem;
import com.akounto.accountingsoftware.response.accounting.GetBankResponse;
import com.akounto.accountingsoftware.response.chartaccount.GetChartData;
import com.akounto.accountingsoftware.response.chartaccount.GetChartResponse;
import com.akounto.accountingsoftware.response.chartaccount.HeadSubType;
import com.akounto.accountingsoftware.response.chartaccount.HeadTransactions;
import com.akounto.accountingsoftware.response.chartaccount.HeadType;
import com.akounto.accountingsoftware.util.AddFragments;
import com.akounto.accountingsoftware.util.UiUtil;
import com.github.mikephil.charting.utils.Utils;
import com.google.gson.Gson;
import com.skydoves.powerspinner.OnSpinnerItemSelectedListener;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class AddTransactionFragment extends Fragment implements TransactionCategoryClick {
    public static int UNCATEGORIZED_ITEM_ID;
    PowerSpinnerView accountSpinner;
    List<BanksItem> banksItems = new ArrayList();
    double categoriesAmountSum = Utils.DOUBLE_EPSILON;
    LinearLayout categoriesList;
    List<HeadTransactions> categoryDetails = new ArrayList();
    View categoryView;
    String depositOrWithdrawal = "deposit";
    PowerSpinnerView depositOrWithdrawalSpinner;
    EditText descEditText;
    Dialog dialog;
    TextView errorTv;
    String isoDatePattern = "yyyy-MM-dd'T'HH:mm:ss";
    List<AddWithdrawalCategorySpinnerItem> list = new ArrayList();
    List<AddWithdrawalCategorySpinnerItem> localCategoriesList = new ArrayList();
    private int mDay;
    private int mMonth;
    private int mYear;
    EditText notesEditText;
    TextView pageTitle;
    View view;
    List<BankSubTransactionsItem> receivedBankSubTransactions = new ArrayList();
    Transaction receivedData;
    int receivedDataId = 0;
    String receivedDateFormat = "yyyy-MM-dd'T'HH:mm:ss";
    TextView selectDate;
    int selectedBankAccountId = 0;
    String selectedFormattedDate;
    SimpleDateFormat simpleDateFormat;
    EditText totalAmountEditText;
    AddWithdrawalCategorySpinnerItem uncategorizedItem = new AddWithdrawalCategorySpinnerItem("Uncategorized", AddWithDrawalCategory.CATEGORY_ITEM, UNCATEGORIZED_ITEM_ID);

    public void setData(Transaction receivedData) {
        this.receivedData = receivedData;
        if (receivedData.getTransactionType() == 1) {
            depositOrWithdrawal = "Deposit";
        } else {
            depositOrWithdrawal = "Withdrawal";
        }
    }

    public void setData(String depositOrWithdrawal) {
        this.depositOrWithdrawal = depositOrWithdrawal;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_addtransection, container, false);
        this.pageTitle = view.findViewById(R.id.pageTitle);
        pageTitle.setText("Add transaction details");
        this.simpleDateFormat = new SimpleDateFormat(this.isoDatePattern, Locale.US);
        this.categoriesList = view.findViewById(R.id.categoriesList);
        addCategory();
        this.errorTv = view.findViewById(R.id.errorNote);
        this.notesEditText = view.findViewById(R.id.notes);
        this.descEditText = view.findViewById(R.id.et_desc);
        this.totalAmountEditText = view.findViewById(R.id.totalAmount);
        this.accountSpinner = view.findViewById(R.id.accountSpinner);
        this.depositOrWithdrawalSpinner = view.findViewById(R.id.depositOrWithdrawalSpinner);
        this.depositOrWithdrawalSpinner.setText(depositOrWithdrawal);
        Locale.setDefault(Locale.US);
        this.selectDate = view.findViewById(R.id.date);
        Calendar c = Calendar.getInstance();
        Transaction transaction = this.receivedData;
        view.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFragments.addFragmentToDrawerActivity(getContext(), null, TransactionsFragment.class);
            }
        });
        if (receivedData != null) {
            try {
                c.setTime(this.simpleDateFormat.parse(transaction.getTransactionDate()));
                this.descEditText.setText(this.receivedData.getDescription());
                this.notesEditText.setText(this.receivedData.getNotes());
                this.totalAmountEditText.setText(String.format("%.02f", (float) this.receivedData.getAmount()));
                this.selectedBankAccountId = this.receivedData.getBankAccountId();
                this.receivedDataId = this.receivedData.getId();
                this.receivedBankSubTransactions = this.receivedData.getBankSubTransactions();
                disableView();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.mYear = c.get(1);
        this.mMonth = c.get(2);
        this.mDay = c.get(5);
        TextView textView2 = this.selectDate;
        textView2.setText(this.mDay + "-" + (this.mMonth + 1) + "-" + this.mYear);
        this.selectedFormattedDate = this.simpleDateFormat.format(c.getTime());
        this.selectDate.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AddTransactionFragment.this.lambda$onCreate$1$AddTransactionFragment(view);
            }
        });
        view.findViewById(R.id.addCategory).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AddTransactionFragment.this.lambda$onCreate$2$AddTransactionFragment(view);
            }
        });
        view.findViewById(R.id.applyButton).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AddTransactionFragment.this.lambda$onCreate$3$AddTransactionFragment(view);
            }
        });
        return this.view;
    }

    public void lambda$onCreate$1$AddTransactionFragment(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                AddTransactionFragment.this.lambda$null$0$AddTransactionFragment(datePicker, i, i2, i3);
            }
        }, this.mYear, this.mMonth, this.mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void lambda$null$0$AddTransactionFragment(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(5, dayOfMonth);
        calendar.set(2, monthOfYear);
        calendar.set(1, year);
        TextView textView = this.selectDate;
        textView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
        this.selectedFormattedDate = this.simpleDateFormat.format(calendar.getTime());
    }

    public void lambda$onCreate$2$AddTransactionFragment(View v) {
        addCategory();
    }

    public void lambda$onCreate$3$AddTransactionFragment(View v) {
        addTransaction();
    }

    private void disableView() {
        this.accountSpinner.setEnabled(false);
        this.accountSpinner.setAlpha(0.4f);
        this.depositOrWithdrawalSpinner.setEnabled(false);
        this.depositOrWithdrawalSpinner.setAlpha(0.4f);
        this.selectDate.setEnabled(false);
        this.selectDate.setAlpha(0.4f);
        this.totalAmountEditText.setEnabled(false);
        this.totalAmountEditText.setAlpha(0.4f);
    }

    private void updateCategories(List<BankSubTransactionsItem> bankSubTransactions) {
        this.categoriesList.removeAllViews();
        for (BankSubTransactionsItem bsti : bankSubTransactions) {
            View newCategoryView = getLayoutInflater().inflate(R.layout.item_add_transaction_category_layout, null, false);
            ((TextView) newCategoryView.findViewById(R.id.cattotalAMout)).setText(String.format("%.02f", Float.parseFloat(bsti.getAmount())));
            onCategoryClick(getCategoryDetails(bsti.getTransactionHeadId()), newCategoryView.findViewById(R.id.category), newCategoryView.findViewById(R.id.cattotalAMout));
            this.categoriesList.addView(newCategoryView);
            newCategoryView.findViewById(R.id.category).setOnClickListener(new View.OnClickListener() {

                public final void onClick(View view) {
                    AddTransactionFragment.this.lambda$updateCategories$4$AddTransactionFragment(newCategoryView, view);
                }
            });
            ImageView deleteCategory = newCategoryView.findViewById(R.id.deleteCategory);
            if (this.categoriesList.getChildCount() > 1) {
                deleteCategory.setVisibility(View.VISIBLE);
            }
            deleteCategory.setOnClickListener(new View.OnClickListener() {

                public final void onClick(View view) {
                    AddTransactionFragment.this.lambda$updateCategories$5$AddTransactionFragment(newCategoryView, view);
                }
            });
        }
    }

    public void lambda$updateCategories$4$AddTransactionFragment(View newCategoryView, View v) {
        selectCategoryDialog(newCategoryView.findViewById(R.id.category), newCategoryView.findViewById(R.id.cattotalAMout));
    }

    public void lambda$updateCategories$5$AddTransactionFragment(View newCategoryView, View v) {
        this.categoriesList.removeView(newCategoryView);
    }

    private AddWithdrawalCategorySpinnerItem getCategoryDetails(int transactionHeadId) {
        if (transactionHeadId == UNCATEGORIZED_ITEM_ID) {
            return this.uncategorizedItem;
        }
        for (AddWithdrawalCategorySpinnerItem item : this.list) {
            if (transactionHeadId == item.getId()) {
                return item;
            }
        }
        return null;
    }

    private String getAccountNameById(int selectedBankAccountId2) {
        for (BanksItem banksItem : this.banksItems) {
            if (selectedBankAccountId2 == banksItem.getBankAccounts().getId()) {
                return banksItem.getInstitutionName();
            }
        }
        return "";
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        getBanks();
        getCharts();
    }

    private void getCharts() {
        RestClient.getInstance(getContext()).getChart(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(getContext()), UiUtil.getComp_Id(getContext()), "").enqueue(new CustomCallBack<GetChartResponse>(getContext(), null) {
            public void onResponse(Call<GetChartResponse> call, Response<GetChartResponse> response) {
                super.onResponse(call, response);
                UiUtil.showLog("111111----2222", new Gson().toJson(response.body().getData()));
                if (response.isSuccessful()) {
                    AddTransactionFragment.this.prepareCategorySpinner(response.body().getData());
                }
            }

            public void onFailure(Call<GetChartResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /* access modifiers changed from: private */
    public void prepareCategorySpinner(GetChartData data) {
        for (HeadType outer : data.getHeadTypes()) {
            for (HeadSubType headSubType : outer.getHeadSubTypes()) {
                if (!headSubType.getHeadTransactions().isEmpty()) {
                    this.categoryDetails.addAll(headSubType.getHeadTransactions());
                    this.list.add(new AddWithdrawalCategorySpinnerItem(headSubType.getName(), AddWithDrawalCategory.HEADER, headSubType.getId()));
                    for (HeadTransactions headTransactions : headSubType.getHeadTransactions()) {
                        this.list.add(new AddWithdrawalCategorySpinnerItem(headTransactions.getName(), AddWithDrawalCategory.CATEGORY_ITEM, headTransactions.getId()));
                        if (headTransactions.getName().equals("Uncategorized")) {
                            UNCATEGORIZED_ITEM_ID = headTransactions.getId();
                            UiUtil.showLog("Uncategorized--", headTransactions.getId() + "");
                        }
                    }
                }
            }
        }
        if (this.receivedData != null) {
            updateCategories(this.receivedBankSubTransactions);
        }
    }

    private void selectCategoryDialog(TextView textView, EditText editText) {
        Dialog dialog2 = new Dialog(getContext());
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.dialogue_add_bill_item);
        this.dialog.setCancelable(true);
        this.dialog.setCanceledOnTouchOutside(true);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        RecyclerView listItemRecycler = this.dialog.findViewById(R.id.listItemRecycler);
        AddWithdrawalCategoryAdapter adapter = new AddWithdrawalCategoryAdapter(getContext(), this.list, textView, editText, this);
        listItemRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        listItemRecycler.setAdapter(adapter);
        this.dialog.findViewById(R.id.addNewItem).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                AddTransactionFragment.this.lambda$selectCategoryDialog$6$AddTransactionFragment(view);
            }
        });
        this.dialog.show();
    }

    public void lambda$selectCategoryDialog$6$AddTransactionFragment(View v) {
        startActivity(new Intent(getContext(), AddChatOfAccountActivity.class));
        this.dialog.dismiss();
    }

    private void getBanks() {
        RestClient.getInstance(getContext()).getBanks(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(getContext()), UiUtil.getComp_Id(getContext())).enqueue(new CustomCallBack<GetBankResponse>(getContext(), null) {
            public void onResponse(Call<GetBankResponse> call, Response<GetBankResponse> response) {
                super.onResponse(call, response);
                Log.d("Banks Response---", response.toString());
                if (response.isSuccessful()) {
                    AddTransactionFragment.this.banksItems = response.body().getBankResponse().getBanks();
                    AddTransactionFragment.this.setAccountSpinner(response.body().getBankResponse());
                }
            }

            public void onFailure(Call<GetBankResponse> call, Throwable t) {
                super.onFailure(call, t);
                Log.d("error", t.toString());
            }
        });
    }

    /* access modifiers changed from: private */
    public void setAccountSpinner(BankResponse bankResponse) {
        ArrayList<String> accounts = new ArrayList<>();
        if (bankResponse != null) {
            for (BanksItem banksItem : bankResponse.getBanks()) {
                accounts.add(banksItem.getInstitutionName());
            }
            this.accountSpinner.setItems(accounts);
            this.accountSpinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {

                public final void onItemSelected(int i, Object obj, int i2, Object obj2) {
                    AddTransactionFragment.this.lambda$setAccountSpinner$7$AddTransactionFragment(bankResponse, i, (String) obj, i2, (String) obj2);
                }
            });
        }
        if (this.receivedData != null) {
            this.accountSpinner.setText(getAccountNameById(this.selectedBankAccountId));
        }
    }

    public void lambda$setAccountSpinner$7$AddTransactionFragment(BankResponse bankResponse, int i, String s, int selectedIndex, String selectedItem) {
        this.selectedBankAccountId = bankResponse.getBanks().get(selectedIndex).getBankAccounts().getId();
    }

    private void addCategory() {
        View newCategoryView = getLayoutInflater().inflate(R.layout.item_add_transaction_category_layout, null, false);
        this.categoriesList.addView(newCategoryView);
        newCategoryView.findViewById(R.id.category).setOnClickListener(new View.OnClickListener() {

            public final void onClick(View view) {
                AddTransactionFragment.this.lambda$addCategory$8$AddTransactionFragment(newCategoryView, view);
            }
        });
        ImageView deleteCategory = newCategoryView.findViewById(R.id.deleteCategory);
        onCategoryClick(this.uncategorizedItem, newCategoryView.findViewById(R.id.category), newCategoryView.findViewById(R.id.cattotalAMout));
        if (this.categoriesList.getChildCount() > 1) {
            deleteCategory.setVisibility(View.VISIBLE);
        }
        deleteCategory.setOnClickListener(new View.OnClickListener() {

            public final void onClick(View view) {
                AddTransactionFragment.this.lambda$addCategory$9$AddTransactionFragment(newCategoryView, view);
            }
        });
    }

    public void lambda$addCategory$8$AddTransactionFragment(View newCategoryView, View v) {
        selectCategoryDialog(newCategoryView.findViewById(R.id.category), newCategoryView.findViewById(R.id.cattotalAMout));
    }

    public void lambda$addCategory$9$AddTransactionFragment(View newCategoryView, View v) {
        this.categoriesList.removeView(newCategoryView);
    }

    private void addTransaction() {
        int transactionType;
        int bankTransactionId;
        String companyId;
        this.categoriesAmountSum = Utils.DOUBLE_EPSILON;
        List<BankSubTransactionsItem> bankSubTransactionsItems = new ArrayList<>();
        for (int i = 0; i < this.categoriesList.getChildCount(); i++) {
            View view = this.categoriesList.getChildAt(i);
            int categoryId = getTransactionHeadIdForCategory((String) ((TextView) view.findViewById(R.id.category)).getText());
            String categoryAmount = ((EditText) view.findViewById(R.id.cattotalAMout)).getText().toString();
            if (categoryAmount.equals("")) {
                categoryAmount = "0";
            }
            String categoryAmount2 = categoryAmount;
            this.categoriesAmountSum += Double.parseDouble(categoryAmount2);
            if (this.receivedData != null) {
                companyId = this.receivedData.getCompanyId() + "";
                bankTransactionId = this.receivedData.getId();
            } else {
                companyId = "0";
                bankTransactionId = 0;
            }
            if (categoryId == UNCATEGORIZED_ITEM_ID) {
                BankSubTransactionsItem bankSubTransactionsItem = new BankSubTransactionsItem(0, companyId, categoryAmount2, categoryId, 0, 0);
                bankSubTransactionsItems.add(bankSubTransactionsItem);
            } else {
                bankSubTransactionsItems.add(new BankSubTransactionsItem(0, companyId, categoryAmount2, categoryId, 0, bankTransactionId));
            }
        }
        String totalAmount = this.totalAmountEditText.getText().toString();
        if (totalAmount.equals("")) {
            totalAmount = "0.00";
        }
        String desc = this.descEditText.getText().toString();
        if (desc.isEmpty()) {
            showErrorTv("Please enter description");
            return;
        }
        hideErrorTv();
        if (this.selectedBankAccountId == 0) {
            showErrorTv("Please select an account");
            return;
        }
        hideErrorTv();
        if (Double.parseDouble(totalAmount) == Utils.DOUBLE_EPSILON) {
            showErrorTv("Please enter amount");
            return;
        }
        hideErrorTv();
        double d = this.categoriesAmountSum;
        if (d == Utils.DOUBLE_EPSILON || d != Double.parseDouble(totalAmount)) {
            showErrorTv("Please make sure that amount is divided among categories");
            return;
        }
        hideErrorTv();
        if (this.depositOrWithdrawal.equalsIgnoreCase("deposit")) {
            transactionType = 1;
        } else {
            transactionType = 2;
        }
        submitTransaction(new AddTransactionRequest(transactionType, desc, String.valueOf(this.selectedBankAccountId), totalAmount, 0, this.receivedDataId, 2, this.notesEditText.getText().toString(), this.selectedFormattedDate, bankSubTransactionsItems));
    }

    private void showErrorTv(String message) {
        this.errorTv.setText(message);
        this.errorTv.setVisibility(View.VISIBLE);
        this.errorTv.requestFocus();
    }

    private void hideErrorTv() {
        this.errorTv.setVisibility(View.GONE);
    }

    private int getTransactionHeadIdForCategory(String text) {
        int transactionID = UNCATEGORIZED_ITEM_ID;
        for (AddWithdrawalCategorySpinnerItem localList : this.localCategoriesList) {
            if (localList.getTitle().equalsIgnoreCase(text)) {
                return localList.getId();
            }
        }
        return transactionID;
    }

    private void submitTransaction(AddTransactionRequest addTransactionRequest) {
        Log.d("addTransactionRequest", new Gson().toJson(addTransactionRequest));
        RestClient.getInstance(getContext()).addTransaction(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(getContext()), UiUtil.getComp_Id(getContext()), addTransactionRequest).enqueue(new CustomCallBack<CustomeResponse>(getContext(), "Adding Transaction...") {
            public void onResponse(Call<CustomeResponse> call, Response<CustomeResponse> response) {
                super.onResponse(call, response);
                try {
                    if (response.body().getTransactionStatus().isIsSuccess()) {
                        UiUtil.showToast(getContext(), "Added");
                        AddFragments.addFragmentToDrawerActivity(getContext(), null, TransactionsFragment.class);
                        return;
                    } else {
                        //UiUtil.showToast(getContext(), "Error while adding");
                        UiUtil.showToast(getContext(), response.body().getTransactionStatus().getError().getDescription());
                    }
                } catch (Exception e) {
                    UiUtil.showToast(getContext(), "Error while adding");
                }
            }

            public void onFailure(Call<CustomeResponse> call, Throwable t) {
                super.onFailure(call, t);
                UiUtil.showToast(getContext(), "Error while adding");
            }
        });
    }

    public void onCategoryClick(AddWithdrawalCategorySpinnerItem item, TextView textView, EditText editText) {
        this.localCategoriesList.add(item);
        textView.setText(item.getTitle());
        textView.setTextColor(getResources().getColor(R.color.blackTextColor));
        editText.setEnabled(true);
        editText.setAlpha(1.0f);
        Dialog dialog2 = this.dialog;
        if (dialog2 != null) {
            dialog2.dismiss();
        }
    }
}
