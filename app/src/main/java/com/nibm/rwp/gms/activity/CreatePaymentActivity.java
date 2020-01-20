package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;

public class CreatePaymentActivity extends BaseActivity implements View.OnClickListener {

    //Constant
    private EditText mEtCustomerName,mEtSelectedGarbageCategory,mEtSelectedGarbageVehicleType,mEtSelectedCustomerArea,mEtTotalPayment;
    private Button mBtnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_payment);

        setToolbar(getResources().getString(R.string.activity_create_payment), CreatePaymentActivity.this);
        initView();
        initView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void initView() {
        mEtCustomerName = findViewById(R.id.activity_create_payment_et_customer_name);
        mEtSelectedGarbageCategory = findViewById(R.id.activity_create_payment_et_customerGarbageCategory);
        mEtSelectedGarbageVehicleType = findViewById(R.id.activity_create_payment_et_customerGarbageVehicleType);
        mEtSelectedCustomerArea = findViewById(R.id.activity_create_payment_et_customerArea);
        mEtTotalPayment = findViewById(R.id.activity_create_payment_et_customerTotalPayment);
        mBtnContinue = findViewById(R.id.activity_create_payment_btn_continue);
        mEtCustomerName.setKeyListener(null);
        mEtSelectedGarbageCategory.setKeyListener(null);
        mEtSelectedGarbageVehicleType.setKeyListener(null);
        mEtSelectedCustomerArea.setKeyListener(null);
        mEtTotalPayment.setKeyListener(null);
        mBtnContinue.setOnClickListener(this);
    }

    private void changeActivity(){
        Intent intent = new Intent(CreatePaymentActivity.this,AddPaymentActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_create_payment_btn_continue:
                changeActivity();
        }
    }
}
