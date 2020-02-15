package com.nibm.rwp.gms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.constant.AppConst;
import com.nibm.rwp.gms.dto.NavDrawerItem;
import com.nibm.rwp.gms.listeners.OnItemClickListener;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    private Context mContext;
    private List<NavDrawerItem> mData = Collections.emptyList();
    private OnItemClickListener<NavDrawerItem> mListener;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data, @NonNull OnItemClickListener<NavDrawerItem> listener) {
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_drawer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem navigationItem = mData.get(position);
        holder.mTvDrawerItemTitle.setText(navigationItem.getTitle());
        if (navigationItem.getTitle().equals(AppConst.NAV_ITEM_Home)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_dashboard));
        } else if (navigationItem.getTitle().equals(AppConst.NAV_ITEM_PAYMENT_HISTORY)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.paymenthistory));
        } else if (navigationItem.getTitle().equals(AppConst.NAV_ITEM_REQUEST_HISTORY)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.activityhistory));
        }else if (navigationItem.getTitle().equals(AppConst.NAV_ITEM_ABOUT)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.about));
        } else if (navigationItem.getTitle().equals(AppConst.NAV_ITEM_FEEDBACK)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.feedback));
        }else if (navigationItem.getTitle().equals(AppConst.NAV_ITEM_Logout)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.logout));
        }

        if (navigationItem.isSelectedItem()) {
            holder.mRlBase.setBackground(mContext.getResources().getDrawable(R.drawable.item_selected_background));
            holder.mTvDrawerItemTitle.setTextColor(mContext.getResources().getColor(R.color.colorSelectedMenuItemText));
        } else {
            holder.mRlBase.setBackground(mContext.getResources().getDrawable(R.drawable.item_click_background));
            holder.mTvDrawerItemTitle.setTextColor(mContext.getResources().getColor(R.color.colorNotSelectedMenuItemText));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRlBase;
        ImageView mIvItemIcon;
        TextView mTvDrawerItemTitle;

        public MyViewHolder(View view) {
            super(view);

            mRlBase = view.findViewById(R.id.row_navigation_drawer_rl_base);
            mIvItemIcon = view.findViewById(R.id.row_navigation_drawer_iv_item_icon);
            mTvDrawerItemTitle = view.findViewById(R.id.row_navigation_drawer_tv_item_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = MyViewHolder.this.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position, mData.get(position));
                    }
                }
            });
        }
    }

}