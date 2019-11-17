package com.ftocs.myapp.uiActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.ListItem;
import com.ftocs.myapp.dataModels.ListProductItem;
import com.ftocs.myapp.dataModels.ProductInfoDM;
import com.ftocs.myapp.dataModels.ProductInfoDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailInfoActivity extends AppCompatActivity {

    SliderLayout slider_layout;
    HashMap<Integer, String> HashMapForURL = new HashMap<>();
    private ProgressBar progressBar;
    private ApiInterface apiInterface;
    private TextView tv_description;
    private Button btn_buy;

    private Call<ProductInfoDM> categoryDMCall;
    ListProductItem listItem;

    public  TextView tv_appBar_title;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        HashMapForURL = new HashMap<Integer, String>();

        progressBar = findViewById(R.id.progressBar);
        tv_description = findViewById(R.id.tv_description);
        slider_layout= (SliderLayout) findViewById(R.id.slider_layout);
        btn_buy = findViewById(R.id.btn_buy);

        tv_appBar_title = findViewById(R.id.tv_appBar_title);

        tv_appBar_title.setText("Product Detail");



        listItem = (ListProductItem) getIntent().getSerializableExtra(Constants.PRODUCTINFO);

        getProductInfo(listItem.getProductID());



        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetailInfoActivity.this, BookingActivity.class);
                intent.putExtra(Constants.PRODUCTINFO,listItem);
                startActivity(intent);
            }

        });

    }

    public void getProductInfo(int id) {
        progressBar.setVisibility(View.VISIBLE);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        categoryDMCall = apiInterface.getProductInfo(id);
        categoryDMCall.enqueue(new Callback<ProductInfoDM>() {
            @Override
            public void onResponse(Call<ProductInfoDM> call, Response<ProductInfoDM> response) {
                if (response.isSuccessful()) {
                    ProductInfoDM productInfoDM = response.body();
                    if (productInfoDM.getData().getDetail()!=null && !productInfoDM.getData().getDetail().isEmpty() ) {

                        tv_description.setText(Html.fromHtml(productInfoDM.getData().getDetail()).toString());
                    }

                    for (int i = 0; i < productInfoDM.getData().getProductPhotos().size(); i++) {
                        HashMapForURL.put(i, productInfoDM.getData().getProductPhotos().get(i).getPath());


                    }
                    setDataToSlider();

                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(ProductDetailInfoActivity.this, "No Found", Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<ProductInfoDM> call, Throwable t) {
                Toast.makeText(ProductDetailInfoActivity.this, "Network Problem", Toast.LENGTH_LONG).show();

                progressBar.setVisibility(View.GONE);

            }
        });
    }

    private void setDataToSlider() {
        for (Integer name : HashMapForURL.keySet()) {

            DefaultSliderView textSliderView = new DefaultSliderView(ProductDetailInfoActivity.this);

            textSliderView
                    .image(HashMapForURL.get(name));

            textSliderView.bundle(new Bundle());



            slider_layout.addSlider(textSliderView);
            slider_layout.stopAutoCycle();

        }


        slider_layout.setDuration(12000);

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                onBackPressed();
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
