package com.nibm.rwp.gms.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.adapter.CustomerRequestAdapter;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.CustomerRequest;

import java.util.ArrayList;
import java.util.List;

public class DriverMapActivity extends BaseActivity {

    //a list to store all the products
    List<CustomerRequest> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_map);

        setToolbar(getResources().getString(R.string.activity_customer_request), DriverMapActivity.this);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.activity_driver_map_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //adding some items to our list
        productList.add(new CustomerRequest("Hasarel ", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        productList.add(new CustomerRequest("Hasarel Madola", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        productList.add(new CustomerRequest("Hasarel Madola", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        productList.add(new CustomerRequest("Hasarel Madola", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        productList.add(new CustomerRequest("Hasarel Madola", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        productList.add(new CustomerRequest("Hasarel Madola", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        productList.add(new CustomerRequest("Hasarel Madola", "29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka", "0713017537"));
        CustomerRequestAdapter adapter = new CustomerRequestAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
