package com.nibm.rwp.gms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.dto.CustomerRequest;

import java.util.List;

public class CustomerRequestAdapter extends RecyclerView.Adapter<CustomerRequestAdapter.CustomerRequestViewHolder>{

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<CustomerRequest> productList;


    //getting the context and product list with constructor
    public CustomerRequestAdapter(Context mCtx, List<CustomerRequest> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CustomerRequestAdapter.CustomerRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_paymenthistory_recycleview, null);
        return new CustomerRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRequestViewHolder holder,final int position) {
        CustomerRequest paymentHistory = productList.get(position);
        holder.mName.setText(paymentHistory.getName());
        holder.mAddress.setText(paymentHistory.getAddress());
        holder.mContactNo.setText(paymentHistory.getContacNo());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class CustomerRequestViewHolder extends RecyclerView.ViewHolder {

        TextView mName,mAddress,mContactNo;
        Button mLocation;

        public CustomerRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.layout_item_paymentHistory_tv_name);
            mAddress = itemView.findViewById(R.id.layout_item_paymentHistory_tv_address);
            mContactNo = itemView.findViewById(R.id.layout_item_paymentHistory_tv_contactNo);
            mLocation = itemView.findViewById(R.id.layout_item_paymentHistory_btn_openMap);
        }
    }
}
