package com.akounto.accountingsoftware.Activity.Bank;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akounto.accountingsoftware.Activity.SplashScreenActivity;
import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.Data.RegisterBank.Bank;
import com.akounto.accountingsoftware.Data.RegisterBank.BankAccountData;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.ViewModel.BankListViewModel;
import com.akounto.accountingsoftware.adapter.BankListAdapter;
import com.akounto.accountingsoftware.util.UiUtil;
import com.plaid.link.Plaid;
import com.plaid.link.configuration.LinkTokenConfiguration;
import com.plaid.link.result.LinkError;
import com.plaid.link.result.LinkResultHandler;
import com.plaid.link.result.LinkSuccessMetadata;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;

public class BankListActivity extends AppCompatActivity implements BankListAdapter.OnItemClickListener {

    private BankListViewModel model;
    private ArrayList<Bank> arr = new ArrayList<>();
    private RecyclerView recyclerView;
    private Context mContext;
    private ImageView btn_coonect_bank, bck;
    private Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);

        btn_coonect_bank = findViewById(R.id.btn_connect_bank);
        bck = findViewById(R.id.iv_back);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        model = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(BankListViewModel.class);
        recyclerView = findViewById(R.id.tv_bank_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mContext = this;
        LifecycleOwner owner = this;
        btn_coonect_bank.setOnClickListener(v -> {
            model.linkBank(mContext, UiUtil.getComp_Id(mContext), UiUtil.getAcccessToken(mContext)).observe(owner, bankLinkData -> {
                if (bankLinkData.getStatus() == 0) {
                    //Toast.makeText(getApplicationContext(), bankLinkData.getBankData().getLinkToken(), Toast.LENGTH_SHORT).show();
                    onLinkTokenSuccess(bankLinkData.getBankData().getLinkToken());
                } else {
                    Toast.makeText(mContext, bankLinkData.getStatusMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        model.loadBank(mContext, UiUtil.getComp_Id(mContext), UiUtil.getAcccessToken(mContext)).observe(this, bankAccountData -> {
            if (bankAccountData.getStatus() == 0) {
                if (bankAccountData.getData().getBanks().size() != 0) {
                    setAdapter(bankAccountData.getData().getBanks());
                } else {
                    Toast.makeText(getApplicationContext(), "Not Data for display", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), bankAccountData.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(Bank bank, int position, boolean state) {
     /*   if (state) {
            showDialog(mContext, bank, state);
        } else {*/
        model.autoSync(mContext, UiUtil.getComp_Id(mContext), UiUtil.getAcccessToken(mContext), bank.getId().toString(), bank.getBankAccounts().getId().toString(), state).observe(this, bankAccountData -> {
            if (bankAccountData.getStatus() == 0) {
                setAdapter(bankAccountData.getBanks());
            } else {
                Toast.makeText(getApplicationContext(), bankAccountData.getStatusMessage(), Toast.LENGTH_LONG).show();
            }
        });
        /* }*/
    }

    @Override
    public void onItemDownload(Bank bank, int position, boolean state) {
        showDialog(mContext, bank);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!myPlaidResultHandler.onActivityResult(requestCode, resultCode, data)) {
            Log.i(BankListActivity.class.getSimpleName(), "Not handled");
        }
    }

    private void onLinkTokenSuccess(String token) {
        Plaid.create(
                getApplication(),
                new LinkTokenConfiguration.Builder()
                        .token(token)
                        .build())
                .open(this);
    }

    private final LinkResultHandler myPlaidResultHandler = new LinkResultHandler(
            linkSuccess -> {
                LinkSuccessMetadata metadata = linkSuccess.getMetadata();
                String publicToken = linkSuccess.getPublicToken();
                String institutionId = metadata.getInstitution().getId();
                String institutionName = metadata.getInstitution().getName();
                model.registerBank(mContext, publicToken, institutionId, institutionName).observe(this, new Observer<BankAccountData>() {
                    @Override
                    public void onChanged(BankAccountData bankAccountData) {
                        if (bankAccountData.getStatus() == 0) {
                            if (bankAccountData.getData().getBanks().size() != 0) {
                                setAdapter(bankAccountData.getData().getBanks());
                                Bundle b=new Bundle();
                                b.putString(Constant.CATEGORY,"banking");
                                b.putString(Constant.ACTION,"connect_bank");
                                SplashScreenActivity.mFirebaseAnalytics.logEvent("account_banking",b);
                            } else {
                                Toast.makeText(getApplicationContext(), "Not data for display", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            //Toast.makeText(getApplicationContext(), bankAccountData.getStatusMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
                return Unit.INSTANCE;
            },
            linkExit -> {
                LinkError error = linkExit.getError();
                try {
                    String displayMessage = error.getDisplayMessage();
                    Toast.makeText(this, displayMessage, Toast.LENGTH_SHORT).show();
                    if (linkExit.getError() != null) {
                        /*Toast.makeText(this, getString(
                                R.string.content_exit,
                                linkExit.getError().getDisplayMessage(),
                                linkExit.getError().getErrorCode()), Toast.LENGTH_SHORT).show();*/
                    } else {
                       /* Toast.makeText(this, getString(
                                R.string.content_cancel,
                                linkExit.getMetadata().getStatus() != null ? linkExit.getMetadata()
                                        .getStatus()
                                        .getJsonValue() : "unknown"), Toast.LENGTH_SHORT).show();*/
                    }
                } catch (Exception e) {
                    setAdapter();
                }
                return Unit.INSTANCE;
            }
    );

    public void setAdapter(List<Bank> banks) {
        arr.clear();
        arr.addAll(banks);
        recyclerView.removeAllViews();
        recyclerView.setAdapter(new BankListAdapter(arr, BankListActivity.this));
        recyclerView.invalidate();
    }

    public void setAdapter() {
        recyclerView.removeAllViews();
        recyclerView.setAdapter(new BankListAdapter(arr, BankListActivity.this));
        recyclerView.invalidate();
    }

    public void showDialog(Context activity, Bank bank) {

        dialog = new Dialog(activity);
        if (!dialog.isShowing()) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dilog_bank_import);
            Button btn_import = dialog.findViewById(R.id.btn_import);
            Button btn_cancle = dialog.findViewById(R.id.btn_cancel);
            btn_import.setOnClickListener(v -> {
                dialog.dismiss();
                model.import_trans(mContext, UiUtil.getComp_Id(mContext), UiUtil.getAcccessToken(mContext), bank.getId().toString()).observe(this, bankAccountData -> {
                    if (bankAccountData.getStatus() == 0) {
                        setAdapter(bankAccountData.getData().getBanks());
                    } else {
                        Toast.makeText(getApplicationContext(), bankAccountData.getStatusMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            });
            btn_cancle.setOnClickListener(v -> {
                dialog.dismiss();
                setAdapter();
            });
            dialog.show();
        }
    }

}
