package com.example.justcook.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.justcook.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail,etPassword;
    private String email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.email_editText_login);
        etPassword = findViewById(R.id.password_editText_login);
        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.login_button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                Log.d("###MainActivit", "Email: " + email);
                Log.d("###MainActivit", "Password: " + password);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Fill the fields first!", Toast.LENGTH_SHORT).show();
                } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("###Login", "loginWithEmail: success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    verifyingUser(user);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(LoginActivity.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this, "Authentication failed: "
                                        + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }
            }
        });
    }

    private void verifyingUser(FirebaseUser user) {
        if (user != null) {
            if (user.isEmailVerified()) {
                Log.d("###UserEmail","EmailVerified");
            } else {
                Log.d("###UserEmail","EmailNOTVerified");
            }
        } else {
            Log.d("###NoUser","Incorrect User");
        }
    }

    public void tmpMainActivityRedirect(View view) {
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

     }

    public void navigationBackToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
