package com.nibm.rwp.gms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.activity.RequestHistoryActivity;
import com.nibm.rwp.gms.dto.PaymentHistory;
import com.nibm.rwp.gms.dto.RequestHistory;

import java.util.List;

public class RequestHistoryAdapter extends RecyclerView.Adapter<RequestHistoryAdapter.RequestHistoryViewHolder>{

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<RequestHistory> productList;

    //getting the context and product list with constructor
    public RequestHistoryAdapter(Context mCtx, List<RequestHistory> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }
    @NonNull
    @Override
    public RequestHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_requesthistory_recycleview, null);
        return new RequestHistoryAdapter.RequestHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHistoryViewHolder holder, int position) {

        RequestHistory requestHistory = productList.get(position);
        holder.mName.setText(requestHistory.getName());
        holder.mAddress.setText(requestHistory.getAddress());
        holder.mContactNo.setText(requestHistory.getContacNo());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RequestHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView mName,mAddress,mContactNo;

        public RequestHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.layout_item_requestHistory_tv_name);
            mAddress = itemView.findViewById(R.id.layout_item_requestHistory_tv_address);
            mContactNo = itemView.findViewById(R.id.layout_item_requestHistory_tv_contactNo);
        }
    }
}
