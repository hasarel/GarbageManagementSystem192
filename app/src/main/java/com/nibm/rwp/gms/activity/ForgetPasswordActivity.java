package com.nibm.rwp.gms.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.utill.AppUtill;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener{

    //Ui components
    private Button mBtnSubmit;
    private EditText mEtForgotEmail;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        progressDialog = new ProgressDialog(this);

        setToolbar(getResources().getString(R.string.activity_forgetPassword), ForgetPasswordActivity.this);
        mAuth = FirebaseAuth.getInstance();
        initView();
    }

    private void showProgressDialogWithTitle(String substring) {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(substring);
        progressDialog.show();
    }

    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void forgetPassword(){

        String email;
        email = mEtForgotEmail.getText().toString();

        if (TextUtils.isEmpty(email)){
            final AlertDialog dialog = new AlertDialog.Builder(ForgetPasswordActivity.this).create();
            AppUtill.showCustomStandardAlert(dialog,
                    ForgetPasswordActivity.this,
                    getResources().getString(R.string.alert_warning_text),
                    getResources().getString(R.string.fill_email_fields_message),
                    getResources().getDrawable(R.drawable.icons8_error),
                    null,
                    getResources().getString(R.string.ok_text), false);
            return;
        }

        showProgressDialogWithTitle("Resetting Password is Start....");
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            hideProgressDialogWithTitle();
                            passwordResetDialog();
                        }
                        else{
                            hideProgressDialogWithTitle();
                            String error = task.getException().toString();
                            passwordResetErrorDialog();
                            //Toast.makeText(getApplicationContext(),"Mail sending error" , Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void passwordResetDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(ForgetPasswordActivity.this).create();
        AppUtill.showCustomConfirmAlert(dialog,
                ForgetPasswordActivity.this,
                getResources().getString(R.string.password_reset_message),
                getResources().getString(R.string.cancel_request),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(ForgetPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                },
                null,
                getResources().getString(R.string.ok_text),
                getResources().getString(R.string.cancel_text),
                false);
    }

    private void passwordResetErrorDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(ForgetPasswordActivity.this).create();
        AppUtill.showCustomStandardAlert(dialog,
                ForgetPasswordActivity.this,
                getResources().getString(R.string.password_reset_error),
                getResources().getString(R.string.password_reset_error_message),
                getResources().getDrawable(R.drawable.verify),
                null,
                getResources().getString(R.string.ok_text), false);
    }

    public void initView(){
        mBtnSubmit = findViewById(R.id.activity_forget_password_btn_submit);
        mEtForgotEmail = findViewById(R.id.activity_forget_password_et_email);
        mBtnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_forget_password_btn_submit:
               forgetPassword();
        }
    }
}
