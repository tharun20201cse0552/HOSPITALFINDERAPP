package com.example.hospitalfinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminViewAppointmentsActivity extends AppCompatActivity {
    private Button viewBtn;
    private ListView list_view;
    private String[] data,array;
    private ArrayList<String> dataArray;
    private String userId,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_appointments);

        list_view = (ListView) findViewById(R.id.list_view);
        viewBtn = (Button) findViewById(R.id.viewBtn);

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db= FirebaseFirestore.getInstance();
                db.collection("newappointment").get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                // after getting the data we are calling on success method
                                // and inside this method we are checking if the received
                                // query snapshot is empty or not.
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // if the snapshot is not empty we are
                                    // hiding our progress bar and adding
                                    // our data in a list.
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    String str = "";
                                    for (DocumentSnapshot d : list) {
                                        // after getting this list we are passing
                                        // that list to our object class.
                                        NewAppointmentClass objClass = d.toObject(NewAppointmentClass.class);
                                        String ShowDataString = "";
                                        Log.d("Id : ", d.getId());
                                        //if (userId.equals(objClass.getUserId())) {
                                        ShowDataString = "Customer Name : " + objClass.getUserName() +
                                                "\nHospital Id : " + objClass.getHospitalId() +
                                                "\nHospital Name : " + objClass.getHospitalName()+
                                                "\nApp Date : " + objClass.getAppDate()+
                                                "\nApp Time : " + objClass.getAppTime();
                                        Log.d("String : ", ShowDataString);
                                        if (str.length() == 0)
                                            str = str + ShowDataString;
                                        else
                                            str = str + "," + ShowDataString;
                                        }
                                    //}
                                    //app_Ids = appId.split(",");
                                    array = str.split(",");
                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_list_item_1, android.R.id.text1, array);
                                    list_view.setAdapter(adapter);
                                } else {
                                    // if the snapshot is empty we are displaying a toast message.
                                    Toast.makeText(getApplicationContext(), "No data found in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // if we do not get any data or any error we are displaying
                                // a toast message that we do not get any data
                                Toast.makeText(getApplicationContext(), "Fail to get the data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                // Adding addValueEventListener method on firebase object.
/*                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("newappointment");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@Nonnull DataSnapshot snapshot) {
                        String str = "";
                        for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                            NewAppointmentClass objClass = SubSnapshot.getValue(NewAppointmentClass.class);
                            String ShowDataString = "";

                            ShowDataString = "Customer Name : " + objClass.getUserName() +
                                    "\nHospital Id : " + objClass.getHospitalId() +
                                    "\nHospital Name : " + objClass.getHospitalName()+
                                    "\nApp Date : " + objClass.getAppDate()+
                                    "\nApp Time : " + objClass.getAppTime();

                            if (str.length() == 0)
                                str = str + ShowDataString;
                            else
                                str = str + "," + ShowDataString;

                        }
                        Log.d("Data : ", str);
                        //ShowDataTextView.setText(str);
                        array = str.split(",");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1, array);
                        //ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, array);
                        list_view.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@Nonnull DatabaseError error) {
                        System.out.println("Data Access Failed" + error.getMessage());
                    }
                });*/
            }
        });
    }
}