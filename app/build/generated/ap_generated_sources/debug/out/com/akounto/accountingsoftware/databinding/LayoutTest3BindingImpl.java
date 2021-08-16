package com.akounto.accountingsoftware.databinding;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutTest3BindingImpl extends LayoutTest3Binding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.linearLayout3, 1);
        sViewsWithIds.put(R.id.iv_close, 2);
        sViewsWithIds.put(R.id.invoice_details, 3);
        sViewsWithIds.put(R.id.billNumber, 4);
        sViewsWithIds.put(R.id.invoice_inner, 5);
        sViewsWithIds.put(R.id.no_invoice, 6);
        sViewsWithIds.put(R.id.due_amount, 7);
        sViewsWithIds.put(R.id.dueOnPTV, 8);
        sViewsWithIds.put(R.id.customer_details, 9);
        sViewsWithIds.put(R.id.customer_company, 10);
        sViewsWithIds.put(R.id.cusmoter_name, 11);
        sViewsWithIds.put(R.id.customer_email, 12);
        sViewsWithIds.put(R.id.rc_item, 13);
        sViewsWithIds.put(R.id.subtotal, 14);
        sViewsWithIds.put(R.id.discount_ll_view, 15);
        sViewsWithIds.put(R.id.tv_dicount, 16);
        sViewsWithIds.put(R.id.taxs_name_list, 17);
        sViewsWithIds.put(R.id.taxs_amount_list, 18);
        sViewsWithIds.put(R.id.tax_total, 19);
        sViewsWithIds.put(R.id.currency, 20);
        sViewsWithIds.put(R.id.grant_total, 21);
        sViewsWithIds.put(R.id.cacncelInvoice, 22);
        sViewsWithIds.put(R.id.convertToInvoice, 23);
        sViewsWithIds.put(R.id.sendEstimate, 24);
        sViewsWithIds.put(R.id.editDraft, 25);
        sViewsWithIds.put(R.id.approveTv, 26);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutTest3BindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private LayoutTest3BindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[26]
            , (android.widget.TextView) bindings[4]
            , (android.widget.Button) bindings[22]
            , (android.widget.Button) bindings[23]
            , (android.widget.TextView) bindings[20]
            , (android.widget.TextView) bindings[11]
            , (android.widget.TextView) bindings[10]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.TextView) bindings[12]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[8]
            , (android.widget.Button) bindings[25]
            , (android.widget.TextView) bindings[21]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[5]
            , (android.widget.LinearLayout) bindings[2]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.TextView) bindings[6]
            , (androidx.recyclerview.widget.RecyclerView) bindings[13]
            , (android.widget.Button) bindings[24]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[19]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[17]
            , (android.widget.TextView) bindings[16]
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