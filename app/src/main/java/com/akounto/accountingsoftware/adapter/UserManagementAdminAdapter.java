package com.akounto.accountingsoftware.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.akounto.accountingsoftware.R;
import com.akounto.accountingsoftware.response.Users;
import java.util.List;

public class UserManagementAdminAdapter extends RecyclerView.Adapter<UserManagementAdminAdapter.MyViewHolder> {
    Context context;
    UserManagementAdminItemClick onClickListener;
    private final List<Users> usersList;

    public UserManagementAdminAdapter(Context context2, List<Users> users, UserManagementAdminItemClick onClickListener2) {
        this.context = context2;
        this.usersList = users;
        this.onClickListener = onClickListener2;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_mgmt_admin_item, parent, false));
    }

    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyViewHolder vh = holder;
        Users user = this.usersList.get(position);
        TextView textView = vh.name;
        textView.setText(user.getFirstName() + " " + user.getLastName());
        vh.email.setText(user.getEmail());
        vh.role.setText(user.getRole());
        vh.edit.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                UserManagementAdminAdapter.this.lambda$onBindViewHolder$0$UserManagementAdminAdapter(user, view);
            }
        });
    }

    public /* synthetic */ void lambda$onBindViewHolder$0$UserManagementAdminAdapter(Users user, View v) {
        this.onClickListener.editAdmin(user);
    }

    public int getItemCount() {
        return this.usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView edit;
        TextView email;
        TextView name;
        TextView role;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.name);
            this.email = itemView.findViewById(R.id.email);
            this.role = itemView.findViewById(R.id.role);
            this.edit = itemView.findViewById(R.id.editButton);
        }
    }
}
