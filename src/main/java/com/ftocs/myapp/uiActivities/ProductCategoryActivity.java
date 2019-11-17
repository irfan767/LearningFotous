package com.ftocs.myapp.uiActivities;

import android.content.Intent;
import android.os.Bundle;
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
import com.ftocs.myapp.adapters.CityListAdap;
import com.ftocs.myapp.dataModels.CategoryDM;
import com.ftocs.myapp.dataModels.ListItem;
import com.ftocs.myapp.itemDecorator.GridSpacingItemDecoration;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
import com.ftocs.myapp.utils.RecyclerClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCategoryActivity extends AppCompatActivity implements RecyclerClickListener.OnRecyclerClickListener {

    private ProgressBar progressBar;
    private ApiInterface apiInterface;
    private RecyclerView recycler_view_more;

    private CityListAdap cityListAdap;

    private Call<CategoryDM> categoryDMCall;

    private RecyclerView.LayoutManager layoutManager;

    List<ListItem> todayRatesDMS;
    TextView tv_title;
    ImageView img_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar = findViewById(R.id.progressBar);
        recycler_view_more = findViewById(R.id.recy_category);
        tv_title = findViewById(R.id.tv_title);
        img_back = findViewById(R.id.img_back);


        recycler_view_more.addOnItemTouchListener(new RecyclerClickListener(this, recycler_view_more, this));
        tv_title.setText("PRODUCT CATEGORIES");
        getProductCategories();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }

        });
    }

    public void getProductCategories() {

        progressBar.setVisibility(View.VISIBLE);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        categoryDMCall = apiInterface.getProductCategories();
        categoryDMCall.enqueue(new Callback<CategoryDM>() {
            @Override
            public void onResponse(Call<CategoryDM> call, Response<CategoryDM> response) {
                if (response.isSuccessful()) {

                    CategoryDM categoryDM = response.body();
                    todayRatesDMS = categoryDM.getList();

                    setDataToViews(todayRatesDMS);


                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ProductCategoryActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<CategoryDM> call, Throwable t) {
                Toast.makeText(ProductCategoryActivity.this, "Network Problem", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void setDataToViews(List<ListItem> todayRatesDMS) {


        cityListAdap = new CityListAdap(ProductCategoryActivity.this, todayRatesDMS);
        layoutManager = new GridLayoutManager(ProductCategoryActivity.this, 2);
        recycler_view_more.setLayoutManager(layoutManager);
        recycler_view_more.setAdapter(cityListAdap);

        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 5, true);
        recycler_view_more.setItemAnimator(new DefaultItemAnimator());
        recycler_view_more.addItemDecoration(gridSpacingItemDecoration);
        recycler_view_more.setHasFixedSize(true);


    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(ProductCategoryActivity.this, ProductCategoryListActivity.class);
        intent.putExtra(Constants.GenericProductItem, todayRatesDMS.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
