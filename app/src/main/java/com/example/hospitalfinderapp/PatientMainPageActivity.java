package com.example.hospitalfinderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PatientMainPageActivity extends AppCompatActivity {
private Button usersearchhospitalsbtn,userviewallhospitalsbtn,userviewappbtn,
         gobackbtn;
private String userId,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_page);
        //useraddschoolcertificate = (Button) findViewById(R.id.updateschoolcertificatebtn);
        usersearchhospitalsbtn = (Button) findViewById(R.id.usersearchhospitalsbtn);
        userviewallhospitalsbtn = (Button) findViewById(R.id.userviewallhospitalsbtn);
        userviewappbtn = (Button) findViewById(R.id.userviewappbtn);
        gobackbtn = (Button) findViewById(R.id.gobackbtn);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        usersearchhospitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientSearchHospitalActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        userviewallhospitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientViewAllHospitalsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        userviewappbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientViewAppointmentsActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

    }
}