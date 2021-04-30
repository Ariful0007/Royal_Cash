package com.example.royalcash.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.royalcash.R;
import com.example.royalcash.model.Message_user;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.myView> {
    private List<Message_user> data;

    public AllUserAdapter(List<Message_user> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public myView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_request_item,parent,false);
        return new myView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myView holder, int position) {
        holder.request_name_frag.setText(data.get(position).getFinalUserName());
        String image=data.get(position).getImage();
        try {
            Picasso.get().load(image)
                    .placeholder(R.drawable.default_avatar)
                    .into(holder.imageView);
        } catch (Exception e) {
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myView extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView request_name_frag,reqTime;

        public myView(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.userSingleImageFrag);
            request_name_frag=itemView.findViewById(R.id.request_name_frag);
            reqTime=itemView.findViewById(R.id.reqTime);
        }
    }
}