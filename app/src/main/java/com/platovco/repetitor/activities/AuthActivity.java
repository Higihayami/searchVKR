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

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class AuthActivity extends AppCompatActivity {

    Button btnSignIn;
    LinearLayout llRegistration;
    private EditText etMail;
    private EditText etPassword;
    private TextView tvForgetPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
    }

    private void init() {
        btnSignIn = findViewById(R.id.singInBTN);
        etMail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        llRegistration = findViewById(R.id.ll_reg);
        initListeners();
    }

    private void initListeners() {
        mAuth = FirebaseAuth.getInstance();
        llRegistration.setOnClickListener(view ->
        {
            Intent intent = new Intent(AuthActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
        btnSignIn.setOnClickListener(view -> {
            if (etMail.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(etMail.getText().toString().trim(), etPassword.getText().toString().trim())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("TAG", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "signInWithEmail:failure", task.getException());
                                Toast.makeText(AuthActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        });


    }
}