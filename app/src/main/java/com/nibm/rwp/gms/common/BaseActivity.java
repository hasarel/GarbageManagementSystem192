package com.nibm.rwp.gms.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.nibm.rwp.gms.activity.AboutActivity;
import com.nibm.rwp.gms.activity.FeedBackActivity;
import com.nibm.rwp.gms.activity.HomeActivity;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.activity.LoginActivity;
import com.nibm.rwp.gms.activity.PaymentHistoryActivity;
import com.nibm.rwp.gms.activity.RequestHistoryActivity;
import com.nibm.rwp.gms.fragment.DrawerFragment;
import com.nibm.rwp.gms.utill.AppUtill;

public class BaseActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

    // constants
    private static final String TAG = BaseActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private DrawerFragment mDrawerFragment;
    private DrawerLayout mDrawerLayout;

    public BaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setToolbar(String toolbarName, Activity activity) {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(toolbarName);
        if (toolbarName.isEmpty()) {
        }
        setSupportActionBar(mToolbar);
        if (!activity.getClass().getSimpleName().equals(HomeActivity.class.getSimpleName())) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setDrawer() {
        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(int position) {
        displayView(this, position);
    }

    private void displayView(final Activity activity, int position) {
        switch (position) {
            case 0:
                if (!activity.getClass().getSimpleName().equals(HomeActivity.class.getSimpleName())) {
                    activity.finish();
                }
                break;
            case 1:
                if (!activity.getClass().getSimpleName().equals(PaymentHistoryActivity.class.getSimpleName())) {
                    AppUtill.startActivityFromDrawer(activity, PaymentHistoryActivity.class);
                }
                break;
            case 2:
                if (!activity.getClass().getSimpleName().equals(RequestHistoryActivity.class.getSimpleName())) {
                    AppUtill.startActivityFromDrawer(activity, RequestHistoryActivity.class);
                }
                break;
            case 3:
                if (!activity.getClass().getSimpleName().equals(AboutActivity.class.getSimpleName())) {
                    AppUtill.startActivityFromDrawer(activity, AboutActivity.class);
                }
                break;
            case 4:
                if (!activity.getClass().getSimpleName().equals(FeedBackActivity.class.getSimpleName())) {
                    AppUtill.startActivityFromDrawer(activity, FeedBackActivity.class);
                }
                break;

            case 5:// Logout
                final Activity innerActivity = activity;
                final AlertDialog dialog = new AlertDialog.Builder(activity).create();
                AppUtill.showCustomConfirmAlert(dialog, activity, getResources().getString(R.string.sign_out_text), getResources().getString(R.string.sign_out_message),

                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                Toast.makeText(innerActivity, getResources().getText(R.string.nav_item_logout), Toast.LENGTH_SHORT).show();
//                                UserInfoManager.logout(innerActivity);
//                                getAppDaoSession().getEmployeeDao().deleteAll();
//                                getAppDaoSession().getAttachmentDao().deleteAll();
//                                getAppDaoSession().getTaskAndActivityListCountDao().deleteAll();
//                                getAppDaoSession().getTaskDetailsDao().deleteAll();
//                                getAppDaoSession().getSyncTextDetailsDao().deleteAll();
                        /*innerActivity.finish();
                        AppUtil.startActivity(innerActivity, LoginActivity.class);*/
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        }, null, getResources().getString(R.string.yes_text), getResources().getString(R.string.no_text), false);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }
    }

}
