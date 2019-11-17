package com.ftocs.myapp.uiActivities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.ftocs.myapp.R;
import com.ftocs.myapp.adapters.BookingsAdapter;
import com.ftocs.myapp.adapters.OrderHistoryAdapter;
import com.ftocs.myapp.dataModels.ListItem;
import com.ftocs.myapp.dataModels.OrderHistoryDM;
import com.ftocs.myapp.dataModels.OrderListHistoryItem;
import com.ftocs.myapp.utils.Constants;

public class OrderHistoryDetailsActivitynew extends AppCompatActivity {
    RecyclerView rc_products;
    OrderHistoryAdapter orderHistoryAdapter;
    LinearLayoutManager layoutManager;
    SharedPreferences prefLogIn;
    OrderHistoryDM listItem;
    String userId;
    int position;
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history_details_activitynew);
        rc_products = findViewById(R.id.rc_products);
        img_back=findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        listItem = (OrderHistoryDM) getIntent().getSerializableExtra(Constants.ORDERINFO);
        position =getIntent().getIntExtra(Constants.position,0);
        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        userId = prefLogIn.getString(Constants.PREF_USER_ID_KEY, Constants.NULL);
        if (listItem!=null){
            orderHistoryAdapter = new OrderHistoryAdapter(OrderHistoryDetailsActivitynew.this,listItem.getList().get(position).getListOrderProducts());
            layoutManager = new LinearLayoutManager(OrderHistoryDetailsActivitynew.this, LinearLayoutManager.VERTICAL, false);
            rc_products.setLayoutManager(layoutManager);
            rc_products.setAdapter(orderHistoryAdapter);
        }

    }
}
