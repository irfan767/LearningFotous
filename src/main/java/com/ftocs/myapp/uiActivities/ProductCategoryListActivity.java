package com.ftocs.myapp.uiActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.adapters.ProductistAdap;
import com.ftocs.myapp.dataModels.ListProductItem;
import com.ftocs.myapp.dataModels.ProductListDM;
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

public class ProductCategoryListActivity extends AppCompatActivity implements RecyclerClickListener.OnRecyclerClickListener {

    private ProgressBar progressBar;
    private ApiInterface apiInterface;
    private RecyclerView recycler_view_more;

    private ProductistAdap cityListAdap;

    private Call<ProductListDM> categoryDMCall;

    private RecyclerView.LayoutManager layoutManager;

    TextView tv_title;
    ListItem listItem;

    List<ListProductItem> todayRatesDMS;
    ImageView img_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar = findViewById(R.id.progressBar);
        recycler_view_more =findViewById(R.id.recy_category);
        tv_title =findViewById(R.id.tv_title);
        listItem = (ListItem) getIntent().getSerializableExtra(Constants.GenericProductItem);
        tv_title.setText("PRODUCT LISTING");
        img_back = findViewById(R.id.img_back);


        recycler_view_more.addOnItemTouchListener(new RecyclerClickListener(this, recycler_view_more, this));

        getProductCategories(listItem.getID());
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
        categoryDMCall = apiInterface.getProductList(id);
        categoryDMCall.enqueue(new Callback<ProductListDM>() {
            @Override
            public void onResponse(Call<ProductListDM> call, Response<ProductListDM> response) {
                if (response.isSuccessful()) {

                    ProductListDM categoryDM = response.body();
                    todayRatesDMS   = categoryDM.getListProduct();

                    setDataToViews(todayRatesDMS);



                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ProductCategoryListActivity.this, "No found", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<ProductListDM> call, Throwable t) {
                Toast.makeText(ProductCategoryListActivity.this, "Network Problem", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void setDataToViews(List<ListProductItem> todayRatesDMS) {


        cityListAdap = new ProductistAdap(ProductCategoryListActivity.this, todayRatesDMS);
        layoutManager = new GridLayoutManager(ProductCategoryListActivity.this, 2);
        recycler_view_more.setLayoutManager(layoutManager);
        recycler_view_more.setAdapter(cityListAdap);

        GridSpacingItemDecoration gridSpacingItemDecoration = new GridSpacingItemDecoration(2, 55, true);
        recycler_view_more.setItemAnimator(new DefaultItemAnimator());
        recycler_view_more.addItemDecoration(gridSpacingItemDecoration);
        recycler_view_more.setHasFixedSize(true);


    }
    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(ProductCategoryListActivity.this,ProductDetailInfoActivity.class);
        intent.putExtra(Constants.PRODUCTINFO,      todayRatesDMS .get(position));
        startActivity(intent);
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
