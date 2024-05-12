package com.example.csempe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView userEmailTextView;
    private EditText newEmailEditText;
    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private Button changePasswordButton;
    private Button deleteAccountButton;
    private Button editEmailButton;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        toolbar = findViewById(R.id.toolbar);
        userEmailTextView = findViewById(R.id.user_email);
        currentPasswordEditText = findViewById(R.id.current_password);
        newPasswordEditText = findViewById(R.id.new_password);
        changePasswordButton = findViewById(R.id.change_password_button);
        deleteAccountButton = findViewById(R.id.delete_account_button);

        // Set toolbar (optional)
        setSupportActionBar(toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Access user information
            userEmailTextView.setText(user.getEmail());
        } else {

        }

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle change password functionality
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();

                // Implement logic to validate passwords and update password
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ProfileActivity.this, "Jelszó sikeresen megváltoztatva", Toast.LENGTH_SHORT).show();
                                        navigateToHome();
                                    } else {
                                        Toast.makeText(ProfileActivity.this, "A jelszó megváltoztatása sikertelen", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete account functionality
                // Implement confirmation dialog and logic to delete account

                // Create an AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Delete Profile");
                builder.setMessage("Are you sure you want to delete your profile?");

                // Set the positive button
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete the user account
                        FirebaseAuth.getInstance().getCurrentUser().delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            // Account deleted successfully
                                            Toast.makeText(ProfileActivity.this, "Fiók sikeresen törölve", Toast.LENGTH_SHORT).show();
                                            navigateToHome();
                                        } else {
                                            // Failed to delete account
                                            Toast.makeText(ProfileActivity.this, "A fiók törlése sikertelen", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });

                // Set the negative button
                builder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Dismiss the dialog
                        dialog.dismiss();
                    }
                });

                // Show the AlertDialog
                builder.show();
            }
        });

    }
    public void navigateToHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void navigateToWebshop(){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
    public void navigateBack(View view){
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
    }
}

