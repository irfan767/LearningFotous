package com.ftocs.myapp.uiActivities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ftocs.myapp.R;
import com.ftocs.myapp.dataModels.LoginResponseDM;
import com.ftocs.myapp.retrofit.ApiClient;
import com.ftocs.myapp.retrofit.ApiInterface;
import com.daasuu.ahp.AnimateHorizontalProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ForgetPassword extends AppCompatActivity {
    EditText ed_email, ed_ext;
    Button btn_recovaer;
    ImageView btn_back;
    AnimateHorizontalProgressBar progressBar;
    Call<LoginResponseDM> callRegister;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        progressBar = findViewById(R.id.animate_progress_bar);
        ed_email = findViewById(R.id.ed_email);
        ed_ext = findViewById(R.id.ed_ext);
        btn_recovaer = findViewById(R.id.btn_recover);
        btn_back = findViewById(R.id.btn_back);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        btn_recovaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString();
                String ext = ed_ext.getText().toString();
                if (ed_email.getText().toString().isEmpty() || ed_ext.getText().toString().isEmpty()) {
                    Toast.makeText(ForgetPassword.this, "Field is empty", Toast.LENGTH_SHORT).show();
                } else {
                    progressBar.invalidate();
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setMinimumWidth(2);
                    progressBar.setMax(1000);
                    progressBar.setProgress(0);
                    progressBar.setProgressWithAnim(1000);
                    progressBar.setMaxWithAnim(200);
                    progressBar.setAnimDuration(12000);
                    callRegister = apiInterface.callForgetPassword(ext, email);
                    callRegister.enqueue(new Callback<LoginResponseDM>() {
                        @Override
                        public void onResponse(Call<LoginResponseDM> call, Response<LoginResponseDM> response) {
                            LoginResponseDM login = response.body();
                            if (response.isSuccessful()) {
                                String userId = login.getId();

                                progressBar.invalidate();
                                progressBar.setVisibility(View.GONE);


                                if (response.code() == 200) {


                                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                                    emailIntent.setType("text/plain"); // <-- HERE
                                    startActivity(emailIntent); // <-- AND HERE

                                    Toast.makeText(ForgetPassword.this, "Password sent to your mobile.", Toast.LENGTH_SHORT).show();


                                } else {
                                    Toast.makeText(ForgetPassword.this, login.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.invalidate();
                                    progressBar.setVisibility(View.GONE);

                                }


                            } else {
                                Toast.makeText(ForgetPassword.this, "Some thing went wrong try again", Toast.LENGTH_SHORT).show();
                                progressBar.invalidate();
                                progressBar.setVisibility(View.GONE);


                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponseDM> call, Throwable t) {
                            Toast.makeText(ForgetPassword.this, "Network Problem", Toast.LENGTH_LONG).show();
                            progressBar.invalidate();
                            progressBar.setVisibility(View.GONE);

                        }
                    });

                }

            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ForgetPassword.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void startForgetActivity(Context context) {
        Intent intent = new Intent(context, ForgetPassword.class);
        context.startActivity(intent);
    }


}
