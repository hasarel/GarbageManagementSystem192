package com.nibm.rwp.gms.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.utill.AppUtill;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    //Ui components
    private EditText mEtFname, mEtLname, mEtAddress1, mEtAddress2, mEtAddress3, mEtContact, mEtEmail, mEtPassword;
    Button mBtnRegister;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setToolbar(getResources().getString(R.string.activity_register), RegisterActivity.this);

        mAuth = FirebaseAuth.getInstance();
        initView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initView() {
        mBtnRegister = findViewById(R.id.activity_register_btn_submit);
        mEtFname = findViewById(R.id.activity_Register_et_fname);
        mEtAddress1 = findViewById(R.id.activity_Register_et_address1);
        mEtAddress2 = findViewById(R.id.activity_Register_et_address2);
        mEtAddress3 = findViewById(R.id.activity_Register_et_address3);
        mEtContact = findViewById(R.id.activity_Register_et_contactNo);
        mEtEmail = findViewById(R.id.activity_Register_et_email);
        mEtPassword = findViewById(R.id.activity_Register_et_password);
        mBtnRegister.setOnClickListener(this);
    }

    public void passDataToAddRequest() {
        String fname = mEtFname.getText().toString();
        String address1 = mEtAddress1.getText().toString();
        String address2 = mEtAddress2.getText().toString();
        String address3 = mEtAddress3.getText().toString();
        String contact = mEtContact.getText().toString();
        String email = mEtEmail.getText().toString();

        SharedPreferences prf = getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = prf.edit();
        editor.putString("fname", fname);
        editor.putString("address1", address1);
        editor.putString("address2", address2);
        editor.putString("address3", address3);
        editor.putString("contact", contact);
        editor.putString("email", email);
        editor.commit();
    }


    public void userRegister() {

        String email, password;

        email = mEtEmail.getText().toString();
        password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            final AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
            AppUtill.showCustomStandardAlert(dialog,
                    RegisterActivity.this,
                    getResources().getString(R.string.alert_warning_text),
                    getResources().getString(R.string.fill_email_fields_message),
                    getResources().getDrawable(R.drawable.icons8_error),
                    null,
                    getResources().getString(R.string.ok_text), false);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            final AlertDialog dialog = new AlertDialog.Builder(RegisterActivity.this).create();
            AppUtill.showCustomStandardAlert(dialog,
                    RegisterActivity.this,
                    getResources().getString(R.string.alert_warning_text),
                    getResources().getString(R.string.fill_password_fields_message),
                    getResources().getDrawable(R.drawable.icons8_error),
                    null,
                    getResources().getString(R.string.ok_text), false);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Successfully Register !!! ", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else
                            Toast.makeText(RegisterActivity.this, "Failed !!! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_register_btn_submit:
                userRegister();
                passDataToAddRequest();
                break;
        }
    }
}
