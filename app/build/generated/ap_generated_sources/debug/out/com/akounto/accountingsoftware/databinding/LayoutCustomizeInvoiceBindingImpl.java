package com.akounto.accountingsoftware.databinding;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class LayoutCustomizeInvoiceBindingImpl extends LayoutCustomizeInvoiceBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.iv_close, 1);
        sViewsWithIds.put(R.id.ivSave, 2);
        sViewsWithIds.put(R.id.btn_style, 3);
        sViewsWithIds.put(R.id.btn_information, 4);
        sViewsWithIds.put(R.id.btn_columns, 5);
        sViewsWithIds.put(R.id.layout_style, 6);
        sViewsWithIds.put(R.id.preview, 7);
        sViewsWithIds.put(R.id.layout_one, 8);
        sViewsWithIds.put(R.id.iv_one, 9);
        sViewsWithIds.put(R.id.layout_two, 10);
        sViewsWithIds.put(R.id.iv_two, 11);
        sViewsWithIds.put(R.id.layout_three, 12);
        sViewsWithIds.put(R.id.iv_three, 13);
        sViewsWithIds.put(R.id.layout_blue, 14);
        sViewsWithIds.put(R.id.layout_green, 15);
        sViewsWithIds.put(R.id.layout_purple, 16);
        sViewsWithIds.put(R.id.layout_red, 17);
        sViewsWithIds.put(R.id.layout_yellow, 18);
        sViewsWithIds.put(R.id.logo_upload, 19);
        sViewsWithIds.put(R.id.iv_logo, 20);
        sViewsWithIds.put(R.id.cbLogo, 21);
        sViewsWithIds.put(R.id.layout_info, 22);
        sViewsWithIds.put(R.id.due_days, 23);
        sViewsWithIds.put(R.id.et_invoice_title, 24);
        sViewsWithIds.put(R.id.et_invoice_subheading, 25);
        sViewsWithIds.put(R.id.et_invoice_no, 26);
        sViewsWithIds.put(R.id.et_invoice_prefix, 27);
        sViewsWithIds.put(R.id.et_invoice_suffix, 28);
        sViewsWithIds.put(R.id.layout_coloum, 29);
        sViewsWithIds.put(R.id.radioGroupItemtype, 30);
        sViewsWithIds.put(R.id.radioItems, 31);
        sViewsWithIds.put(R.id.radioProducts, 32);
        sViewsWithIds.put(R.id.radioServices, 33);
        sViewsWithIds.put(R.id.radioItemtypeCustom, 34);
        sViewsWithIds.put(R.id.et_item_type, 35);
        sViewsWithIds.put(R.id.radioGroupUnittype, 36);
        sViewsWithIds.put(R.id.radioQuantity, 37);
        sViewsWithIds.put(R.id.radioHours, 38);
        sViewsWithIds.put(R.id.radioUnitCustome, 39);
        sViewsWithIds.put(R.id.et_unit_type, 40);
        sViewsWithIds.put(R.id.radioGroupPricetype, 41);
        sViewsWithIds.put(R.id.radioPrice, 42);
        sViewsWithIds.put(R.id.radioRate, 43);
        sViewsWithIds.put(R.id.radioPricetypeCustome, 44);
        sViewsWithIds.put(R.id.et_pricetype, 45);
    }
    // views
    @NonNull
    private final android.widget.LinearLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public LayoutCustomizeInvoiceBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 46, sIncludes, sViewsWithIds));
    }
    private LayoutCustomizeInvoiceBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.TextView) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[3]
            , (android.widget.CheckBox) bindings[21]
            , (android.widget.Spinner) bindings[23]
            , (android.widget.EditText) bindings[26]
            , (android.widget.EditText) bindings[27]
            , (android.widget.EditText) bindings[25]
            , (android.widget.EditText) bindings[28]
            , (android.widget.EditText) bindings[24]
            , (android.widget.EditText) bindings[35]
            , (android.widget.EditText) bindings[45]
            , (android.widget.EditText) bindings[40]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.ImageView) bindings[20]
            , (android.widget.ImageView) bindings[9]
            , (android.widget.ImageView) bindings[2]
            , (android.widget.ImageView) bindings[13]
            , (android.widget.ImageView) bindings[11]
            , (android.widget.LinearLayout) bindings[14]
            , (android.widget.LinearLayout) bindings[29]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[22]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[17]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[10]
            , (android.widget.LinearLayout) bindings[18]
            , (android.widget.LinearLayout) bindings[19]
            , (android.widget.TextView) bindings[7]
            , (android.widget.RadioGroup) bindings[30]
            , (android.widget.RadioGroup) bindings[41]
            , (android.widget.RadioGroup) bindings[36]
            , (android.widget.RadioButton) bindings[38]
            , (android.widget.RadioButton) bindings[31]
            , (android.widget.RadioButton) bindings[34]
            , (android.widget.RadioButton) bindings[42]
            , (android.widget.RadioButton) bindings[44]
            , (android.widget.RadioButton) bindings[32]
            , (android.widget.RadioButton) bindings[37]
            , (android.widget.RadioButton) bindings[43]
            , (android.widget.RadioButton) bindings[33]
            , (android.widget.RadioButton) bindings[39]
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