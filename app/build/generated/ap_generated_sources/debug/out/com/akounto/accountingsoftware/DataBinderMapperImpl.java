package com.akounto.accountingsoftware;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.akounto.accountingsoftware.databinding.ActivityAddtransectionBindingImpl;
import com.akounto.accountingsoftware.databinding.ActivityBankListBindingImpl;
import com.akounto.accountingsoftware.databinding.ActivityCreatingProductsAndServicesBindingImpl;
import com.akounto.accountingsoftware.databinding.FragmentWebViewBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutAccountingFragmentBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutBussniessListBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutBviewBillBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutCreateBillBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutCustomizeInvoiceBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutCustomizeInvoiceStyleBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutMenuFragmentBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutMoreBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutPurchaseFragmentBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutSalesFargmentBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutSettingAccountingBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutSettingFragmentBindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutTest2BindingImpl;
import com.akounto.accountingsoftware.databinding.LayoutTest3BindingImpl;
import com.akounto.accountingsoftware.databinding.MobileCodeDilogBindingImpl;
import com.akounto.accountingsoftware.databinding.MobileCodeItemBindingImpl;
import com.akounto.accountingsoftware.databinding.SendEstimateHomeFragmentBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYADDTRANSECTION = 1;

  private static final int LAYOUT_ACTIVITYBANKLIST = 2;

  private static final int LAYOUT_ACTIVITYCREATINGPRODUCTSANDSERVICES = 3;

  private static final int LAYOUT_FRAGMENTWEBVIEW = 4;

  private static final int LAYOUT_LAYOUTACCOUNTINGFRAGMENT = 5;

  private static final int LAYOUT_LAYOUTBUSSNIESSLIST = 6;

  private static final int LAYOUT_LAYOUTBVIEWBILL = 7;

  private static final int LAYOUT_LAYOUTCREATEBILL = 8;

  private static final int LAYOUT_LAYOUTCUSTOMIZEINVOICE = 9;

  private static final int LAYOUT_LAYOUTCUSTOMIZEINVOICESTYLE = 10;

  private static final int LAYOUT_LAYOUTMENUFRAGMENT = 11;

  private static final int LAYOUT_LAYOUTMORE = 12;

  private static final int LAYOUT_LAYOUTPURCHASEFRAGMENT = 13;

  private static final int LAYOUT_LAYOUTSALESFARGMENT = 14;

  private static final int LAYOUT_LAYOUTSETTINGACCOUNTING = 15;

  private static final int LAYOUT_LAYOUTSETTINGFRAGMENT = 16;

  private static final int LAYOUT_LAYOUTTEST2 = 17;

  private static final int LAYOUT_LAYOUTTEST3 = 18;

  private static final int LAYOUT_MOBILECODEDILOG = 19;

  private static final int LAYOUT_MOBILECODEITEM = 20;

  private static final int LAYOUT_SENDESTIMATEHOMEFRAGMENT = 21;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(21);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.activity_addtransection, LAYOUT_ACTIVITYADDTRANSECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.activity_bank_list, LAYOUT_ACTIVITYBANKLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.activity_creating_products_and_services, LAYOUT_ACTIVITYCREATINGPRODUCTSANDSERVICES);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.fragment_web_view, LAYOUT_FRAGMENTWEBVIEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_accounting_fragment, LAYOUT_LAYOUTACCOUNTINGFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_bussniess_list, LAYOUT_LAYOUTBUSSNIESSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_bview_bill, LAYOUT_LAYOUTBVIEWBILL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_create_bill, LAYOUT_LAYOUTCREATEBILL);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_customize_invoice, LAYOUT_LAYOUTCUSTOMIZEINVOICE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_customize_invoice_style, LAYOUT_LAYOUTCUSTOMIZEINVOICESTYLE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_menu_fragment, LAYOUT_LAYOUTMENUFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_more, LAYOUT_LAYOUTMORE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_purchase_fragment, LAYOUT_LAYOUTPURCHASEFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_sales_fargment, LAYOUT_LAYOUTSALESFARGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_setting_accounting, LAYOUT_LAYOUTSETTINGACCOUNTING);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_setting_fragment, LAYOUT_LAYOUTSETTINGFRAGMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_test2, LAYOUT_LAYOUTTEST2);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.layout_test3, LAYOUT_LAYOUTTEST3);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.mobile_code_dilog, LAYOUT_MOBILECODEDILOG);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.mobile_code_item, LAYOUT_MOBILECODEITEM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.akounto.accountingsoftware.R.layout.send_estimate_home_fragment, LAYOUT_SENDESTIMATEHOMEFRAGMENT);
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
        case  LAYOUT_ACTIVITYADDTRANSECTION: {
          if ("layout/activity_addtransection_0".equals(tag)) {
            return new ActivityAddtransectionBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_addtransection is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYBANKLIST: {
          if ("layout/activity_bank_list_0".equals(tag)) {
            return new ActivityBankListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_bank_list is invalid. Received: " + tag);
        }
        case  LAYOUT_ACTIVITYCREATINGPRODUCTSANDSERVICES: {
          if ("layout/activity_creating_products_and_services_0".equals(tag)) {
            return new ActivityCreatingProductsAndServicesBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for activity_creating_products_and_services is invalid. Received: " + tag);
        }
        case  LAYOUT_FRAGMENTWEBVIEW: {
          if ("layout/fragment_web_view_0".equals(tag)) {
            return new FragmentWebViewBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for fragment_web_view is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTACCOUNTINGFRAGMENT: {
          if ("layout/layout_accounting_fragment_0".equals(tag)) {
            return new LayoutAccountingFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_accounting_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBUSSNIESSLIST: {
          if ("layout/layout_bussniess_list_0".equals(tag)) {
            return new LayoutBussniessListBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_bussniess_list is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTBVIEWBILL: {
          if ("layout/layout_bview_bill_0".equals(tag)) {
            return new LayoutBviewBillBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_bview_bill is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTCREATEBILL: {
          if ("layout/layout_create_bill_0".equals(tag)) {
            return new LayoutCreateBillBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_create_bill is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTCUSTOMIZEINVOICE: {
          if ("layout/layout_customize_invoice_0".equals(tag)) {
            return new LayoutCustomizeInvoiceBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_customize_invoice is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTCUSTOMIZEINVOICESTYLE: {
          if ("layout/layout_customize_invoice_style_0".equals(tag)) {
            return new LayoutCustomizeInvoiceStyleBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_customize_invoice_style is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTMENUFRAGMENT: {
          if ("layout/layout_menu_fragment_0".equals(tag)) {
            return new LayoutMenuFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_menu_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTMORE: {
          if ("layout/layout_more_0".equals(tag)) {
            return new LayoutMoreBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_more is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTPURCHASEFRAGMENT: {
          if ("layout/layout_purchase_fragment_0".equals(tag)) {
            return new LayoutPurchaseFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_purchase_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSALESFARGMENT: {
          if ("layout/layout_sales_fargment_0".equals(tag)) {
            return new LayoutSalesFargmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_sales_fargment is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSETTINGACCOUNTING: {
          if ("layout/layout_setting_accounting_0".equals(tag)) {
            return new LayoutSettingAccountingBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_setting_accounting is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTSETTINGFRAGMENT: {
          if ("layout/layout_setting_fragment_0".equals(tag)) {
            return new LayoutSettingFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_setting_fragment is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTTEST2: {
          if ("layout/layout_test2_0".equals(tag)) {
            return new LayoutTest2BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_test2 is invalid. Received: " + tag);
        }
        case  LAYOUT_LAYOUTTEST3: {
          if ("layout/layout_test3_0".equals(tag)) {
            return new LayoutTest3BindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for layout_test3 is invalid. Received: " + tag);
        }
        case  LAYOUT_MOBILECODEDILOG: {
          if ("layout/mobile_code_dilog_0".equals(tag)) {
            return new MobileCodeDilogBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for mobile_code_dilog is invalid. Received: " + tag);
        }
        case  LAYOUT_MOBILECODEITEM: {
          if ("layout/mobile_code_item_0".equals(tag)) {
            return new MobileCodeItemBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for mobile_code_item is invalid. Received: " + tag);
        }
        case  LAYOUT_SENDESTIMATEHOMEFRAGMENT: {
          if ("layout/send_estimate_home_fragment_0".equals(tag)) {
            return new SendEstimateHomeFragmentBindingImpl(component, view);
          }
          throw new IllegalArgumentException("The tag for send_estimate_home_fragment is invalid. Received: " + tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(1);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(1);

    static {
      sKeys.put(0, "_all");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(21);

    static {
      sKeys.put("layout/activity_addtransection_0", com.akounto.accountingsoftware.R.layout.activity_addtransection);
      sKeys.put("layout/activity_bank_list_0", com.akounto.accountingsoftware.R.layout.activity_bank_list);
      sKeys.put("layout/activity_creating_products_and_services_0", com.akounto.accountingsoftware.R.layout.activity_creating_products_and_services);
      sKeys.put("layout/fragment_web_view_0", com.akounto.accountingsoftware.R.layout.fragment_web_view);
      sKeys.put("layout/layout_accounting_fragment_0", com.akounto.accountingsoftware.R.layout.layout_accounting_fragment);
      sKeys.put("layout/layout_bussniess_list_0", com.akounto.accountingsoftware.R.layout.layout_bussniess_list);
      sKeys.put("layout/layout_bview_bill_0", com.akounto.accountingsoftware.R.layout.layout_bview_bill);
      sKeys.put("layout/layout_create_bill_0", com.akounto.accountingsoftware.R.layout.layout_create_bill);
      sKeys.put("layout/layout_customize_invoice_0", com.akounto.accountingsoftware.R.layout.layout_customize_invoice);
      sKeys.put("layout/layout_customize_invoice_style_0", com.akounto.accountingsoftware.R.layout.layout_customize_invoice_style);
      sKeys.put("layout/layout_menu_fragment_0", com.akounto.accountingsoftware.R.layout.layout_menu_fragment);
      sKeys.put("layout/layout_more_0", com.akounto.accountingsoftware.R.layout.layout_more);
      sKeys.put("layout/layout_purchase_fragment_0", com.akounto.accountingsoftware.R.layout.layout_purchase_fragment);
      sKeys.put("layout/layout_sales_fargment_0", com.akounto.accountingsoftware.R.layout.layout_sales_fargment);
      sKeys.put("layout/layout_setting_accounting_0", com.akounto.accountingsoftware.R.layout.layout_setting_accounting);
      sKeys.put("layout/layout_setting_fragment_0", com.akounto.accountingsoftware.R.layout.layout_setting_fragment);
      sKeys.put("layout/layout_test2_0", com.akounto.accountingsoftware.R.layout.layout_test2);
      sKeys.put("layout/layout_test3_0", com.akounto.accountingsoftware.R.layout.layout_test3);
      sKeys.put("layout/mobile_code_dilog_0", com.akounto.accountingsoftware.R.layout.mobile_code_dilog);
      sKeys.put("layout/mobile_code_item_0", com.akounto.accountingsoftware.R.layout.mobile_code_item);
      sKeys.put("layout/send_estimate_home_fragment_0", com.akounto.accountingsoftware.R.layout.send_estimate_home_fragment);
    }
  }
}
