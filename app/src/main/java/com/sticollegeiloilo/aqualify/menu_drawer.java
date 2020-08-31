package com.sticollegeiloilo.aqualify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class menu_drawer extends AppCompatActivity{
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    LottieAnimationView lot1,lot2,lot3;
    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_drawer);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        lot1 = findViewById(R.id.lot1);
        lot1.enableMergePathsForKitKatAndAbove(true);

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));

    }
}
