package com.nibm.rwp.gms.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.utill.AppUtill;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Ui components
    private TextView mTvForgetPassword, mTvRegister;
    private Button btnLogin;
    private EditText mEtemail, mEtpassword;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        initView();
    }

    private void userLogin() {

       if (AppUtill.checkNetworkConnection(LoginActivity.this)){

           String email, password;
           email = mEtemail.getText().toString();
           password = mEtpassword.getText().toString();

           if (TextUtils.isEmpty(email)) {
               final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
               AppUtill.showCustomStandardAlert(dialog,
                       LoginActivity.this,
                       getResources().getString(R.string.alert_warning_text),
                       getResources().getString(R.string.fill_email_fields_message),
                       getResources().getDrawable(R.drawable.icons8_error),
                       null,
                       getResources().getString(R.string.ok_text), false);
               return;
           }
           if (TextUtils.isEmpty(password)) {
               final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
               AppUtill.showCustomStandardAlert(dialog,
                       LoginActivity.this,
                       getResources().getString(R.string.alert_warning_text),
                       getResources().getString(R.string.fill_password_fields_message),
                       getResources().getDrawable(R.drawable.icons8_error),
                       null,
                       getResources().getString(R.string.ok_text), false);
               return;
           }

           if (mEtemail.getText().toString().equals("admin") && mEtpassword.getText().toString().equals("admin")) {

               Intent intent = new Intent(LoginActivity.this,DriverMapActivity.class);
               startActivity(intent);
           }

           mAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(LoginActivity.this,"--------------------"+task,Toast.LENGTH_SHORT).show();
                           if (task.isSuccessful()) {
                               Toast.makeText(LoginActivity.this, "Successfully Login", Toast.LENGTH_SHORT).show();

                               Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                               startActivity(intent);
                           } else {

                           }
                       }
                   });

       } else {
           final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
           AppUtill.showCustomConfirmAlert(dialog,
                   LoginActivity.this,
                   getResources().getString(R.string.no_internet_connection_text),
                   getResources().getString(R.string.turn_on_internet_connection_text),
                   new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           dialog.dismiss();
                           AppUtill.showSystemSettingsDialog(LoginActivity.this);
                       }
                   },
                   null,
                   getResources().getString(R.string.settings_text),
                   getResources().getString(R.string.cancel_text),
                   false);
       }


    }
    public void initView() {
        mTvForgetPassword = findViewById(R.id.activity_login_tv_forgetPassword);
        mEtemail = findViewById(R.id.activity_login_et_username);
        mEtpassword = findViewById(R.id.activity_login_et_password);
        mTvRegister = findViewById(R.id.activity_login_tv_register);
        btnLogin = findViewById(R.id.activity_login_btn_login);
        mTvRegister.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    public void moveRegisterActivity() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void moveForgetPasswordActivity() {
        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.activity_login_tv_register:
                moveRegisterActivity();
                break;
            case R.id.activity_login_tv_forgetPassword:
                moveForgetPasswordActivity();
                break;
            case R.id.activity_login_btn_login:
                userLogin();
                break;
            default:
                break;
        }
    }
}
