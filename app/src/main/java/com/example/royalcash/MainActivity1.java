package com.example.royalcash;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.royalcash.fragment.HomeFragment;
import com.example.royalcash.fragment.RequestsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
private Toolbar mainToolbar;
private String current_user_id;
private BottomNavigationView mainBottomNav;
private DrawerLayout mainDrawer;
private ActionBarDrawerToggle mainToggle;
private NavigationView mainNav;

        FrameLayout frameLayout;
private TextView drawerName;
private CircleImageView drawerImage;
        FirebaseAuth firebaseAuth;
//firebase
private FirebaseAuth mAuth;
private FirebaseFirestore firebaseFirestore;
private FirebaseFirestoreSettings settings;
private DatabaseReference mUserRef;
private HomeFragment homeFragment;
private RequestsFragment requestsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        Toolbar toolbar = findViewById(R.id.toolbar);
        mAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        toolbar.setTitle("Royal Cash");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        mainDrawer=findViewById(R.id.main_activity);
        mainNav = findViewById(R.id.main_nav);
        mainNav.setNavigationItemSelectedListener(this);
        frameLayout=findViewById(R.id.main_layout);
        mainToggle = new ActionBarDrawerToggle(this,mainDrawer,toolbar,R.string.open,R.string.close);
        mainDrawer.addDrawerListener(mainToggle);
        mainToggle.setDrawerIndicatorEnabled(true);
        mainToggle.syncState();
        mainBottomNav = findViewById(R.id.mainBottomNav);
        //BottomNavigationViewHelper.disableShiftMode(mainBottomNav);

        mainBottomNav.setOnNavigationItemSelectedListener(selectlistner);
        if (mAuth.getCurrentUser() != null) {
            mainNav = findViewById(R.id.main_nav);
            View headerView = mainNav.getHeaderView(0);
            drawerName = headerView.findViewById(R.id.nav_name);
            drawerImage = headerView.findViewById(R.id.nav_image);



            settings = new FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build();

            firebaseFirestore.setFirestoreSettings(settings);
            //fragment
            homeFragment = new HomeFragment();
            requestsFragment = new RequestsFragment();

            requestsFragment = new RequestsFragment();

            firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid())
                    .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w("MAIN_NAV", "listen:error", e);
                                return;
                            }
                            if (documentSnapshot.exists()) {

                                String image = documentSnapshot.getString("image");

                                drawerName.setText(documentSnapshot.getString("name"));
                            }
                        }
                    });
            initializeFragment();
            String token_id = FirebaseInstanceId.getInstance().getToken();
            Map<String,Object> tokenMap = new HashMap<>();
            tokenMap.put("token_id",token_id);
            Log.i("token","Map made uploading .....");
            firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid())
                    .update(tokenMap).addOnCompleteListener(MainActivity1.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        Log.i("token","updated");
                    } else {
                        Log.i("token","failed "+task.getException().getMessage());
                    }
                }
            });

            mainNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.action_find:

                            return true;


                        //Do some thing here
                        case R.id.action_following:
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                            return true;

                        case R.id.action_followers:
                            startActivity(new Intent(getApplicationContext(),Balance.class));
                            return true;
                        case R.id.investment:
                            startActivity(new Intent(getApplicationContext(),Investment.class));
                            return true;
                        case R.id.confrm:
                            startActivity(new Intent(getApplicationContext(),Confrom_investment.class));
                            return true;
                        case R.id.my_investment:
                            startActivity(new Intent(getApplicationContext(),My_Investment.class));
                            return true;
                        case R.id.profit:
                            startActivity(new Intent(getApplicationContext(),My_profit.class));
                            return true;
                        case R.id.contact:
                            startActivity(new Intent(getApplicationContext(),About_US.class));
                            return true;
                        case R.id.history:
                            startActivity(new Intent(getApplicationContext(),TranscationHistory.class));
                            return true;
                        case R.id.refer:
                            startActivity(new Intent(getApplicationContext(),Refer_List.class));
                            return true;
                    }
                    return false;
                }
            });
            mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.bottom_home:
                            replaceFragment(homeFragment);
                            return true;
                        case R.id.bottom_requests:
                            replaceFragment(requestsFragment);
                            return true;


                        default:
                            return false;
                    }
                }
            });

            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(mAuth.getCurrentUser().getUid());

        }


    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectlistner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.bottom_home:
                            HomeFragment fragment2 = new HomeFragment();
                            FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                            ft2.replace(R.id.content, fragment2, "");
                            ft2.commit();
                            break;


                    }
                    return false;
                }
            };

    private void sendToFollow(String data, String userid) {
       /* Intent intent = new Intent(MainActivity.this, FollowActivity.class);
        intent.putExtra("title",data);
        intent.putExtra("whichUser",userid);
        startActivity(intent);*/
    }
    public void initializeFragment(){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container,homeFragment);
        fragmentTransaction.add(R.id.main_container,requestsFragment);

        fragmentTransaction.hide(requestsFragment);



        fragmentTransaction.commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mainToggle.onOptionsItemSelected(item)){
            return true;
        }

        switch (item.getItemId()){

            case R.id.action_logout:
                logout();

                return true;



            default:
                return false;

        }

    }
    private void logout() {
        Map<String,Object> tokenMapRemove = new HashMap<>();
        tokenMapRemove.put("token_id", FieldValue.delete());
        firebaseFirestore.collection("Users").document(mAuth.getCurrentUser().getUid())
                .update(tokenMapRemove).addOnCompleteListener(MainActivity1.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    mAuth.signOut();
                    sendToLogin();
                } else {
                    Toast.makeText(MainActivity1.this,"Error logging out, please check your connection."
                            ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendToLogin(){
        Intent intent = new Intent(MainActivity1.this,Welcome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAuth.getCurrentUser()!=null){
            mUserRef.child("online").setValue("true");
        }
    }
    @Override
    public void onBackPressed() {
        if (this.mainDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mainDrawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            AlertDialog.Builder warning = new AlertDialog.Builder(this)
                    .setTitle("Do you want to exit ?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(final DialogInterface dialog, int which) {


                            dialog.dismiss();
                            finishAffinity();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // ToDO: delete all the notes created by the Anon user

                            // TODO: delete the anon user

                            dialog.dismiss();
                        }
                    });

            warning.show();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null){
            sendToLogin();
        } else {


            current_user_id = mAuth.getCurrentUser().getUid();
            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()){
                        if (!task.getResult().exists()){

                            Intent setupIntent = new Intent(MainActivity1.this,SetupActivity.class);
                            setupIntent.putExtra("whichState","not_setup");
                            startActivity(setupIntent);
                            finish();

                        }
                    } else {
                        Snackbar.make(findViewById(R.id.main_activity),task.getException().getMessage(),Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
        }
        mUserRef.child("online").setValue("true").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("online","updated");
            }
        });


    }
    private void replaceFragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (fragment == homeFragment){
            fragmentTransaction.hide(requestsFragment);


        } else if (fragment == requestsFragment){
            fragmentTransaction.hide(homeFragment);

        }

        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }


}