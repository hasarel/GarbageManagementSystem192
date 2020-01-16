package com.nibm.rwp.gms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.utill.AppUtill;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    //Ui components
    private EditText mEtFname,mEtLname,mEtAddress1,mEtAddress2,mEtStreet,mEtNic,mEtLocation,mEtContact,mEtEmail,mEtPassword,mEtCpassword;
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

    public void initView(){
        mBtnRegister = findViewById(R.id.activity_register_btn_submit);
//        mEtFname = findViewById(R.id.activity_Register_et_fname);
//        mEtLname = findViewById(R.id.activity_Register_et_lname);
//        mEtAddress1 = findViewById(R.id.activity_Register_et_address1);
//        mEtAddress2 = findViewById(R.id.activity_Register_et_address2);
//        mEtStreet = findViewById(R.id.activity_Register_et_addressStreet);
//        mEtNic = findViewById(R.id.activity_Register_et_nic);
//        mEtLocation = findViewById(R.id.activity_Register_et_location);
//        mEtContact = findViewById(R.id.activity_Register_et_contactNo);
        mEtEmail = findViewById(R.id.activity_Register_et_email);
        mEtPassword = findViewById(R.id.activity_Register_et_password);
        //mEtCpassword = findViewById(R.id.activity_Register_et_comPassword);
        mBtnRegister.setOnClickListener(this);
    }

    public void userRegister(){

        String email,password;

        email = mEtEmail.getText().toString();
        password = mEtPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
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

        if (TextUtils.isEmpty(password)){
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

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Successfully Register !!! ",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(RegisterActivity.this,"Failed !!! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_register_btn_submit:
                userRegister();
                break;
        }
    }
}
