package com.example.royalcash.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royalcash.R;
import com.example.royalcash.admin.Admin_panel;
import com.example.royalcash.model.Conforim_Investment1;
import com.example.royalcash.model.Withdraw_model;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Withdraw_Adapter extends RecyclerView.Adapter<Withdraw_Adapter.myView> {
    private List<Withdraw_model> data;
    FirebaseFirestore firebaseFirestore;

    public Withdraw_Adapter(List<Withdraw_model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw,parent,false);
        return new Withdraw_Adapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, final int position) {
        String email=data.get(position).getEmail();
        String amount=data.get(position).getAmmount();
        String methode=data.get(position).getMethode();
        holder.ammount.setText("Email : "+email);
        holder.date.setText("Amount : "+amount);
        firebaseFirestore=FirebaseFirestore.getInstance();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                androidx.appcompat.app.AlertDialog.Builder warning = new AlertDialog.Builder(v.getContext());
                warning.setTitle("Delete.");
                warning.setMessage("Delete this user");
                warning.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        firebaseFirestore.collection("Withdraw_Request")
                                .document(data.get(position).getUuid()).delete();
                        v.getContext().startActivity(new Intent(v.getContext(), Admin_panel.class));
                    }
                });
                warning.create().show();

            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder{
        TextView ammount,date,methode;

        public myView(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            ammount=itemView.findViewById(R.id.ammount);


        }
    }
}
