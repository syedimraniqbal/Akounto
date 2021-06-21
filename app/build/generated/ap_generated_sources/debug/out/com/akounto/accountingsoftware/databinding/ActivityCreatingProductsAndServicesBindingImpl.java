package com.akounto.accountingsoftware.databinding;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityCreatingProductsAndServicesBindingImpl extends ActivityCreatingProductsAndServicesBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.backButton, 2);
        sViewsWithIds.put(R.id.pageTitle, 3);
        sViewsWithIds.put(R.id.rightTextView, 4);
        sViewsWithIds.put(R.id.parent, 5);
        sViewsWithIds.put(R.id.relativeLayoutName, 6);
        sViewsWithIds.put(R.id.textViewName, 7);
        sViewsWithIds.put(R.id.et_name, 8);
        sViewsWithIds.put(R.id.relativeLayoutDescription, 9);
        sViewsWithIds.put(R.id.textViewDescription, 10);
        sViewsWithIds.put(R.id.et_desc, 11);
        sViewsWithIds.put(R.id.relativeLayoutPrice, 12);
        sViewsWithIds.put(R.id.textViewPrice, 13);
        sViewsWithIds.put(R.id.et_price, 14);
        sViewsWithIds.put(R.id.relativeLayoutIncomeAccount, 15);
        sViewsWithIds.put(R.id.textViewIncomeAccount, 16);
        sViewsWithIds.put(R.id.expenseRL, 17);
        sViewsWithIds.put(R.id.incomeAccountSpinner, 18);
        sViewsWithIds.put(R.id.selectExpenbseError, 19);
        sViewsWithIds.put(R.id.relativeLayoutSalesTax, 20);
        sViewsWithIds.put(R.id.textViewSalesTax, 21);
        sViewsWithIds.put(R.id.salesTaxSpinner, 22);
        sViewsWithIds.put(R.id.taxRecycler, 23);
        sViewsWithIds.put(R.id.addSalesTax, 24);
        sViewsWithIds.put(R.id.saveProductButton, 25);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityCreatingProductsAndServicesBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private ActivityCreatingProductsAndServicesBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.ImageView) bindings[24]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.EditText) bindings[11]
            , (android.widget.EditText) bindings[8]
            , (android.widget.EditText) bindings[14]
            , (android.widget.RelativeLayout) bindings[17]
            , (com.skydoves.powerspinner.PowerSpinnerView) bindings[18]
            , (android.widget.TextView) bindings[3]
            , (android.widget.ScrollView) bindings[5]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.TextView) bindings[4]
            , (com.skydoves.powerspinner.PowerSpinnerView) bindings[22]
            , (android.widget.TextView) bindings[25]
            , (android.widget.TextView) bindings[19]
            , (androidx.recyclerview.widget.RecyclerView) bindings[23]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[16]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[13]
            , (android.widget.TextView) bindings[21]
            , (android.widget.RelativeLayout) bindings[1]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}