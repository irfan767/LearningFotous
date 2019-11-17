package com.ftocs.myapp.uiActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.CityDM;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
import com.daasuu.ahp.AnimateHorizontalProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignupActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    Button btn_signup;
    EditText et_email, et_pass, et_number, et_name, et_address;
    Spinner spinner_code, spinner_city, spinner_typeOfAccount;
    AnimateHorizontalProgressBar progressBar;
    ImageView img_back;

    Call<LoginResponseDM> callRegister;
    ArrayList<String> stringArrayListCityName;
    ArrayList<String> stringArrayListUserType;
    String selectedCityName;
    String selectedUserType;
    SharedPreferences prefLogIn;
    SharedPreferences.Editor editor;
    String userId;

    HashMap<String, String> intituteHashMap = new HashMap<>();


    List<String> codeList;
    private String selectedCode;
    private int cittyIDSelected;

    Call<List<CityDM>> callCity;
boolean gender = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        img_back = findViewById(R.id.img_back);
        btn_signup = findViewById(R.id.btn_signup);
        progressBar = findViewById(R.id.animate_progress_bar);

        et_email = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        et_number = findViewById(R.id.et_number);
        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);

        spinner_code = findViewById(R.id.spinner_code);
        spinner_city = findViewById(R.id.spinner_city);

        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        sendingNetworkRequestCity();



        // call login activity
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SignupActivity.this, SelectionSignupActivity.class);
              startActivity(intent);

            }

        });
        codeList = new ArrayList<>();
        codeList();
        ArrayAdapter<String> codeadapter = new ArrayAdapter<>(SignupActivity.this, R.layout.spinner_item, codeList);
        codeadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_code.setAdapter(codeadapter);



        spinner_code.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCode = spinner_code.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_pass.getText().toString();
                String phone1 = et_number.getText().toString();
                String address = et_address.getText().toString();

                if (et_name.getText().toString().isEmpty()) {
                    et_name.setError("Name is Empty");
                }

                if (et_pass.getText().toString().isEmpty()) {
                    et_pass.setError("Password is empty");
                }
                if (et_number.getText().toString().isEmpty()) {
                    et_number.setError("Number is empty");
                }
                if (et_address.getText().toString().isEmpty()) {
                    et_address.setError("Address is empty");
                }
                if (et_number.getText().length() > 7 || et_number.getText().length() < 7) {
                    et_number.setError("Number is invalid");
                }


                if (et_name.getText().toString().isEmpty()
                        || et_pass.toString().isEmpty() || et_number.getText().toString().isEmpty() || et_number.getText().length() > 7 || et_number.getText().length() < 7 || et_address.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Fill the empty fields", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.invalidate();
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setMinimumWidth(2);
                    progressBar.setMax(1000);
                    progressBar.setProgress(0);
                    progressBar.setProgressWithAnim(1000);
                    progressBar.setMaxWithAnim(200);
                    progressBar.setAnimDuration(12000);
                    callRegister = apiInterface.registerUser(name, email, password, address, selectedCode, phone1, gender, cittyIDSelected);
                    callRegister.enqueue(new Callback<LoginResponseDM>() {
                        @Override
                        public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {
                            LoginResponseDM login = response.body();
                            progressBar.invalidate();
                            progressBar.setVisibility(View.GONE);


                            if (response.code() == 200) {

                                Toast.makeText(SignupActivity.this, "Logged in", Toast.LENGTH_SHORT).show();

                                editor = prefLogIn.edit();
                                String id = String.valueOf(login.getID());
                                editor.putString(Constants.PREF_USER_ID_KEY, id);
                                editor.putString(Constants.PREF_USER_NAME, login.getFullName());
                                editor.putString(Constants.PREF_USER_EMAIL, login.getFullName());
                                editor.putString(Constants.PREF_USER_PASSWORD, login.getPassword());
                                editor.putString(Constants.PREF_USER_ADDRESS, login.getAddress());
                                editor.putString(Constants.PREF_USER_ContactNo, login.getContactNo());
                                editor.putString(Constants.PREF_USER_user_Type, String.valueOf(login.getUserType()));
                                editor.putString(Constants.PREF_USER_City, String.valueOf(login.getCityID()));
                                editor.commit();
                                editor.apply();
                                Constants.skip = null;
                                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();



                            } else if (response.code() == 400) {

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

                                Toast.makeText(SignupActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                                progressBar.invalidate();
                                progressBar.setVisibility(View.GONE);


                            } else {
                                Toast.makeText(SignupActivity.this, "Somehing went wrong try again", Toast.LENGTH_SHORT).show();
                                progressBar.invalidate();
                                progressBar.setVisibility(View.GONE);


                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponseDM> call, Throwable t) {
                            Toast.makeText(SignupActivity.this, "Network Problem", Toast.LENGTH_LONG).show();
                            progressBar.invalidate();
                            progressBar.setVisibility(View.GONE);

                        }
                    });

                }


            }

        });
    }

    public void codeList() {
        codeList.add("0300");
        codeList.add("0301");
        codeList.add("0302");
        codeList.add("0303");
        codeList.add("0304");
        codeList.add("0305");
        codeList.add("0306");
        codeList.add("0307");
        codeList.add("0308");
        codeList.add("0310");
        codeList.add("0311");
        codeList.add("0312");
        codeList.add("0313");
        codeList.add("0314");
        codeList.add("0315");
        codeList.add("0316");
        codeList.add("0317");
        codeList.add("0318");
        codeList.add("0320");
        codeList.add("0321");
        codeList.add("0322");
        codeList.add("0323");
        codeList.add("0324");
        codeList.add("0325");
        codeList.add("0326");
        codeList.add("0327");
        codeList.add("0328");
        codeList.add("0330");
        codeList.add("0331");
        codeList.add("0332");
        codeList.add("0333");
        codeList.add("0334");
        codeList.add("0335");
        codeList.add("0336");
        codeList.add("0337");
        codeList.add("0338");
        codeList.add("0340");
        codeList.add("0341");
        codeList.add("0342");
        codeList.add("0343");
        codeList.add("0344");
        codeList.add("0345");
        codeList.add("0346");
        codeList.add("0347");
        codeList.add("0348");
    }

    public void sendingNetworkRequestCity() {
        callCity = apiInterface.getMeCity();
        callCity.enqueue(new Callback<List<CityDM>>() {
            @Override
            public void onResponse(Call<List<CityDM>> call, Response<List<CityDM>> response) {
                if (response.isSuccessful()) {
                    List<CityDM> cityDMs = response.body();

                    if (cityDMs == null || cityDMs.size() == 0) {
//                        progressDialog.dismiss();
//                        linearLayout.setVisibility(View.GONE);
                        Toast.makeText(SignupActivity.this, "City Not Found ", Toast.LENGTH_SHORT).show();

                    } else {
//                        progressDialog.dismiss();


                        stringArrayListCityName = new ArrayList<>();
                        for (int i = 0; i < cityDMs.size(); i++) {

                            CityDM classTypeDataModel = cityDMs.get(i);
                            String cityName = classTypeDataModel.getCityName();
                            stringArrayListCityName.add(cityName);
                            intituteHashMap.put(String.valueOf(classTypeDataModel.getID()), classTypeDataModel.getCityName());


                        }

                        Constants.stringArrayListCity.addAll(stringArrayListCityName);
                        setDataCitySpinner(stringArrayListCityName);


                    }
                } else {
                    Toast.makeText(SignupActivity.this, "Something went wrong ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<CityDM>> call, Throwable t) {

            }
        });

    }

    private void setDataCitySpinner(ArrayList<String> stringArrayListCityName) {
        ArrayAdapter<String> classArrayadapter = new ArrayAdapter<String>(SignupActivity.this, android.R.layout.simple_dropdown_item_1line, stringArrayListCityName);
        spinner_city.setAdapter(classArrayadapter);
    }






    // call this method to open this activity.
    public static void callSignupActivity(Context context) {
        Intent intent = new Intent(context, SignupActivity.class);
        context.startActivity(intent);
    }


}
