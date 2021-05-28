package com.akounto.accountingsoftware.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.response.PurchaseItem;

import java.util.ArrayList;
import java.util.List;

public class BillItem extends RecyclerView.Adapter<BillItem.ViewHolder> {

    private List<PurchaseItem> item_list = new ArrayList<>();
    private String cur;
    private BillItem.OnItemClickListener onItemClickListener;

    public BillItem(List<PurchaseItem> item_list, String cur, BillItem.OnItemClickListener onItemClickListener) {
        if (item_list != null) {
            this.item_list = item_list;
        } else {
            this.item_list = new ArrayList<>();
        }
        this.cur = cur;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public BillItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillItem.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BillItem.ViewHolder holder, int position) {
        try {

            if (item_list.get(position).getQuantity() == 1)
                holder.item_name.setText(item_list.get(position).getName().trim());
            else
                holder.item_name.setText(item_list.get(position).getName().trim() + " +" + item_list.get(position).getQuantity());

            holder.item_price.setText(cur + " " + String.valueOf(item_list.get(position).getPrice()));
            holder.product_decription.setText(item_list.get(position).getDescription().trim());
            if (item_list.get(position).getProductServiceTaxes().size() != 1)
                holder.taxtes.setText(item_list.get(position).getProductServiceTaxes().get(0).getTaxName().trim() + "+" + item_list.get(position).getProductServiceTaxes().size());
            else
                holder.taxtes.setText(item_list.get(position).getProductServiceTaxes().get(0).getTaxName().trim());
        } catch (Exception e) {
        }
        holder.item_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onItemClickListener.onItemClick(item_list.get(position), position);
                } catch (Exception e) {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(PurchaseItem item, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name, item_price, product_decription, taxtes;
        public LinearLayout item_main;

        public ViewHolder(View itemView) {
            super(itemView);
            this.item_name = itemView.findViewById(R.id.item_name);
            this.item_price = itemView.findViewById(R.id.item_price);
            this.product_decription = itemView.findViewById(R.id.product_decription);
            this.taxtes = itemView.findViewById(R.id.taxtes);
            this.item_main = itemView.findViewById(R.id.item_main);
        }
    }
}
