package com.nibm.rwp.gms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.nibm.rwp.gms.interfaces.EndPoints;
import com.nibm.rwp.gms.listeners.OnCatClickListener;
import com.nibm.rwp.gms.utill.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class AddRequestActivity extends BaseActivity implements View.OnClickListener, OnCatClickListener<GarbageCategoryList> {

    //Constant
    public static final String TAG = AddRequestActivity.class.getSimpleName();

    //Ui Components
    private Button mBtnProgress;
    private EditText mEtDescription, mEtLocationLongTiute, mEtName, mEtContactNo, mEtAddress1, mEtAddress2, mEtAddress3, mEtLocationLatitute;
    private ProgressBar mPbProgreassBar;

    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    TextView latTextView, lonTextView;

    private List<GarbageCategoryList> userList =null;
    private RecyclerView mCatList;
    private Spinner catSpinner;

    private List<UcArea> ucAreaList=null;
    private RecyclerView mUcList;
    private Spinner mSpUcArea;

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
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation(){
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
                                    mEtLocationLatitute.setText(location.getLatitude()+"");
                                    mEtLocationLongTiute.setText(location.getLongitude()+"");
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
    private void requestNewLocationData(){

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
            mEtLocationLatitute.setText(mLastLocation.getLatitude()+"");
            mEtLocationLongTiute.setText(mLastLocation.getLongitude()+"");

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
    public void onResume(){
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
        mBtnProgress.setOnClickListener(this);

        //-----
        mCatList = findViewById(R.id.activity_add_request_cat_list);
        catSpinner = findViewById(R.id.activity_add_request_sp_category);
        mSpUcArea = findViewById(R.id.activity_add_request_sp_area);
        mUcList = findViewById(R.id.activity_add_request_area_cat_list);

        getGarbageCategoryList();
        getUcAreaList();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_add_request_btn_progress:
                break;
        }
    }

    private void getUcAreaList(){
        Log.i("autolog", "getUserList");
        try {

            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<UcArea>> call = service.getAllUcArea();
            call.enqueue(new Callback<List<UcArea>>() {
                @Override
                public void onResponse(Call<List<UcArea>> call, Response<List<UcArea>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i("autolog", "onResponse");

                    ucAreaList = response.body();
                    Log.i("autolog", "---"+ucAreaList.get(0).getName());

                    if(ucAreaList != null)
                        setUcAreaRecycleView(ucAreaList);
                }

                @Override
                public void onFailure(Call<List<UcArea>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}
    }

    private void setUcAreaRecycleView(List<UcArea> ucAreas){

        if (ucAreas.size() > 0) {
            List<String> list = new ArrayList<>();

            for(int i=0; i<ucAreas.size(); i++){
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

        Log.i("autolog", "getUserList");
        try {

            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
            Call<List<GarbageCategoryList>> call = service.getGarbageList();
            call.enqueue(new Callback<List<GarbageCategoryList>>() {
                @Override
                public void onResponse(Call<List<GarbageCategoryList>> call, Response<List<GarbageCategoryList>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i("autolog", "onResponse");

                    userList = response.body();
                    Log.i("autolog", "---"+userList.get(0).getName());

                    if(userList != null)
                        setupCatRecyclerView(userList);
                }

                @Override
                public void onFailure(Call<List<GarbageCategoryList>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}
    }


    private void setupCatRecyclerView(List<GarbageCategoryList> caseList) {
        if (caseList.size() > 0) {
            List<String> list = new ArrayList<>();

            for(int i=0; i<caseList.size(); i++){
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

    @Override
    public void OnCatClick(int position, GarbageCategoryList data) {

    }

//    private void setUserGarbageCategoryList(GarbageRequest garbageRequest){
//        try {
//            GarbageRequest request = new GarbageRequest(userList.get(0).getName(),mEtDescription.getText().toString(),mEtLocationLongTiute.getText().toString(),
//                    mEtLocationLatitute.getText().toString(),mEtName.getText().toString(),ucAreaList.get(0).getName(),mEtAddress1.getText().toString(),mEtAddress2.getText().toString(),
//                    mEtAddress3.getText().toString(),mEtContactNo.getText().toString());
//            EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
//            Call<GarbageRequest> call = service.setCustomerRequest(garbageRequest);
//            call.enqueue(request,new Callback<GarbageRequest>() {
//                @Override
//                public void onResponse(Call<GarbageRequest> call, Response<GarbageRequest> response) {
//                    GarbageRequest responseUser = response.body();
//                    if (response.isSuccessful() && responseUser != null) {
//
//
//                    } else {
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<GarbageRequest> call, Throwable t) {
//                    Log.i("autolog", t.getMessage());
//                }
//            });
//        }catch (Exception r ){
//
//        }
//    }
}
