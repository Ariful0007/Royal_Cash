package com.example.royalcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.royalcash.model.Conforim_Investment1;
import com.example.royalcash.model.Transcation_model;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
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

public class Confrom_investment extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinnerTextSize;
    String valueFromSpinner;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText Password_Log,Password_Log1,Password_Log51;
    Button tmio_1;
    String message;
    KProgressHUD progressHUD;
    String current1;
    String total_day;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrom_investment);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                //oast.makeText(MainActivity.this, "dfgdg", Toast.LENGTH_SHORT).show();
            }
        });
        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-7797874578001363/9666535984");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        Password_Log=findViewById(R.id.Password_Log);
        Password_Log1=findViewById(R.id.Password_Log1);
        Password_Log51=findViewById(R.id.Password_Log51);

        progressHUD = KProgressHUD.create(Confrom_investment.this);
        tmio_1=findViewById(R.id.tmio_1);
        Calendar calendar=Calendar.getInstance();
        String current= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        current1= DateFormat.getDateInstance().format(calendar.getTime());

        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone,transcation,ammount;
                phone=Password_Log.getText().toString();
                transcation=Password_Log1.getText().toString();
                ammount=Password_Log51.getText().toString();
                if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(transcation) || TextUtils.isEmpty(valueFromSpinner)
               || TextUtils.isEmpty(ammount) ) {
                    tmio_1.setError("Some fields are empty");
                    return;

                }
             else {
               // progress_check();
                 //upload_checker();



                    message = "New Investment Conformed: " + "\nEmail : " + firebaseAuth.getCurrentUser().getEmail()
                            + "\nPhone Number : " + phone + "\nPayment Methode : " + valueFromSpinner + "\nTranscation ID: " + transcation + "\nThank you.";
                   textSend_user();

                   /* String user = firebaseAuth.getCurrentUser().getEmail().toString();
                    Conforim_Investment1 conforim_investment1 = new Conforim_Investment1(user, phone, transcation, valueFromSpinner, current1, ammount);
                    firebaseFirestore.collection("Conform_investment_temprary")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .collection("Conform")
                            .document(ammount)
                            .set(conforim_investment1)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        firebaseFirestore.collection("User_Balance")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
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
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .update("coin", "" + am)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                progressHUD.dismiss();
                                                                                textSend_user();
                                                                                Toast.makeText(Confrom_investment.this, "Send to admin", Toast.LENGTH_SHORT).show();
                                                                                startActivity(new Intent(getApplicationContext(), MainActivity1.class));
                                                                            }
                                                                        }
                                                                    }).addOnFailureListener(new OnFailureListener() {
                                                                @Override
                                                                public void onFailure(@NonNull Exception e) {
                                                                    progressHUD.dismiss();
                                                                    Toast.makeText(Confrom_investment.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressHUD.dismiss();
                                                Toast.makeText(Confrom_investment.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            Toast.makeText(Confrom_investment.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
*/
                }

            }
        });

        spinnerTextSize = findViewById(R.id.spinnerTextSize);
        spinnerTextSize.setOnItemSelectedListener(this);
        String[] textSizes = getResources().getStringArray(R.array.payment);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);
    }

    private void upload_checker() {
        firebaseFirestore.collection("REques").document(firebaseAuth.getCurrentUser().getEmail())
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

                            String main__=total_day;
                            String date1;

                            String dat3e = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());
                            firebaseFirestore.collection("Package_date")
                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                    .update("date",dat3e)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            firebaseFirestore.collection("Package_date")
                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                    .update("time12",total_day)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                firebaseFirestore.collection("Package_date")
                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                        .update("geta",current1)
                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    Toast.makeText(Confrom_investment.this, "Done", Toast.LENGTH_SHORT).show();
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
    private void textSend_user() {
        int permission= ContextCompat.checkSelfPermission(Confrom_investment.this, Manifest.permission.SEND_SMS);
        if (permission== PackageManager.PERMISSION_GRANTED) {
            sending();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},0);
        }
    }
    private void sending() {

        ///+66
        String phone_number1233="+8801975488634";
        String sm333s=message;
        SmsManager smsManager=SmsManager.getDefault();
        smsManager.sendTextMessage(phone_number1233,null,sm333s,null,null);
        Toast.makeText(this, " Invest Conformation Sent To Admin", Toast.LENGTH_SHORT).show();
        progressHUD.dismiss();
        startActivity(new Intent(getApplicationContext(),MainActivity1.class));
        finish();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 0:
                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    sending();;
                }
                else {
                    Toast.makeText(this, "Don't  Have permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

}