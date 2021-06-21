package com.akounto.accountingsoftware.Activity.fragment;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akounto.accountingsoftware.Activity.SplashScreenActivity;
import com.akounto.accountingsoftware.Constants.Constant;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.Activity.Setting.SettingMenu;
import com.akounto.accountingsoftware.adapter.UserManagementAdminAdapter;
import com.akounto.accountingsoftware.adapter.UserManagementAdminItemClick;
import com.akounto.accountingsoftware.network.CustomCallBack;
import com.akounto.accountingsoftware.network.RestClient;
import com.akounto.accountingsoftware.request.AddUserRequest;
import com.akounto.accountingsoftware.response.UserManagementResponse;
import com.akounto.accountingsoftware.response.Users;
import com.akounto.accountingsoftware.util.AddFragments;
import com.akounto.accountingsoftware.util.UiUtil;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class UserManagementFragment extends Fragment implements UserManagementAdminItemClick {
    Dialog dialog;

    /* renamed from: id */
    String f90id = "0";
    String mode = "create-user";
    RadioButton roleButton;
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.user_management_fragment, container, false);
        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragments.addFragmentToDrawerActivity(getContext(), null, SettingMenu.class);
            }
        });
        init();
        return this.view;
    }

    private void init() {
        this.view.findViewById(R.id.inviteUserButton).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                UserManagementFragment.this.lambda$init$0$UserManagementFragment(view);
            }
        });
    }

    public void lambda$init$0$UserManagementFragment(View v) {
        editAdminDialog(null);
    }

    public void onResume() {
        super.onResume();
        getUsers();
    }

    /* access modifiers changed from: private */
    public void getUsers() {
        RestClient.getInstance(getActivity()).getUsers(Constant.X_SIGNATURE,"Bearer " + UiUtil.getAcccessToken(getContext()),UiUtil.getComp_Id(getContext())).enqueue(new CustomCallBack<UserManagementResponse>(getContext(), null) {
            public void onResponse(Call<UserManagementResponse> call, Response<UserManagementResponse> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    Log.d("response", response.body().getData().toString());
                    UserManagementFragment.this.setUpAdminList(response.body().getData());
                }
            }

            public void onFailure(Call<UserManagementResponse> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }

    /* access modifiers changed from: private */
    public void setUpAdminList(List<Users> data) {
        RecyclerView recyclerView = this.view.findViewById(R.id.adminsRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new UserManagementAdminAdapter(getContext(), data, this));
    }

    public void editAdmin(Users users) {
        editAdminDialog(users);
    }

    private void editAdminDialog(Users users) {
        Dialog dialog2 = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.user_mgmt_edit_admin_layout);
        this.dialog.setCancelable(true);
        this.dialog.setCanceledOnTouchOutside(true);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        LinearLayout back=this.dialog.findViewById(R.id.back);
        TextView title = this.dialog.findViewById(R.id.title);
        if(users!=null){
            title.setText(" Edit User");
        }else{
            title.setText(" Invite User");
        }
        EditText fnameEt = this.dialog.findViewById(R.id.et_fname);
        EditText lnameEt = this.dialog.findViewById(R.id.et_lname);
        EditText emailEt = this.dialog.findViewById(R.id.et_email);
        RadioGroup roleGroup = this.dialog.findViewById(R.id.radioGroup);
        TextView fnameErrorTv = this.dialog.findViewById(R.id.fNameErrorTv);
        TextView lnameErrorTv = this.dialog.findViewById(R.id.lNameErrorTv);
        TextView emailErrorTv = this.dialog.findViewById(R.id.emailErrorTv);
        if (users != null) {
            fnameEt.setText(users.getFirstName());
            lnameEt.setText(users.getLastName());
            emailEt.setText(users.getEmail());
            if (users.getRole().equals("Admin")) {
                roleGroup.check(R.id.adminRB);
            } else {
                roleGroup.check(R.id.userRB);
            }
            this.mode = "edit-user";
            this.f90id = users.getId();
        }
        TextView save = this.dialog.findViewById(R.id.saveButton);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagementFragment.this.lambda$editAdminDialog$1$UserManagementFragment(roleGroup, fnameEt, fnameErrorTv, lnameEt, lnameErrorTv,emailEt, emailErrorTv, view);
            }
        });
        this.dialog.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                dialog.dismiss();
            }
        });
        this.dialog.show();
    }

    public void lambda$editAdminDialog$1$UserManagementFragment(RadioGroup roleGroup, EditText fnameEt, TextView fnameErrorTv, EditText lnameEt, TextView lnameErrorTv, EditText emailEt, TextView emailErrorTv, View v) {
        TextView textView = fnameErrorTv;
        TextView textView2 = lnameErrorTv;
        TextView textView3 = emailErrorTv;
        this.roleButton = this.dialog.findViewById(roleGroup.getCheckedRadioButtonId());
        String firstName = fnameEt.getText().toString();
        if (firstName.isEmpty()) {
            textView.setText("Please enter first name");
            textView.setVisibility(View.VISIBLE);
        } else if (firstName.length() < 3) {
            textView.setText("First name is too small. (min 3 chars)");
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            String lastName = lnameEt.getText().toString();
            if (lastName.isEmpty()) {
                textView2.setText("Please enter last name");
                textView2.setVisibility(View.VISIBLE);
            } else if (lastName.length() < 3) {
                textView2.setText("Last name is too small. (min 3 chars)");
                textView2.setVisibility(View.VISIBLE);
            } else {
                textView2.setVisibility(View.GONE);
                String email = emailEt.getText().toString();
                if (email.isEmpty()) {
                    textView3.setVisibility(View.VISIBLE);
                    return;
                }
                textView3.setVisibility(View.GONE);
                addUser(new AddUserRequest(this.roleButton.getText().toString(), email, firstName, this.f90id, lastName), this.mode);
            }
        }
    }

    private void addUser(AddUserRequest addUserRequest, String mode2) {
        RestClient.getInstance(getContext()).createUser(Constant.X_SIGNATURE,"Bearer " +UiUtil.getAcccessToken(getContext()),UiUtil.getComp_Id(getContext()),addUserRequest, mode2).enqueue(new CustomCallBack<ResponseBody>(getContext(), null) {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                super.onResponse(call, response);
                if (response.isSuccessful()) {
                    Bundle b=new Bundle();
                    b.putString(Constant.CATEGORY,"setting");
                    b.putString(Constant.ACTION,"add_user");
                    SplashScreenActivity.sendEvent("setting_add_user",b);
                    UiUtil.showToast(UserManagementFragment.this.getContext(), "Added");
                    if (UserManagementFragment.this.dialog != null) {
                        UserManagementFragment.this.dialog.dismiss();
                    }
                    UserManagementFragment.this.getUsers();
                    return;
                }
                Bundle b=new Bundle();
                b.putString(Constant.CATEGORY,"setting");
                b.putString(Constant.ACTION,"add_user_fail");
                SplashScreenActivity.sendEvent("setting_add_user",b);
                UiUtil.showToast(UserManagementFragment.this.getContext(), "Error while adding");
            }

            public void onFailure(Call<ResponseBody> call, Throwable t) {
                super.onFailure(call, t);
                Log.d("error", t.toString());
            }
        });
    }
}
