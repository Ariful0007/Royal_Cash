package com.example.royalcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
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

import com.example.royalcash.model.Transcation_model;
import com.example.royalcash.model.Withdraw_model;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.UUID;

public class My_profit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private AdView mAdView;
    private Spinner spinnerTextSize;
    String valueFromSpinner;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText Password_Log,Password_Log1,Password_Log51;
    Button tmio_1;
    KProgressHUD progressHUD;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profit);
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
        //
        spinnerTextSize = findViewById(R.id.spinnerTextSize);
        spinnerTextSize.setOnItemSelectedListener(this);
        String[] textSizes = getResources().getStringArray(R.array.payment);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);
        //
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        Password_Log=findViewById(R.id.Password_Log);

        Password_Log51=findViewById(R.id.Password_Log51);
        progressHUD = KProgressHUD.create(My_profit.this);
        tmio_1=findViewById(R.id.tmio_1);
        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone,transcation,ammount;
                phone=Password_Log.getText().toString();
                ammount=Password_Log51.getText().toString();
                if (TextUtils.isEmpty(phone)  || TextUtils.isEmpty(valueFromSpinner)
                        || TextUtils.isEmpty(ammount) || phone.length()<11) {
                    Toast.makeText(My_profit.this, "Some fields are empty", Toast.LENGTH_SHORT).show();
                    return;

                }
                else {
                    final String option[]={"Main Balance","Investment","Profit"};
                    AlertDialog.Builder builder=new AlertDialog.Builder(My_profit.this);
                    builder.setTitle("Select a option");
                    builder.setCancelable(false);
                    builder.setItems(option, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(which==0) {
                                progress_check();
                                firebaseFirestore.collection("Main_balance_per_hand")
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    final String coin=task.getResult().getString("main_balance");
                                                    if (Integer.parseInt(coin)==0) {
                                                        Toast.makeText(My_profit.this, "Balance is empty", Toast.LENGTH_SHORT).show();
                                                        progressHUD.dismiss();
                                                        return;
                                                    }
                                                    else {
                                                        String uuid= UUID.randomUUID().toString();
                                                        message="New Withdraw Request Arrive.\n"+"Email : "+firebaseAuth.getCurrentUser().getEmail()
                                                                +"\nPhone Number : "+phone+"\nAmmount : "+ammount+"\nPayment Methode : "+valueFromSpinner+"\nThank You.";
                                                        Withdraw_model withdraw_model=new Withdraw_model(firebaseAuth.getCurrentUser().getEmail(),
                                                                ammount,valueFromSpinner,uuid);
                                                        firebaseFirestore.collection("Withdraw_Request")
                                                                .document(uuid)
                                                                .set(withdraw_model)
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
                                                                                                String coin2=task.getResult().getString("coin");
                                                                                                String then=Password_Log51.getText().toString();
                                                                                                int main=Integer.parseInt(coin2)-Integer.parseInt(then);

                                                                                                firebaseFirestore.collection("User_Balance")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .update("coin","0")
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    firebaseFirestore.collection("profit_package")
                                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                            .update("porfit","0")
                                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                @Override
                                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                    if (task.isSuccessful()) {
                                                                                                                                        textSend_user();
                                                                                                                                        progressHUD.dismiss();
                                                                                                                                    }

                                                                                                                                }
                                                                                                                            });
                                                                                                                }

                                                                                                            }
                                                                                                        });
                                                                                            }
                                                                                        }
                                                                                    });

                                                                        }
                                                                    }
                                                                });


                                                    }

                                                }

                                            }
                                        });

                            }
                            if (which==1) {
                                progress_check();
                                firebaseFirestore.collection("User_Balance")
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    final String coin=task.getResult().getString("coin");
                                                    if (Integer.parseInt(coin)==0) {
                                                        Toast.makeText(My_profit.this, "Investment is empty", Toast.LENGTH_SHORT).show();
                                                        progressHUD.dismiss();
                                                        return;
                                                    }
                                                    else if (Integer.parseInt(coin)>=Integer.parseInt(ammount)) {
                                                        String uuid= UUID.randomUUID().toString();
                                                        message="New Withdraw Request Arrive.\n"+"Email : "+firebaseAuth.getCurrentUser().getEmail()
                                                                +"\nPhone Number : "+phone+"\nAmmount : "+ammount+"\nPayment Methode : "+valueFromSpinner+"\nThank You.";
                                                        Withdraw_model withdraw_model=new Withdraw_model(firebaseAuth.getCurrentUser().getEmail(),
                                                                ammount,valueFromSpinner,uuid);
                                                        firebaseFirestore.collection("Withdraw_Request")
                                                                .document(uuid)
                                                                .set(withdraw_model)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            int fina=Integer.parseInt(coin)-Integer.parseInt(ammount);
                                                                            firebaseFirestore.collection("User_Balance")
                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                    .update("coin",""+fina)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if(task.isSuccessful()) {

                                                                                                textSend_user();
                                                                                                progressHUD.dismiss();

                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });


                                                    }

                                                }

                                            }
                                        });

                            }
                            else if(which==1) {
                                progress_check();
                                firebaseFirestore.collection("profit_package")
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    final String coin=task.getResult().getString("porfit");
                                                    if (Integer.parseInt(coin)==0) {
                                                        Toast.makeText(My_profit.this, "Profit is empty", Toast.LENGTH_SHORT).show();

                                                        progressHUD.dismiss();
                                                        return;
                                                    }
                                                    else if (Integer.parseInt(coin)>=Integer.parseInt(ammount)) {
                                                        String uuid= UUID.randomUUID().toString();
                                                        message="New Withdraw Request Arrive.\n"+"Email : "+firebaseAuth.getCurrentUser().getEmail()
                                                                +"\nPhone Number : "+phone+"\nAmmount : "+ammount+"\nPayment Methode : "+valueFromSpinner+"\nThank You.";
                                                        Withdraw_model withdraw_model=new Withdraw_model(firebaseAuth.getCurrentUser().getEmail(),
                                                                ammount,valueFromSpinner,uuid);
                                                        firebaseFirestore.collection("Withdraw_Request")
                                                                .document(uuid)
                                                                .set(withdraw_model)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            int fina=Integer.parseInt(coin)-Integer.parseInt(ammount);
                                                                            firebaseFirestore.collection("profit_package")
                                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                    .update("porfit",""+fina)
                                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                        @Override
                                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                                            if(task.isSuccessful()) {
                                                                                                //textSend_user();
                                                                                              //  progressHUD.dismiss();
                                                                                                Transcation_model transcation_model=new Transcation_model(firebaseAuth.getCurrentUser().getEmail(),"Withdraw",ammount);
                                                                                                firebaseFirestore.collection("Transcation_History")
                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                        .collection("get")
                                                                                                        .document(UUID.randomUUID().toString())
                                                                                                        .set(transcation_model)
                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                            @Override
                                                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                                                if (task.isSuccessful()) {
                                                                                                                    // textSend_user();
                                                                                                                    textSend_user();
                                                                                                                      progressHUD.dismiss();
                                                                                                                }

                                                                                                            }
                                                                                                        });

                                                                                            }
                                                                                        }
                                                                                    });
                                                                        }
                                                                    }
                                                                });


                                                    }

                                                }

                                            }
                                        });
                            }

                        }
                    });
                    builder.create().show();



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
    private void textSend_user() {
        int permission= ContextCompat.checkSelfPermission(My_profit.this, Manifest.permission.SEND_SMS);
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
        Toast.makeText(this, "Request Send", Toast.LENGTH_SHORT).show();
        progressHUD.dismiss();
        startActivity(new Intent(getApplicationContext(),MainActivity1.class));
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sending();
                    ;
                } else {
                    Toast.makeText(this, "Don't  Have permission", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }
    }