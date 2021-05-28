package com.akounto.accountingsoftware.databinding;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityAddtransectionBindingImpl extends ActivityAddtransectionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.toolbar, 1);
        sViewsWithIds.put(R.id.relativeLayoutName, 2);
        sViewsWithIds.put(R.id.textViewName, 3);
        sViewsWithIds.put(R.id.et_desc, 4);
        sViewsWithIds.put(R.id.accountSpinner, 5);
        sViewsWithIds.put(R.id.date, 6);
        sViewsWithIds.put(R.id.depositOrWithdrawalSpinner, 7);
        sViewsWithIds.put(R.id.totalAmount, 8);
        sViewsWithIds.put(R.id.categoriesList, 9);
        sViewsWithIds.put(R.id.addCategory, 10);
        sViewsWithIds.put(R.id.errorNote, 11);
        sViewsWithIds.put(R.id.notes, 12);
        sViewsWithIds.put(R.id.applyButton, 13);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityAddtransectionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds));
    }
    private ActivityAddtransectionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (com.skydoves.powerspinner.PowerSpinnerView) bindings[5]
            , (android.widget.TextView) bindings[10]
            , (android.widget.TextView) bindings[13]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.TextView) bindings[6]
            , (com.skydoves.powerspinner.PowerSpinnerView) bindings[7]
            , (android.widget.TextView) bindings[11]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[12]
            , (android.widget.RelativeLayout) bindings[2]
            , (android.widget.TextView) bindings[3]
            , (android.view.View) bindings[1]
            , (android.widget.EditText) bindings[8]
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