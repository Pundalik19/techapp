package com.example.techapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class useradapter extends RecyclerView.ViewHolder {

    TextView firstname;
    TextView lastname;
    TextView email;
    TextView srno;
    ImageView image;
    View view;

    public useradapter(@NonNull View itemView) {
        super(itemView);
        email
                = (TextView)itemView
                .findViewById(R.id.email);
        firstname
                = (TextView)itemView
                .findViewById(R.id.firstname);
        lastname
                = (TextView)itemView
                .findViewById(R.id.lastname);
        srno
                = (TextView)itemView
                .findViewById(R.id.srno);
        image
                = (ImageView)itemView
                .findViewById(R.id.imageView);


        view = itemView;
    }
}
