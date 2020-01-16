package com.nibm.rwp.gms.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.utill.AppUtill;

public class AddPaymentActivity extends BaseActivity implements View.OnClickListener {

    private Button mBtnPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        setToolbar(getResources().getString(R.string.activity_addpayment), AddPaymentActivity.this);
        initView();
    }

    private void initView() {
        mBtnPayment = findViewById(R.id.activity_add_payment_btn_done);
        mBtnPayment.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void popUp() {

        final AlertDialog dialog = new AlertDialog.Builder(AddPaymentActivity.this).create();
        AppUtill.showCustomStandardAlert(dialog,
                AddPaymentActivity.this,
                getResources().getString(R.string.payment_sucess),
                getResources().getString(R.string.payment_sucess2),
                getResources().getDrawable(R.drawable.icons8888),
                null,
                getResources().getString(R.string.ok_text), false);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_add_payment_btn_done:
                popUp();
        }
    }
}
