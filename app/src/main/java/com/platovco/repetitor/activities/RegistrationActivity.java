package com.platovco.repetitor.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.platovco.repetitor.R;
import com.platovco.repetitor.managers.AppwriteClient;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.Session;
import io.appwrite.models.User;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import io.appwrite.services.Account;

public class RegistrationActivity extends AppCompatActivity {

    Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;
    private LinearLayout llSignIn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPassword2 = findViewById(R.id.et_password2);
        btnRegister = findViewById(R.id.regBTN);
        llSignIn = findViewById(R.id.ll_sign);
        initListener();
    }

    private void initListener() {
        mAuth = FirebaseAuth.getInstance();

        btnRegister.setOnClickListener(view -> {
            if (etEmail.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isEmailValid(etEmail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Введите корректную почту", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword.getText().toString().length() < 8) {
                Toast.makeText(getApplicationContext(), "В пароле должно быть 8 и более символов", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword2.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!etPassword.getText().toString().equals(etPassword2.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.d("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        llSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, AuthActivity.class);
            startActivity(intent);
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}