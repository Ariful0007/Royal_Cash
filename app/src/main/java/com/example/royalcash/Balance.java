package com.example.royalcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.royalcash.model.Main_balance;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Balance extends AppCompatActivity {
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;

    private Uri mainImageUri = null;

    private CircleImageView setupImage;
    private EditText setupName;
    private TextView changePhoto;
    private EditText setupSkills;
    private ImageView setupButton;
    private ProgressBar setupProgress;
    private Boolean isChanged = false;
    private String current_user_id;
    private EditText aboutYou;
    private EditText emailUser;
    private EditText phoneNumber;
    private Bitmap compressedUserImage;
    private Toolbar mToolbar;

    String user_id = "";

    //firebase
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseFirestoreSettings settings;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;


    private String intentThatStartedThisActivity;
    TextView changeProfilePhoto;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;//Firebase
    DatabaseReference mDatabase;
    Button signup_btn;
    private AdView mAdView;
    int sub_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setContentView(R.layout.activity_balance);
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
        mToolbar = findViewById(R.id.profileToolBar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Balance");
        actionBar.setElevation(10.0f);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
        firebaseDatabase = FirebaseDatabase.getInstance();
        changeProfilePhoto=findViewById(R.id.changeProfilePhoto);

        setupName = findViewById(R.id.setup_name);
        setupButton = findViewById(R.id.setup_button);
        signup_btn=findViewById(R.id.signup_btn);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity1.class));
            }
        });

        setupSkills = findViewById(R.id.setup_skill);
        setupImage = findViewById(R.id.setup_image);
        firebaseFirestore.collection("User_Balance").document(user_id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String name=task.getResult().getString("finalUserName");
                    setupName.setText(name);
                    String image=task.getResult().getString("toString");
                    Picasso.get().load(image)
                            .placeholder(R.drawable.default_pic)
                            .into(setupImage);
                    final String coin=task.getResult().getString("coin");
                    firebaseFirestore.collection("profit_package")
                            .document(firebaseAuth.getCurrentUser().getEmail())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        String profit=task.getResult().getString("porfit");

                                         sub_get=Integer.parseInt(profit)+Integer.parseInt(coin);
                                        setupSkills.setText(""+sub_get);
                                        Main_balance main_balance=new Main_balance(firebaseAuth.getCurrentUser().getEmail(),
                                                ""+sub_get);

                                        firebaseFirestore.collection("Main_balance_per_hand")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .set(main_balance)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity1.class));
    }
}
