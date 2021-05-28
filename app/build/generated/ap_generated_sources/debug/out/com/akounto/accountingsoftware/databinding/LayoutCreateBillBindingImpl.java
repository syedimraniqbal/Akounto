package com.akounto.accountingsoftware.databinding;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutCreateBillBindingImpl extends LayoutCreateBillBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_close, 1);
        sViewsWithIds.put(R.id.title, 2);
        sViewsWithIds.put(R.id.iv_save, 3);
        sViewsWithIds.put(R.id.bill_no, 4);
        sViewsWithIds.put(R.id.po_soET, 5);
        sViewsWithIds.put(R.id.invoice_date, 6);
        sViewsWithIds.put(R.id.iv_invoice_date, 7);
        sViewsWithIds.put(R.id.tv_due_date, 8);
        sViewsWithIds.put(R.id.due_date, 9);
        sViewsWithIds.put(R.id.add_vendor, 10);
        sViewsWithIds.put(R.id.id_vendor, 11);
        sViewsWithIds.put(R.id.tv_name_char, 12);
        sViewsWithIds.put(R.id.comp_name, 13);
        sViewsWithIds.put(R.id.tv_name, 14);
        sViewsWithIds.put(R.id.btn_vendor, 15);
        sViewsWithIds.put(R.id.imageView4, 16);
        sViewsWithIds.put(R.id.rc_customer, 17);
        sViewsWithIds.put(R.id.add_items, 18);
        sViewsWithIds.put(R.id.subtotal, 19);
        sViewsWithIds.put(R.id.taxs_name_list, 20);
        sViewsWithIds.put(R.id.taxs_amount_list, 21);
        sViewsWithIds.put(R.id.tax_total, 22);
        sViewsWithIds.put(R.id.currencySpinner, 23);
        sViewsWithIds.put(R.id.grant_total, 24);
        sViewsWithIds.put(R.id.tv_note, 25);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutCreateBillBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private LayoutCreateBillBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.EditText) bindings[4]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.TextView) bindings[13]
            , (android.widget.Spinner) bindings[23]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.TextView) bindings[24]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.ImageView) bindings[16]
            , (android.widget.TextView) bindings[6]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.ImageView) bindings[7]
            , (android.widget.ImageView) bindings[3]
            , (android.widget.EditText) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[17]
            , (android.widget.TextView) bindings[19]
            , (android.widget.TextView) bindings[22]
            , (android.widget.LinearLayout) bindings[21]
            , (android.widget.LinearLayout) bindings[20]
            , (android.widget.TextView) bindings[2]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[12]
            , (android.widget.EditText) bindings[25]
            );
        this.mboundView0 = (android.widget.LinearLayout) bindings[0];
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