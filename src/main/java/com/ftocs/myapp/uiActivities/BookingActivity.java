package com.ftocs.myapp.uiActivities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.adapters.BookingsAdapter;
import com.ftocs.myapp.dataModels.DataSizeItem;
import com.ftocs.myapp.dataModels.ListProductItem;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.dataModels.OrderObj;
import com.ftocs.myapp.dataModels.OrderProductObj;
import com.ftocs.myapp.dataModels.SizeDM;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.retrofit.OrderAPIClient;
import com.ftocs.myapp.utils.Constants;
import com.ftocs.myapp.utils.PriceListener;
import com.ftocs.myapp.utils.WorkaroundMapFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingActivity extends AppCompatActivity implements OnMapReadyCallback, PriceListener {
    RecyclerView rc_products;
    BookingsAdapter bookingsAdapter;
    LinearLayoutManager layoutManager;
    Call<SizeDM> callCity;
    ListProductItem listItem;
    ApiInterface apiInterface;
    ArrayList<Integer> listwithsize = new ArrayList<>();
    ArrayList<String> stringArrayListCityName;
    HashMap<String, String> intituteHashMap = new HashMap<>();
    HashMap<String, String> priceHashMap = new HashMap<>();
    float intermediat_price = 0;
    int value = 1;
    List<OrderProductObj> product_list;
    OrderProductObj dataCollectedModel;
    Button btn_submit;
    private GoogleMap mMap;
    public String TAG = "PLaceManish";
        private int request_code = 1001;
    MarkerOptions markerOptions;
    LatLng latLng;
    LinearLayout lay_search;
    TextView txt_search;
    String LocationLong;
    OrderObj orderObj;
    Call<LoginResponseDM> callGetProfile;
    String LocationLat;
    NestedScrollView scroll_main;
    String userId;
    SharedPreferences prefLogIn;
    TextView tv_price;
    float totalcalculation = 0;
    EditText ed_address;
    ArrayList<Float> pricelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        pricelist = new ArrayList<Float>();
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("KINNO BOOKING");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_white);
        toolbar.setTitleTextColor(getResources().getColor(R.color.md_white_1000));
        btn_submit = findViewById(R.id.btn_submit);
        ed_address = findViewById(R.id.ed_address);
        tv_price = findViewById(R.id.tv_price);
        product_list = new ArrayList<>();
        listwithsize.clear();
        listwithsize.add(value);
        scroll_main = findViewById(R.id.sc_nested);
        rc_products = findViewById(R.id.rc_products);
        listItem = (ListProductItem) getIntent().getSerializableExtra(Constants.PRODUCTINFO);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        apiInterface = OrderAPIClient.getApiClient().create(ApiInterface.class);
        rc_products.setNestedScrollingEnabled(false);
        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        userId = prefLogIn.getString(Constants.PREF_USER_ID_KEY, Constants.NULL);
        Places.initialize(BookingActivity.this, "AIzaSyA-BwappyurEmlYo8DRZcH5ngG6o1gJw94");
        sendingNetworkRequestCity(881);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        lay_search = findViewById(R.id.lay_search);
        txt_search = findViewById(R.id.txt_search);
        lay_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAutocompleteActivity();
            }
        });
        // getting data from the all items.
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (bookingsAdapter != null) {
                    final int count = bookingsAdapter.getItemCount();
                    boolean isDandi;
                    for (int i = 0; i < count; i++) {
                        TextView quantity = rc_products.getChildAt(i).findViewById(R.id.et_qty);
                        Spinner spinner = rc_products.getChildAt(i).findViewById(R.id.spinner_city);
                        TextView price = rc_products.getChildAt(i).findViewById(R.id.tv_price);
                        LinearLayout linear_dundee = rc_products.getChildAt(i).findViewById(R.id.linear_dundee);
//                        if (linear_dundee.getBackground()== getDrawable(R.drawable.bg_border)){
//                            isDandi=true;
//                        } else {
//                            isDandi =false;
//                        }

                        dataCollectedModel = new OrderProductObj(881, true, spinner.getSelectedItem().toString(), Integer.valueOf(quantity.getText().toString()),
                                pricelist.get(i), bookingsAdapter.booleans.get(i));
                        product_list.add(dataCollectedModel);

                    }

                    Log.d("products", "products" + product_list.size());
                    if (ed_address.getText().toString() != null) {
                        orderObj = new OrderObj(Integer.valueOf(userId), LocationLat, LocationLong, bookingsAdapter.grandtotal, product_list, ed_address.getText().toString());
                        sendOrderData(orderObj);
                    } else {
                        orderObj = new OrderObj(Integer.valueOf(userId), LocationLat, LocationLong, bookingsAdapter.grandtotal, product_list, "null");
                        sendOrderData(orderObj);
                    }


                }
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listwithsize.add(value + 1);
                bookingsAdapter.notifyItemInserted(listwithsize.size());
                bookingsAdapter.notifyItemRangeChanged(listwithsize.size(), listwithsize.size());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(BookingActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    // sending data to the server
    public void sendOrderData(OrderObj orderObj) {

        final ProgressDialog progressDialog = new ProgressDialog(BookingActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Placing..");
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        callGetProfile = apiInterface.userOrder(orderObj);
        callGetProfile.enqueue(new Callback<LoginResponseDM>() {
            @Override
            public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {
                response.body();
                if (response.code() == 200) {
                    progressDialog.dismiss();
                    Intent intent = new Intent(BookingActivity.this, OrderHistoryActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();


                } else if (response.code() == 400) {
                    progressDialog.dismiss();
                    JSONObject jsonObject = null;
                    String userMessage = "";
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                        userMessage = jsonObject.getString("Message");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Toast.makeText(BookingActivity.this, userMessage, Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();


                } else {
                    Toast.makeText(BookingActivity.this, "Somehing went wrong try again", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            }

            @Override
            public void onFailure(Call<LoginResponseDM> call, Throwable t) {

                Toast.makeText(BookingActivity.this, "Network Problem", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();


            }
        });
    }


    // getting the prices and other essential data.
    public void sendingNetworkRequestCity(int productID) {
        callCity = apiInterface.getSizes(productID);
        callCity.enqueue(new Callback<SizeDM>() {
            @Override
            public void onResponse(Call<SizeDM> call, Response<SizeDM> response) {
                if (response.isSuccessful()) {
                    Constants.stringArrayListCity.clear();
                    SizeDM cityDMs = response.body();
                    Constants.stringArrayListCity.add(0, "Select Size");
                    if (cityDMs.getDataSize() == null || cityDMs.getDataSize().size() == 0) {
//                        progressDialog.dismiss();
//                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(BookingActivity.this, "City Not Found ", Toast.LENGTH_SHORT).show();

                    } else {
//                        progressDialog.dismiss();


                        stringArrayListCityName = new ArrayList<>();
                        for (int i = 0; i < cityDMs.getDataSize().size(); i++) {

                            DataSizeItem classTypeDataModel = cityDMs.getDataSize().get(i);
                            String cityName = classTypeDataModel.getSizeName();
                            stringArrayListCityName.add(cityName);
                            intituteHashMap.put(String.valueOf(classTypeDataModel.getSizeID()), classTypeDataModel.getSizeName());
                            priceHashMap.put(String.valueOf(classTypeDataModel.getSizePrice()), classTypeDataModel.getSizeName());

                        }
                        Constants.stringArrayListCity.addAll(stringArrayListCityName);
                        bookingsAdapter = new BookingsAdapter(BookingActivity.this, true, Constants.stringArrayListCity, listwithsize, intituteHashMap, priceHashMap, BookingActivity.this);
                        layoutManager = new LinearLayoutManager(BookingActivity.this, LinearLayoutManager.VERTICAL, false);
                        rc_products.setLayoutManager(layoutManager);
                        rc_products.setAdapter(bookingsAdapter);

                    }
                } else {
                    Toast.makeText(BookingActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SizeDM> call, Throwable t) {

            }
        });

    }

    private void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(BookingActivity.this);
        startActivityForResult(intent, request_code);
    }

    @Override
    public void totalPrice(float price) {
        tv_price.setText(price + "");
        if (price != totalcalculation) {
            totalcalculation = price;
            intermediat_price = price - intermediat_price;
            if (price != 0.0 && intermediat_price != 0.0) {
                pricelist.add(intermediat_price);
            }
        }
        Log.d("pricelistadded", "RS" + intermediat_price);
    }

    // An AsyncTask class for accessing the GeoCoding Web Service
    public class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(BookingActivity.this);
            List<Address> addresses = null;

            try {
                // Getting a maximum of 3 Address that matches the input text
                addresses = geocoder.getFromLocationName(locationName[0], 3);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {

            if (addresses == null || addresses.size() == 0) {
                Toast.makeText(BookingActivity.this, "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map
            mMap.clear();

            // Adding Markers on Google Map for each matching address
            for (int i = 0; i < addresses.size(); i++) {

                Address address = addresses.get(i);

                System.out.println("svsfssfjfjf " + address);
                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                LocationLat = String.valueOf(address.getLatitude());
                LocationLong = String.valueOf(address.getLongitude());


                System.out.println("aadtetsetse " + address.getAddressLine(0));
                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.draggable(true);
                markerOptions.title(address.getAddressLine(0));
                mMap.addMarker(markerOptions);

                // Locate the first location
                if (i == 0)
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ((WorkaroundMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .setListener(new WorkaroundMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scroll_main.requestDisallowInterceptTouchEvent(true);
                    }
                });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                LocationLat = String.valueOf(arg0.getPosition().latitude);
                LocationLong = String.valueOf(arg0.getPosition().longitude);
            }

            @SuppressWarnings("unchecked")
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub
                Log.d("System out", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                LocationLat = String.valueOf(arg0.getPosition().latitude);
                LocationLong = String.valueOf(arg0.getPosition().longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub
                Log.i("System out", "onMarkerDrag...");
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == request_code) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "ManishPlace: " + place.getName() + ", " + place.getId());

                txt_search.setText(place.getName());
                String location = place.toString();

                System.out.println("manishshis" + location);
                if (location != null && !location.equals("")) {
                    new GeocoderTask().execute(place.toString());
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

}
