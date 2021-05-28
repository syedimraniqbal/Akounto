package com.akounto.accountingsoftware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akounto.accountingsoftware.Data.RegisterBank.Bank;
import com.akounto.accountingsoftware.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.ViewHolder> {

    private List<Bank> item_list;
    private OnItemClickListener onItemClickListener;

    // RecyclerView recyclerView;
    public BankListAdapter(List<Bank> item_list, OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        this.item_list = item_list;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banklist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Bank myListData = item_list.get(position);
        holder.tv_bank_name.setText(myListData.getInstitutionName());
        holder.tv_bank_Account_name.setText(myListData.getBankAccounts().getName());
        holder.tv_last_update.setText("Last updated "+myListData.getBankAccounts().getLastTransactionFetchedOn().split("T")[0]);
        if (myListData.getBankAccounts().getCurrency() == null) {
            holder.tv_currency.setText(myListData.getBankAccounts().getAvailableBalance()+" USD");
        } else {
            holder.tv_currency.setText(myListData.getBankAccounts().getAvailableBalance()+" "+myListData.getBankAccounts().getCurrency());
        }
        if(myListData.getBankAccounts().getIsAutoImport()){
            holder.auto_sync.setImageResource(R.drawable.on_toggle);
        }else{
            holder.auto_sync.setImageResource(R.drawable.off_toggle);
        }
        holder.auto_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(myListData, position, !myListData.getBankAccounts().getIsAutoImport());
            }
        });
        holder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemDownload(myListData, position, myListData.getBankAccounts().getIsAutoImport());
            }
        });
       /* holder.auto_sync.setChecked(myListData.getBankAccounts().getIsAutoImport());
        holder.auto_sync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            onItemClickListener.onItemClick(myListData, position, isChecked);
        });*/
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Bank bank, int position, boolean state);
        void onItemDownload(Bank bank, int position, boolean state);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_bank_name, tv_bank_Account_name, tv_last_update, tv_currency;
        public ImageView auto_sync,download;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tv_bank_name = itemView.findViewById(R.id.bank_name);
            this.tv_bank_Account_name = itemView.findViewById(R.id.bank_account_name);
            this.tv_last_update = itemView.findViewById(R.id.last_update);
            this.tv_currency = itemView.findViewById(R.id.currency_sign);
            this.auto_sync = itemView.findViewById(R.id.auto_sync);
            this.download = itemView.findViewById(R.id.download);
        }
    }
}