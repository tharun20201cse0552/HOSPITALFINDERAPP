package com.example.hospitalfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminMainPageActivity extends AppCompatActivity {
    private Button adminviewpatientsbtn, adminviewhospitalsbtn, adminviewdoctorsbtn, logoutbtn, addnewhospitalbtn,
    adminaddhospitalbtn, adminadddoctorsbtn, adminviewappointmentsbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_page);

        //adminaddhospitalbtn=(Button) findViewById(R.id.addHospitalsBtn);
        adminadddoctorsbtn=(Button) findViewById(R.id.addDoctorsBtn);
        adminviewdoctorsbtn=(Button) findViewById(R.id.viewDoctorsBtn);
        logoutbtn=(Button) findViewById(R.id.logoutBtn);
        adminaddhospitalbtn=(Button) findViewById(R.id.viewHospitalsBtn);
        adminviewpatientsbtn=(Button) findViewById(R.id.viewUsersBtn);
        adminviewhospitalsbtn=(Button) findViewById(R.id.viewHospitalsBtn);
        adminviewappointmentsbtn=(Button) findViewById(R.id.viewAppointmentsBtn);

        /*adminaddhospitalbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminAddHospitalActivity.class);
                startActivity(intent);
            }
        });*/

        adminadddoctorsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewDoctorActivity.class);
                startActivity(intent);
            }
        });

        adminviewappointmentsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewAppointmentsActivity.class);
                startActivity(intent);
            }
        });

        adminviewdoctorsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewDoctorsActivity.class);
                startActivity(intent);
            }
        });
        adminviewhospitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewHospitalsActivity.class);
                startActivity(intent);
            }
        });
        adminviewpatientsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminViewPatientsActivity.class);
                startActivity(intent);
            }
        });
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}