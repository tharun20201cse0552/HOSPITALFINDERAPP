package com.example.hospitalfinderapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import javax.annotation.Nonnull;

public class AdminViewDoctorsActivity extends AppCompatActivity {
    private Button viewBtn;
    private ListView list_view;
    private String[] data,array;
    private ArrayList<String> dataArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_doctors);

        list_view = (ListView) findViewById(R.id.list_view);
        viewBtn = (Button) findViewById(R.id.viewBtn);

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Adding addValueEventListener method on firebase object.
                /*FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("newdoctor");

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@Nonnull DataSnapshot snapshot) {
                        String str = "";
                        for (DataSnapshot SubSnapshot : snapshot.getChildren()) {

                            NewDoctorClass objClass = SubSnapshot.getValue(NewDoctorClass.class);
                            String Id = objClass.getDoctorId();
                            String ShowDataString = "";

                            ShowDataString = "Doctor Name : " + objClass.getFirstName() + " " +
                                    objClass.getLastName() +
                                    "\nPhone Num : " + objClass.getPhoneNum() +
                                    "\nEmail Id : " + objClass.getEmailId()+
                                    "\nQualification : " + objClass.getQualification()+
                                    "\nSpecialization : " + objClass.getSpecialization();

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