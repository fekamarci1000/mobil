package com.example.csempe;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG = RegisterActivity.class.getSimpleName();
    EditText userName;
    EditText password;
    EditText email;
    EditText password2;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RegisterActivity", "onCreate");
        setContentView(R.layout.register);
        auth = FirebaseAuth.getInstance();
    }
    public void register(View view){
        userName = findViewById(R.id.editTextUsernameReg);
        password = findViewById(R.id.editTextPasswordReg);
        password2 = findViewById(R.id.editTextPasswordReg2);
        email = findViewById(R.id.editTextEmailReg);

        String userNameStr = String.valueOf(userName.getText());
        String passwordStr = String.valueOf(password.getText());
        String password2Str = String.valueOf(password2.getText());
        String emailStr = String.valueOf(email.getText());
        Log.d("RegisterActivity", "Username: " + userNameStr);

        if (TextUtils.isEmpty(userNameStr) || TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(passwordStr) || TextUtils.isEmpty(password2Str)) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            Log.d("RegisterActivity222", "Username: " + userNameStr);
            return;
        }

        if (!passwordStr.equals(password2Str)){
            Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            password.setText("");
            password2.setText("");
            return;
        }
        auth.createUserWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    navigateToWebshop( );
                }
                else {
                    Toast.makeText(RegisterActivity.this, "User wasn't created successfully: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void navigateToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void navigateToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void navigateToWebshop(/*View view*/){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOG_TAG, "onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "onDestroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
    }
}
