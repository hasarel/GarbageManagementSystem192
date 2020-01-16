package com.nibm.rwp.gms.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.rwp.gms.activity.AboutActivity;
import com.nibm.rwp.gms.activity.HomeActivity;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.activity.PaymentHistoryActivity;
import com.nibm.rwp.gms.activity.RequestHistoryActivity;
import com.nibm.rwp.gms.adapter.NavigationDrawerAdapter;
import com.nibm.rwp.gms.constant.AppConst;
import com.nibm.rwp.gms.dto.NavDrawerItem;
import com.nibm.rwp.gms.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class DrawerFragment extends Fragment implements OnItemClickListener<NavDrawerItem> {

    // constants
    private static String TAG = DrawerFragment.class.getSimpleName();

    private static String[] mTitles = null;

    // UI components
    private RecyclerView mRvDrawerList;
    private TextView mTvLoggedUser, mTvLoggedUserEmail;
    private LinearLayout mLlLogout;

    private Activity mActivity;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private View mContainerView;
    private NavigationDrawerAdapter mAdapter;
    private FragmentDrawerListener mDrawerListener;

    public DrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO - You can find drawer labels from here
        mTitles = getActivity().getResources().getStringArray(R.array.nav_drawer_labels);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflating view layout
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        mActivity = getActivity();

        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRvDrawerList = view.findViewById(R.id.fragment_navigation_drawer_rv_drawer_list);

        mAdapter = new NavigationDrawerAdapter(mActivity, getData(), this);
        mRvDrawerList.setAdapter(mAdapter);
        mRvDrawerList.setLayoutManager(new LinearLayoutManager(getContext()));

        mTvLoggedUser = view.findViewById(R.id.fragment_navigation_drawer_tv_logged_user);
        mTvLoggedUserEmail = view.findViewById(R.id.fragment_navigation_drawer_tv_logged_user_email);

  /*      mTvLoggedUser.setText(SharedPref.getString(mActivity, SharedPref.USER_VIEW_NAME_KEY, SharedPref.DEFAULT_VALUE_FOR_STRING));
        mTvLoggedUserEmail.setText(SharedPref.getString(mActivity, SharedPref.USERNAME_KEY, SharedPref.DEFAULT_VALUE_FOR_STRING));*/
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout, final Toolbar toolbar) {
        mContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                //TODO - Can change toolbar color when drawer opens
                //toolbar.setAlpha(1 - slideOffset / 2);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

    @Override
    public void onItemClick(int position, NavDrawerItem data) {
        mDrawerListener.onDrawerItemSelected(position);
        mDrawerLayout.closeDrawer(mContainerView);
    }

    public void setDrawerListener(FragmentDrawerListener listener) {
        this.mDrawerListener = listener;
    }

    public List<NavDrawerItem> getData() {
        List<NavDrawerItem> data = new ArrayList<>();

        // preparing navigation drawer items
        for (int i = 0; i < mTitles.length; i++) {
            NavDrawerItem navItem = new NavDrawerItem();
            navItem.setTitle(mTitles[i]);
            if (mActivity.getClass().getSimpleName().equals(HomeActivity.class.getSimpleName())) {
                if (navItem.getTitle().equals(AppConst.NAV_ITEM_Home)) {
                    navItem.setSelectedItem(true);
                }
            } else if (mActivity.getClass().getSimpleName().equals(PaymentHistoryActivity.class.getSimpleName())) {
                if (navItem.getTitle().equals(AppConst.NAV_ITEM_PAYMENT_HISTORY)) {
                    navItem.setSelectedItem(true);
                }
            }else if (mActivity.getClass().getSimpleName().equals(RequestHistoryActivity.class.getSimpleName())) {
                if (navItem.getTitle().equals(AppConst.NAV_ITEM_REQUEST_HISTORY)) {
                    navItem.setSelectedItem(true);
                }
            }  else if (mActivity.getClass().getSimpleName().equals(AboutActivity.class.getSimpleName())) {
                if (navItem.getTitle().equals(AppConst.NAV_ITEM_ABOUT)) {
                    navItem.setSelectedItem(true);
                }
            }
            data.add(navItem);
        }
        return data;
    }

    public interface FragmentDrawerListener {
        void onDrawerItemSelected(int position);
    }
}
