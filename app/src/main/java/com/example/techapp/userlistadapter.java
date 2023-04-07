package com.example.techapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class userlistadapter extends RecyclerView.Adapter<useradapter> {

    List<users> list;
    Context context;

    public userlistadapter(List<users> list,Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public useradapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context
                = parent.getContext();
        LayoutInflater inflater
                = LayoutInflater.from(context);

        View photoView
                = inflater
                .inflate(R.layout.consumer_card,
                        parent, false);

        useradapter viewHolder
                = new useradapter(photoView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull useradapter viewHolder, int position) {
        final int index = viewHolder.getAdapterPosition();

        int id = list.get(position).id;
        String email = list.get(position).email;
        String first_name = list.get(position).first_name;
        String last_name = list.get(position).last_name;
        String avatar = list.get(position).avatar;

        viewHolder.srno.setText(id+" ");
        viewHolder.email.setText(email);
        viewHolder.firstname.setText(first_name);
        viewHolder.lastname.setText(" "+last_name);

        Picasso.with(context).load(avatar).error( R.drawable.ic_launcher_foreground ).into(viewHolder.image);

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent bilform = new Intent(context, profile.class);
                bilform.putExtra("id",id);
                bilform.putExtra("email",email);
                bilform.putExtra("first_name",first_name);
                bilform.putExtra("last_name",last_name);
                bilform.putExtra("avatar",avatar);
                bilform.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(bilform);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(
            RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
