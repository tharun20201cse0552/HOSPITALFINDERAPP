package com.example.hospitalfinderapp;

//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DoctorLoginActivity extends AppCompatActivity {
    private Button signInBtn, goBackBtn;
    private EditText txtUname, txtPwd;
    private String username, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        signInBtn = (Button) findViewById(R.id.loginBtn);
        goBackBtn = (Button) findViewById(R.id.gobackBtn);
        txtUname = (EditText) findViewById(R.id.editTextUname);
        txtPwd = (EditText) findViewById(R.id.editTextPassword);

        txtUname.setText("doctor");
        txtPwd.setText("1234");

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = txtUname.getText().toString();
                password = txtPwd.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    txtUname.setError("User Name is Empty");
                    txtUname.setFocusable(true);
                } else if (TextUtils.isEmpty(password)) {
                    txtPwd.setError("Password is Empty");
                    txtPwd.setFocusable(true);
                } else {

                }
            }
        });

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}