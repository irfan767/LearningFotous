package com.ftocs.myapp.uiActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.ContactUsDm;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactusActivity extends AppCompatActivity implements OnMapReadyCallback {

    public TextView tv_appBar_title;
    public  TextView tv_ContactNo;
    public  TextView tv_Email;
    public  TextView tv_address;
    private ApiInterface apiInterface;

    private Call<ContactUsDm> contactUsDmCall;

    private GoogleMap mMap;

    ContactUsDm contactUsDm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_appBar_title = findViewById(R.id.tv_appBar_title);

        tv_address = findViewById(R.id.tv_address);
        tv_ContactNo = findViewById(R.id.tv_ContactNo);
        tv_Email = findViewById(R.id.tv_Email);

        tv_appBar_title.setText("CONTECT US");

        loadContactUs();


    }

    public void loadContactUs() {


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        contactUsDmCall = apiInterface.getContactUs();
        contactUsDmCall.enqueue(new Callback<ContactUsDm>() {
            @Override
            public void onResponse(Call<ContactUsDm> call, Response<ContactUsDm> response) {

                 contactUsDm = response.body();
                tv_address.setText(contactUsDm.getAddress());
                tv_ContactNo.setText(contactUsDm.getContactNo());
                tv_Email.setText(contactUsDm.getEmail());

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(ContactusActivity.this);



            }

            @Override
            public void onFailure(Call<ContactUsDm> call, Throwable t) {

            }
        });


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

       float lat = Float.parseFloat(contactUsDm.getLocationLat());
       float lo =  Float.parseFloat(contactUsDm.getLocationLong());

        mMap.clear(); //clear old markers
        CameraPosition googlePlex = CameraPosition.builder()
                .target(new LatLng(lat,lo))
                .zoom(17)
                .bearing(90)
                .tilt(30)
                .build();
        // Add a marker in Sydney and move the camera
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

        LatLng sydney = new LatLng(lat,lo );
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (contactUsDmCall!=null)
        {
            contactUsDmCall.cancel();
        }
    }
}
