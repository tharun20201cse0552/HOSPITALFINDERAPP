package com.example.hospitalfinderapp;
import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class PatientSearchHospitalActivity1 extends AppCompatActivity implements View.OnClickListener{
    private Button btn;
    private Button datebtn, timebtn;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private TextView txtDate, txtTime;
    private String[] hospital_Names, hospital_Address, hospital_Latitudes,hospital_PhNum,hospital_Rating,
            hospital_Location,
            hospital_Longitudes, hospital_image1, hospital_image2, hospital_image3, hospital_image4, hospital_image5;
    private String hospitalName, hospitalAddress, hospitalLatitude, hospitalPhnum,hospitallocation,
            hospitalLongitudes, hospitalimage1, hospitalimage2, hospitalimage3, hospitalimage4, hospitalimage5,
    userId, userName, appId;
    private TextView txthospitalName, txtaddress, txtphoneNum;
    private ImageView img1,img2,img3,img4,img5;
    private Button callbtn, appbtn, gobackbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search_hospitals1);

        Intent intent = getIntent();
        int index =intent.getIntExtra("index",0);
        userId=intent.getStringExtra("userId");
        userName=intent.getStringExtra("userName");
        Log.d("Index : ", "Value1 : " + index);
        //int index  = Integer.parseInt(s);

        hospital_Names = getResources().getStringArray(R.array.hospitalnames);
        hospital_Address = getResources().getStringArray(R.array.address);
        hospital_Location = getResources().getStringArray(R.array.hospitallocation);
        hospital_PhNum = getResources().getStringArray(R.array.phone);
        hospital_Rating = getResources().getStringArray(R.array.hospitalrating);
        hospital_Latitudes = getResources().getStringArray(R.array.lattitude);
        hospital_Longitudes = getResources().getStringArray(R.array.longitude);
        hospital_image1 = getResources().getStringArray(R.array.img1);
        hospital_image2 = getResources().getStringArray(R.array.img2);
        hospital_image3 = getResources().getStringArray(R.array.img3);
        hospital_image4 = getResources().getStringArray(R.array.img4);
        hospital_image5 = getResources().getStringArray(R.array.img5);

        callbtn = (Button)findViewById(R.id.btncall);
        appbtn = (Button)findViewById(R.id.appointmentbutton);
        gobackbtn = (Button)findViewById(R.id.gobackbtn);
        datebtn = (Button) findViewById(R.id.datebutton);
        timebtn = (Button) findViewById(R.id.timebutton);
        txtDate=(TextView) findViewById(R.id.textDate);
        txtTime=(TextView) findViewById(R.id.textTime);

        datebtn.setOnClickListener(this);
        timebtn.setOnClickListener(this);

        gobackbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PatientMainPageActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });

        appbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    UUID uuid=UUID.randomUUID();
                    appId = uuid.toString();
                    CollectionReference dbCourses = db.collection("newappointment");
                    Log.d("UserId : ", userId + " AppId : " + appId);

                    NewAppointmentClass newClass =new NewAppointmentClass(userId, appId, userName, hospitalName, String.valueOf(index),
                            txtDate.getText().toString(), txtTime.getText().toString());

                    // below method is use to add data to Firebase Firestore.
                    dbCourses.add(newClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // after the data addition is successful
                            // we are displaying a success toast message.
                            Toast.makeText(getApplicationContext(), "Appointment has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // this method is called when the data addition process is failed.
                            // displaying a toast message when data addition is failed.
                            Toast.makeText(getApplicationContext(), "Fail to add appointment\n" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch(Exception e)
                {
                    Log.d("Exception : ", e.getMessage());
                }
            }
        });

        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhoneNumber(hospitalPhnum);
            }
        });

        txthospitalName=(TextView) findViewById(R.id.editTextHname);
        txtaddress=(TextView)findViewById(R.id.editTextHAddress);
        txtphoneNum=(TextView)findViewById(R.id.editTextPhoneNum);
        img1  =(ImageView)findViewById(R.id.imageView1);
        img2  =(ImageView)findViewById(R.id.imageView2);
        img3  =(ImageView)findViewById(R.id.imageView3);
        img4  =(ImageView)findViewById(R.id.imageView4);
        img5  =(ImageView)findViewById(R.id.imageView5);

        hospitalName = hospital_Names[index];
        hospitalPhnum = hospital_PhNum[index];
        hospitalAddress = hospital_Address[index];
        hospitalimage1 = hospital_image1[index];
        hospitalimage2 = hospital_image2[index];
        hospitalimage3 = hospital_image3[index];
        hospitalimage4 = hospital_image4[index];
        hospitalimage5 = hospital_image5[index];

        txthospitalName.setText(hospitalName);
        txtaddress.setText(hospitalAddress);
        txtphoneNum.setText(hospitalPhnum);

        Picasso.get().load(hospitalimage1).into(img1);
        Picasso.get().load(hospitalimage2).into(img2);
        Picasso.get().load(hospitalimage3).into(img3);
        Picasso.get().load(hospitalimage4).into(img4);
        Picasso.get().load(hospitalimage5).into(img5);
    }
    public void callPhoneNumber(String phnum) {
        try {
            Log.d("SDK_INT : ", "SDK "+Build.VERSION.SDK_INT);
            if (Build.VERSION.SDK_INT > 22) {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    ActivityCompat.requestPermissions(PatientSearchHospitalActivity1.this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                    return;
                }
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phnum));
                startActivity(callIntent);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phnum));
                startActivity(callIntent);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == datebtn) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        }
                    }, mYear, mMonth, mDay);
            //datePickerDialog.show();
            c.add(Calendar.DATE, 3);
            datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            c.add(Calendar.DATE, -3);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
        else if (v == timebtn) {
            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            Calendar datetime = Calendar.getInstance();
                            Calendar c = Calendar.getInstance();
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);
                            if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {
                                //it's after current
                                int hour = hourOfDay % 12;
                                txtTime.setText(String.format("%02d:%02d %s", hour == 0 ? 12 : hour,
                                        minute, hourOfDay < 12 ? "am" : "pm"));
                            } else {
                                //it's before current'
                                Toast.makeText(getApplicationContext(), "Invalid Time", Toast.LENGTH_LONG).show();
                            }
                            //txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }
}