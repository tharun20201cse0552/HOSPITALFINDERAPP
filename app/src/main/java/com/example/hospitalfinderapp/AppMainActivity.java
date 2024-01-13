package com.example.hospitalfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppMainActivity extends AppCompatActivity {

    private Button patloginbtn, docloginbtn, newpatbtn, newdocbtn, exitbtn, adminloginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        patloginbtn=(Button) findViewById(R.id.patientLoginBtn);
        newpatbtn=(Button) findViewById(R.id.newPatientBtn);
        //newdocbtn=(Button) findViewById(R.id.newdoctorbutton);
        exitbtn=(Button) findViewById(R.id.logoutBtn);
        //docloginbtn=(Button) findViewById(R.id.doctorLoginBtn);
        adminloginbtn=(Button) findViewById(R.id.adminLoginBtn);

        adminloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                startActivity(intent);
            }
        });

        patloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientLoginActivity.class);
                startActivity(intent);
            }
        });


        newpatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewPatientActivity.class);
                startActivity(intent);
            }
        });

        /*newdocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewDoctorActivity.class);
                startActivity(intent);
            }
        });*/
    }
}