package com.nibm.rwp.gms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);

        setToolbar(getResources().getString(R.string.activity_customer_request), DriverMapActivity.this);

        recyclerView = findViewById(R.id.activity_driver_map_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setDriverMap();
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
