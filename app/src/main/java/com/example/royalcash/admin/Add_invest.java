package com.example.royalcash.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.royalcash.Confrom_investment;
import com.example.royalcash.MainActivity1;
import com.example.royalcash.R;
import com.example.royalcash.model.Conforim_Investment1;
import com.example.royalcash.model.Transcation_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Add_invest extends AppCompatActivity
 implements AdapterView.OnItemSelectedListener{
        private Spinner spinnerTextSize;
        String valueFromSpinner;
        FirebaseFirestore firebaseFirestore;
        FirebaseAuth firebaseAuth;
        EditText Password_Log,Password_Log1,Password_Log51,Password_Log9;
        Button tmio_1;
        String message;
        KProgressHUD progressHUD;
        String current1;
        String total_day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_invest);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        Password_Log=findViewById(R.id.Password_Log);
        Password_Log1=findViewById(R.id.Password_Log1);
        Password_Log51=findViewById(R.id.Password_Log51);
        Password_Log9=findViewById(R.id.Password_Log9);
        progressHUD = KProgressHUD.create(Add_invest.this);
        tmio_1=findViewById(R.id.tmio_1);
        Calendar calendar=Calendar.getInstance();
        String current= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        current1= DateFormat.getDateInstance().format(calendar.getTime());
        spinnerTextSize = findViewById(R.id.spinnerTextSize);
        spinnerTextSize.setOnItemSelectedListener(this);
        String[] textSizes = getResources().getStringArray(R.array.payment);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);
        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone,transcation,ammount,email;
                phone=Password_Log.getText().toString();
                transcation=Password_Log1.getText().toString();
                ammount=Password_Log51.getText().toString();
                email=Password_Log9.getText().toString().toLowerCase().trim();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(transcation) || TextUtils.isEmpty(valueFromSpinner)
                        || TextUtils.isEmpty(ammount)||TextUtils.isEmpty(email) ) {
                    tmio_1.setError("Some fields are empty");
                    return;

                }
                else {
                    progress_check();
                    upload_checker();
                    final String user = Password_Log9.getText().toString().toLowerCase().trim();
                    Conforim_Investment1 conforim_investment1 = new Conforim_Investment1(user, phone, transcation, valueFromSpinner, current1, ammount);
                    firebaseFirestore.collection("Conform_investment_temprary")
                            .document(user)
                            .collection("Conform")
                            .document(ammount)
                            .set(conforim_investment1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseFirestore.collection("User_Balance")
                                                .document(Password_Log9.getText().toString())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            String coin = task.getResult().getString("coin");
                                                            int a = Integer.parseInt(coin);
                                                            int gett = Integer.parseInt(ammount);
                                                            int am = a + gett;
                                                            firebaseFirestore.collection("User_Balance")
                                                                    .document(user)
                                                                    .update("coin", "" + am)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Transcation_model transcation_model=new Transcation_model(user,"Investment",ammount);
                                                                                firebaseFirestore.collection("Transcation_History")
                                                                                        .document(user)
                                                                                        .collection("get")
                                                                                        .document(UUID.randomUUID().toString())
                                                                                        .set(transcation_model)
                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    progressHUD.dismiss();
                                                                                                    Toast.makeText(Add_invest.this, "Send to User Account", Toast.LENGTH_SHORT).show();
                                                                                                    startActivity(new Intent(getApplicationContext(), Admin_panel.class));
                                                                                                }

                                                                                            }
                                                                                        });

                                                                            }
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressHUD.dismiss();
                                                                    Toast.makeText(Add_invest.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressHUD.dismiss();
                                                Toast.makeText(Add_invest.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            Toast.makeText(Add_invest.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinnerTextSize) {
            valueFromSpinner = parent.getItemAtPosition(position).toString();
            //txtHelloWorld.setTextSize(Float.parseFloat(valueFromSpinner));
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
    private void upload_checker() {
        firebaseFirestore.collection("REques").document(Password_Log9.getText().toString().toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String methode=task.getResult().getString("methode");

                            if (methode.equals("Weekly")) {
                                total_day="7";
                            }
                            else  if (methode.equals("Half Month")) {
                                total_day="15";
                            }
                            else   if (methode.equals("Monthly")) {
                                total_day="30";
                            }
                            else   if (methode.equals("3 Month")) {
                                total_day="90";
                            }
                            else  if (methode.equals("6 Month")) {
                                total_day="180";
                            }
                            else  if (methode.equals("12 Month")) {
                                total_day="365";
                            }

                            String main__="654";
                            String date1;

                            String dat3e = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());
                            firebaseFirestore.collection("Package_date")
                                    .document(Password_Log9.getText().toString().toLowerCase())
                                    .update("date",dat3e)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            firebaseFirestore.collection("Package_date")
                                                    .document(Password_Log9.getText().toString().toLowerCase())
                                                    .update("time12",total_day)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                firebaseFirestore.collection("Package_date")
                                                                        .document(Password_Log9.getText().toString().toLowerCase())
                                                                        .update("geta",current1)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    Toast.makeText(Add_invest.this, "Done", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });

                                                            }

                                                        }
                                                    });

                                        }
                                    });


                        }

                    }
                });

    }

}