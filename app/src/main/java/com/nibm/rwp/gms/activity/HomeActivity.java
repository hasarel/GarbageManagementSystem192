package com.nibm.rwp.gms.activity;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    // constants
    private static final String TAG = SplashActivity.class.getSimpleName();

    //Ui Components
    private Button mBtnGetStart;
    private TextView mTvRequestHistory,mTvPaymentHistory,mTvAboutUs, mTvFeedback;
    final Activity activity = null;

    public FirebaseAuth mAuth;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("TestPushNotification","TestPushNotification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        String msg = getString(R.string.fcm_token, token);
                        Log.d(TAG, msg);
                    }
                });

        mAuth = FirebaseAuth.getInstance();
        setToolbar("Home", HomeActivity.this);
        setDrawer();
        initView();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public void changeActivity(){
        Intent intent = new Intent(HomeActivity.this,AddRequestActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void initView() {
        mTvRequestHistory = findViewById(R.id.activity_home_page_tv_request_history);
        mTvPaymentHistory = findViewById(R.id.activity_home_page_tv_payment_history);
        mTvAboutUs = findViewById(R.id.activity_home_page_tv_about_us);
        mTvFeedback = findViewById(R.id.activity_home_page_tv_feedback);
        mBtnGetStart = findViewById(R.id.activity_home_page_btn_getStart);
        mTvRequestHistory.setOnClickListener(this);
        mTvPaymentHistory.setOnClickListener(this);
        mTvFeedback.setOnClickListener(this);
        mTvAboutUs.setOnClickListener(this);
        mBtnGetStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_home_page_btn_getStart:
             changeActivity();
             break;
            case R.id.activity_home_page_tv_payment_history:
                Intent intent = new Intent(HomeActivity.this,PaymentHistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_home_page_tv_request_history:
                Intent intent1 = new Intent(HomeActivity.this,RequestHistoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.activity_home_page_tv_about_us:
                Intent intent2 = new Intent(HomeActivity.this,AboutActivity.class);
                startActivity(intent2);
                break;
            case R.id.activity_home_page_tv_feedback:
                Intent intent3 = new Intent(HomeActivity.this,FeedBackActivity.class);
                startActivity(intent3);
        }
    }
}
