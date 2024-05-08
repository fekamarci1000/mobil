package com.example.csempe;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class RegisterActivity extends AppCompatActivity {
    EditText userName;
    EditText password;
    EditText email;
    EditText password2;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int secret_key = getIntent().getIntExtra("SECRET_KEY",0);
        if (secret_key != 99){
            finish();
        }
        setContentView(R.layout.register);
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

        if (!passwordStr.equals(password2Str)){
            //TODO
        }
    }
}
