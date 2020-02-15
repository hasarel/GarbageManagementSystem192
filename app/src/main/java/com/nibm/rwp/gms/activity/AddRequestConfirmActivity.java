package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;

public class AddRequestConfirmActivity extends BaseActivity implements View.OnClickListener {

    //constant
    private static final String TAG = AddRequestConfirmActivity.class.getSimpleName();

    //Ui components
    private EditText mEtName,mEtGarbageType,mEtVehicleType,mEtAreaType,mEtTotal;
    private Button mBtnConfirm;

    public String total,name,categorytype,area,vehicle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request_confirm);

        setToolbar(getResources().getString(R.string.activity_addrequest), AddRequestConfirmActivity.this);

        initView();

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView() {

        mEtName = findViewById(R.id.activity_add_payment_confirm_et_name);
        mEtName.setKeyListener(null);
        mEtGarbageType = findViewById(R.id.activity_add_payment_confirm_et_Garbage_category);
        mEtGarbageType.setKeyListener(null);
        mEtAreaType = findViewById(R.id.activity_add_payment_confirm_et_area_code);
        mEtAreaType.setKeyListener(null);
        mEtVehicleType = findViewById(R.id.activity_add_payment_confirm_et_vehicle_type);
        mEtVehicleType.setKeyListener(null);
        mEtTotal = findViewById(R.id.activity_add_payment_confirm_et_total);
        mEtTotal.setKeyListener(null);
        mBtnConfirm = findViewById(R.id.activity_add_payment_confirm_btn_done_payment);
        mBtnConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_add_payment_confirm_btn_done_payment:
                changeActivity();
        }
    }

    private void changeActivity() {
        Intent intent = new Intent(AddRequestConfirmActivity.this,AddPaymentActivity.class);
        startActivity(intent);
    }
}
