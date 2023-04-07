package com.example.techapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        TextView name = (TextView) findViewById(R.id.user_name);
        TextView email = (TextView) findViewById(R.id.emailid);
        ImageView photo = (ImageView) findViewById(R.id.photo);


        Bundle extras = getIntent().getExtras();
        String emailid="";
        String user_name="";
        String photopath="";

        if (extras != null) {
            emailid = extras.getString("email");
            user_name = extras.getString("first_name")+" "+extras.getString("last_name");
            photopath = extras.getString("avatar");

        }

        name.setText(user_name);

        email.setText(emailid);

        Picasso.with(profile.this).load(photopath).error( R.drawable.ic_launcher_foreground ).into(photo);

    }
}