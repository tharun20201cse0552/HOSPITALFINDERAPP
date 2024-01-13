package com.example.hospitalfinderapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewPatientActivity extends AppCompatActivity {

    public static InputFilter acceptonlyAlphabetValuesnotNumbersMethod() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

                boolean isCheck = true;
                StringBuilder sb = new StringBuilder(end - start);
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (isCharAllowed(c)) {
                        sb.append(c);
                    } else {
                        isCheck = false;
                    }
                }
                if (isCheck)
                    return null;
                else {
                    if (source instanceof Spanned) {
                        SpannableString spannableString = new SpannableString(sb);
                        TextUtils.copySpansFrom((Spanned) source, start, sb.length(), null, spannableString, 0);
                        return spannableString;
                    } else {
                        return sb;
                    }
                }
            }

            private boolean isCharAllowed(char c) {
                Pattern pattern = Pattern.compile("^[a-zA-Z ]+$");
                Matcher match = pattern.matcher(String.valueOf(c));
                return match.matches();
            }
        };
    }

    private EditText txtfirstName,
            txtlastName, txtemailId, txtphoneNum,
            txtuserName, txtpassword;
    private String userId, firstName, lastName, emailId, phoneNum, userName, password,
            gender;
    private Button signupbtn, gobackbtn;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_patient);

        txtfirstName=(EditText)findViewById(R.id.editTextFname);
        txtlastName=(EditText)findViewById(R.id.editTextLname);
        txtemailId=(EditText)findViewById(R.id.editEmailid);
        txtphoneNum=(EditText)findViewById(R.id.editTextPhoneNum);
        txtuserName=(EditText)findViewById(R.id.editTextUname);
        txtpassword=(EditText)findViewById(R.id.editTextPwd);
        radioGroup = (RadioGroup)findViewById(R.id.genderradioGroup);
        signupbtn = (Button)findViewById(R.id.newuserbutton);
        gobackbtn= (Button)findViewById(R.id.gobackbutton);

        txtfirstName.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});
        txtlastName.setFilters(new InputFilter[]{acceptonlyAlphabetValuesnotNumbersMethod()});

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                gender = radioButton.getText().toString();
            }
        });

        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppMainActivity.class);
                startActivity(intent);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                firstName=txtfirstName.getText().toString();
                lastName=txtlastName.getText().toString();
                emailId=txtemailId.getText().toString();
                phoneNum=txtphoneNum.getText().toString();
                userName=txtuserName.getText().toString();
                password=txtpassword.getText().toString();

                Pattern pattern = Pattern.compile("^\\d{10}$");
                Matcher matcher = pattern.matcher(phoneNum);

                if(TextUtils.isEmpty(firstName))
                {
                    Toast.makeText(getApplicationContext(), "First Name is Empty", Toast.LENGTH_SHORT).show();
                    txtfirstName.setFocusable(true);
                }
                else if(TextUtils.isEmpty(lastName))
                {
                    Toast.makeText(getApplicationContext(), "Last Name is Empty", Toast.LENGTH_SHORT).show();
                    txtlastName.setFocusable(true);
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
                else if(TextUtils.isEmpty(gender))
                {
                    Toast.makeText(getApplicationContext(), "Gender is Empty", Toast.LENGTH_SHORT).show();
                    radioGroup.setFocusable(true);
                }
                else if(TextUtils.isEmpty(userName))
                {
                    Toast.makeText(getApplicationContext(), "User Name is Empty", Toast.LENGTH_SHORT).show();
                    txtuserName.setFocusable(true);
                }
                else if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "Password is Empty", Toast.LENGTH_SHORT).show();
                    txtpassword.setFocusable(true);
                }
                else{
                    try {
                        Log.d("Click on New User", "New User");
                    /*
                        FirebaseFirestore db = FirebaseFirestore.getInstance();

                        UUID uuid=UUID.randomUUID();
                        userId = uuid.toString();

                        // Create a new user with a first and last name
                        Map<String, Object> user = new HashMap<>();
                        user.put("userId", userId);
                        user.put("firstName", firstName);
                        user.put("lastName", lastName);
                        user.put("userName", userName);
                        user.put("password", password);
                        user.put("emailId", emailId);
                        user.put("phoneNum", phoneNum);
                        user.put("gender", gender);

                        // Add a new document with a generated ID
                        db.collection("users")
                                .add(user)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("Success : ", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("Error : ", "Error adding document", e);
                                    }
                                });

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference dbRef = database.getReference();
                    dbRef = database.getReference("NewUser");
                    userId = dbRef.push().getKey();
                    NewPatientClass newUserClass =new NewPatientClass(userId, firstName, lastName, gender,
                            emailId, phoneNum, userName, password);
                    dbRef.child(userId).setValue(newUserClass);
                    Toast.makeText(getApplicationContext(), "New User Inserted Successfully",
                                Toast.LENGTH_LONG).show();

                    try{
                    FileInputStream serviceAccount = new FileInputStream("key.json");
                    FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
                            .setDatabaseUrl("https://yamahaapp-75988-default-rtdb.firebaseio.com")
                            .build();
                    FirebaseApp.initializeApp(options);
                    Toast.makeText(getApplicationContext(), "New User Inserted Successfully",
                            Toast.LENGTH_LONG).show();
                    }catch (Exception ex)
                    {
                        Log.d("Exception : ", ex.getMessage());
                    }*/
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        UUID uuid=UUID.randomUUID();
                        userId = uuid.toString();
                        CollectionReference dbCourses = db.collection("newpatient");
                        Log.d("UserId : ", userId);
                        //String encData = aes.AESencrypt(key.getBytes("UTF-16LE"), password.getBytes("UTF-16LE"));
                        //String encData = AESCrypt.encrypt(password);
                        // adding our data to our courses object class.
                        NewPatientClass newUserClass =new NewPatientClass(userId, firstName, lastName, gender,
                                emailId, phoneNum, userName, password);

                        // below method is use to add data to Firebase Firestore.
                        dbCourses.add(newUserClass).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                // after the data addition is successful
                                // we are displaying a success toast message.
                                Toast.makeText(getApplicationContext(), "Patient has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // this method is called when the data addition process is failed.
                                // displaying a toast message when data addition is failed.
                                Toast.makeText(getApplicationContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
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