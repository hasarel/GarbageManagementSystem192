package com.nibm.rwp.gms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.GarbageCategoryList;
import com.nibm.rwp.gms.dto.GarbageRequest;
import com.nibm.rwp.gms.dto.UcArea;
import com.nibm.rwp.gms.dto.UcVehicleList;
import com.nibm.rwp.gms.interfaces.EndPoints;
import com.nibm.rwp.gms.listeners.OnCatClickListener;
import com.nibm.rwp.gms.utill.AppUtill;
import com.nibm.rwp.gms.utill.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRequestActivity extends BaseActivity implements View.OnClickListener, OnCatClickListener<GarbageCategoryList> {

    //Constant
    public static final String TAG = AddRequestActivity.class.getSimpleName();

    //Ui Components
    private Button mBtnProgress;
    private EditText mEtDescription, mEtLocationLongTiute, mEtName, mEtContactNo, mEtAddress1, mEtAddress2, mEtAddress3, mEtLocationLatitute, mEtEmail;
    private ProgressBar mPbProgreassBar;

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;

    private List<GarbageCategoryList> userList = null;
    private RecyclerView mCatList;
    private Spinner catSpinner;

    private List<UcArea> ucAreaList = null;
    private RecyclerView mUcList;
    private Spinner mSpUcArea;

    private List<UcVehicleList> ucVehicleLists = null;
    private RecyclerView mUcVehicleRecycleView;
    private Spinner mSpUcVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_request);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLastLocation();

        setToolbar(getResources().getString(R.string.activity_addrequest), AddRequestActivity.this);
        initView();

        getGarbageCategoryList();
        getUcAreaList();
        getUcVehicleList();
        sharedPreferences();
    }

    private void activity() {
        Intent intent = new Intent(AddRequestActivity.this, AddPaymentActivity.class);
        startActivity(intent);
    }

    private void confirmDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(AddRequestActivity.this).create();
        AppUtill.showCustomConfirmAlert(dialog,
                AddRequestActivity.this,
                getResources().getString(R.string.confim_request),
                getResources().getString(R.string.cancel_request),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        activity();
                    }
                },
                null,
                getResources().getString(R.string.yes_text),
                getResources().getString(R.string.no_text),
                false);
    }

    private void sharedPreferences() {
        SharedPreferences prf = getSharedPreferences("details", MODE_PRIVATE);
        String fname = prf.getString("fname", "no value");
        String address1 = prf.getString("address1", "no value");
        String address2 = prf.getString("address2", "no value");
        String address3 = prf.getString("address3", "no value");
        String contact = prf.getString("contact", "no values");
        String email = prf.getString("email", "no values");
        mEtName.setText(fname);
        mEtAddress1.setText(address1);
        mEtAddress2.setText(address2);
        mEtAddress3.setText(address3);
        mEtContactNo.setText(contact);
        mEtEmail.setText(email);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    mEtLocationLatitute.setText(location.getLatitude() + "");
                                    mEtLocationLongTiute.setText(location.getLongitude() + "");
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            mEtLocationLatitute.setText(mLastLocation.getLatitude() + "");
            mEtLocationLongTiute.setText(mLastLocation.getLongitude() + "");

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView() {
        mEtDescription = findViewById(R.id.activity_add_request_et_description);
        mEtLocationLongTiute = findViewById(R.id.activity_add_request_et_location_logtitte);
        mEtLocationLongTiute.setKeyListener(null);
        mEtLocationLatitute = findViewById(R.id.activity_add_request_et_location_latitiute);
        mEtLocationLatitute.setKeyListener(null);
        mEtName = findViewById(R.id.activity_add_request_et_name);
        mEtContactNo = findViewById(R.id.activity_add_request_et_contact);
        mBtnProgress = findViewById(R.id.activity_add_request_btn_progress);
        mPbProgreassBar = findViewById(R.id.activity_add_request_pb_progress);
        mEtAddress1 = findViewById(R.id.activity_add_request_et_address1);
        mEtAddress2 = findViewById(R.id.activity_add_request_et_address2);
        mEtAddress3 = findViewById(R.id.activity_add_request_et_address3);
        mEtEmail = findViewById(R.id.activity_add_request_et_email);
        mBtnProgress.setOnClickListener(this);
        mCatList = findViewById(R.id.activity_add_request_cat_list);
        catSpinner = findViewById(R.id.activity_add_request_sp_category);
        mSpUcArea = findViewById(R.id.activity_add_request_sp_area);
        mUcList = findViewById(R.id.activity_add_request_area_cat_list);
        mUcVehicleRecycleView = findViewById(R.id.activity_add_request_garbage_vehicle_list);
        mSpUcVehicle = findViewById(R.id.activity_add_request_sp_garbage_vehicle_category);

        getGarbageCategoryList();
        getUcAreaList();
        getUcVehicleList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_add_request_btn_progress:
                setCustomerRequest();
                confirmDialog();
                break;
        }
    }

    private void getUcAreaList() {
        Log.i(TAG, "getUserList");
        try {

            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<UcArea>> call = service.getAllUcArea();
            call.enqueue(new Callback<List<UcArea>>() {
                @Override
                public void onResponse(Call<List<UcArea>> call, Response<List<UcArea>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i(TAG, "onResponse");

                    ucAreaList = response.body();
                    Log.i(TAG, "---" + ucAreaList.get(0).getName());

                    if (ucAreaList != null)
                        setUcAreaRecycleView(ucAreaList);
                }

                @Override
                public void onFailure(Call<List<UcArea>> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "Exception");
        }
    }

    private void setUcAreaRecycleView(List<UcArea> ucAreas) {

        if (ucAreas.size() > 0) {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < ucAreas.size(); i++) {
                list.add(ucAreas.get(i).getName());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpUcArea.setAdapter(dataAdapter);

        } else {
            Toast.makeText(AddRequestActivity.this, "No any suggestions found", Toast.LENGTH_SHORT).show();

        }
    }

    private void getGarbageCategoryList() {

        Log.i(TAG, "getUserList");
        try {

            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<GarbageCategoryList>> call = service.getGarbageList();
            call.enqueue(new Callback<List<GarbageCategoryList>>() {
                @Override
                public void onResponse(Call<List<GarbageCategoryList>> call, Response<List<GarbageCategoryList>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i(TAG, "onResponse");

                    userList = response.body();
                    Log.i(TAG, "---" + userList.get(0).getName());

                    if (userList != null)
                        setupCatRecyclerView(userList);
                }

                @Override
                public void onFailure(Call<List<GarbageCategoryList>> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "Exception");
        }
    }

    private void setupCatRecyclerView(List<GarbageCategoryList> caseList) {
        if (caseList.size() > 0) {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < caseList.size(); i++) {
                list.add(caseList.get(i).getName());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catSpinner.setAdapter(dataAdapter);

        } else {
            Toast.makeText(AddRequestActivity.this, "No any suggestions found", Toast.LENGTH_SHORT).show();

        }
    }

    private void getUcVehicleList() {

        Log.i(TAG, "getUserList");
        try {

            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<UcVehicleList>> call = service.getUcVehicle();
            call.enqueue(new Callback<List<UcVehicleList>>() {
                @Override
                public void onResponse(Call<List<UcVehicleList>> call, Response<List<UcVehicleList>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i(TAG, "onResponse");

                    ucVehicleLists = response.body();
                    Log.i(TAG, "---" + ucVehicleLists.get(0).getType_code());

                    if (ucVehicleLists != null)
                        setUcVehicleListRecyclerView(ucVehicleLists);
                }

                @Override
                public void onFailure(Call<List<UcVehicleList>> call, Throwable t) {
                    Log.i(TAG, t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.i(TAG, "Exception");
        }
    }

    private void setUcVehicleListRecyclerView(List<UcVehicleList> vehicleLists) {
        if (vehicleLists.size() > 0) {
            List<String> list = new ArrayList<>();

            for (int i = 0; i < vehicleLists.size(); i++) {
                list.add(vehicleLists.get(i).getType_code());
            }

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpUcVehicle.setAdapter(dataAdapter);

        } else {
            Toast.makeText(AddRequestActivity.this, "No any suggestions found", Toast.LENGTH_SHORT).show();

        }
    }

    private void setCustomerRequest() {

        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<GarbageRequest> call = service.setCustomerRequest(
                mEtName.getText().toString(),
                mEtEmail.getText().toString(),
                mSpUcArea.getSelectedItem().toString(),
                mEtLocationLongTiute.getText().toString(),
                mEtLocationLatitute.getText().toString(),
                mEtAddress1.getText().toString(),
                mEtAddress2.getText().toString(),
                mEtAddress3.getText().toString(),
                mEtContactNo.getText().toString(),
                mEtDescription.getText().toString(),
                catSpinner.getSelectedItem().toString());

//                Log.i("VALUES",mEtName.getText().toString());
//                Log.i("VALUES",mEtEmail.getText().toString());
//                Log.i("VALUES",mSpUcArea.getSelectedItem().toString());
//                Log.i("VALUES",mEtLocationLongTiute.getText().toString());
//                Log.i("VALUES",mEtLocationLatitute.getText().toString());
//                Log.i("VALUES",mEtAddress1.getText().toString());
//                Log.i("VALUES",mEtAddress2.getText().toString());
//                Log.i("VALUES",mEtAddress3.getText().toString());
//                Log.i("VALUES",mEtContactNo.getText().toString());
//                Log.i("VALUES",mEtDescription.getText().toString());
//                Log.i("VALUES",catSpinner.getSelectedItem().toString());


        call.enqueue(new Callback<GarbageRequest>() {
            @Override
            public void onResponse(Call<GarbageRequest> call, Response<GarbageRequest> response) {
                if (response.isSuccessful()) {
                    if (response.code() == 200) {
                        GarbageRequest myResponse = response.body();
                        if (myResponse != null) {
                            Toast.makeText(AddRequestActivity.this, "Successfully Executed", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<GarbageRequest> call, Throwable t) {
                Log.i(TAG, t.toString());
                Toast.makeText(AddRequestActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void OnCatClick(int position, GarbageCategoryList data) {

    }
}
