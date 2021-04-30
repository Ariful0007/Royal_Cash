package com.example.royalcash.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.royalcash.Confrom_investment;
import com.example.royalcash.R;
import com.example.royalcash.model.Transcation_model;
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

public class Referal_Bonus extends AppCompatActivity {
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
        setContentView(R.layout.activity_referal__bonus);
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
        Password_Log1=findViewById(R.id.Password_Log51);
        progressHUD = KProgressHUD.create(Referal_Bonus.this);
        tmio_1=findViewById(R.id.tmio_1);
        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phone,transcation,ammount;
                phone=Password_Log.getText().toString();
                ammount=Password_Log1.getText().toString();
                if (TextUtils.isEmpty(phone)
                        || TextUtils.isEmpty(ammount) ) {
                    tmio_1.setError("Some fields are empty");
                    return;

                }
                else {
                    progress_check();
                    firebaseFirestore.collection("User_Balance")
                            .document(phone)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        final String coin=task.getResult().getString("coin");
                                        int main=Integer.parseInt(coin)+Integer.parseInt(ammount);
                                        firebaseFirestore.collection("User_Balance")
                                                .document(phone)
                                                .update("coin",""+main)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Transcation_model transcation_model=new Transcation_model(phone,"Referal Bonus",""+ammount);
                                                            firebaseFirestore.collection("Transcation_History")
                                                                    .document(phone)
                                                                    .collection("get")
                                                                    .document(UUID.randomUUID().toString())
                                                                    .set(transcation_model)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                // textSend_user();
                                                                                progressHUD.dismiss();
                                                                                startActivity(new Intent(getApplicationContext(),Admin_panel.class));
                                                                                finish();
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
    private void progress_check() {
        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
}