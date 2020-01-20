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
        View view = inflater.inflate(R.layout.layout_request_history, null);
        return new RequestHistoryAdapter.RequestHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestHistoryViewHolder holder, int position) {

        RequestHistory requestHistory = productList.get(position);
        holder.mName.setText(requestHistory.getCustomer_name());
        holder.mDateTime.setText(requestHistory.getUpdated_at());
        holder.mCategory.setText(requestHistory.getCategory_id());
        holder.mStatus.setText(requestHistory.getStatus());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class RequestHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView mName,mCategory,mDateTime,mStatus;

        public RequestHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.layout_request_History_tv_name);
            mCategory = itemView.findViewById(R.id.layout_request_History_tv_garbageCategory);
            mDateTime = itemView.findViewById(R.id.layout_request_tHistory_tv_dateTime);
            mStatus = itemView.findViewById(R.id.layout_request_History_tv_status);

        }
    }
}
