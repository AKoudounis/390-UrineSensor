package com.example.myapplication;

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

public class MainActivity extends AppCompatActivity {
    private TextView sensorDataTextView; // TextView to display sensor data
    private DatabaseReference sensorDataRef; // Reference to the sensor data in the database
    private Button goToLoginButton; // Button to go to LoginActivity

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the sensorDataRef to the path where the sensor data is stored
        sensorDataRef = FirebaseDatabase.getInstance().getReference("Sensor/Analog_Value");

        sensorDataTextView = findViewById(R.id.DataTextView);
        goToLoginButton = findViewById(R.id.go_to_login_button);

        // Set click listener for the button to go to LoginActivity
        goToLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the LoginActivity when the button is clicked
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);
            }
        });

        // Read sensor data from the database and update the UI
        sensorDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the data exists
                if (dataSnapshot.exists()) {
                    // Get the sensor data value from the dataSnapshot
                    Double sensorValue = dataSnapshot.getValue(Double.class);

                    if (sensorValue != null) {
                        // Update the TextView with the sensor data
                        sensorDataTextView.setText("Sensor Data: " + sensorValue);
                    } else {
                        // Handle the case where the sensor data is null
                        sensorDataTextView.setText("Sensor Data: not found");
                    }
                } else {
                    // Handle the case where the data doesn't exist
                    sensorDataTextView.setText("Sensor Data: not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the read operation
                sensorDataTextView.setText("Sensor Data: error occurred");
            }
        });
    }
}
