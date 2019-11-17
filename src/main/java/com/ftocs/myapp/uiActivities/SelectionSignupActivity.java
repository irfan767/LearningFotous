package com.ftocs.myapp.uiActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ftocs.myapp.R;


public class SelectionSignupActivity extends AppCompatActivity {

    private Button email_sign_in_button;
    private Button btn_request_form;
    private LinearLayout lin_have_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_signup);
        email_sign_in_button  = findViewById(R.id.email_sign_in_button);
        lin_have_account  = findViewById(R.id.lin_have_account);
        btn_request_form  = findViewById(R.id.btn_request_form);

        email_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectionSignupActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });
        btn_request_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectionSignupActivity.this,RequestFormActivity.class);
                startActivity(intent);
            }
        });
        lin_have_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectionSignupActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
