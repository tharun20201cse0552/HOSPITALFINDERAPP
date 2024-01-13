package com.example.hospitalfinderapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nonnull;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;

import android.util.Log;

public class PatientSearchHospitalActivity extends AppCompatActivity {
    private Button viewBtn;
    private ListView list_view;
    private String[] hospital_Names, hospital_Address, hospital_Latitudes,hospital_PhNum,hospital_Rating,
            hospital_Location,
            hospital_Longitudes, hospital_image1, hospital_image2, hospital_image3, hospital_image4, hospital_image5;
    private String hospitalName, hospitalAddress, hospitalLatitude, hospitalPhnum,hospitallocation,
            hospitalLongitudes, hospitalimage1, hospitalimage2, hospitalimage3, hospitalimage4, hospitalimage5;
    private String[] array;
    protected LocationManager locationManager;
    protected Double current_latitude, current_longitude;
    private TextView txtLat,locationTxt;
    private String address, city, state, country,postalCode, knownName;
    protected String latitude, longitude;
    private static final int REQUEST_LOCATION = 1;
    private ArrayList<String> arrayList;
    private ArrayList<Integer> indexList;
    private DistanceCalculation distanceCalculation;
    private DecimalFormat f;
    private String userId,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_search_hospitals);

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        userName = intent.getStringExtra("userName");

        list_view = (ListView) findViewById(R.id.list_view);
        viewBtn = (Button) findViewById(R.id.viewBtn);
        //txtLat = (TextView) findViewById(R.id.textView1);
        locationTxt= (TextView) findViewById(R.id.locationTxt);
        //showLocation = findViewById(R.id.showLocation);
        //btnGetLocation = findViewById(R.id.btnGetLocation);
        distanceCalculation = new DistanceCalculation();
        f = new DecimalFormat("###.00");
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

        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
        arrayList = new ArrayList<>();
        indexList = new ArrayList<>();
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str="";
                for(int x=0;x<hospital_Names.length;x++)
                {
                    if(address.toLowerCase().contains(hospital_Location[x].toLowerCase())) {
                        double toLatitude = Double.parseDouble(hospital_Latitudes[x]);
                        double toLongitude = Double.parseDouble(hospital_Longitudes[x]);
                        double distance = distanceCalculation.calculateDistance(current_latitude, current_longitude, toLatitude, toLongitude);
                        String dataString = "Hospital Name : " + hospital_Names[x] + "\n" +
                                "Hospital Location : " + hospital_Location[x] + "\n" +
                                "Hospital Address : " + hospital_Address[x] + "\n" +
                                "Hospital PhNum : " + hospital_PhNum[x] + "\n" +
                                "Hospital Rating : " + hospital_Rating[x]+"\n"+
                                "Distance : " + f.format(distance)+" Km";
                        arrayList.add(dataString);
                        indexList.add(x);
                    }
                }
                array = new String[arrayList.size()];
                for(int x=0; x<arrayList.size(); x++){
                    array[x]=arrayList.get(x);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, array);
                //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
                list_view.setAdapter(adapter);
            }
        });

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = indexList.get(position);
                //Log.d("Index : ", "Value : " +index);
                Intent intent = new Intent(getApplicationContext(), PatientSearchHospitalActivity1.class);
                intent.putExtra("index",index);
                intent.putExtra("userId",userId);
                intent.putExtra("userName",userName);
                startActivity(intent);
            }
        });


        /*
        list_view.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = indexList.get(position);

                hospitalAddress = hospital_Address[index];
                hospitalPhnum= hospital_PhNum[index];
                hospitalName= hospital_Names[index];
                hospitalimage1= hospital_image1[index];
                hospitalimage2= hospital_image2[index];
                hospitalimage3= hospital_image3[index];
                hospitalimage4= hospital_image4[index];
                hospitalimage5= hospital_image5[index];

                Intent intent = new Intent(getApplicationContext(), PatientSearchHospitalActivity1.class);
                intent.putExtra("index",index);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        */

        /*
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }
            }
        });
        */
    }

    public void getLocationAddress(Location location) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Log.e("latitude", "latitude--" + latitude);

        try {
            Log.e("latitude", "inside latitude--" + latitude);
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                address = addresses.get(0).getAddressLine(0);
                city = addresses.get(0).getLocality();
                state = addresses.get(0).getAdminArea();
                country = addresses.get(0).getCountryName();
                postalCode = addresses.get(0).getPostalCode();
                knownName = addresses.get(0).getFeatureName();

                locationTxt.setText("Address : " + address + "\n" + city + "\n" + state +"\n"+ country
                +"\n"+postalCode +"\n"+knownName + "\nLatitude : " + f.format(current_latitude) +
                        "\nLongitude : "+ f.format(current_longitude));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                current_latitude = locationGPS.getLatitude();
                current_longitude = locationGPS.getLongitude();
                //latitude = String.valueOf(lat);
                //longitude = String.valueOf(longi);
                //locationTxt.setText("Your Location: " + " " + "Latitude: " + latitude + " " + "Longitude: " + longitude);
                getLocationAddress(locationGPS);
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}