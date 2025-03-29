package com.example.testapp1;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button btnAddStudent = findViewById(R.id.btnAddStudent);
        Button btnViewStudents = findViewById(R.id.btnViewStudents);
        Button btnExit = findViewById(R.id.btnExit);

        // Initialize and animate the logo
        ImageView logoImageView = findViewById(R.id.logoImageView);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.logo_animation);
        logoImageView.startAnimation(animation);

        // Add button click listeners here
        btnExit.setOnClickListener(v -> finish());
    }
} 