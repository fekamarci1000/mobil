package com.example.csempe;


import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText userName;
    EditText password;

    EditText email;
    EditText password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
