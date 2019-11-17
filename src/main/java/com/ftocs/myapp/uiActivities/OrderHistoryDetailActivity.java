package com.ftocs.myapp.uiActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.DataSizeItem;
import com.ftocs.myapp.dataModels.ListProductItem;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.dataModels.OrderListHistoryItem;
import com.ftocs.myapp.dataModels.SizeDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryDetailActivity extends AppCompatActivity {
    public TextView tv_appBar_title;
    OrderListHistoryItem listItem;
    TextView spinner_city;

    TextView tv_price;
    LinearLayout linear_without_dundee;
    LinearLayout linear_dundee;
    TextView btn_submit;
    TextView et_qty;


    SharedPreferences prefLogIn;
    String userId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_appBar_title = findViewById(R.id.tv_appBar_title);
        tv_price = findViewById(R.id.tv_price);
        spinner_city = findViewById(R.id.spinner_city);
        et_qty = findViewById(R.id.et_qty);
        linear_without_dundee = findViewById(R.id.linear_without_dundee);
        linear_dundee = findViewById(R.id.linear_dundee);



        tv_appBar_title.setText("Product Detail");
        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        userId = prefLogIn.getString(Constants.PREF_USER_ID_KEY, Constants.NULL);


        listItem = (OrderListHistoryItem) getIntent().getSerializableExtra(Constants.ORDERINFO);

        if (listItem!=null) {

            et_qty.setText(listItem.getQuantity()+"");
            tv_price.setText(listItem.getPrice()+"");
            spinner_city.setText(listItem.getSize()+"");


            if (listItem.isHasDandi()) {
                linear_without_dundee.setVisibility(View.VISIBLE);
                linear_dundee.setVisibility(View.VISIBLE);
            } else {
                linear_without_dundee.setVisibility(View.GONE);
                linear_dundee.setVisibility(View.GONE);
            }

            if (listItem.isDandi()) {
                linear_dundee.setBackgroundResource(R.drawable.bg_border);
                linear_without_dundee.setBackgroundResource(0);


            } else {
                linear_without_dundee.setBackgroundResource(R.drawable.bg_border);
                linear_dundee.setBackgroundResource(0);
            }

        }



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
