package com.ftocs.myapp.uiActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.AboutUsDm;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentInfoActivity extends AppCompatActivity {

    public  TextView tv_appBar_title;
    public  TextView tv_about_us;
    private ApiInterface apiInterface;

    private Call<AboutUsDm> aboutUsActivityCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_appBar_title = findViewById(R.id.tv_appBar_title);
        tv_about_us = findViewById(R.id.tv_about_us);
        tv_appBar_title.setText("PAYMENT INFO");
        loadAboutUs();

    }

    public void loadAboutUs() {


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        aboutUsActivityCall = apiInterface.getPaymentInfo();
        aboutUsActivityCall.enqueue(new Callback<AboutUsDm>() {
            @Override
            public void onResponse(Call<AboutUsDm> call, Response<AboutUsDm> response) {

                AboutUsDm aboutUsDm = response.body();

                tv_about_us.setText(Html.fromHtml(aboutUsDm.getDescriptions()).toString());

            }

            @Override
            public void onFailure(Call<AboutUsDm> call, Throwable t) {

            }
        });
    }

}
