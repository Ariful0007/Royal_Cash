package com.example.royalcash.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royalcash.R;
import com.example.royalcash.model.Refer_id;
import com.example.royalcash.model.Seconde_person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Request_ extends RecyclerView.Adapter<Request_.myView> {
    private List<Seconde_person> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public Request_(List<Seconde_person> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_request_item12,parent,false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new Request_.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
        String email=data.get(position).getUid();
        firebaseFirestore.collection("Users")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    }
                });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder{
        View mView;
        TextView userName, userStatus;
        CircleImageView userImage;
        Button yes,cancel;
        public myView(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            userName = (TextView) itemView.findViewById(R.id.request_name_frag);
            userStatus = (TextView) itemView.findViewById(R.id.reqTime);
            userImage = (CircleImageView) itemView.findViewById(R.id.userSingleImageFrag);
            yes=itemView.findViewById(R.id.accept_request_btn_frag);
            cancel=itemView.findViewById(R.id.cancel_request_btn_frag);
        }
    }
}
