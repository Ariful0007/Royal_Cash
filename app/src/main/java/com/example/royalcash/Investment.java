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

import com.example.royalcash.model.Give_Request;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.text.DateFormat;
import java.util.Calendar;

public class Investment extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinnerTextSize;
    String valueFromSpinner;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText Password_Log,Password_Log1,ammount;
    Button tmio_1;
    String current1;
    String message;
    KProgressHUD progressHUD;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investment);
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
        spinnerTextSize = findViewById(R.id.spinnerTextSize);
        spinnerTextSize.setOnItemSelectedListener(this);
        progressHUD = KProgressHUD.create(Investment.this);
        String[] textSizes = getResources().getStringArray(R.array.package_investmtn);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, textSizes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTextSize.setAdapter(adapter);
        Password_Log=findViewById(R.id.Password_Log);
        Password_Log1=findViewById(R.id.Password_Log1);
        ammount=findViewById(R.id.ammount);
        tmio_1=findViewById(R.id.tmio_1);
        Calendar calendar=Calendar.getInstance();
        String current= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        current1= DateFormat.getDateInstance().format(calendar.getTime());

        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,name,phone,methode,ammount_in;
                email=firebaseAuth.getCurrentUser().getEmail().toString();
                name=Password_Log.getText().toString();
                phone=Password_Log1.getText().toString();
                methode=valueFromSpinner;
                ammount_in=ammount.getText().toString();
                if (TextUtils.isEmpty(email)||TextUtils.isEmpty(name)|| TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(methode)||TextUtils.isEmpty(ammount_in)) {
                    tmio_1.setError("Some Fields are empty");
                    return;
                }
                else {
                    message="New Investment Application Arrive : "+"Name : "+name+"\nPhone Number : "
                            +phone+"\nMethode : "+valueFromSpinner+"\nInvest Amount : "+ammount_in+"\nThank You";
                    int main_ammount=Integer.parseInt(ammount_in);
                    if (main_ammount<100 || main_ammount>20000) {
                        Toast.makeText(Investment.this, "Minimum amount is 100 taka \n and Maximum is 20000 taka", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        progress_check();

                        Give_Request give_request=new Give_Request(firebaseAuth.getCurrentUser().getEmail(),
                                name,phone,methode,ammount_in,current1);
                        firebaseFirestore.collection("REques")
                                .document(firebaseAuth.getCurrentUser().getEmail())
                                .set(give_request)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressHUD.dismiss();

                                            AlertDialog.Builder warning = new AlertDialog.Builder(Investment.this);
                                            warning.setTitle("Admin Information.");
                                            warning.setMessage("Admin Phoone Number: 01975488634");
                                            warning.setCancelable(false);
                                            warning.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    textSend_user();
                                                    progressHUD.dismiss();
                                                    dialog.dismiss();

                                                }
                                            });
                                            warning.create().show();

                                        }

                                    }
                                });


                    }


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
        int permission= ContextCompat.checkSelfPermission(Investment.this, Manifest.permission.SEND_SMS);
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
        Toast.makeText(this, "Request  Sent", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MainActivity1.class));
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