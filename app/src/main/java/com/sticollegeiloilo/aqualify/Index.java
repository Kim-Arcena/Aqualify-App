package com.sticollegeiloilo.aqualify;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.sanojpunchihewa.glowbutton.GlowButton;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Index extends AppCompatActivity {
    int minutes;
    int seconds;
    Toolbar toolbar1;
    ImageView mainimg, stalactites;
    TextView title, timertext, tViewMsg;
    TextView username;
    Animation FadeIn, UpToDown, DownToUp, DownToUpSpecial;
    LottieAnimationView oceanbkg, seagrass;
    FirebaseAuth fAuth;
    DatabaseReference reference, reference1;
    CountDownTimer timer;
    FirebaseFirestore fStore;
    Integer progressNum = new Random().nextInt();
    String progressID = Integer.toString(progressNum);
    String userId;
    String storTimer, score;
    String  trackdone= "notemptyempty";
    TextView usernamel;
    AnimatedVectorDrawable mMenuDrawable;
    AnimatedVectorDrawable mBackDrawable;
    boolean mMenuFlag;
    ImageView decoy;


    @Override
    protected void onPause() {
        super.onPause();
        if(trackdone == "Ticking"){
            timer.cancel();
            reference1 = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Pfailed");
            reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    dataSnapshot.getRef().child("Pfailed"+ progressID).child("Pfailed").setValue(storTimer);
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            Toast.makeText(Index.this, "OH SNAP YOU THREW A TRASH", Toast.LENGTH_LONG).show();
        }
    }



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

        AlertDialog dialog = new AlertDialog.Builder(Index.this, R.style.AlertDialogStyle).setTitle("Aqualify!").setMessage("\nAre you sure you want to exit?")
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
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_index);
        final SeekBar seekbar = (SeekBar) findViewById(R.id.seekBarGauge);
        final GlowButton bkgbutton = findViewById(R.id.bkgbutton);
        final Button buttonStart = findViewById(R.id.buttonG);
        mMenuDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_animatable);
        mBackDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_back_animatable);
        decoy = findViewById(R.id.decoy);
        timertext = (TextView) findViewById(R.id.timer);
        toolbar1 = findViewById(R.id.toolbar);
        toolbar1.setTitle("lol");
        stalactites = (ImageView) findViewById(R.id.stalactites);
        mainimg = (ImageView) findViewById(R.id.mainimage);
        title = findViewById(R.id.textView2);
        oceanbkg = findViewById(R.id.oceanbkg);

        seagrass = findViewById(R.id.seagrass);

        oceanbkg.enableMergePathsForKitKatAndAbove(true);
        seagrass.enableMergePathsForKitKatAndAbove(true);

        UpToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        FadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        DownToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        DownToUpSpecial = AnimationUtils.loadAnimation(this, R.anim.downtoupspecial);
        mainimg.startAnimation(DownToUpSpecial);
        title.startAnimation(FadeIn);
        stalactites.startAnimation(DownToUp);
        oceanbkg.startAnimation(UpToDown);
        seagrass.startAnimation(UpToDown);


