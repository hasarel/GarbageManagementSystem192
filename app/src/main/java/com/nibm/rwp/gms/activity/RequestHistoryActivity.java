package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.adapter.PaymentHistoryAdapter;
import com.nibm.rwp.gms.adapter.RequestHistoryAdapter;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.PaymentHistory;
import com.nibm.rwp.gms.dto.RequestHistory;

import java.util.ArrayList;
import java.util.List;

public class RequestHistoryActivity extends BaseActivity {

    //a list to store all the products
    List<RequestHistory> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_history);

        setToolbar(getResources().getString(R.string.activity_request_history), RequestHistoryActivity.this);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.activity_requesttHistory_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(new RequestHistory("Hasarel ","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new RequestHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new RequestHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new RequestHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new RequestHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new RequestHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new RequestHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        RequestHistoryAdapter adapter1 = new RequestHistoryAdapter(this, productList);
        recyclerView.setAdapter(adapter1);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
