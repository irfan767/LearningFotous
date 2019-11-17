package com.ftocs.myapp.uiActivities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dao.OrderDb;
import com.ftocs.myapp.dataModels.DataSizeItem;
import com.ftocs.myapp.dataModels.ListProductItem;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.dataModels.OrderEntity;
import com.ftocs.myapp.dataModels.SizeDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
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
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductBookingActivity extends AppCompatActivity implements OnMapReadyCallback {
    public TextView tv_appBar_title;
    ListProductItem listItem;
    Spinner spinner_city;
    Call<SizeDM> callCity;
    ApiInterface apiInterface;
    ArrayList<String> stringArrayListCityName;
    HashMap<String, String> intituteHashMap = new HashMap<>();
    HashMap<String, String> priceHashMap = new HashMap<>();
    private int cittyIDSelected;
    String selectedCityName;
    private String price;
    TextView tv_price;
    LinearLayout linear_without_dundee;
    LinearLayout linear_dundee;
    Button btn_submit;
    EditText et_qty;
    Call<LoginResponseDM> callGetProfile;
    float total;
    boolean IsDandi;

    SharedPreferences prefLogIn;
    String userId;


    private GoogleMap mMap;


    public String TAG = "PLaceManish";
    private int request_code = 1001;

    MarkerOptions markerOptions;
    LatLng latLng;

    LinearLayout lay_search;
    TextView txt_search;

    Button btn_publishLocation;

    String latitude;
    String longitude;

    String LocationLong;
    String LocationLat;

    ScrollView scroll_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_booking);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_appBar_title = findViewById(R.id.tv_appBar_title);
        tv_price = findViewById(R.id.tv_price);
        spinner_city = findViewById(R.id.spinner_city);
        et_qty = findViewById(R.id.et_qty);
        linear_without_dundee = findViewById(R.id.linear_without_dundee);
        linear_dundee = findViewById(R.id.linear_dundee);

        btn_submit = findViewById(R.id.btn_submit);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        tv_appBar_title.setText("Kinno Booking");
        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        userId = prefLogIn.getString(Constants.PREF_USER_ID_KEY, Constants.NULL);


        listItem = (ListProductItem) getIntent().getSerializableExtra(Constants.PRODUCTINFO);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Places.initialize(ProductBookingActivity.this, "AIzaSyA-BwappyurEmlYo8DRZcH5ngG6o1gJw94");
        scroll_main = findViewById(R.id.scroll_main);
        lay_search = findViewById(R.id.lay_search);
        txt_search = findViewById(R.id.txt_search);
        lay_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAutocompleteActivity();
            }
        });

        sendingNetworkRequestCity(listItem.getProductID());
        linear_without_dundee.setBackgroundResource(R.drawable.bg_border);
        IsDandi = false;
        if (listItem.isHasDandi()) {
            linear_without_dundee.setVisibility(View.VISIBLE);
            linear_dundee.setVisibility(View.VISIBLE);
        } else {
            linear_without_dundee.setVisibility(View.GONE);
            linear_dundee.setVisibility(View.GONE);
        }

        linear_without_dundee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_without_dundee.setBackgroundResource(R.drawable.bg_border);
                linear_dundee.setBackgroundResource(0);
                IsDandi = false;

            }
        });
        linear_dundee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_without_dundee.setBackgroundResource(0);
                linear_dundee.setBackgroundResource(R.drawable.bg_border);
                IsDandi = true;

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_qty.getText().toString().isEmpty()) {
                    et_qty.setError("Empty");
                    Toast.makeText(ProductBookingActivity.this, "Fill QTY", Toast.LENGTH_SHORT).show();
                } else {
                    total = Float.parseFloat(price) * Float.parseFloat(et_qty.getText().toString());
                    logOutDialog("TOTAL PRICE:" + total, "ARE YOU SURE YOU WANT TO PLACE ORDER?");
                }

            }
        });
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCityName = spinner_city.getItemAtPosition(position).toString();
                for (String key : intituteHashMap.keySet()) {

                    if (intituteHashMap.get(key).equals(selectedCityName)) {
                        cittyIDSelected = Integer.parseInt(key);


                    }

                }

                for (String key : priceHashMap.keySet()) {

                    if (priceHashMap.get(key).equals(selectedCityName)) {
                        price = key;


                    }
                    tv_price.setText(price);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void sendingNetworkRequestCity(int productID) {
        callCity = apiInterface.getSizes(productID);
        callCity.enqueue(new Callback<SizeDM>() {
            @Override
            public void onResponse(Call<SizeDM> call, Response<SizeDM> response) {
                if (response.isSuccessful()) {
                    SizeDM cityDMs = response.body();

                    if (cityDMs.getDataSize() == null || cityDMs.getDataSize().size() == 0) {
//                        progressDialog.dismiss();
//                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(ProductBookingActivity.this, "City Not Found ", Toast.LENGTH_SHORT).show();

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
                        setDataCitySpinner(stringArrayListCityName);


                    }
                } else {
                    Toast.makeText(ProductBookingActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SizeDM> call, Throwable t) {

            }
        });

    }

    private void setDataCitySpinner(ArrayList<String> stringArrayListCityName) {
        ArrayAdapter<String> classArrayadapter = new ArrayAdapter<String>(ProductBookingActivity.this, android.R.layout.simple_dropdown_item_1line, stringArrayListCityName);
        spinner_city.setAdapter(classArrayadapter);
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

    public void logOutDialog(String title, String message) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductBookingActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic


                        sendOrderData(listItem.getProductID(),
                                listItem.isHasDandi(), selectedCityName,
                                Integer.parseInt(et_qty.getText().toString()),
                                total, IsDandi, Integer.parseInt(userId));
//                        Log.d("checlistSize","id= "+listItem.getProductID());
//                        OrderEntity orderEntity = new OrderEntity();
//                        orderEntity.setId(listItem.getProductID());
//                        orderEntity.setHasDandi(listItem.isHasDandi());
//                        orderEntity.setIsDandi(IsDandi);
//                        orderEntity.setQuantity(Integer.parseInt(et_qty.getText().toString()));
//                        orderEntity.setSize(selectedCityName);
//                        Constants.orderEntities.add(orderEntity);



                    }
                });

        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }

    public void sendOrderData(int productId, boolean HasDandi, String Size, int Quantity, float Price, boolean IsDandi, int UserID) {


        final ProgressDialog progressDialog = new ProgressDialog(ProductBookingActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Placing..");
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

//        callGetProfile = apiInterface.userOrder(productId, HasDandi, Size, Quantity, Price, IsDandi, UserID, LocationLat, LocationLong);
        callGetProfile.enqueue(new Callback<LoginResponseDM>() {
            @Override
            public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {

                LoginResponseDM login = response.body();


                if (response.code() == 200) {
                    progressDialog.dismiss();

                    Intent intent = new Intent(ProductBookingActivity.this, OrderHistoryActivity.class);
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


                    Toast.makeText(ProductBookingActivity.this, userMessage, Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();


                } else {
                    Toast.makeText(ProductBookingActivity.this, "Somehing went wrong try again", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();


                }
            }

            @Override
            public void onFailure(Call<LoginResponseDM> call, Throwable t) {

                Toast.makeText(ProductBookingActivity.this, "Network Problem", Toast.LENGTH_LONG).show();

                progressDialog.dismiss();


            }
        });
    }

    private void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .build(ProductBookingActivity.this);
        startActivityForResult(intent, request_code);
    }


    // An AsyncTask class for accessing the GeoCoding Web Service
    public class GeocoderTask extends AsyncTask<String, Void, List<Address>> {

        @Override
        protected List<Address> doInBackground(String... locationName) {
            // Creating an instance of Geocoder class
            Geocoder geocoder = new Geocoder(ProductBookingActivity.this);
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
                Toast.makeText(ProductBookingActivity.this, "No Location found", Toast.LENGTH_SHORT).show();
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
