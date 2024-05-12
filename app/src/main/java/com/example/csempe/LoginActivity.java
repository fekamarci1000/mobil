package com.example.csempe;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginActivity extends AppCompatActivity {
    private static final String LOG_TAG = LoginActivity.class.getName();
    EditText password;
    EditText email;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ConstraintLayout loginContainer = findViewById(R.id.main);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        loginContainer.startAnimation(animation);
        auth= FirebaseAuth.getInstance();
    }
    public void login(View view){
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);

        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        if (TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr)) {
            Toast.makeText(LoginActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }


        auth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    navigateToWebshop();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    public void loginAnonymous(View view){
        auth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(LOG_TAG, "Anonymous loged in succesfully");
                    navigateToWebshop();
                }
                else{
                    Log.d(LOG_TAG, "Login is faild");
                    Toast.makeText(LoginActivity.this, "User can't signed in: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void navigateToWebshop() {
        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
        startActivity(intent);
    }


    public void navigateToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void navigateToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void navigateToLogin(View view) {
        setContentView(R.layout.login);
    }
}
