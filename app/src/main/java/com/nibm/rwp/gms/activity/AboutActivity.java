package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setToolbar(getResources().getString(R.string.activity_about), AboutActivity.this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
