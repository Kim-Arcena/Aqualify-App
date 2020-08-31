package com.sticollegeiloilo.aqualify;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.sanojpunchihewa.glowbutton.GlowButton;

public class MainActivity extends AppCompatActivity {
    Animation fade_in, fade_out;
    ViewFlipper viewFlipper;
    TextView reg;
    EditText mPassword, mEmail;
    FirebaseAuth fAuth;
    Boolean isFirstRun;
    @Override
    protected void onStart() {
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onStart();
    }


    @Override
    public void onBackPressed() {
/*        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?").setTitle("Aqualify")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(1);
                        finish();
                        finishActivity(1);
                        Index.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.cancel();
                    }
                })
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        TextView lel = (TextView) alertDialog.getWindow().findViewById(android.R.id.message);
        TextView leel = (TextView) alertDialog.getWindow().findViewById(android.R.id.button1);
        TextView leeeel = (TextView) alertDialog.getWindow().findViewById(android.R.id.button2);
        TextView leeel = (TextView) alertDialog.getWindow().findViewById(android.R.id.title);
        Typeface face=Typeface.createFromAsset(getAssets(),"font/bahnschrift.ttf");
        lel.setTypeface(face);
        leel.setTypeface(face);
        leeel.setTypeface(face);
        leeeel.setTypeface(face);*/

        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogStyle).setTitle("Aqualify!").setMessage("\nAre you sure you want to exit?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(R.drawable.bkgaquarium).show();
        TextView lel = (TextView) dialog.getWindow().findViewById(android.R.id.message);
        TextView leel = (TextView) dialog.getWindow().findViewById(android.R.id.button1);
        TextView leeel = (TextView) dialog.getWindow().findViewById(android.R.id.title);
        Typeface face=Typeface.createFromAsset(getAssets(),"font/bahnschrift.ttf");
        lel.setTypeface(face);
        leel.setTypeface(face);
        leel.setTypeface(face);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView forgotTextLink = findViewById(R.id.forgetpasstxt);

        ImageView introanim = (ImageView) findViewById(R.id.logoindex);
        LottieAnimationView oceanbg = findViewById(R.id.oceanbkg);
         mEmail = findViewById(R.id.editText);
         mPassword = findViewById(R.id.editText2);
        final Button mLoginBtn= findViewById(R.id.button);
        TextView copyright = findViewById(R.id.copyright);
        ViewFlipper logoindex = findViewById(R.id.backgroundViewFlipper1);
        GlowButton buttonglo = findViewById(R.id.btnglo);

        fAuth = FirebaseAuth.getInstance();

        Animation UpToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        Animation DownToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        Animation fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeout = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        logoindex.startAnimation(fadein);
        oceanbg.startAnimation(fadein);
        mEmail.startAnimation(fadein);
        mPassword.startAnimation(fadein);
        //btn.startAnimation(fadein);
        mLoginBtn.startAnimation(fadein);
        copyright.startAnimation(UpToDown);
        buttonglo.startAnimation(fadein);

        reg = findViewById(R.id.textRegister);
        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });




        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required.");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password must be at least 6 characters.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            mLoginBtn.setClickable(false);
                            Toast.makeText(MainActivity.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(), Index.class);
                            startActivity(i);
                            finish();
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        } else {
                            mLoginBtn.setClickable(true);
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ? ");
                passwordResetDialog.setMessage("Enter Your Email To Received Reset Link. ");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error ! Reset Link Is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });
        isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show start activity
            startActivity(new Intent(MainActivity.this, AboutApp.class));
            Toast.makeText(MainActivity.this, "Welcome to Aqualify", Toast.LENGTH_LONG)
                    .show();

        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
    }


}
