package com.example.royalcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.royalcash.adapter.Referal;
import com.example.royalcash.adapter.myInvestment;
import com.example.royalcash.model.Conforim_Investment1;
import com.example.royalcash.model.Counter;
import com.example.royalcash.model.Person_to_person;
import com.example.royalcash.model.Refer_id;
import com.example.royalcash.model.Seconde_person;
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
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public class Refer_List extends AppCompatActivity {
    private Spinner spinnerTextSize;
    String valueFromSpinner;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    EditText Password_Log,Password_Log1,Password_Log51;
    Button tmio_1;
    KProgressHUD progressHUD;
    String message;
    //
    DocumentReference documentReference;
    RecyclerView recyclerView;
    Referal getDataAdapter1;
    List<Refer_id> getList;

    Toolbar toolbar;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer__list);
        //
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


        progressHUD = KProgressHUD.create(Refer_List.this);
        tmio_1=findViewById(R.id.tmio_1);
        firebaseFirestore.collection("Single").
                document(firebaseAuth.getCurrentUser().getEmail()).collection("get")

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }


                        }

                    }
                });



        tmio_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=Password_Log.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Refer_List.this, "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    progress_check();
                    final Refer_id id=new Refer_id(email);
                    firebaseFirestore.collection("Refer")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .collection("main")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .set(id)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Counter counter=new Counter(firebaseAuth.getCurrentUser().getEmail(),"0");
                                        firebaseFirestore.collection("counter")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .set(counter)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {

                                                           /* firebaseFirestore.collection("Single")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .collection("get")
                                                                    .document(UUID.randomUUID().toString())
                                                                    .set(id)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                progressHUD.dismiss();
                                                                                Toast.makeText(Refer_List.this, "Person Added", Toast.LENGTH_SHORT).show();
                                                                                startActivity(new Intent(getApplicationContext(),MainActivity1.class));
                                                                                finish();
                                                                            }
                                                                        }
                                                                    });*/

                                                                               firebaseFirestore.collection("Referals")
                                                                                       .document(email)
                                                                                       .get()
                                                                                       .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                           @Override
                                                                                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                               if (task.isSuccessful())
                                                                                               {
                                                                                                   DocumentSnapshot document = task.getResult();
                                                                                                   if (document.exists()) {
                                                                                                       progressHUD.dismiss();
                                                                                                       Toast.makeText(Refer_List.this, "This user already on referal", Toast.LENGTH_SHORT).show();
                                                                                                       startActivity(new Intent(getApplicationContext(),MainActivity1.class));
                                                                                                       finish();
                                                                                                   }
                                                                                                   else {
                                                                                                       firebaseFirestore.collection("Single")
                                                                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                               .collection("get")
                                                                                                               .document(UUID.randomUUID().toString())
                                                                                                               .set(id)
                                                                                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                   @Override
                                                                                                                   public void onComplete(@NonNull Task<Void> task) {
                                                                                                                       if (task.isSuccessful()) {
                                                                                                                           Seconde_person seconde_person=new Seconde_person(firebaseAuth.getCurrentUser().getEmail(),firebaseAuth.getCurrentUser().getUid());
                                                                                                                           firebaseFirestore.collection("Refer_person_2nd")
                                                                                                                                   .document(email)
                                                                                                                                   .set(seconde_person)
                                                                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                       @Override
                                                                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                           if (task.isSuccessful()) {
                                                                                                                                               Person_to_person person_to_person=new Person_to_person(firebaseAuth.getCurrentUser().getEmail(),email);
                                                                                                                                               firebaseFirestore.collection("Peron_selector")
                                                                                                                                                       .document("arifulpub143t@gmail.com")
                                                                                                                                                       .collection("user")
                                                                                                                                                       .document(UUID.randomUUID().toString())
                                                                                                                                                       .set(person_to_person)
                                                                                                                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                           @Override
                                                                                                                                                           public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                               if (task.isSuccessful()) {
                                                                                                                                                                   firebaseFirestore.collection("Referals")
                                                                                                                                                                           .document(email)
                                                                                                                                                                           .set(id)
                                                                                                                                                                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                                                               @Override
                                                                                                                                                                               public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                                                                   if (task.isSuccessful()) {
                                                                                                                                                                                       progressHUD.dismiss();
                                                                                                                                                                                       Toast.makeText(Refer_List.this, "Person Added", Toast.LENGTH_SHORT).show();
                                                                                                                                                                                       startActivity(new Intent(getApplicationContext(),MainActivity1.class));
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
                                                                                               }
                                                                                           }
                                                                                       });

                                                        }

                                                    }
                                                });

                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressHUD.dismiss();
                            Toast.makeText(Refer_List.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });



                }
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        getList=new ArrayList<>();
        getDataAdapter1=new Referal(getList);
        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("Single").document(firebaseAuth.getCurrentUser().getEmail())
                .collection("get").document();

        recyclerView=findViewById(R.id.blog_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Refer_List.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();

    }
    private void reciveData() {
        firebaseFirestore.collection("Single").document(firebaseAuth.getCurrentUser().getEmail())
                .collection("get").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds:queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType()== DocumentChange.Type.ADDED) {
                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Refer_id get=ds.getDocument().toObject(Refer_id.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }
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