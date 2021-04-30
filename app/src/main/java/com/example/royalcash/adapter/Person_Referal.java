package com.example.royalcash.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royalcash.R;
import com.example.royalcash.model.Conforim_Investment1;
import com.example.royalcash.model.Person_to_person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Person_Referal extends RecyclerView.Adapter<Person_Referal.myView> {
    private List<Person_to_person> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public Person_Referal(List<Person_to_person> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.referal_list_person,parent,false);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        return new Person_Referal.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final myView holder, int position) {
        final String main_email=data.get(position).getMain_email();
        String sub_email=data.get(position).getSub_email();
        holder.ammount.setText("Referal By : \n"+main_email);
        holder.date.setText("Referal to : \n"+sub_email);
        firebaseFirestore.collection("User_Balance")
                .document(sub_email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            final String coin=task.getResult().getString("coin");



                                holder.Bonus.setText("Referal Balance : \n"+coin);

                        }
                    }
                });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder{
        TextView ammount,date,Bonus;
        public myView(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            ammount=itemView.findViewById(R.id.ammount);
            Bonus=itemView.findViewById(R.id.Bonus);

        }
    }
}
