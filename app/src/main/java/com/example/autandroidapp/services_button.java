package com.example.autandroidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.autandroidapp.*;

public class services_button extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_button);

        Intent intent = getIntent();
    }

    public void medicalActivity(View view)
    {
        Intent intent = new Intent(this, medical_button.class);
        startActivity(intent);
    }
}
