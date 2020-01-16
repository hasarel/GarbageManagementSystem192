package com.nibm.rwp.gms.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nibm.rwp.gms.R;
import com.nibm.rwp.gms.adapter.PaymentHistoryAdapter;
import com.nibm.rwp.gms.common.BaseActivity;
import com.nibm.rwp.gms.dto.PaymentHistory;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryActivity extends BaseActivity {

    //a list to store all the products
    List<PaymentHistory> productList;

    //the recyclerview
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        setToolbar(getResources().getString(R.string.activity_paymenthistoery), PaymentHistoryActivity.this);

        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.activity_paymentHistory_rl_recycleView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        productList.add(new PaymentHistory("Hasarel Madola","29/2,Sarwodaya mw, Madola, Avissawella,Sri Lanka","0713017537"));
        PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(this, productList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
