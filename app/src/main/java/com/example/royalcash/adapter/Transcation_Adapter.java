package com.example.royalcash.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royalcash.R;
import com.example.royalcash.model.Transcation_model;
import com.example.royalcash.model.Withdraw_model;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Transcation_Adapter extends RecyclerView.Adapter<Transcation_Adapter.myView>{
    private List<Transcation_model> data;
    FirebaseFirestore firebaseFirestore;

    public Transcation_Adapter(List<Transcation_model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw,parent,false);
        return new Transcation_Adapter.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
        String email=data.get(position).getEmail();
        String amount=data.get(position).getAmmount();
        String methode=data.get(position).getInvestment_type();
        holder.ammount.setText("Transcation Type : "+methode);
        holder.date.setText("Amount              : "+amount+" taka");

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
