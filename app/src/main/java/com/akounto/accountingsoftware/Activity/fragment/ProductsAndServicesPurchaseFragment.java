package com.akounto.accountingsoftware.Activity.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Activity.Bill.BillMenu;
import com.akounto.accountingsoftware.Activity.CreateProductsAndServicesPurchase;
import com.akounto.accountingsoftware.adapter.ProductAndServicesAdapter;
import com.akounto.accountingsoftware.adapter.ProductAndServicesPurchseAdapter;
import com.akounto.accountingsoftware.network.CustomCallBack;
import com.akounto.accountingsoftware.network.RestClient;
import com.akounto.accountingsoftware.response.Product;
import com.akounto.accountingsoftware.response.PurchaseItem;
import com.akounto.accountingsoftware.response.PurchaseItemResponse;
import com.akounto.accountingsoftware.response.SalesProductResponse;
import com.google.gson.Gson;
import com.akounto.accountingsoftware.util.AddFragments;
import com.akounto.accountingsoftware.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ProductsAndServicesPurchaseFragment extends Fragment implements View.OnClickListener, UpdateProduct, UpdatePurchaseProduct {
    ProductAndServicesPurchseAdapter adapter;
    TextView goButton;
    ConstraintLayout noDataLayout;
    /* access modifiers changed from: private */
    public List<Product> productList = new ArrayList();
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.products_and_services_fragment, container, false);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragments.addFragmentToDrawerActivity(getActivity(), null, BillMenu.class);
            }
        });
        view.findViewById(R.id.btn_create_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), CreateProductsAndServicesPurchase.class);
                    intent.putExtra("IS_PURCHASECALL", true);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Error :: ",e.getMessage());
                }
            }
        });
        inItUi();
        return this.view;
    }

    private void inItUi() {
        this.noDataLayout = this.view.findViewById(R.id.noDataLayout);
        TextView textView = this.view.findViewById(R.id.goButton);
        this.goButton = textView;
        textView.setOnClickListener(this);
    }

    private void getSalesProductList() {
        RestClient.getInstance(getContext()).getSalesProductList(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(getContext()), UiUtil.getComp_Id(getContext()), "").enqueue(new CustomCallBack<SalesProductResponse>(getContext(), null) {
            public void onResponse(Call<SalesProductResponse> call, Response<SalesProductResponse> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    List unused = ProductsAndServicesPurchaseFragment.this.productList = response.body().getData();
                    ProductsAndServicesPurchaseFragment productsAndServicesPurchaseFragment = ProductsAndServicesPurchaseFragment.this;
                    productsAndServicesPurchaseFragment.populateData(productsAndServicesPurchaseFragment.productList);
                }
            }

            public void onFailure(Call<SalesProductResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /* access modifiers changed from: private */
    public void populateData(List<Product> productList2) {
        RecyclerView product_rv = this.view.findViewById(R.id.prodAndServicesRecyclerView);
        ProductAndServicesAdapter productAndServicesAdapter = new ProductAndServicesAdapter(getContext(), productList2, this);
        product_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        product_rv.setAdapter(productAndServicesAdapter);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.goButton) {
            Intent intent = new Intent(getActivity(), CreateProductsAndServicesPurchase.class);
            intent.putExtra("IS_PURCHASECALL", true);
            startActivity(intent);
            // CreateProductsAndServicesPurchase.buildIntentNo(getContext(),"PURCHASE");
        }
    }

    public void onResume() {
        super.onResume();
        getProductDataList();
    }

    public void editProduct(Product product) {
        startActivity(CreateProductsAndServicesPurchase.buildIntent(getContext(), product));
    }

    private void getProductDataList() {
        RestClient.getInstance(getActivity()).getPurchaseItem(Constant.X_SIGNATURE, "Bearer " + UiUtil.getAcccessToken(getContext()), UiUtil.getComp_Id(getContext())).enqueue(new CustomCallBack<PurchaseItemResponse>(getActivity(), null) {
            public void onResponse(Call<PurchaseItemResponse> call, Response<PurchaseItemResponse> response) {
                super.onResponse(call, response);
                Log.d("getProductDataList---", new Gson().toJson(response.body().getData()));
                if (response.isSuccessful()) {
                    ProductsAndServicesPurchaseFragment.this.updateProductAndServicesPurchseAdapter(response.body().getData());
                }
            }

            public void onFailure(Call<PurchaseItemResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /* access modifiers changed from: private */
    public void updateProductAndServicesPurchseAdapter(List<PurchaseItem> data) {
        if (data == null) {
            this.noDataLayout.setVisibility(View.VISIBLE);
        } else {
            this.noDataLayout.setVisibility(View.GONE);
        }
        this.adapter = new ProductAndServicesPurchseAdapter(getActivity(), data, this);
        RecyclerView product_rv = this.view.findViewById(R.id.prodAndServicesRecyclerView);
        product_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        product_rv.setAdapter(this.adapter);
    }

    public void editProduct(PurchaseItem product, int type) {
        Intent intent = new Intent(getActivity(), CreateProductsAndServicesPurchase.class);
        intent.putExtra("DATA", new Gson().toJson(product));
        startActivity(intent);
    }
}
