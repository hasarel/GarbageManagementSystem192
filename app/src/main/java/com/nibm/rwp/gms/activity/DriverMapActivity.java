package com.nibm.rwp.gms.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.adapter.CustomerRequestAdapter;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.CustomerRequest;
import com.nibm.rwp.gms.dto.GarbageCategoryList;
import com.nibm.rwp.gms.dto.UcArea;
import com.nibm.rwp.gms.interfaces.EndPoints;
import com.nibm.rwp.gms.listeners.OnItemClickListener;
import com.nibm.rwp.gms.utill.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DriverMapActivity extends BaseActivity {

    public static final String TAG = DriverMapActivity.class.getSimpleName();


    private RecyclerView recyclerView;
    private ArrayList<CustomerRequest> carModels=new ArrayList<>();
    private CustomerRequestAdapter customerRequestAdapter;

    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);

        setToolbar(getResources().getString(R.string.activity_customer_request), DriverMapActivity.this);

        recyclerView = findViewById(R.id.activity_driver_map_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setDriverMap();
        getLastLocation();

    }

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

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                Toast.makeText(DriverMapActivity.this,"Location is Gradated...",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    private void setDriverMap(){

        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<List<CustomerRequest>> call = service.getDriverMap();
        call.enqueue(new Callback<List<CustomerRequest>>() {
            @Override
            public void onResponse(Call<List<CustomerRequest>> call, Response<List<CustomerRequest>> response) {

                carModels = new ArrayList<>(response.body());
                customerRequestAdapter = new CustomerRequestAdapter(DriverMapActivity.this,carModels);
                recyclerView.setAdapter(customerRequestAdapter);
            }

            @Override
            public void onFailure(Call<List<CustomerRequest>> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
