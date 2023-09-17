package com.example.myapplication;// In your SecondActivity.java

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Retrieve the data from the Intent
        String receivedData = getIntent().getStringExtra("data_key");

        // Find the TextView in the layout
        TextView dataTextView = findViewById(R.id.DataTextView);

        // Display the received data in the TextView
        if (receivedData != null) {
            dataTextView.setText("Data from First Activity: " + receivedData);
        }
    }
}


