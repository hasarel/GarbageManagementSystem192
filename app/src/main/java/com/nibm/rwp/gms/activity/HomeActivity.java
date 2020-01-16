package com.nibm.rwp.gms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    // constants
    private static final String TAG = SplashActivity.class.getSimpleName();

    //Ui Components
    private Button mBtnGetStart;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        setToolbar("Home", HomeActivity.this);
        setDrawer();
        initView();
    }

    public void changeActivity(){
        Intent intent = new Intent(HomeActivity.this,AddRequestActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

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
        mBtnGetStart = findViewById(R.id.activity_home_page_btn_getStart);
        mBtnGetStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_home_page_btn_getStart:
             changeActivity();
             break;
        }
    }
}
