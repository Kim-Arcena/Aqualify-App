package com.sticollegeiloilo.aqualify;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class NotesActivity extends AppCompatActivity {
    FirebaseAuth fAuth;
    DatabaseReference reference, reference01;
    RecyclerView recycl;
    ArrayList<Notes> list;
    TextView username;
    FirebaseFirestore fStore;
    String userId;
    Toolbar toolbar1;
    NotesAdapter notesAdapter;
    Button btnAddNew;
    Animation UpToDown, FadeIn, DownToUp, DownToUpSpecial;
    AnimatedVectorDrawable mMenuDrawable;
    AnimatedVectorDrawable mBackDrawable;
    boolean mMenuFlag;
    ImageView decoy;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), Index.class) ;
        startActivity(i);
        super.onBackPressed();
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
        setContentView(R.layout.activity_notes);

        UpToDown = AnimationUtils.loadAnimation(this, R.anim.uptodown);
        FadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        DownToUp = AnimationUtils.loadAnimation(this, R.anim.downtoup);
        DownToUpSpecial = AnimationUtils.loadAnimation(this, R.anim.downtoupspecial);
        mMenuDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_menu_animatable);
        mBackDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_back_animatable);
        decoy = findViewById(R.id.decoy);
        btnAddNew = findViewById(R.id.btnaddnew);
        recycl = findViewById(R.id.recyclev);
        recycl.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Notes>();
        recycl.startAnimation(FadeIn);
        fAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.usernamedisplay);
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
/*        final String noteKey = getIntent().getStringExtra("noteId");


        final ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView note, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reference01 = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Aqualify").child("Notes" + noteKey);
                reference01.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to Delete", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        };*/

        reference = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("Aqualify");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Notes p = dataSnapshot1.getValue(Notes.class);
                    list.add(p);
                }
                notesAdapter = new NotesAdapter(NotesActivity.this, list);
                recycl.setAdapter(notesAdapter);
               // new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recycl);
                notesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Failed to load Data due to slow internet connection", Toast.LENGTH_LONG).show();
            }
        });

        btnAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NotesActivity.this, NewNotesActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        //nav
        toolbar1 = findViewById(R.id.toolbar);
        toolbar1.setTitle("lol");
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
                if (documentSnapshot != null) {
                    username.setText(documentSnapshot.getString("fName"));
                } else {
                    username.setText(nulll);
                }
            }
        });
        TextView logout = findViewById(R.id.logoutBtn);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
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
                Intent i = new Intent(getApplicationContext(), Index.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        TextView memo = findViewById(R.id.memoBtn);
        memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), NotesActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });


        TextView Progress = findViewById(R.id.progressBtn);
        Progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProgressActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
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

    }


}
