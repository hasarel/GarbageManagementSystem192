package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.adapter.CustomerRequestAdapter;
import com.nibm.rwp.gms.adapter.PaymentHistoryAdapter;
import com.nibm.rwp.gms.adapter.RequestHistoryAdapter;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.CustomerRequest;
import com.nibm.rwp.gms.dto.PaymentHistory;
import com.nibm.rwp.gms.dto.RequestHistory;
import com.nibm.rwp.gms.interfaces.EndPoints;
import com.nibm.rwp.gms.utill.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestHistoryActivity extends BaseActivity {

    public static final String TAG = RequestHistoryActivity.class.getSimpleName();


    private RecyclerView recyclerView;
    private ArrayList<RequestHistory> carModels=new ArrayList<>();
    private RequestHistoryAdapter requestHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);

        setToolbar(getResources().getString(R.string.activity_request_history), RequestHistoryActivity.this);

        recyclerView = findViewById(R.id.activity_requesttHistory_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setDriverMap();
    }

    private void setDriverMap(){

        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);

        Call<List<RequestHistory>> call = service.getRequestHistory();
        call.enqueue(new Callback<List<RequestHistory>>() {
            @Override
            public void onResponse(Call<List<RequestHistory>> call, Response<List<RequestHistory>> response) {

                carModels = new ArrayList<>(response.body());
                requestHistoryAdapter = new RequestHistoryAdapter(RequestHistoryActivity.this,carModels);
                recyclerView.setAdapter(requestHistoryAdapter);
            }

            @Override
            public void onFailure(Call<List<RequestHistory>> call, Throwable t) {
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