//nav
DragStateListener dragStateListener = new DragStateListener() {
            @Override
            public void onDragStart() {
                if (mMenuFlag) {
                    decoy.setImageDrawable(mBackDrawable);
                    mBackDrawable.start();
                } else {
                    decoy.setImageDrawable(mMenuDrawable);
                    mMenuDrawable.start();
                }
                mMenuFlag = !mMenuFlag;
            }

            @Override
            public void onDragEnd(boolean isMenuOpened) {
                if (mMenuFlag) {
                    decoy.setImageDrawable(mBackDrawable);
                    mBackDrawable.start();
                } else {
                    decoy.setImageDrawable(mMenuDrawable);
                    mMenuDrawable.start();
                }
                mMenuFlag = !mMenuFlag;
            }
        };
        new SlidingRootNavBuilder(this).addDragStateListener(dragStateListener)
                .withToolbarMenuToggle(toolbar1)
                .withMenuLayout(R.layout.activity_menu_drawer)
                .withContentClickableWhenMenuOpened(true)
                .inject();
        username = findViewById(R.id.usernamedisplay);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                String nulll = "errorN";
                if(documentSnapshot != null) {
                    username.setText(documentSnapshot.getString("fName"));
                }else {
                    username.setText(nulll);
                }
            }
        });
        TextView logout=findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        TextView index = findViewById(R.id.homet);
        index.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Index.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finishAndRemoveTask();
            }
        });
        TextView memo=findViewById(R.id.memoBtn);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),NotesActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finishAndRemoveTask();

            }
        });
        TextView Progress = findViewById(R.id.progressBtn);
        Progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ProgressActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finishAndRemoveTask();
            }
        });
        TextView About = (TextView)  findViewById(R.id.aboutBTN);
        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AboutApp.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

            }
        });

                //PATIENCES SOLVER
        final TextView patience = findViewById(R.id.patience);
        final TextView levelname = findViewById(R.id.levelname);

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Score");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double sum= 0;
                DecimalFormat df = new DecimalFormat("#.##");

                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object scor = map.get("Score");
                    double pValue = Double.parseDouble(String.valueOf(scor));
                    double patienceV = sum+= pValue;
                    String patienceval = df.format(patienceV) ;

                    patience.setText("Patience: " + patienceval);

                    if(patienceV < 60){
                        levelname.setText("Aristotle");
                    }else if (patienceV>=60&& patienceV<120){
                        levelname.setText("Plato");

                    }else if(patienceV>=120&& patienceV<300){
                        levelname.setText("Socrates");

                    }else if(patienceV>=300&& patienceV<310){
                        levelname.setText("Saint");

                    }else if(patienceV>=310&& patienceV<320){
                        levelname.setText("Saint I");

                    }else if(patienceV>=320&& patienceV<330){
                        levelname.setText("Saint II");

                    }else if(patienceV>=330&& patienceV<340){
                        levelname.setText("Saint III");

                    }else if(patienceV>=340&& patienceV<350){
                        levelname.setText("Saint IV");

                    }else if(patienceV>=350&& patienceV<360){
                        levelname.setText("Saint V");

                    }else if(patienceV>=360&& patienceV<370){
                        levelname.setText("Saint VI");

                    }else if(patienceV>=370&& patienceV<380){
                        levelname.setText("Saint VII");

                    }else if(patienceV>=380&& patienceV<390){
                        levelname.setText("Saint VIII");

                    }else if(patienceV>=390&& patienceV<400){
                        levelname.setText("Saint VIV");

                    }else if(patienceV>=400&& patienceV<410){
                        levelname.setText("Saint IX");

                    }else if(patienceV>=410&& patienceV<420){
                        levelname.setText("Saint X");

                    }else if(patienceV>=420&& patienceV<540){
                        levelname.setText("Saint XI");

                    }else if(patienceV>=540&& patienceV<1440){
                        levelname.setText("Saint XII");

                    }else if(patienceV>=1440&& patienceV<43800) {
                        levelname.setText("Matt\nDajer");
                    }else if(patienceV>=43800){
                        levelname.setText("Mohandas\nGandhi");
                    }


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//navends
        seekbar.setMax(3600);
        seekbar.setProgress(0);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                minutes = progress / 60;
                seconds = progress - minutes * 60;
                timertext.setText(String.format("%d:%02d", minutes, seconds));
            }
        });
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storTimer = timertext.getText().toString();
                if ((minutes == 0) && (seconds == 0)) {
                    Toast.makeText(Index.this, "Please move the seek bar to change the timer value!", Toast.LENGTH_LONG).show();
                } else {
                    if (minutes <= 4) {

                        AlertDialog dialog = new AlertDialog.Builder(Index.this, R.style.AlertDialogStyle).setTitle("Oh Snap!").setMessage("\nYou're cheating! Next time please \ntry more than 5 minutes!") .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
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
                    buttonStart.setClickable(false);
                    buttonStart.setVisibility(View.GONE);
                    bkgbutton.setVisibility(View.GONE);
                    seekbar.setVisibility(View.GONE);
                    title.setText("DONT LOOK AT ME WAIT FOR\nME TO CLEAN THE OCEAN!");

                    mainimg.setImageResource(R.drawable.bkgaquariumdead);
                    mainimg.startAnimation(FadeIn);
                    Toast.makeText(Index.this, "The ocean is dirty please stay for a while to clean!", Toast.LENGTH_LONG).show();
                     timer = new CountDownTimer(minutes * 60000 + seconds * 1000, 1000) { // adjust the milli seconds here
                        @SuppressLint({"DefaultLocale", "SetTextI18n"})
                        public void onTick(long millisUntilFinished) {
                            trackdone="Ticking";
                            timertext.setText(String.format("%d:%02d",
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                        }

                        public void onFinish() {
                            seekbar.setProgress(0);
                            buttonStart.setClickable(true);
                            buttonStart.setVisibility(View.VISIBLE);
                            seekbar.setVisibility(View.VISIBLE);
                            bkgbutton.setVisibility(View.VISIBLE);
                            mainimg.setImageResource(R.drawable.bkgaquarium);
                            mainimg.startAnimation(FadeIn);
                            title.setText("You have to stay focused \nfor atleast 30 mins today!");
                            //uploading to db
                            trackdone="Uploading!";

                           score = storTimer.replace(':' ,'.');
                            reference = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    dataSnapshot.getRef().child("Progress").child("Progress"+ progressID).child("Progress").setValue(storTimer);
                                    dataSnapshot.getRef().child("Score").child("Score"+ progressID).child("Score").setValue(score);

                                    trackdone="Done";


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {


                                }


                            });



                            Toast.makeText(Index.this, "GOOD GREAT YOU CLEANED THE OCEAN!", Toast.LENGTH_LONG).show();
                        }
                    }.start();
                }
            }
        });
    }

}
  /*      decoy.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {

                if (mMenuFlag) {
                    close();
                    decoy.setImageDrawable(mBackDrawable);
                    mBackDrawable.start();

                } else {
                    open();
                    decoy.setImageDrawable(mMenuDrawable);
                    mMenuDrawable.start();

                }

                mMenuFlag = !mMenuFlag;

            }
        });
    }
    public void close(){
        new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar1)
                .withMenuOpened(false)
                .withMenuLayout(R.layout.activity_menu_drawer)
                .inject().closeMenu(true);
    }
    public void open(){
        new SlidingRootNavBuilder(this)
                 .withToolbarMenuToggle(toolbar1)
                 .withMenuOpened(true)
                .withMenuLayout(R.layout.activity_menu_drawer)
                .inject().openMenu(true);
    }



    }

*/


//        final Runnable runnable = new Runnable() {
//            public void run() {
//
//                title.startAnimation(FadeIn);
//                title.setText("Back off from your\nphone and be productive!");
//                if (count++ < 5) {
//                    handler.postDelayed(this, 5000);
//                }
//            }
//        };
//
//// trigger first time
//        handler.post(runnable);

