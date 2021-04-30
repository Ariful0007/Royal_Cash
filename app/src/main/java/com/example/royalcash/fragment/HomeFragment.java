package com.example.royalcash.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.royalcash.MainActivity1;
import com.example.royalcash.R;
import com.example.royalcash.adapter.AllUserAdapter;
import com.example.royalcash.admin.Add_invest;
import com.example.royalcash.admin.Admin_panel;
import com.example.royalcash.model.Message_user;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.annotation.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;
    long date4;
    private Uri mainImageUri = null;

    private CircleImageView setupImage;
    private TextView setupName;
    private TextView changePhoto, setup1_ski1ll;
    private TextView setupSkills, setup_skill1, setup_ski1ll;
    private ImageView setupButton;
    private ProgressBar setupProgress;
    private Boolean isChanged = false;
    private String current_user_id;
    private EditText aboutYou;
    private EditText emailUser;
    private EditText phoneNumber;
    private Bitmap compressedUserImage;
    private Toolbar mToolbar;
    Date date221;
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
    String date1;
    String profit;

    long simpledate;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        AdView adView = new AdView(getContext());

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-7797874578001363/9666535984");
        MobileAds.initialize(getContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getEmail();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        firebaseFirestore.setFirestoreSettings(settings);
        firebaseDatabase = FirebaseDatabase.getInstance();
        setupName = view.findViewById(R.id.setup_name);
        setup_skill1 = view.findViewById(R.id.setup_skill1);
        setup_ski1ll = view.findViewById(R.id.setup_ski1ll);
        setup1_ski1ll = view.findViewById(R.id.setup1_ski1ll);


        setupSkills = view.findViewById(R.id.setup_skill);
        setupImage = view.findViewById(R.id.setup_image);
        firebaseFirestore.collection("User_Balance").document(user_id)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    String name = task.getResult().getString("finalUserName");
                    setupName.setText(name);
                    String image = task.getResult().getString("toString");
                    Picasso.get().load(image)
                            .placeholder(R.drawable.default_pic)
                            .into(setupImage);
                    String coin = task.getResult().getString("coin");
                    setup_ski1ll.setText(coin);
                }
            }
        });
        firebaseFirestore.collection("Package_date").document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String date = task.getResult().getString("geta");
                            setupSkills.setText(date);

                        }

                    }
                });
        firebaseFirestore.collection("REques")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            String methode = task.getResult().getString("methode");
                            setup_skill1.setText(methode);
                        }
                    }
                });
       /* //
        firebaseFirestore.collection("Package_date")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                             time22=task.getResult().getString("time12");

                            int counter=0;
                            int main_counter=Integer.parseInt(time22);
                            if (counter==Integer.parseInt(time22)) {
                                firebaseFirestore.collection("Package_date")
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .update("time12","0")
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Your Package Date is over.", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });
                            }
                            else if (main_counter<=0) {

                            }
                            else if (main_counter>1) {
                                String date = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());
                               // Toast.makeText(getContext(), ""+date, Toast.LENGTH_SHORT).show();
                                String date1="01/01/2030";
                                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");
                                try {
                                    Date date21=simpleDateFormat.parse(date);
                                    Date date22=simpleDateFormat.parse(date1);
                                    long simpledate=date21.getTime();
                                    long simpleDate2=date22.getTime();
                                    if (simpledate<=simpleDate2) {
                                       counter++;
                                       // Toast.makeText(getContext(), ""+counter, Toast.LENGTH_SHORT).show();
                                       firebaseFirestore.collection("User_Balance")
                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                               .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   String coin=task.getResult().getString("coin");
                                                   int main_account=Integer.parseInt(coin);
                                                   final int profit;


                                                   if (time22.equals("7")) {
                                                       profit=(main_account*7)/100;
                                                       firebaseFirestore.collection("profit_package")
                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           String ptofit1=task.getResult().getString("porfit");
                                                                           int main2=Integer.parseInt(ptofit1);
                                                                           int main_logic=main2+profit;
                                                                           setup1_ski1ll.setText(""+main_logic);
                                                                           firebaseFirestore.collection("profit_package")
                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                   .update("porfit",""+main_logic)
                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                       @Override
                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               Toast.makeText(getContext(), "Daily Invest Added", Toast.LENGTH_SHORT).show();
                                                                                           }
                                                                                       }
                                                                                   });
                                                                       }
                                                                   }
                                                               });


                                                   }
                                                   else if (time22.equals("15")) {
                                                       profit=(main_account*15)/100;
                                                       firebaseFirestore.collection("profit_package")
                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           String ptofit1=task.getResult().getString("porfit");
                                                                           int main2=Integer.parseInt(ptofit1);
                                                                           int main_logic=main2+profit;
                                                                           setup1_ski1ll.setText(""+main_logic);
                                                                           firebaseFirestore.collection("profit_package")
                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                   .update("porfit",""+main_logic)
                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                       @Override
                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               Toast.makeText(getContext(), "Daily Invest Added", Toast.LENGTH_SHORT).show();
                                                                                           }
                                                                                       }
                                                                                   });
                                                                       }
                                                                   }
                                                               });
                                                   }
                                                   else if (time22.equals("30")) {
                                                       profit=(main_account*30)/100;
                                                       firebaseFirestore.collection("profit_package")
                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           String ptofit1=task.getResult().getString("porfit");
                                                                           int main2=Integer.parseInt(ptofit1);
                                                                           int main_logic=main2+profit;
                                                                           setup1_ski1ll.setText(""+main_logic);
                                                                           firebaseFirestore.collection("profit_package")
                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                   .update("porfit",""+main_logic)
                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                       @Override
                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               Toast.makeText(getContext(), "Daily Invest Added", Toast.LENGTH_SHORT).show();
                                                                                           }
                                                                                       }
                                                                                   });
                                                                       }
                                                                   }
                                                               });
                                                   }
                                                   else if (time22.equals("90")) {
                                                       profit=(main_account*30)/100;
                                                       firebaseFirestore.collection("profit_package")
                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           String ptofit1=task.getResult().getString("porfit");
                                                                           int main2=Integer.parseInt(ptofit1);
                                                                           int main_logic=main2+profit;
                                                                           setup1_ski1ll.setText(""+main_logic);
                                                                           firebaseFirestore.collection("profit_package")
                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                   .update("porfit",""+main_logic)
                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                       @Override
                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               Toast.makeText(getContext(), "Daily Invest Added", Toast.LENGTH_SHORT).show();
                                                                                           }
                                                                                       }
                                                                                   });
                                                                       }
                                                                   }
                                                               });
                                                   }
                                                   else if (time22.equals("180")) {
                                                       profit=(main_account*30)/100;
                                                       firebaseFirestore.collection("profit_package")
                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           String ptofit1=task.getResult().getString("porfit");
                                                                           int main2=Integer.parseInt(ptofit1);
                                                                           int main_logic=main2+profit;
                                                                           setup1_ski1ll.setText(""+main_logic);
                                                                           firebaseFirestore.collection("profit_package")
                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                   .update("porfit",""+main_logic)
                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                       @Override
                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               Toast.makeText(getContext(), "Daily Invest Added", Toast.LENGTH_SHORT).show();
                                                                                           }
                                                                                       }
                                                                                   });
                                                                       }
                                                                   }
                                                               });
                                                   }
                                                   else if(time22.equals("365")) {
                                                       profit=(main_account*30)/100;
                                                       firebaseFirestore.collection("profit_package")
                                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                                               .get()
                                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                   @Override
                                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                       if (task.isSuccessful()) {
                                                                           String ptofit1=task.getResult().getString("porfit");
                                                                           int main2=Integer.parseInt(ptofit1);
                                                                           int main_logic=main2+profit;
                                                                           setup1_ski1ll.setText(""+main_logic);
                                                                           firebaseFirestore.collection("profit_package")
                                                                                   .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                   .update("porfit",""+main_logic)
                                                                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                       @Override
                                                                                       public void onComplete(@NonNull Task<Void> task) {
                                                                                           if (task.isSuccessful()) {
                                                                                               Toast.makeText(getContext(), "Daily Invest Added", Toast.LENGTH_SHORT).show();
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
                                       firebaseFirestore.collection("Package_date")
                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                               .update("time12",counter)
                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<Void> task) {
                                                       if (task.isSuccessful()) {
                                                       }

                                                   }
                                               });

                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }



                            }

                        }

                    }
                });
        return view;

    }


}*/
       firebaseFirestore.collection("Package_date")
               .document(firebaseAuth.getCurrentUser().getEmail())
               .get()
               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()) {
                           String time22;
                           time22=task.getResult().getString("time12");
                           int counter=0;
                           int main_counter=Integer.parseInt(time22);
                          // Toast.makeText(getContext(), ""+time22, Toast.LENGTH_SHORT).show();
                           if (counter==main_counter-1) {
                               firebaseFirestore.collection("Package_date")
                                       .document(firebaseAuth.getCurrentUser().getEmail())
                                       .update("time12","0")
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()) {
                                                   Toast.makeText(getContext(), "Your Package Date is over.", Toast.LENGTH_SHORT).show();
                                               }

                                           }
                                       });

                           }
                           else if (main_counter==0) {
                           }
                           else if (main_counter>5) {
                               String date = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());
                               // Toast.makeText(getContext(), ""+date, Toast.LENGTH_SHORT).show();
                               final String date1="20/05/2030";
                               final SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/mm/yyyy");

                               Date date21= null;
                               try {
                                   date21 = simpleDateFormat.parse(date);
                                   Date date22=simpleDateFormat.parse(date1);
                                    simpledate=date21.getTime();
                                   long simpleDate2=date22.getTime();
                                   if (simpledate<simpleDate2) {

                                    firebaseFirestore.collection("Package_date")
                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {


                                                        final String date=task.getResult().getString("date");
                                                        try {
                                                             date221=simpleDateFormat.parse(date);
                                                             date4=date221.getTime();
                                                        } catch (ParseException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if (date4!=simpledate) {
                                                            firebaseFirestore.collection("User_Balance")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                               final String coin=task.getResult().getString("coin");
                                                                                 final int main_get=Integer.parseInt(coin);
                                                                                Toast.makeText(getContext(), ""+coin, Toast.LENGTH_SHORT).show();
                                                                                firebaseFirestore.collection("profit_package")
                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                        .get()
                                                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                                if (task.isSuccessful()) {
                                                                                                    String profit=task.getResult().getString("porfit");
                                                                                                    int sub_get=Integer.parseInt(profit);
                                                                                                    final int daily_get=sub_get+((main_get*1)/100);
                                                                                                    firebaseFirestore.collection("profit_package")
                                                                                                            .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                            .update("porfit",""+daily_get)
                                                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                @Override
                                                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                                                    if (task.isSuccessful()) {
                                                                                                                        final String date9 = new SimpleDateFormat("dd/MM/YYYY", Locale.getDefault()).format(new Date());

                                                                                                                        firebaseFirestore.collection("Package_date")
                                                                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                                .update("date",date9)
                                                                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                    @Override
                                                                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                                                                        if (task.isSuccessful()) {
                                                                                                                                            setup1_ski1ll.setText(""+daily_get);
                                                                                                                                            Transcation_model transcation_model=new Transcation_model(firebaseAuth.getCurrentUser().getEmail(),"Daily Profit",""+daily_get);
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
                                                                                                                                                            }

                                                                                                                                                        }
                                                                                                                                                    });


                                                                                                                                            //Toast.makeText(getContext(), ""+date9, Toast.LENGTH_SHORT).show();
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
                                                        else {
                                                            firebaseFirestore.collection("profit_package")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .get()
                                                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                            if (task.isSuccessful()) {
                                                                                String profit=task.getResult().getString("porfit");
                                                                                setup1_ski1ll.setText(profit);

                                                                            }
                                                                        }
                                                                    });
                                                        }



                                                    }

                                                }
                                            });
                                   }
                               } catch (ParseException e) {
                                   e.printStackTrace();
                               }

                           }
                       }

                   }
               });
     /* firebaseFirestore.collection("counter").document(firebaseAuth.getCurrentUser().getEmail())
              .get()
              .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                      if(task.isSuccessful()) {
                          String counter=task.getResult().getString("counter");
                          if (Integer.parseInt(counter)==0) {
                              firebaseFirestore.collection("Refer")
                                      .document(firebaseAuth.getCurrentUser().getEmail())
                                      .collection("main")
                                      .document(firebaseAuth.getCurrentUser().getEmail())
                                      .get()
                                      .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                          @Override
                                          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                              if (task.isSuccessful()) {
                                                  String referal=task.getResult().getString("email");

                                                  firebaseFirestore.collection("User_Balance")
                                                          .document(firebaseAuth.getCurrentUser().getEmail())
                                                          .get()
                                                          .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                              @Override
                                                              public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                  if (task.isSuccessful()) {
                                                                      String coin=task.getResult().getString("coin");
                                                                      Toast.makeText(getContext(), ""+coin, Toast.LENGTH_SHORT).show();
                                                                      final int final_get=(Integer.parseInt(coin)*2)/100;
                                                                      int total=Integer.parseInt(coin)+final_get;
                                                                      firebaseFirestore.collection("User_Balance")
                                                                              .document(firebaseAuth.getCurrentUser().getEmail())
                                                                              .update("coin",""+total)
                                                                              .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                  @Override
                                                                                  public void onComplete(@NonNull Task<Void> task) {
                                                                                      if (task.isSuccessful()) {
                                                                                          Transcation_model transcation_model=new Transcation_model(firebaseAuth.getCurrentUser().getEmail(),"Referal Bonus",""+final_get)
                                                                                                  ;
                                                                                          firebaseFirestore.collection("Transcation_History")
                                                                                                  .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                  .collection("get")
                                                                                                  .document(UUID.randomUUID().toString())
                                                                                                  .set(transcation_model)
                                                                                                  .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                      @Override
                                                                                                      public void onComplete(@NonNull Task<Void> task) {
                                                                                                          if (task.isSuccessful()) {
                                                                                                              firebaseFirestore.collection("counter")
                                                                                                                      .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                      .update("counter","1")
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
                                                              }
                                                          });

                                              }

                                          }
                                      });



                          }
                          else {
                          }
                      }
                  }
              });*/


        return view;
    }
}