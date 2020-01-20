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
        View view = inflater.inflate(R.layout.driver_map_request, null);
        return new CustomerRequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRequestViewHolder holder,final int position) {
        CustomerRequest customerRequest = productList.get(position);
        holder.mName.setText(customerRequest.getCustomer_name());
        holder.mAddress1.setText(customerRequest.getAddress_1());
        holder.mAddress2.setText(customerRequest.getAddress_2());
        holder.mAddress3.setText(customerRequest.getAddress_3());
        holder.mContactNo.setText(customerRequest.getTele_no());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class CustomerRequestViewHolder extends RecyclerView.ViewHolder {

        TextView mName,mAddress1,mAddress2,mAddress3,mContactNo;
        Button mLocation;

        public CustomerRequestViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.layout_driver_map_request_tv_name);
            mAddress1 = itemView.findViewById(R.id.layout_driver_map_request_tv_address1);
            mAddress2 = itemView.findViewById(R.id.layout_driver_map_request_tv_address2);
            mAddress3 = itemView.findViewById(R.id.layout_driver_map_request_tv_address3);
            mContactNo = itemView.findViewById(R.id.layout_driver_map_request_tv_contactNo);
            mLocation = itemView.findViewById(R.id.layout_driver_map_request_btn_openMap);
        }
    }
}
