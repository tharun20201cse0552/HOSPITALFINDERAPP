package com.example.hospitalfinderapp;

import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminAddHospitalActivity extends AppCompatActivity {
    private EditText txthospitalName, txtaddress, txtemailId, txtphoneNum;
    private String hospitalId, hospitalArea, hospitalName, hospitalAddress, emailId, phoneNum, specialization;
    private Spinner areaSpinner, specializationSpinner;
    private Button signupbtn, gobackbtn;
    private String[] qualifications, specializations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_hospital);

        txthospitalName=(EditText)findViewById(R.id.editTextHname);
        txtaddress=(EditText)findViewById(R.id.editTextHAddress);
        txtemailId=(EditText)findViewById(R.id.editEmailid);
        txtphoneNum=(EditText)findViewById(R.id.editTextPhoneNum);
        signupbtn = (Button)findViewById(R.id.newhospitalbutton);
        gobackbtn= (Button)findViewById(R.id.buttonGoback);
        areaSpinner = (Spinner) findViewById(R.id.areaspinner);
        specializationSpinner = (Spinner) findViewById(R.id.speciliazationspinner);

        qualifications = getResources().getStringArray(R.array.qualification);
        specializations = getResources().getStringArray(R.array.specialization);

        ArrayAdapter<String> qualiadapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, qualifications);
        areaSpinner.setAdapter(qualiadapter);

        ArrayAdapter<String> specializationadapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, specializations);
        specializationSpinner.setAdapter(specializationadapter);

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                hospitalName=txthospitalName.getText().toString();
                hospitalAddress=txtaddress.getText().toString();
                emailId=txtemailId.getText().toString();
                phoneNum=txtphoneNum.getText().toString();
                hospitalArea = areaSpinner.getSelectedItem().toString();
                specialization = specializationSpinner.getSelectedItem().toString();

                Pattern pattern = Pattern.compile("^\\d{10}$");
                Matcher matcher = pattern.matcher(phoneNum);

                if(TextUtils.isEmpty(hospitalName))
                {
                    Toast.makeText(getApplicationContext(), "Hospital Name is Empty", Toast.LENGTH_SHORT).show();
                    txthospitalName.setFocusable(true);
                }
                else if(TextUtils.isEmpty(hospitalAddress))
                {
                    Toast.makeText(getApplicationContext(), "Hospital Address is Empty", Toast.LENGTH_SHORT).show();
                    txtaddress.setFocusable(true);
                }
                else if(TextUtils.isEmpty(emailId))
                {
                    Toast.makeText(getApplicationContext(), "Email Id is Empty", Toast.LENGTH_SHORT).show();
                    txtemailId.setFocusable(true);
                }else if(!Patterns.EMAIL_ADDRESS.matcher(emailId).matches())
                {
                    txtemailId.setError("EmailId is Not Valid");
                    txtemailId.setFocusable(true);
                }
                else if(TextUtils.isEmpty(phoneNum))
                {
                    Toast.makeText(getApplicationContext(), "Phone Num is Empty", Toast.LENGTH_SHORT).show();
                    txtphoneNum.setFocusable(true);
                }
                else if(!matcher.matches())
                {
                    txtphoneNum.setError("Phone Num is Invalid should be 10 digits");
                    txtphoneNum.setFocusable(true);
                }
                else if(TextUtils.isEmpty(hospitalArea))
                {
                    Toast.makeText(getApplicationContext(), "Hospital Area is Empty", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(specialization))
                {
                    Toast.makeText(getApplicationContext(), "Specialization is Empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    try {
                        Log.d("Click on New User", "New Hospital");
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        UUID uuid=UUID.randomUUID();
                        hospitalId = uuid.toString();
                        //NewHospitalClass newClass =new NewHospitalClass(hospitalId, hospitalName, hospitalAddress, emailId, phoneNum, hospitalArea, specialization);
                        NewHospitalClass newClass = new NewHospitalClass();
                        // Add a new document with a generated ID
                        db.collection("newhospital")
                                .add(newClass)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("Success : ", "DocumentSnapshot added with ID: " + documentReference.getId());
                                        Toast.makeText(getApplicationContext(), "New Hospital Inserted Successfully",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Error : ", "Error adding document", e);
                                        Toast.makeText(getApplicationContext(), "Error adding document",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }catch(Exception e)
                    {
                        Log.d("Exception : ", e.getMessage());
                    }
                }
            }
        });
    }
}