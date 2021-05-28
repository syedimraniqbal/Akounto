package com.akounto.accountingsoftware.databinding;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutBviewBillBindingImpl extends LayoutBviewBillBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_close, 1);
        sViewsWithIds.put(R.id.bill_details, 2);
        sViewsWithIds.put(R.id.billNumber, 3);
        sViewsWithIds.put(R.id.bill_inner, 4);
        sViewsWithIds.put(R.id.no_bill_title, 5);
        sViewsWithIds.put(R.id.no_bill, 6);
        sViewsWithIds.put(R.id.due_amount_title, 7);
        sViewsWithIds.put(R.id.due_amount, 8);
        sViewsWithIds.put(R.id.dueOnPTV_title, 9);
        sViewsWithIds.put(R.id.dueOnPTV, 10);
        sViewsWithIds.put(R.id.customer_details, 11);
        sViewsWithIds.put(R.id.bill_details_exr, 12);
        sViewsWithIds.put(R.id.customer_company, 13);
        sViewsWithIds.put(R.id.cusmoter_name, 14);
        sViewsWithIds.put(R.id.customer_email, 15);
        sViewsWithIds.put(R.id.rc_item, 16);
        sViewsWithIds.put(R.id.subtotal, 17);
        sViewsWithIds.put(R.id.taxs_name_list, 18);
        sViewsWithIds.put(R.id.taxs_amount_list, 19);
        sViewsWithIds.put(R.id.tax_total, 20);
        sViewsWithIds.put(R.id.currency, 21);
        sViewsWithIds.put(R.id.grant_total, 22);
        sViewsWithIds.put(R.id.cacncelbill, 23);
        sViewsWithIds.put(R.id.convertTobill, 24);
        sViewsWithIds.put(R.id.sendEstimate, 25);
        sViewsWithIds.put(R.id.editDraft, 26);
        sViewsWithIds.put(R.id.approveTv, 27);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutBviewBillBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 28, sIncludes, sViewsWithIds));
    }
    private LayoutBviewBillBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[27]
            , (android.widget.LinearLayout) bindings[2]
            , (android.view.View) bindings[12]
            , (android.widget.LinearLayout) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.Button) bindings[23]
            , (android.widget.Button) bindings[24]
            , (android.widget.TextView) bindings[21]
            , (android.widget.TextView) bindings[14]
            , (android.widget.TextView) bindings[13]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.TextView) bindings[15]
            , (android.widget.TextView) bindings[8]
            , (android.widget.TextView) bindings[7]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[9]
            , (android.widget.Button) bindings[26]
            , (android.widget.TextView) bindings[22]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.TextView) bindings[6]
            , (android.widget.TextView) bindings[5]
            , (androidx.recyclerview.widget.RecyclerView) bindings[16]
            , (android.widget.Button) bindings[25]
            , (android.widget.TextView) bindings[17]
            , (android.widget.TextView) bindings[20]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.LinearLayout) bindings[18]
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