package com.sticollegeiloilo.aqualify;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText mUsername, mEmail, mPassword;
    Button mRegisterBtn, mLoginBtn;
    FirebaseFirestore mFstore;
    String userID;
    private static final String TAG ="SignupActivity";
    FirebaseAuth fAuth;


    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mUsername = findViewById(R.id.editText);
        mEmail  = findViewById(R.id.email);
        mPassword = findViewById(R.id.editText2);
        mRegisterBtn = findViewById(R.id.button);

        fAuth = FirebaseAuth.getInstance();

        mFstore = FirebaseFirestore.getInstance();
        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String name = mUsername.getText().toString();
                mRegisterBtn.setClickable(false);

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    mRegisterBtn.setClickable(true);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    mRegisterBtn.setClickable(true);
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters.");
                    mRegisterBtn.setClickable(true);
                    return;
                }


                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mRegisterBtn.setClickable(false);
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //database
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = mFstore.collection("users").document(userID);
                            Map<String,Object> users = new HashMap<>();
                            users.put("fName", name);
                            documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            }) .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());
                                }

                            });
                            startActivity(new Intent(getApplicationContext(), Index.class));
                        } else {
                            mRegisterBtn.setClickable(true);
                            Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
