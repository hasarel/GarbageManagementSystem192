package com.nibm.rwp.gms.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonElement;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.CustomerData;
import com.nibm.rwp.gms.interfaces.EndPoints;
import com.nibm.rwp.gms.utill.AppUtill;
import com.nibm.rwp.gms.utill.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    //Constant
    public static final String TAG = RegisterActivity.class.getSimpleName();

    //Ui components
    private EditText mEtFname, mEtLname, mEtAddress1, mEtAddress2, mEtAddress3, mEtContact, mEtEmail, mEtPassword, mEtNic;
    private Button mBtnRegister;
    private ProgressDialog progressDialog;
    private TextInputLayout fname,lname,address1,nic,contact,email,password;

    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setToolbar(getResources().getString(R.string.activity_register), RegisterActivity.this);

        progressDialog = new ProgressDialog(this);

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

    public void initView() {
        mBtnRegister = findViewById(R.id.activity_register_btn_submit);
        mEtFname = findViewById(R.id.activity_Register_et_fname);
        mEtLname = findViewById(R.id.activity_Register_et_lname);
        mEtAddress1 = findViewById(R.id.activity_Register_et_address1);
        mEtAddress2 = findViewById(R.id.activity_Register_et_address2);
        mEtAddress3 = findViewById(R.id.activity_Register_et_address3);
        mEtContact = findViewById(R.id.activity_Register_et_contactNo);
        mEtEmail = findViewById(R.id.activity_Register_et_email);
        mEtNic = findViewById(R.id.activity_Register_et_nic);
        mEtPassword = findViewById(R.id.activity_Register_et_password);
        mBtnRegister.setOnClickListener(this);

        fname = findViewById(R.id.activity_login_til_fname);
        fname.setHint(fname.getHint()+" "+getString(R.string.asteriskred));
        lname = findViewById(R.id.activity_register_til_lname);
        lname.setHint(lname.getHint()+" "+getString(R.string.asteriskred));
        address1 = findViewById(R.id.activity_register_til_address1);
        address1.setHint(address1.getHint()+" "+getString(R.string.asteriskred));
        nic = findViewById(R.id.activity_register_til_nic);
        nic.setHint(nic.getHint()+" "+getString(R.string.asteriskred));
        contact = findViewById(R.id.activity_register_til_contactNo);
        contact.setHint(contact.getHint()+" "+getString(R.string.asteriskred));
        email = findViewById(R.id.activity_register_til_email);
        email.setHint(email.getHint()+" "+getString(R.string.asteriskred));
        password = findViewById(R.id.activity_register_til_password);
        password.setHint(password.getHint()+" "+getString(R.string.asteriskred));


//        textInputLayout = findViewById(R.id.activity_login_til_username);
//        textInputLayout.setHint(textInputLayout.getHint()+" "+getString(R.string.asteriskred));
    }

    public void passDataToAddRequest() {
        String fname = mEtFname.getText().toString();
        String address1 = mEtAddress1.getText().toString();
        String address2 = mEtAddress2.getText().toString();
        String address3 = mEtAddress3.getText().toString();
        String contact = mEtContact.getText().toString();
        String email = mEtEmail.getText().toString();
        String password = mEtPassword.getText().toString();

        SharedPreferences prf = getSharedPreferences("details", MODE_PRIVATE);
        SharedPreferences.Editor editor = prf.edit();
        editor.putString("fname", fname);
        editor.putString("address1", address1);
        editor.putString("address2", address2);
        editor.putString("address3", address3);
        editor.putString("contact", contact);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.commit();
    }

    public void userRegister() {

        String email, password,fname,lname,address1,address2,address3,nic,contact;

        email = mEtEmail.getText().toString();
        password = mEtPassword.getText().toString();
        fname = mEtFname.getText().toString();
        lname = mEtLname.getText().toString();
        address1 = mEtAddress1.getText().toString();
        address2 = mEtAddress2.getText().toString();
        address3 = mEtAddress3.getText().toString();
        nic = mEtNic.getText().toString();
        contact = mEtContact.getText().toString();



        if (TextUtils.isEmpty(contact)) {
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

        if (TextUtils.isEmpty(nic)) {
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

        if (TextUtils.isEmpty(address3)) {
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

        if (TextUtils.isEmpty(address2)) {
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

        if (TextUtils.isEmpty(address1)) {
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

        if (TextUtils.isEmpty(lname)) {
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

        if (TextUtils.isEmpty(fname)) {
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

        showProgressDialogWithTitle("Now Register is Starting!!!!");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            hideProgressDialogWithTitle();
                            sendEmailVerificationMessage();
                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                            startActivity(intent);

                        }
                        else

                            Toast.makeText(RegisterActivity.this, "This Email Alredy Registered..!!! ", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendEmailVerificationMessage(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!= null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){

                        Toast.makeText(RegisterActivity.this,"Please check your inbox",Toast.LENGTH_LONG).show();

                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(RegisterActivity.this,"This Email Alredy Registered..!!!",Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }

    private void sendCustomerReq(){
        try {
            showProgressDialogWithTitle("Uploading....");
            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<JsonElement> call = service.setCustomerData(getCustomerData());
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {

                    if (response.code()==200){

                        if (response.body()!=null){

                            hideProgressDialogWithTitle();
                            Toast.makeText(RegisterActivity.this,"Successfully..... ",Toast.LENGTH_LONG).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                    hideProgressDialogWithTitle();

                    String error = t.getMessage();
                    Toast.makeText(RegisterActivity.this,"onFailure  " +error,Toast.LENGTH_LONG).show();
                    //  requestActivityErrorDialog();
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "Exception");
        }
    }

    private CustomerData getCustomerData(){
        CustomerData customerData = new CustomerData();
        customerData.setFirst_name(mEtFname.getText().toString());
        customerData.setLast_name(mEtLname.getText().toString());
        customerData.setAddress_line_1(mEtAddress1.getText().toString());
        customerData.setAddress_line_2(mEtAddress2.getText().toString());
        customerData.setAddress_line_3(mEtAddress3.getText().toString());
        customerData.setNic(mEtNic.getText().toString());
        customerData.setTelephone_no(mEtContact.getText().toString());
        customerData.setEmail(mEtEmail.getText().toString());

        return customerData;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_register_btn_submit:
               sendCustomerReq();
                userRegister();
                passDataToAddRequest();
                break;
        }
    }
}
