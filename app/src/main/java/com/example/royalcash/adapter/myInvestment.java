package com.example.royalcash.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royalcash.R;
import com.example.royalcash.model.Conforim_Investment1;
import com.example.royalcash.model.Message_user;

import java.util.List;

public class myInvestment extends RecyclerView.Adapter<myInvestment.myView> {
    private List<Conforim_Investment1> data;

    public myInvestment(List<Conforim_Investment1> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.investment_list,parent,false);
        return new myInvestment.myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
        holder.ammount.setText(data.get(position).getAmmount());
        holder.date.setText(data.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder{
        TextView ammount,date;

        public myView(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            ammount=itemView.findViewById(R.id.ammount);
        }
    }

}
