package com.example.csempe;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int SECRET_KEY = 99;
    EditText userName;
    EditText password;
    EditText email;
    EditText password2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ConstraintLayout mainLayout = findViewById(R.id.main);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        if (animation != null) {
            Log.d(LOG_TAG, "Animation loaded successfully");
            mainLayout.startAnimation(animation);
        } else {
            Log.w(LOG_TAG, "Failed to load animation resource");
        }

        if (animation != null) {
            Log.d(LOG_TAG, "Animation loaded successfully");
            mainLayout.startAnimation(animation);
        } else {
            Log.d("Animation", "Failed to load animation resource");
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void navigateToWebshop(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void navigateToHome(View view){
        setContentView(R.layout.activity_main);
    }

    public void navigateToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}