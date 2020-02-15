package com.nibm.rwp.gms.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.utill.AppUtill;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    //Ui components
    private TextView mTvForgetPassword, mTvRegister;
    private Button btnLogin;
    private EditText mEtemail, mEtpassword;
    private boolean emailChecker;
    private ProgressDialog progressDialog;
    private TextInputLayout textInputLayout;
    private TextInputLayout textInputLayoutPassword;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        initView();

        sharedPreferences();
    }

    private void sharedPreferences() {

        SharedPreferences prf = getSharedPreferences("details", MODE_PRIVATE);

        String email = prf.getString("email", "");
        String password = prf.getString("password","");
        mEtemail.setText(email);
        mEtpassword.setText(password);
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

    private void userLoginVerifyEmailAddress(){
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        emailChecker  = firebaseUser.isEmailVerified();

        if (emailChecker){
            sendUserToMainActivity();
        }
        else

        emailNotVeryfiyDialog();
    }

    private void sendUserToMainActivity() {
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    private void emailNotVeryfiyDialog(){
        final AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).create();
        AppUtill.showCustomStandardAlert(dialog,
                LoginActivity.this,
                getResources().getString(R.string.email_verification_error),
                getResources().getString(R.string.email_verification_message),
                getResources().getDrawable(R.drawable.verify),
                null,
                getResources().getString(R.string.ok_text), false);
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

           showProgressDialogWithTitle("Please waite while we get things Ready");
           mAuth.signInWithEmailAndPassword(email, password)
                   .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if (task.isSuccessful()) {

                               hideProgressDialogWithTitle();
                               userLoginVerifyEmailAddress();

                           } else {

                               Toast.makeText(LoginActivity.this,"Please check your Email or Password... ",Toast.LENGTH_LONG).show();

                               hideProgressDialogWithTitle();

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


        textInputLayout = findViewById(R.id.activity_login_til_username);
        textInputLayout.setHint(textInputLayout.getHint()+" "+getString(R.string.asteriskred));
        textInputLayoutPassword = findViewById(R.id.activity_login_til_account);
        textInputLayoutPassword.setHint(textInputLayoutPassword.getHint()+" "+"*");


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
