package com.ftocs.myapp.uiActivities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.ftocs.myapp.utils.Constants;
import com.daasuu.ahp.AnimateHorizontalProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    TextView txt_signup;
    EditText email, password;
    String userEmail, userPassword;
    AnimateHorizontalProgressBar progressBar;
    Button btn_login, facebookbutton;
    LinearLayout linearLayout, lil_forgetpass;
    private static final String EMAIL = "email";

    SharedPreferences prefLogIn;
    SharedPreferences.Editor editor;
    ApiInterface apiInterfaceLogin;
    Call<LoginResponseDM> logInDMCall;
    String userId;
    boolean isLogin;

    private TextView tv_skip_to_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        lil_forgetpass = findViewById(R.id.lil_forgetpass);
        linearLayout = findViewById(R.id.lil_signup);
        progressBar = findViewById(R.id.animate_progress_bar);
        btn_login = findViewById(R.id.btn_login);
        txt_signup = findViewById(R.id.txt_signup);
        tv_skip_to_home = findViewById(R.id.tv_skip_to_home);


        prefLogIn = getSharedPreferences(Constants.MY_PREF_LOGIN, Context.MODE_PRIVATE);
        apiInterfaceLogin = ApiClient.getApiClient().create(ApiInterface.class);

        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, SelectionSignupActivity.class);
                startActivity(intent);
            }
        });

        tv_skip_to_home.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra(Constants.SKIP,"skip");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
        lil_forgetpass = findViewById(R.id.lil_forgetpass);
        lil_forgetpass.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });
    /*    facebookbutton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.callOnClick();
            }
        });*/

        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = email.getText().toString();
                userPassword = password.getText().toString();


                progressBar.invalidate();
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setMinimumWidth(2);
                progressBar.setMax(1000);
                progressBar.setProgress(0);
                progressBar.setProgressWithAnim(1000);
                progressBar.setMaxWithAnim(200);
                progressBar.setAnimDuration(12000);

                if (email.getText().length() > 11 || email.getText().length() < 11) {
                    email.setError("Number is invalid");
                }
                if (password.getText().toString().isEmpty()) {
                    password.setError("Password is empty");
                }

                if ( email.getText().toString().isEmpty() || email.getText().length() > 11 || email.getText().length() < 11
                        ||  password.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill the fields", Toast.LENGTH_SHORT).show();
                } else {


                    getLoginInfo(userEmail, userPassword);

                }
            }
        });


    }

    public boolean getLoginInfo(String email, String password) {




        Log.d(TAG, "login: is called");

        Log.d(TAG, "onClick: email is" + email);
        Log.d(TAG, "onClick: password id" + password);


        logInDMCall = apiInterfaceLogin.logInCall(email,"", password);
        logInDMCall.enqueue(new Callback<LoginResponseDM>() {
            @Override
            public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {
                Log.d(TAG, "onResponse: is called ");
                LoginResponseDM login = response.body();





                    if (response.code() == 200) {

                        progressBar.invalidate();
                        progressBar.setVisibility(View.GONE);
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
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        finish();
                        startActivity(intent);


                    } else if (response.code() == 404) {

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

                        Toast.makeText(LoginActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                        progressBar.invalidate();
                        progressBar.setVisibility(View.GONE);



                    }
                    else if (response.code() == 400) {

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

                        Toast.makeText(LoginActivity.this, userMessage, Toast.LENGTH_SHORT).show();
                        progressBar.invalidate();
                        progressBar.setVisibility(View.GONE);



                    }


                 else {
                        Toast.makeText(LoginActivity.this, "Something went wrong try again", Toast.LENGTH_SHORT).show();

                        Log.d(TAG, "onResponse: is failed ");
                    progressBar.invalidate();
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<LoginResponseDM> call, Throwable t) {
                progressBar.invalidate();
                progressBar.setVisibility(View.INVISIBLE);

                Toast.makeText(LoginActivity.this, "Network Problem", Toast.LENGTH_LONG).show();


            }
        });

        return isLogin;


    }

    public void loginFailedDialog() {


        final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(getString(R.string.dialog_title));
        builder.setMessage(getString(R.string.dialog_message));

        String positiveText = getString(android.R.string.ok);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                    }
                });
/*
        String negativeText = getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                    }
                });*/

        AlertDialog dialog = builder.create();
        // display dialog
        dialog.show();


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (logInDMCall != null) {
            logInDMCall.cancel();
        }
    }





}


