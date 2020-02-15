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
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.CustomerRequest;
import com.nibm.rwp.gms.dto.PaymentHistory;
import com.nibm.rwp.gms.interfaces.EndPoints;
import com.nibm.rwp.gms.utill.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHistoryActivity extends BaseActivity {

    public static final String TAG = PaymentHistoryActivity.class.getSimpleName();


    private RecyclerView recyclerView;
    private ArrayList<PaymentHistory> carModels=new ArrayList<>();
    private PaymentHistoryAdapter paymentHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        setToolbar(getResources().getString(R.string.activity_paymenthistoery), PaymentHistoryActivity.this);

        recyclerView = findViewById(R.id.activity_paymentHistory_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setDriverMap();
    }

    private void setDriverMap(){

//        SharedPreferences prf = getSharedPreferences("details", MODE_PRIVATE);
//        String email = prf.getString("email", "");

        EndPoints service = RetrofitClient.getRetrofitInstance().create(EndPoints.class);
        Call<List<PaymentHistory>> call = service.getPaymentHistory();
        call.enqueue(new Callback<List<PaymentHistory>>() {
            @Override
            public void onResponse(Call<List<PaymentHistory>> call, Response<List<PaymentHistory>> response) {

                carModels = new ArrayList<>(response.body());
                paymentHistoryAdapter = new PaymentHistoryAdapter(PaymentHistoryActivity.this,carModels);
                recyclerView.setAdapter(paymentHistoryAdapter);
            }

            @Override
            public void onFailure(Call<List<PaymentHistory>> call, Throwable t) {
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
