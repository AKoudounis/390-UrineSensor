package com.example.myapplication;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private WebView chartWebView;
    private DatabaseReference sensorDataRef; // Reference to the sensor data in the database

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize the WebView
        chartWebView = findViewById(R.id.chartWebView);
        chartWebView.setWebViewClient(new WebViewClient());

        // Enable JavaScript in the WebView
        WebSettings webSettings = chartWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Retrieve sensor data from Firebase and update the chart
        sensorDataRef = FirebaseDatabase.getInstance().getReference("Sensor/Analog_Value");
        sensorDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the data exists
                if (dataSnapshot.exists()) {
                    // Get the sensor data value from the dataSnapshot
                    Double sensorValue = dataSnapshot.getValue(Double.class);

                    if (sensorValue != null) {
                        // Load the HTML page for chart rendering with the sensor data
                        String htmlData = generateChartHTML(sensorValue);
                        chartWebView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors that occur during the read operation
            }
        });
    }

    private String generateChartHTML(Double sensorData) {
        // Here's a simple HTML content with JavaScript to render a bar chart
        // You can customize this code to create your desired chart
        String htmlContent = "<html><head>" +
                "<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.7.0/chart.min.js'></script>" +
                "</head><body>" +
                "<canvas id='myChart'></canvas>" +
                "<script>" +
                "var ctx = document.getElementById('myChart').getContext('2d');" +
                "var chart = new Chart(ctx, {" +
                "type: 'bar'," +
                "data: {" +
                "labels: ['Sensor Data']," +
                "datasets: [{" +
                "label: 'Sensor Data'," +
                "data: [" + sensorData + "]," + // Use the sensor data here
                "backgroundColor: 'rgba(75, 192, 192, 0.2)'," +
                "borderColor: 'rgba(75, 192, 192, 1)'," +
                "borderWidth: 1" +
                "}]" +
                "}," +
                "options: {" +
                "scales: {" +
                "y: {" +
                "beginAtZero: true" +
                "}" +
                "}" +
                "}" +
                "});" +
                "</script>" +
                "</body></html>";
        return htmlContent;
    }
}




