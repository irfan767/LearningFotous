package com.ftocs.myapp.uiActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.adapters.OrderListAdap;
import com.ftocs.myapp.dataModels.ListItem;
import com.ftocs.myapp.dataModels.OrderHistoryDM;
import com.ftocs.myapp.dataModels.OrderListHistoryItem;
import com.ftocs.myapp.itemDecorator.GridSpacingItemDecoration;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
import com.ftocs.myapp.utils.RecyclerClickListener;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity implements RecyclerClickListener.OnRecyclerClickListener {

    private ProgressBar progressBar;
    private ApiInterface apiInterface;
    private RecyclerView recycler_view_more;

    private OrderListAdap cityListAdap;

    private Call<OrderHistoryDM> categoryDMCall;

    private RecyclerView.LayoutManager layoutManager;

    List<OrderListHistoryItem> todayRatesDMS;
    TextView tv_title;
    ImageView img_back;
    OrderHistoryDM categoryDM;

    SharedPreferences prefLogIn;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar = findViewById(R.id.progressBar);
        recycler_view_more =findViewById(R.id.recy_category);
        tv_title =findViewById(R.id.tv_title);
        img_back = findViewById(R.id.img_back);

        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        userId =prefLogIn.getString(Constants.PREF_USER_ID_KEY,Constants.NULL);

        recycler_view_more.addOnItemTouchListener(new RecyclerClickListener(this, recycler_view_more, this));

        getProductCategories(Integer.valueOf(userId));

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }

        });

    }
    public void getProductCategories(int id) {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        categoryDMCall = apiInterface.getOrders(id);
        categoryDMCall.enqueue(new Callback<OrderHistoryDM>() {
            @Override
            public void onResponse(Call<OrderHistoryDM> call, Response<OrderHistoryDM> response) {
                if (response.isSuccessful()) {

                     categoryDM = response.body();
                    setDataToViews(categoryDM.getList());
                    progressBar.setVisibility(View.GONE);
                } else {
                    Toast.makeText(OrderHistoryActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<OrderHistoryDM> call, Throwable t) {
                Toast.makeText(OrderHistoryActivity.this, "Network Problem", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void setDataToViews(List<ListItem> todayRatesDMS) {


        cityListAdap = new OrderListAdap(OrderHistoryActivity.this, todayRatesDMS);
        layoutManager = new LinearLayoutManager(OrderHistoryActivity.this, LinearLayoutManager.VERTICAL,false);
        recycler_view_more.setLayoutManager(layoutManager);
        recycler_view_more.setAdapter(cityListAdap);

      /*  GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 5, true);
        recycler_view_more.setItemAnimator(new DefaultItemAnimator());
        recycler_view_more.addItemDecoration(gridSpacingItemDecoration);
        recycler_view_more.setHasFixedSize(true);*/


    }

    @Override
    public void onItemClick(View view, int position) {

        Intent intent = new Intent(OrderHistoryActivity.this, OrderHistoryDetailsActivitynew.class);
        intent.putExtra(Constants.ORDERINFO, (Serializable) categoryDM);
        intent.putExtra(Constants.position, position);
        startActivity(intent);

    }
    @Override
    public void onLongClick(View view, int position) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (categoryDMCall!=null)
            categoryDMCall.cancel();
    }
}
