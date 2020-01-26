package com.nibm.rwp.gms.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.activity.AddRequestActivity;
import com.nibm.rwp.gms.activity.DriverMapActivity;
import com.nibm.rwp.gms.dto.CustomerRequest;
import com.nibm.rwp.gms.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class CustomerRequestAdapter extends RecyclerView.Adapter<CustomerRequestAdapter.CustomerRequestViewHolder> {


    private Context mCtx;
    private List<CustomerRequest> productList;

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
       // holder.mContactNo.setText(customerRequest.getLatitude());

      //  CustomerRequest req = new CustomerRequest();
        final Double lat=Double.parseDouble(customerRequest.getLatitude());
        final Double longTude=Double.parseDouble( customerRequest.getLongitude());
        holder.mLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/dir/?api=1&destination="+longTude+","+lat+""));
                mCtx.startActivity(intent);

                Toast.makeText(mCtx,"My Position is :" +position,Toast.LENGTH_SHORT).show();
            }
        });
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
